<%@page contentType="text/html; charset=iso-8859-1" language="java" %>
<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.util.Utilities,
                 com.randr.webdw.importData.importProcessorAbstract.CsvLine" %>


<jsp:useBean id="dbErrorMsg" class="java.lang.String" scope="request"/>
<jsp:useBean id="importProcessorResult" class="com.randr.webdw.importData.importProcessorAbstract.ImportProcessorResult" scope="request"/>


<% try{ %>

     <html>
     <head>
     <title>Data Import - Execution results</title>
     <style>
         body, table {font-family:Arial; font-size:10pt;}
         h1 {font-size:12pt}
         table {border-collapse:collapse}
     </style>
     </head>
     <body>
     <form name="frmUpload" method=post action="import_data">
        <input type="hidden" name="formAction" value="DISPLAY_UPLOAD">
     </form>
<center>
<%   if (dbErrorMsg.equals("Invalid upload")) { %>
       <h1>Error</h1>
        Either the specified file does not exist on your hard drive or it does not have an apropriate file extension (.csv).
       <br><br>
       Please <a href="javascript:history.go(-1)">go back</a> and specify a valid file.
<%   } else  { %>
<%     if (importProcessorResult.isImportSuccessful()) { %>
        <h1>Operation successfull</h1>
         <b></b><br><br>

<%     } else if (importProcessorResult.getInvalidLines() != null) { %>
         <h1>Error</h1>
         <br>
            <%=importProcessorResult.getInvalidLines().size()%> lines could not be processed:
            <p>
            <% for (int i = 0; i < importProcessorResult.getInvalidLines().size(); i++){ %>
                <%CsvLine csvLine = (CsvLine) importProcessorResult.getInvalidLines().elementAt(i);%>
                <br><b>Line# <%=csvLine.getLineNumber().intValue()%></b> - Error:<i><%=csvLine.getErrorMessage()%></i>
            <%} %>
            </p>

<%     } else { %>
         <b>
         Errors occured while processing the file.<br>
         <i><%=Utilities.nullToBlank(importProcessorResult.getErrorMessage())%></i>
         <br>
         </b>
<%     } %>
        <br><br>
        <a href="javascript: document.frmUpload.submit()"><b>Click here</b></a> if you want to process other file.</b>
<%   }  %>
    </center>
     </body>
     </html>

<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();
   } finally {} %>