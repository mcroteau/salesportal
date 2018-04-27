package com.randr.webdw.upload;

import java.io.File;
import java.io.FilePermission;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.randr.webdw.util.DateUtilities;
import com.randr.webdw.util.Utilities;


/**
 */
public class UploadProcessor {
    protected MultipartRequest multi = null;
    protected String uploadTmpDir = null;

    /**
     * Constructor for UploadProcessor.
     * @param req HttpServletRequest
     * @param dir String
     * @throws Exception
     */
    public UploadProcessor(HttpServletRequest req, String dir) throws Exception {
        File fDir = new File(dir);
        FilePermission filePermission = null;

        if (!fDir.isDirectory()) {
            fDir.mkdir();

            filePermission = new FilePermission(dir, "read,write");
        }

        multi = new MultipartRequest(req, dir, 100 * 1024 * 1024, new com.oreilly.servlet.multipart.DefaultFileRenamePolicy());
        uploadTmpDir = dir;
    }

    /**
     * Method getMultipartRequest.
     * @return MultipartRequest
     */
    public MultipartRequest getMultipartRequest() {
        return multi;
    }

    /**
     * Method deleteUploadedFiles.
     */
    public void deleteUploadedFiles() {
        try {
            Enumeration files = multi.getFileNames();

            while (files.hasMoreElements()) {
                String name = (String) files.nextElement();
                File f = multi.getFile(name);

                if (f != null) {
                    f.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Method getUploadedFiles.
     * @param dir String
     * @return HashMap
     * @throws Exception
     */
    public HashMap getUploadedFiles(String dir) throws Exception {
        File fDir = new File(dir);
        FilePermission filePermission = null;

        if (!fDir.isDirectory()) {
            fDir.mkdir();

            filePermission = new FilePermission(dir, "read,write");
        }

        if (!fDir.isDirectory()) {
            throw new Exception("Cannot create directory " + dir);
        }

        HashMap vFile = new HashMap();
        Enumeration files = multi.getFileNames();

        while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            String filename = multi.getFilesystemName(name);

            if (filename == null) {
                vFile.put(name, null);
            } else {
                int extOffset = 0;
                String extension = null;

                if (extOffset > -1) {
                    extension = filename.substring(filename.lastIndexOf("."));
                } else {
                    extension = "";
                }

                File f = multi.getFile(name);
                String originalFileName = multi.getOriginalFileName(name);

                originalFileName = Utilities.replaceIlegalFileChars(originalFileName);

                if ((f != null) && (f.length() > 0)) {
                    String origFileNoExt = originalFileName.substring(0, originalFileName.length() - extension.length());
                    int countRename = 0;
                    boolean renamed = false;

                    do {
                        String underscoreCount = null;

                        if (countRename > 0) {
                            underscoreCount = "_" + String.valueOf(countRename);
                        } else {
                            underscoreCount = "";
                        }

                        filename = origFileNoExt + underscoreCount + extension;

                        File newFile = new File(dir + "/" + filename);

                        if (!newFile.isFile()) {
                            renamed = f.renameTo(new File(dir + "/" + filename));
                        }

                        countRename++;
                    } while (!renamed);

                    filePermission = new FilePermission(dir + "/" + filename, "read,write");

                    // vFile.add(filename);
                    vFile.put(name, filename);
                } else {

                    // vFile.add(null);
                    vFile.put(name, null);

                    if (f != null) {
                        f.delete();
                    }
                }
            }
        }

        return vFile;
    }

    /**
     * Method isValidUpload.
     * @return boolean
     */
    public boolean isValidUpload() {
        return isValidUpload(new Vector());
    }

    /**
     * Method isValidUpload.
     * @param vAcceptMissingFile Vector
     * @return boolean
     */
    public boolean isValidUpload(Vector vAcceptMissingFile) {
        Vector vAccept = new Vector(2);

        vAccept.add("gif");
        vAccept.add("jpg");

        return isValidUpload(vAccept, vAcceptMissingFile);
    }

    /**
     * Method isValidUpload.
     * @param vAccept Vector
     * @param vAcceptMissingFile Vector
     * @return boolean
     */
    public boolean isValidUpload(Vector vAccept, Vector vAcceptMissingFile) {
        HashMap hAcceptMissingFile = new HashMap();

        for (int i = 0; i < vAcceptMissingFile.size(); i++) {
            hAcceptMissingFile.put((String) vAcceptMissingFile.elementAt(i), "");
        }

        boolean accepted = false;
        int extOffset = 0;
        String extension = null;
        int index = 0;

        // int	    indexAcceptMissing = 0;
        Enumeration files = multi.getFileNames();

        while (files.hasMoreElements()) {
            String name = (String) files.nextElement();
            String filename = multi.getFilesystemName(name);

            if (filename == null) {
                if (hAcceptMissingFile.get(name) == null) {
                    return false;
                }
            } else {
                if (extOffset > -1) {
                    extension = filename.substring(filename.lastIndexOf("."));
                } else {
                    extension = "";
                }

                if (vAccept != null) {
                    accepted = false;
                    extOffset = filename.lastIndexOf(".");

                    for (int i = 0; i < vAccept.size(); i++) {
                        if (("." + (String) vAccept.elementAt(i)).toLowerCase().equals(extension.toLowerCase())) {
                            accepted = true;

                            break;
                        }
                    }
                }

                File f = multi.getFile(name);

                if ((f == null) || (!(accepted))) {
                    return false;
                }
            }

            index++;
        }

        return true;
    }

    /**
     * Method moveToTrash.
     * @param fileStr String
     */
    public static void moveToTrash(String fileStr) {
        FilePermission filePermission = null;
        File file = new File(fileStr);

        if (file.isFile()) {
            int offset = fileStr.lastIndexOf("/");

            if (offset == -1) {
                offset = fileStr.lastIndexOf("\\");
            }

            String dir = fileStr.substring(0, offset + 1);
            String fileName = fileStr.substring(offset + 1);
            String fileNameNoExt = fileName.substring(0, fileName.lastIndexOf("."));
            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
            File trashFolder = new File(dir + "deleted");

            if (!trashFolder.isDirectory()) {
                trashFolder.mkdir();

                filePermission = new FilePermission(dir + "deleted", "read,write");
            }

            String trashFileName = fileNameNoExt + "_" + DateUtilities.formatDate(new Date(), "dd_MMM_yyyy_HH_mm_ss_SSS") + "." + fileExt;

            file.renameTo(new File(dir + "deleted/" + trashFileName));
        }
    }

}
