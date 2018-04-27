<%@page contentType="text/html; charset=iso-8859-1" language="java" 
import="com.randr.webdw.AppSettings" %>

<% try{ 
     String graphicsCompletePath = AppSettings.getGraphicsCompleteWebPath(request); 
     String graphicsPath = AppSettings.getGraphicsPath(); %>
    <style>body {background-color: beige;}</style>
     <SCRIPT language="javascript">
     var graphicsPath = '<%=graphicsPath%>';
     var graphicsCompletePath = '<%=graphicsCompletePath%>';
     var isCommonElement = <%=request.getParameter("isCommonElement")%>;
     var fieldType = '<%=request.getParameter("fieldType")%>';
     </SCRIPT>
     <SCRIPT language="javascript" src="<%=AppSettings.getAppWebPath()%>scripts/configurations/editor.js">
     </SCRIPT>

     <SCRIPT language=javascript>
     var tbCE_doc_complet = 1;
     function tbCE_guardar() { 
       var cont = contingut_html('tbCE'); 
       document.tbCE_doc_html.tbCE_contingut_html.value = cont; 
       salvar_html(cont);
     } 
     </SCRIPT>

     <center>
     <b><%=request.getParameter("fieldName")%></b><br><br>
     </center>

     <TABLE bgColor=silver border=1 cellPadding=0 cellSpacing=2 align="center">
     <TBODY>
     <TR>
     <TD>
      <FORM action="" method=post name=tbCE_doc_html><INPUT 
      name=tbCE_contingut_html type=hidden>
      <TABLE border=0 cellPadding=0 cellSpacing=0 hspace="3">
        <TBODY>
        <TR>
          <TD vAlign=center>&nbsp;&nbsp;&nbsp; </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_nou','<%=graphicsPath%>html_editor/images/newdoc.gif'); nou_doc(); return false;" 
            onmouseout="canvi_imatge('tbCE_nou','<%=graphicsPath%>html_editor/images/newdoc.gif')" 
            onmouseover="canvi_imatge('tbCE_nou','<%=graphicsPath%>html_editor/images/newdoc_focus.gif');"><IMG 
            align=absMiddle alt="New document" border=0 name=tbCE_nou 
            src="<%=graphicsPath%>html_editor/images/newdoc.gif"></A> </TD>
          
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; cortar(); return false;" 
            onmouseout="canvi_imatge('tbCE_cort','<%=graphicsPath%>html_editor/images/cut.gif')" 
            onmouseover="canvi_imatge('tbCE_cort','<%=graphicsPath%>html_editor/images/cut_focus.gif');"><IMG 
            align=absMiddle alt=Cut border=0 name=tbCE_cort 
            src="<%=graphicsPath%>html_editor/images/cut.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; copiar(); return false;" 
            onmouseout="canvi_imatge('tbCE_cop','<%=graphicsPath%>html_editor/images/copy.gif')" 
            onmouseover="canvi_imatge('tbCE_cop','<%=graphicsPath%>html_editor/images/copy_focus.gif');"><IMG 
            align=absMiddle alt=Copy border=0 name=tbCE_cop 
            src="<%=graphicsPath%>html_editor/images/copy.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; pegar(); return false;" 
            onmouseout="canvi_imatge('tbCE_paster','<%=graphicsPath%>html_editor/images/paste.gif')" 
            onmouseover="canvi_imatge('tbCE_paster','<%=graphicsPath%>html_editor/images/paste_focus.gif');"><IMG 
            align=absMiddle alt=Paste border=0 name=tbCE_paster 
            src="<%=graphicsPath%>html_editor/images/paste.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; desfer(); return false;" 
            onmouseout="canvi_imatge('tbCE_ud','<%=graphicsPath%>html_editor/images/undo.gif')" 
            onmouseover="canvi_imatge('tbCE_ud','<%=graphicsPath%>html_editor/images/undo_focus.gif');"><IMG 
            align=absMiddle alt=Undo border=0 name=tbCE_ud 
            src="<%=graphicsPath%>html_editor/images/undo.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; refer(); return false;" 
            onmouseout="canvi_imatge('tbCE_rd','<%=graphicsPath%>html_editor/images/redo.gif')" 
            onmouseover="canvi_imatge('tbCE_rd','<%=graphicsPath%>html_editor/images/redo_focus.gif');"><IMG 
            align=absMiddle alt=Redo border=0 name=tbCE_rd 
            src="<%=graphicsPath%>html_editor/images/redo.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_fi','<%=graphicsPath%>html_editor/images/find.gif'); cercar(); return false;" 
            onmouseout="canvi_imatge('tbCE_fi','<%=graphicsPath%>html_editor/images/find.gif')" 
            onmouseover="canvi_imatge('tbCE_fi','<%=graphicsPath%>html_editor/images/find_focus.gif');"><IMG 
            align=absMiddle alt=Find border=0 name=tbCE_fi 
            src="<%=graphicsPath%>html_editor/images/find.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_lnk','<%=graphicsPath%>html_editor/images/link.gif'); fer_link(); return false;" 
            onmouseout="canvi_imatge('tbCE_lnk','<%=graphicsPath%>html_editor/images/link.gif')" 
            onmouseover="canvi_imatge('tbCE_lnk','<%=graphicsPath%>html_editor/images/link_focus.gif');"><IMG 
            align=absMiddle alt="Insert link" border=0 name=tbCE_lnk 
            src="<%=graphicsPath%>html_editor/images/link.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_image','<%=graphicsPath%>html_editor/images/image.gif'); ins_img(); return false;" 
            onmouseout="canvi_imatge('tbCE_image','<%=graphicsPath%>html_editor/images/image.gif')" 
            onmouseover="canvi_imatge('tbCE_image','<%=graphicsPath%>html_editor/images/image_focus.gif');"><IMG 
            align=absMiddle alt="Insert image" border=0 name=tbCE_image 
            src="<%=graphicsPath%>html_editor/images/image.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_dbgc','<%=graphicsPath%>html_editor/images/doc_bgcolor.gif');  mostra_pal_doc_bgcolor(); return false;" 
            onmouseout="canvi_imatge('tbCE_dbgc','<%=graphicsPath%>html_editor/images/doc_bgcolor.gif')" 
            onmouseover="canvi_imatge('tbCE_dbgc','<%=graphicsPath%>html_editor/images/doc_bgcolor_focus.gif');"><IMG 
            align=absMiddle alt="Document background´s colour" border=0 
            name=tbCE_dbgc 
            src="<%=graphicsPath%>html_editor/images/doc_bgcolor.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_table','<%=graphicsPath%>html_editor/images/instable.gif');  mostra_insert_table(); return false;" 
            onmouseout="canvi_imatge('tbCE_table','<%=graphicsPath%>html_editor/images/instable.gif')" 
            onmouseover="canvi_imatge('tbCE_table','<%=graphicsPath%>html_editor/images/instable_focus.gif');"><IMG 
            align=absMiddle alt="Insert table" border=0 name=tbCE_table 
            src="<%=graphicsPath%>html_editor/images/instable.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {insert_fila_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_ir','<%=graphicsPath%>html_editor/images/insrow.gif')" 
            onmouseover="canvi_imatge('tbCE_ir','<%=graphicsPath%>html_editor/images/insrow_focus.gif');"><IMG 
            align=absMiddle alt="Insert row" border=0 name=tbCE_ir 
            src="<%=graphicsPath%>html_editor/images/insrow.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {elim_fila_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_dr','<%=graphicsPath%>html_editor/images/delrow.gif')" 
            onmouseover="canvi_imatge('tbCE_dr','<%=graphicsPath%>html_editor/images/delrow_focus.gif');"><IMG 
            align=absMiddle alt="Delete row" border=0 name=tbCE_dr 
            src="<%=graphicsPath%>html_editor/images/delrow.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {insert_col_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_ic','<%=graphicsPath%>html_editor/images/inscol.gif')" 
            onmouseover="canvi_imatge('tbCE_ic','<%=graphicsPath%>html_editor/images/inscol_focus.gif');"><IMG 
            align=absMiddle alt="Insert column" border=0 name=tbCE_ic 
            src="<%=graphicsPath%>html_editor/images/inscol.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {elim_col_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_dc','<%=graphicsPath%>html_editor/images/delcol.gif')" 
            onmouseover="canvi_imatge('tbCE_dc','<%=graphicsPath%>html_editor/images/delcol_focus.gif');"><IMG 
            align=absMiddle alt="Delete column" border=0 name=tbCE_dc 
            src="<%=graphicsPath%>html_editor/images/delcol.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {insert_celda_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_ice','<%=graphicsPath%>html_editor/images/inscell.gif')" 
            onmouseover="canvi_imatge('tbCE_ice','<%=graphicsPath%>html_editor/images/inscell_focus.gif');"><IMG 
            align=absMiddle alt="Inser cell" border=0 name=tbCE_ice 
            src="<%=graphicsPath%>html_editor/images/inscell.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {elim_celda_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_dce','<%=graphicsPath%>html_editor/images/delcell.gif')" 
            onmouseover="canvi_imatge('tbCE_dce','<%=graphicsPath%>html_editor/images/delcell_focus.gif');"><IMG 
            align=absMiddle alt="Delete cell" border=0 name=tbCE_dce 
            src="<%=graphicsPath%>html_editor/images/delcell.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {combinar_celdas_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_cc','<%=graphicsPath%>html_editor/images/mrgcell.gif')" 
            onmouseover="canvi_imatge('tbCE_cc','<%=graphicsPath%>html_editor/images/mrgcell_focus.gif');"><IMG 
            align=absMiddle alt="Merge cells" border=0 name=tbCE_cc 
            src="<%=graphicsPath%>html_editor/images/mrgcell.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; try {split_celdas_table();} catch(e) { alert('Please select the table !')}; return false;" 
            onmouseout="canvi_imatge('tbCE_sc','<%=graphicsPath%>html_editor/images/spltcell.gif')" 
            onmouseover="canvi_imatge('tbCE_sc','<%=graphicsPath%>html_editor/images/spltcell_focus.gif');"><IMG 
            align=absMiddle alt="Split cells" border=0 name=tbCE_sc 
            src="<%=graphicsPath%>html_editor/images/spltcell.gif"></A>
        </TD></TR></TBODY></TABLE>
      <TABLE border=0 cellPadding=0 cellSpacing=0>
        <TBODY>
        <TR>
          <TD vAlign=center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </TD>
          <TD vAlign=center><SELECT language=javascript name=tbCE_FontName
            onchange="obj_editor=tbCE; return FontName_onchange(this)"
            style="WIDTH: 140px" title="Font Name"> <OPTION selected
              value=0>Sel. Font type<OPTION value=Verdana>Verdana<OPTION
              value=Arial>Arial<OPTION value=Tahoma>Tahoma<OPTION
              value="Courier New">Courier New<OPTION
              value="Times New Roman">Times New Roman<OPTION
              value=Wingdings>Wingdings</OPTION><OPTION value="Tahoma">Tahoma</SELECT> </TD>
          <TD vAlign=center><SELECT language=javascript name=tbCE_FontSize
            onchange="obj_editor = tbCE; return FontSize_onchange(this)"
            style="WIDTH: 40px" title="Font Size"> <OPTION selected
              value=0>Sel. Font size<OPTION value=1>1<OPTION value=2>2<OPTION
              value=3>3<OPTION value=4>4<OPTION value=5>5<OPTION
              value=6>6<OPTION value=7>7</OPTION></SELECT> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; negreta(); return false;" 
            onmouseout="canvi_imatge('tbCE_negr','<%=graphicsPath%>html_editor/images/bold.gif')" 
            onmouseover="canvi_imatge('tbCE_negr','<%=graphicsPath%>html_editor/images/bold_focus.gif');"><IMG 
            align=absMiddle alt=Bold border=0 name=tbCE_negr 
            src="<%=graphicsPath%>html_editor/images/bold.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; cursiva(); return false;" 
            onmouseout="canvi_imatge('tbCE_curs','<%=graphicsPath%>html_editor/images/italic.gif')" 
            onmouseover="canvi_imatge('tbCE_curs','<%=graphicsPath%>html_editor/images/italic_focus.gif');"><IMG 
            align=absMiddle alt=Italic border=0 name=tbCE_curs 
            src="<%=graphicsPath%>html_editor/images/italic.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; subry(); return false;" 
            onmouseout="canvi_imatge('tbCE_subr','<%=graphicsPath%>html_editor/images/under.gif')" 
            onmouseover="canvi_imatge('tbCE_subr','<%=graphicsPath%>html_editor/images//under_focus.gif');"><IMG 
            align=absMiddle alt=Underline border=0 name=tbCE_subr 
            src="<%=graphicsPath%>html_editor/images/under.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_fg','<%=graphicsPath%>html_editor/images/fgcolor.gif'); mostra_pal_fg_color(); return false;" 
            onmouseout="canvi_imatge('tbCE_fg','<%=graphicsPath%>html_editor/images/fgcolor.gif')" 
            onmouseover="canvi_imatge('tbCE_fg','<%=graphicsPath%>html_editor/images/fgcolor_focus.gif');"><IMG 
            align=absMiddle alt="Font colour" border=0 name=tbCE_fg 
            src="<%=graphicsPath%>html_editor/images/fgcolor.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; canvi_imatge('tbCE_bg','<%=graphicsPath%>html_editor/images/bgcolor.gif'); mostra_pal_bg_color(); return false;" 
            onmouseout="canvi_imatge('tbCE_bg','<%=graphicsPath%>html_editor/images/bgcolor.gif')" 
            onmouseover="canvi_imatge('tbCE_bg','<%=graphicsPath%>html_editor/images/bgcolor_focus.gif');"><IMG 
            align=absMiddle alt="Text background" border=0 name=tbCE_bg 
            src="<%=graphicsPath%>html_editor/images/bgcolor.gif" colour? 
            s></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; alin_esq(); return false;" 
            onmouseout="canvi_imatge('tbCE_ae','<%=graphicsPath%>html_editor/images/left.gif')" 
            onmouseover="canvi_imatge('tbCE_ae','<%=graphicsPath%>html_editor/images/left_focus.gif');"><IMG 
            align=absMiddle alt="Align to left" border=0 name=tbCE_ae 
            src="<%=graphicsPath%>html_editor/images/left.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; centrat(); return false;" 
            onmouseout="canvi_imatge('tbCE_center','<%=graphicsPath%>html_editor/images/center.gif')" 
            onmouseover="canvi_imatge('tbCE_center','<%=graphicsPath%>html_editor/images/center_focus.gif');"><IMG 
            align=absMiddle alt=Center border=0 name=tbCE_center 
            src="<%=graphicsPath%>html_editor/images/center.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; alin_dreta(); return false;" 
            onmouseout="canvi_imatge('tbCE_ad','<%=graphicsPath%>html_editor/images/right.gif')" 
            onmouseover="canvi_imatge('tbCE_ad','<%=graphicsPath%>html_editor/images/right_focus.gif');"><IMG 
            align=absMiddle alt="Align to right" border=0 name=tbCE_ad 
            src="<%=graphicsPath%>html_editor/images/right.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; llista_numerada(); return false;" 
            onmouseout="canvi_imatge('tbCE_nl','<%=graphicsPath%>html_editor/images/numlist.gif')" 
            onmouseover="canvi_imatge('tbCE_nl','<%=graphicsPath%>html_editor/images/numlist_focus.gif');"><IMG 
            align=absMiddle alt="Numbered list" border=0 name=tbCE_nl 
            src="<%=graphicsPath%>html_editor/images/numlist.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; llista_no_num(); return false;" 
            onmouseout="canvi_imatge('tbCE_ul','<%=graphicsPath%>html_editor/images/bullist.gif')" 
            onmouseover="canvi_imatge('tbCE_ul','<%=graphicsPath%>html_editor/images/bullist_focus.gif');"><IMG 
            align=absMiddle alt="Bulleted list" border=0 name=tbCE_ul 
            src="<%=graphicsPath%>html_editor/images/bullist.gif"></A> </TD>
          <TD vAlign=center><IMG align=absMiddle border=0 
            src="<%=graphicsPath%>html_editor/images/separator.gif"> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; deindentat(); return false;" 
            onmouseout="canvi_imatge('tbCE_deind','<%=graphicsPath%>html_editor/images/deindent.gif')" 
            onmouseover="canvi_imatge('tbCE_deind','<%=graphicsPath%>html_editor/images/deindent_focus.gif');"><IMG 
            align=absMiddle alt=Deindent border=0 name=tbCE_deind 
            src="<%=graphicsPath%>html_editor/images/deindent.gif"></A> </TD>
          <TD vAlign=center><A 
            href="./" 
            onclick="obj_editor=tbCE; indentat(); return false;" 
            onmouseout="canvi_imatge('tbCE_ind','<%=graphicsPath%>html_editor/images/inindent.gif')" 
            onmouseover="canvi_imatge('tbCE_ind','<%=graphicsPath%>html_editor/images/inindent_focus.gif');"><IMG 
            align=absMiddle alt=Indent border=0 name=tbCE_ind 
            src="<%=graphicsPath%>html_editor/images/inindent.gif"></A> </TD>
          <TD>
          <!-- DEInsertTableParam Object -->
          <OBJECT classid=clsid:47B0DFC7-B7A3-11D1-ADC5-006008A5848C height=2
          id=ObjTableInfo width=2 VIEWASTEXT></OBJECT></TD></TR></TBODY></TABLE>

         <OBJECT classid=clsid:2D360201-FFF5-11D1-8D03-00A0C959BC0A height=400
            id=tbCE width=700 VIEWASTEXT><PARAM NAME="Scrollbars" VALUE="true"></OBJECT>
    </TD></TR></TBODY></TABLE>
    </FORM>

     <SCRIPT language=javascript>
     tbCE_timerID=setInterval("tbCE_inicial()",100);
     </SCRIPT>

     <center>

     <form name="frmConfig">
     <input type=hidden name="dfFile" value="">
     <input type=button value="Apply Changes" onclick="fOnClick();">
     <input type=button value="Cancel" onclick="window.close();">
     </form>
     </center>
<% } catch (Exception e) { %>
     Error: <b><%=e.getMessage()%></b>
<%   e.printStackTrace();  
   } finally {
   }
%>
