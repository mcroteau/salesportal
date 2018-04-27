/*--- formatted by Jindent 2.1, (www.c-lab.de/~jindent) ---*/

package com.randr.webdw.util;

import java.io.File;
import java.io.FilePermission;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Copydir;
import org.apache.tools.ant.taskdefs.Delete;
// import org.apache.xpath.operations.String;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 *
 * @version 1.0
 * @author randr
 */
public class FileHandle {

    /**
     * Method declaration
     *
     * @param fromDir
     * @param toDir
     * @throws java.io.IOException
     * @see
     */
    public static void copyDir(String fromDir, String toDir) throws java.io.IOException {
        copyDir(new File(fromDir), new File(toDir));
    }

    /**
     * Method declaration
     *
     * @param fromDir
     * @param toDir
     * @throws java.io.IOException
     * @see
     */
    public static void copyDir(File fromDir, File toDir) throws java.io.IOException {
        Copydir copyDir = new Copydir();

        copyDir.setSrc(fromDir);
        copyDir.setDest(toDir);
        copyDir.setTaskName("copydir");
        copyDir.setLocation(Location.UNKNOWN_LOCATION);
        copyDir.setProject(new Project());
        copyDir.execute();

        copyDir = null;
    }

    /**
     * Method declaration
     *
     * @param fromDir
     * @param toDir
     * @param includes
     * @param excludes
     * @throws java.io.IOException
     * @see
     */
    public static void copyDir(String fromDir, String toDir, String[] includes, String[] excludes) throws java.io.IOException {
        copyDir(new File(fromDir), new File(toDir), includes, excludes);
    }

    /**
     * Method declaration
     *
     * @param fromDir
     * @param toDir
     * @param includes
     * @param excludes
     * @throws java.io.IOException
     * @see
     */
    public static void copyDir(File fromDir, File toDir, String[] includes, String[] excludes) throws java.io.IOException {
        Copy copy = new org.apache.tools.ant.taskdefs.Copy();

        copy.setOverwrite(true);
        copy.setLocation(Location.UNKNOWN_LOCATION);
        copy.setTaskName("copyFile");
        copy.setProject(new Project());

        java.io.File file = null;
        java.io.File toFile = null;
        DirectoryScanner dirScan = new DirectoryScanner();

        dirScan.setBasedir(fromDir);
        dirScan.setIncludes(includes);
        dirScan.setExcludes(excludes);
        dirScan.scan();

        String[] files = dirScan.getIncludedFiles();

        for (int i = 0; i < files.length; i++) {
            file = new File(fromDir, files[i]);

            copy.setFile(file);

            toFile = new File(toDir, files[i]);

            copy.setTofile(toFile);

            // try {
            copy.execute();

            // } catch (Exception tmp) {
            // } finally {}
        }

        copy = null;
        dirScan = null;
    }

    /**
     * Method declaration
     *
     * @param fromFile
     * @param toFile
     * @throws java.io.IOException
     * @see
     */
    public static void copyFile(String fromFile, String toFile) throws java.io.IOException {
        copyFile(new File(fromFile), new File(toFile));
    }

    /**
     * Method declaration
     *
     * @param fromFile
     * @param toFile
     * @throws java.io.IOException
     * @see
     */
    public static void copyFile(File fromFile, File toFile) throws java.io.IOException {
        Copy copy = new org.apache.tools.ant.taskdefs.Copy();

        copy.setOverwrite(true);
        copy.setLocation(Location.UNKNOWN_LOCATION);
        copy.setTaskName("copyFile");
        copy.setProject(new Project());
        copy.setFile(fromFile);
        copy.setTofile(toFile);
        copy.execute();

        copy = null;
    }

    /**
     * Method declaration
     *
     * @param fromFile
     * @param toDir
     * @throws java.io.IOException
     * @see
     */
    public static void copyFileToDir(String fromFile, String toDir) throws java.io.IOException {
        copyFileToDir(new File(fromFile), new File(toDir));
    }

    /**
     * Method declaration
     *
     * @param fromFile
     * @param toDir
     * @throws java.io.IOException
     * @see
     */
    public static void copyFileToDir(File fromFile, File toDir) throws java.io.IOException {
        Copy copy = new org.apache.tools.ant.taskdefs.Copy();

        copy.setOverwrite(true);
        copy.setLocation(Location.UNKNOWN_LOCATION);
        copy.setTaskName("copyFile");
        copy.setProject(new Project());
        copy.setFile(fromFile);
        copy.setTodir(toDir);
        copy.execute();

        copy = null;
    }

    /**
     * Method declaration
     *
     * @param file
     * @throws java.io.IOException
     * @see
     */
    public static void deleteFile(String file) throws java.io.IOException {
        deleteFile(new File(file));
    }

    /**
     * Method declaration
     *
     * @param file
     * @throws java.io.IOException
     * @see
     */
    public static void deleteFile(File file) throws java.io.IOException {
        Delete delete = new Delete();

        delete.setLocation(Location.UNKNOWN_LOCATION);
        delete.setTaskName("delete");
        delete.setProject(new Project());
        delete.setFile(file);
        delete.execute();

        delete = null;
    }

    /**
     * Method declaration
     *
     * @param dir
     * @throws java.io.IOException
     * @see
     */
    public static void deleteDir(String dir) throws java.io.IOException {
        deleteDir(new File(dir));
    }

    /**
     * Method declaration
     *
     * @param dir
     * @throws java.io.IOException
     * @see
     */
    public static void deleteDir(File dir) throws java.io.IOException {
        Delete delete = new Delete();

        delete.setIncludeEmptyDirs(true);
        delete.setLocation(Location.UNKNOWN_LOCATION);
        delete.setTaskName("deleteDir");
        delete.setProject(new Project());
        delete.setDir(dir);
        delete.execute();

        delete = null;
    }

    /**
     * Method declaration
     *
     * @param dir
     * @throws java.io.IOException
     * @see
     */
    public static void deleteDirFiles(String dir) throws java.io.IOException {
        deleteDirFiles(new File(dir));
    }

    /**
     * Method declaration
     *
     * @param dir
     * @throws java.io.IOException
     * @see
     */
    public static void deleteDirFiles(File dir) throws java.io.IOException {
        Delete delete = new Delete();

        delete.setLocation(Location.UNKNOWN_LOCATION);
        delete.setTaskName("deleteDir");
        delete.setProject(new Project());

        DirectoryScanner scanner = new DirectoryScanner();

        scanner.setBasedir(dir);
        scanner.scan();

        String[] files = scanner.getIncludedFiles();

        for (int i = 0; i < files.length; i++) {
            delete.setFile(new File(dir, files[i]));
            delete.execute();
        }

        delete = null;
        scanner = null;
    }

    /**
     * Method declaration
     *
     * @param dir
     * @return String[]
     * @throws java.io.IOException
     * @see
     */
    public static String[] getFiles(String dir) throws java.io.IOException {
        return getFiles(new File(dir), null, null);
    }

    /**
     * Method declaration
     *
     * @param dir
     * @return String[]
     * @throws java.io.IOException
     * @see
     */
    public static String[] getFiles(File dir) throws java.io.IOException {
        return getFiles(dir, null, null);
    }

    /**
     * Method declaration
     *
     * @param dir
     * @param includes
     * @param excludes
     * @return String[]
     * @throws java.io.IOException
     * @see
     */
    public static String[] getFiles(String dir, String[] includes, String[] excludes) throws java.io.IOException {
        return getFiles(new File(dir), includes, excludes);
    }

    /**
     * Method declaration
     *
     * @param dir
     * @param includes
     * @param excludes
     * @return String[]
     * @throws java.io.IOException
     * @see
     */
    public static String[] getFiles(File dir, String[] includes, String[] excludes) throws java.io.IOException {
        DirectoryScanner scanner = new DirectoryScanner();

        scanner.setBasedir(dir);
        scanner.setIncludes(includes);
        scanner.setExcludes(excludes);
        scanner.scan();

        String[] includedFiles = scanner.getIncludedFiles();

        scanner = null;

        return includedFiles;
    }

    /**
     * Method declaration
     *
     * @param args
     * @throws Exception
     * @see
     */
    public static void main(String[] args) throws Exception {

        // copy dir with includes and excludes
        // String[] includes = {
        // "**"
        // };
        // String[] excludes = {"*\\*deleted\\*"};
        //
        // FileHandle.copyDir("d:\\temp\\temp", "d:\\temp\\temp1\\temp1\\temp1",
        // includes, excludes);
        // copy all files and folders from a dir
        // FileHandle.copyDir("d:\\temp\\temp","d:\\temp\\temp1\\temp2");
        // copy one file to the specified dir and new file name
        // FileHandle.copyFile("d:\\temp\\temp\\temp.txt",
        // "d:\\temp\\temp1\\caca.txt");
        // copy one file to the specified folder
        // FileHandle.copyFileToDir("d:\\temp\\temp\\temp.txt","d:\\x");
        // delete a file
        // FileHandle.deleteFile("d:\\temp\\temp\\strutdemo.war");
        // delete a directory
        FileHandle.deleteDir("d:\\cd_files");

        // get the list of files from a folder, including their subdirectories (relative to parent)
        // String files[] = FileHandle.getFiles("d:\\temp\\temp");
        // for (int i=0; i<files.length;i++) {
        // }
        // get the list of files from a folder, with inlude and exclude rules...
        // (specify includes = null if you want all, specify excludes = null if you dont want to have exclude rules)
        // String[] includes = {"**test/**"};
        // String[] excludes = {"**WEB-INF/**"};
        // String files[] = FileHandle.getFiles("d:/temp/temp",includes, excludes);
        // for (int i=0; i<files.length;i++) {
        // }
    }

    /**
     * Method declaration
     *
     * @param dir
     * @see
     */
    public static void makeDir(String dir) {
        File directory = new File(dir);

        directory.mkdirs();
        new FilePermission(dir, "read,write");

        directory = null;
    }

    /**
     * Method declaration
     *
     * @param dir
     * @return boolean
     * @see
     */
    public static boolean isDir(String dir) {
        File directory = new File(dir);

        if (directory.isDirectory()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method declaration
     *
     * @param fromObject
     * @param toObject
     * @throws java.io.IOException
     * @see
     */
    private static void move(String fromObject, String toObject) throws java.io.IOException {
        File file = new File(fromObject);

        file.renameTo(new File(toObject));
    }

}


/*--- formatting done in "Sun Java Convention" style on 01-31-2003 ---*/

