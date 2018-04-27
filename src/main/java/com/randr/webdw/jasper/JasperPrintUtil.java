package com.randr.webdw.jasper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import com.randr.webdw.AppSettings;
import com.randr.webdw.util.FileHandle;

/**
 * @author randr
 * 
 */
public class JasperPrintUtil {
    private static boolean jasperCompileClasspathEstablished = false;
    
    public static void writePdfReport(HttpServletRequest request, HttpServletResponse response, String relativeReportSourcePath,
            Vector reportCollection) throws JRException, IOException {
		writePdfReport(request, response, relativeReportSourcePath, reportCollection, new HashMap());
	}
    
    public static void writePdfReport(HttpServletRequest request, HttpServletResponse response, String relativeReportSourcePath,
            Vector reportCollection, HashMap reportParametersMap) throws JRException, IOException {
    	
    	if(!AppSettings.isLoaded()) {
    		AppSettings.load(request.getSession().getServletContext());
        	AppSettings.establishAppWebPath(request);
    	}
    	
    	// compile source (.xml file) to obtain a .jasper file, but only if necessary
		String reportSourcePath = AppSettings.getAppRealPath() + AppSettings.getParm("JASPER_REPORTS_ROOT") +
		"/" + relativeReportSourcePath;
		String compiledReportPath = reportSourcePath.substring(0, reportSourcePath.length() - 6) + ".jasper";
		File reportSourceFile = new File(reportSourcePath);
		File compiledReportFile = new File(compiledReportPath);
		
		if (!compiledReportFile.exists() || compiledReportFile.lastModified() < reportSourceFile.lastModified()) {
		establishJasperCompileClassPath();
		JasperManager.compileReportToFile(reportSourcePath, compiledReportPath);
		}
		
		// obtain instance of JasperReportDataContainer
		JasperReport jasperReport = JasperManager.loadReport(compiledReportPath);
		JasperReportDataContainer jasperReportDataContainer =
		new JasperReportDataContainer(reportCollection, jasperReport, reportParametersMap);
		response.setContentType("application/pdf");
		ServletOutputStream out = response.getOutputStream();
		
		try {
			
			out.write(JasperRunManager.runReportToPdf( jasperReportDataContainer.getJasperReport(), 
					   jasperReportDataContainer.getReportParametersMap(),  jasperReportDataContainer.getJRDataSource()));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    /**
     * Method establishJasperCompileClassPath.
     * @throws IOException
     */
    private static synchronized void establishJasperCompileClassPath() throws IOException {
        if (!jasperCompileClasspathEstablished) {
        	
            String appJarsFolder = AppSettings.getAppRealPath() + "WEB-INF/lib";
            String[] appJars = FileHandle.getFiles(appJarsFolder);
            StringBuffer jasperCompilePath = new StringBuffer();

            for (int i = 0; i < appJars.length; i++) {
                jasperCompilePath.append(appJarsFolder + "/" + appJars[i] +
                	System.getProperty("path.separator"));
            }

            System.setProperty("jasper.reports.compile.class.path", jasperCompilePath.toString());
            jasperCompileClasspathEstablished = true;
        }
    }
    
}
