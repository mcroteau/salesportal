// DHTML Editing Component Constants for JavaScript
DECMD_BOLD = 5000
DECMD_COPY = 5002
DECMD_CUT = 5003
DECMD_DELETE = 5004
DECMD_DELETECELLS = 5005
DECMD_DELETECOLS = 5006
DECMD_DELETEROWS = 5007
DECMD_FINDTEXT = 5008
DECMD_FONT = 5009
DECMD_GETBACKCOLOR = 5010
DECMD_GETBLOCKFMT = 5011
DECMD_GETBLOCKFMTNAMES = 5012
DECMD_GETFONTNAME = 5013
DECMD_GETFONTSIZE = 5014
DECMD_GETFORECOLOR = 5015
DECMD_HYPERLINK = 5016
DECMD_IMAGE = 5017
DECMD_INDENT = 5018
DECMD_INSERTCELL = 5019
DECMD_INSERTCOL = 5020
DECMD_INSERTROW = 5021
DECMD_INSERTTABLE = 5022
DECMD_ITALIC = 5023
DECMD_JUSTIFYCENTER = 5024
DECMD_JUSTIFYLEFT = 5025
DECMD_JUSTIFYRIGHT = 5026
DECMD_LOCK_ELEMENT = 5027
DECMD_MAKE_ABSOLUTE = 5028
DECMD_MERGECELLS = 5029
DECMD_ORDERLIST = 5030
DECMD_OUTDENT = 5031
DECMD_PASTE = 5032
DECMD_REDO = 5033
DECMD_REMOVEFORMAT = 5034
DECMD_SELECTALL = 5035
DECMD_SEND_BACKWARD = 5036
DECMD_BRING_FORWARD = 5037
DECMD_SEND_BELOW_TEXT = 5038
DECMD_BRING_ABOVE_TEXT = 5039
DECMD_SEND_TO_BACK = 5040
DECMD_BRING_TO_FRONT = 5041
DECMD_SETBACKCOLOR = 5042
DECMD_SETBLOCKFMT = 5043
DECMD_SETFONTNAME = 5044
DECMD_SETFONTSIZE = 5045
DECMD_SETFORECOLOR = 5046
DECMD_SPLITCELL = 5047
DECMD_UNDERLINE = 5048
DECMD_UNDO = 5049
DECMD_UNLINK = 5050
DECMD_UNORDERLIST = 5051
DECMD_PROPERTIES = 5052
OLECMDEXECOPT_DODEFAULT = 0
OLECMDEXECOPT_PROMPTUSER = 1
OLECMDEXECOPT_DONTPROMPTUSER = 2
DECMDF_NOTSUPPORTED = 0
DECMDF_DISABLED = 1
DECMDF_ENABLED = 3
DECMDF_LATCHED = 7
DECMDF_NINCHED = 11
DEAPPEARANCE_FLAT = 0
DEAPPEARANCE_3D = 1
OLE_TRISTATE_UNCHECKED = 0
OLE_TRISTATE_CHECKED = 1
OLE_TRISTATE_GRAY = 2
var obj_editor = 0 ;
function canvi_imatge(nom_img,graf){
    document.images[nom_img].src = graf;
    return true;
}
function MakeArray(n){
    this.length=n
    for(var j=1; j<=n; j++){
        this[n]=0
    }
    return this
}
function taula_colors(){
    var t=0,taco
    taco='<center><br><br><TABLE border=1 cellspacing=0 cellpadding=0>';
    while(t<140){
        if(t%16==0){
            if(t!=0){
                taco+='</tr>'
            }
            taco+='<tr>'
        }
        taco+='<TD bgcolor="'+colors[t]+'" ><a href=javascript:canvi("'+colors[t]+'"); ><img src="' + graphicsPath + 'html_editor/images/trans.gif" border=0 width=18 height=18 alt="'+colors[t]+'"></a></TD>';
        t++
    }
    taco+='</tr></TABLE></center>'
    return taco
}
function paleta_colors(ruta_funct){
    var pal_col, k, tc
    pal_col=window.open("","paleta_colors","screenX=80,screenY=80,width=360,height=250")
    pal_col.document.open()
    k=pal_col.document;
    k.writeln("<html><head><style> td,body { font-family:Arial; font-size:8pt; } </style> <script> function canvi(hexa) { "+ruta_funct+"(hexa); window.close(); }</"+"script></head><body bgcolor=white ><center>")
    k.writeln("<font color=black face=arial size=-1 ><b> Please select a color:</b></font>")
    tc=taula_colors()
    k.writeln(tc)
    k.writeln("</center></body></html>")
    k.close()
    pal_col.focus()
}
function contingut_html(nom_editor){
    var obj_ed = eval("document." + nom_editor);
    var cont = obj_ed.DocumentHTML;
    var texto = "" + cont
    var complet = eval(nom_editor + '_doc_complet');
    if(!complet){
        texto = strip_body(texto);
    }
    return texto
}
function posa_contingut_html(nom_editor,contingut){
    var obj_ed = eval("document." + nom_editor);
    obj_ed.DocumentHTML = contingut;
}
function strip_body(cont){
    var ini_cos = cont.search(/<BODY/i);
    if( ini_cos == -1 ){
        return cont;
    }
    var lon = cont.length
    var fi = false
    var prob = false
    var i = ini_cos + 5
    while( !fi ){
        car = cont.charAt(i);
        if( car == '>' ){
            ini_cos = i + 1
            fi = true
        }
        if( car == '"' || car == "'" ){
            fi_com = false
            i++
            if( i >= lon ){
                fi = true;
                prob = true;
                fi_com = true;
            }
            while( !fi_com ){
                car_aux = cont.charAt(i);
                if( car_aux == car ){
                    fi_com = true
                }
                else{
                    i++;
                }
                if( i >= lon ){
                    fi = true;
                    prob = true;
                    fi_com = true;
                }
            }
        }
        i++;
        if( i >= lon ){
            fi = true;
            prob = true;
        }
    }
    if( prob == true ){
        alert('Due to problems with the HTML code of the page it is not possible to execute this action.');
    }
    else{
        var fi_cos = cont.search(/<\/BODY/i);
        var aux = cont.substring(ini_cos,fi_cos)
        cont = aux
    }
    return cont;
}
function nou_doc(){
    if( confirm('If you open a new blank document you will loose the unsaved changes of the current document.\nAre you sure you want to continue?') && confirm('Are you sure you want to open a blank document and loose the unsaved changes of the current document?') ){
        obj_editor.NewDocument();
    }
}
function cortar(){
    obj_editor.ExecCommand(DECMD_CUT,OLECMDEXECOPT_DODEFAULT);
}
function copiar(){
    obj_editor.ExecCommand(DECMD_COPY,OLECMDEXECOPT_DODEFAULT);
}
function pegar(){
    obj_editor.ExecCommand(DECMD_PASTE,OLECMDEXECOPT_DODEFAULT);
}
function desfer(){
    obj_editor.ExecCommand(DECMD_UNDO,OLECMDEXECOPT_DODEFAULT);
}
function refer(){
    obj_editor.ExecCommand(DECMD_REDO,OLECMDEXECOPT_DODEFAULT);
}
function cercar(){
    obj_editor.ExecCommand(DECMD_FINDTEXT,OLECMDEXECOPT_PROMPTUSER);
}
function fer_link(){
    obj_editor.ExecCommand(DECMD_HYPERLINK,OLECMDEXECOPT_PROMPTUSER);
}
var pictureAdded=0;
function ins_img(){
    if (pictureAdded==0){
        www=window.open('upload?ActionType=5&PIC_TYPE=Picture','www','width=450,height=300,left=0,top=0,screenX=0,screenY=0,outerwidth=450,outerheight=300,toolbar=no,menubar=no');
        www.opener=self;
        www.focus();
    }
    else{
        obj_editor.ExecCommand(DECMD_IMAGE,OLECMDEXECOPT_DONTPROMPTUSER);
        pictureAdded=0;
        re=new RegExp('<img>','gi');
        document.tbCE.DocumentHTML = document.tbCE.DocumentHTML.replace(re,'<img src="' + graphicsCompletePath + document.frmConfig.dfFile.value + '">');
    }
}
function mostra_pal_doc_bgcolor(){
    paleta_colors('opener.doc_bgcolor_posa')
    return true;
}
function doc_bgcolor_posa(hexa_color){
    var content = obj_editor.DocumentHTML;
    var codi, noucodi, lon, loc, k, car, car_aux, a, codi_result
    codi = new String(content);
    lon = codi.length
    loc = codi.search(/<BODY/i);
    fi = false;
    prob = false;
    i = loc + 5;
    loc_bg = i
    loc_bg_fi = i
    while( !fi ){
        car = codi.charAt(i);
        if( car == '>' ){
            loc_bg = i
            loc_bg_fi = i
            fi = true
        }
        if( codi.substr(i,8) == 'BGCOLOR=' ){
            loc_bg = i + 8;
            while( codi.charAt(i) != ' ' ){
                i++;
            }
            loc_bg_fi = i;
            fi = true;
        }
        if( codi.substr(i,8) == 'bgcolor=' ){
            loc_bg = i + 8;
            fi = true;
        }
        if( car == '"' || car == "'" ){
            fi_com = false
            i++
            if( i >= lon ){
                fi = true;
                prob = true;
                fi_com = true;
            }
            while( !fi_com ){
                car_aux = codi.charAt(i);
                if( car_aux == car ){
                    fi_com = true
                }
                else{
                    i++;
                }
                if( i >= lon ){
                    fi = true;
                    prob = true;
                    fi_com = true;
                }
            }
        }
        i++;
        if( i >= lon ){
            fi = true;
            prob = true;
        }
    }
    if( prob == true ){
        alert('Due to problems with the HTML code of the page it is not possible to execute this action.');
    }
    else{
        codi_result = "" + codi.substring(0,loc_bg)
        if( loc_bg == loc_bg_fi ){
            codi_result += ' BGCOLOR="' + hexa_color + '" '
        }
        else{
            codi_result += '"' + hexa_color + '" '
        }
        codi_result += codi.substring(loc_bg_fi,lon);
        obj_editor.DocumentHTML = codi_result;
    }
    return true;
}
function mostra_insert_table(){
    var pVar = document.ObjTableInfo;
    var NR = pVar.NumRows;
    var NC = pVar.NumCols;
    var TA = pVar.TableAttrs;
    TA='border=1';
    var CA = pVar.CellAttrs;
    CA='';
    var funct = 'opener.inserta_table'
    var par_tab, k, tc
    par_tab=window.open("","param_tables","screenX=80,screenY=80,width=400,height=215")
    par_tab.document.open()
    k=par_tab.document
    k.writeln('<HTML><HEAD>')
    k.writeln('<STYLE TYPE="text/css">')
    k.writeln(" td,body { font-family:Arial; font-size:9pt; font-weight:bold; } ")
    k.writeln('</STYLE>')
    k.writeln("<script> function comprova_valors() { ")
    k.writeln(" var nf, nc, at, ac, tit, nerr=0 , avis")
    k.writeln(" avis = '\\nTable can´t be created due to:' ")
    k.writeln(" nf = document.info_table.NumRows.value")
    k.writeln(" nc = document.info_table.NumCols.value")
    k.writeln(" at = document.info_table.TableAttrs.value")
    k.writeln(" ac = document.info_table.CellAttrs.value")
    k.writeln(" tit = document.info_table.Caption.value")
    k.writeln(" if( nf != parseInt(nf) || nf < 0 ){ ")
    k.writeln(" nerr++")
    k.writeln(" avis += '\\n\\n-The number of rows must be a positive integer.'")
    k.writeln(" }")
    k.writeln(" if( nc != parseInt(nc) || nc < 0 ){ ")
    k.writeln(" nerr++")
    k.writeln(" avis += '\\n\\n-The number of columns must be a positive integer.'")
    k.writeln(" }")
    k.writeln(" if( nerr == 0){ ")
    k.writeln(" "+funct+"(nf,nc,at,ac,tit) ")
    k.writeln(" window.close(); ")
    k.writeln(" }")
    k.writeln(" else")
    k.writeln(" {")
    k.writeln(" alert(avis)")
    k.writeln(" }")
    k.writeln(" return true ")
    k.writeln(" }</"+"script>")
    k.writeln('</HEAD><BODY bgcolor=white ><center>')
    k.writeln('<form name=info_table onsubmit="comprova_valors();" >');
    k.writeln("<font color=black face=arial size=-1 ><b> Give values for the parameters and press OK:</b></font>")
    k.writeln('<TABLE CELLSPACING=10><TR><TD valign=absmiddle >Number of rows:&nbsp;&nbsp;&nbsp;<INPUT TYPE=TEXT SIZE=3 maxlength=2 NAME=NumRows value='+NR+' ></TD>')
    k.writeln('<TD valign=absmiddle >Number of columns:&nbsp;&nbsp;&nbsp;<INPUT TYPE=TEXT SIZE=3 maxlength=2 NAME=NumCols value='+NC+'></TD></TR>')
    k.writeln('<TR><TD>Table properties:</TD><TD valign=absmiddle ><INPUT TYPE=TEXT SIZE=20 NAME=TableAttrs maxlength=120 value='+TA+'></TD></TR>')
    k.writeln('<TR><TD>Cells properties:</TD><TD><INPUT TYPE=TEXT SIZE=20 NAME=CellAttrs value='+CA+'><INPUT TYPE=hidden NAME=Caption ></TD></TR>')
    k.writeln('</TABLE>')
    k.writeln('<TR><TD valign=absmiddle colspan=2 align=center ><INPUT TYPE=BUTTON NAME=OK VALUE=OK onclick="comprova_valors()" ></TD></TR></TABLE></form>')
    k.writeln('</center></BODY></HTML>')
    k.close()
    par_tab.focus()
    return true
}
function inserta_table(nf,nc,at,ac,tit){
    var pVar = document.ObjTableInfo;
    pVar.NumRows = nf;
    pVar.NumCols = nc;
    var re=/border *= *\"? *0 *\"?/gi;
    var count = fGetNrOfMatches(re,at);
    if (count != 0 ){
        at = fReplace (at, 'border *= *\"? *0 *\"?', ' style="border: 1px dashed" ');
        ac = ac + ' style="border: 1px dashed" ';
    }
    else if (at.toUpperCase().search('BORDER')==-1){
        at = ' style="border: 1px dashed" ' + at;
        ac = ac + ' style="border: 1px dashed" ';
    }
    pVar.TableAttrs = at;
    pVar.CellAttrs = ac;
    obj_editor.ExecCommand(DECMD_INSERTTABLE,OLECMDEXECOPT_DODEFAULT, pVar);
    return true;
}
function insert_fila_table(){
    obj_editor.ExecCommand(DECMD_INSERTROW,OLECMDEXECOPT_DODEFAULT);
}
function elim_fila_table(){
    obj_editor.ExecCommand(DECMD_DELETEROWS,OLECMDEXECOPT_DODEFAULT);
}
function insert_col_table(){
    obj_editor.ExecCommand(DECMD_INSERTCOL,OLECMDEXECOPT_DODEFAULT);
}
function elim_col_table(){
    obj_editor.ExecCommand(DECMD_DELETECOLS,OLECMDEXECOPT_DODEFAULT);
}
function insert_celda_table(){
    obj_editor.ExecCommand(DECMD_INSERTCELL,OLECMDEXECOPT_DODEFAULT);
}
function elim_celda_table(){
    obj_editor.ExecCommand(DECMD_DELETECELLS,OLECMDEXECOPT_DODEFAULT);
}
function combinar_celdas_table(){
    obj_editor.ExecCommand(DECMD_MERGECELLS,OLECMDEXECOPT_DODEFAULT);
}
function split_celdas_table(){
    obj_editor.ExecCommand(DECMD_SPLITCELL,OLECMDEXECOPT_DODEFAULT);
}
function FontName_onchange(sel_obj){
    var ty = sel_obj.options[sel_obj.selectedIndex].value;
    if( ty != 0 ){
        obj_editor.ExecCommand(DECMD_SETFONTNAME, OLECMDEXECOPT_DODEFAULT, ty);
    }
    sel_obj.options[0].selected = true
}
function FontSize_onchange(sel_obj){
    var sz = sel_obj.options[sel_obj.selectedIndex].value;
    if( sz != 0 ){
        obj_editor.ExecCommand(DECMD_SETFONTSIZE, OLECMDEXECOPT_DODEFAULT, sz);
    }
    sel_obj.options[0].selected = true
}
function negreta(){
    alert('negreta');
    obj_editor.ExecCommand(DECMD_BOLD,OLECMDEXECOPT_DODEFAULT);
    return false;
}
function cursiva(){
    obj_editor.ExecCommand(DECMD_ITALIC,OLECMDEXECOPT_DODEFAULT);
}
function subry(){
    obj_editor.ExecCommand(DECMD_UNDERLINE,OLECMDEXECOPT_DODEFAULT);
}
function openColorPicker(colorField,fieldType,fieldName){
    wColor = window.open('config?formAction=DISPLAY_UPDATE_COLOR&initialColor='+colorField+'&fieldType='+fieldType+'&fieldName='+fieldName,'wColor','width=500,height=350,left=0,top=0,screenX=0,screenY=0,outerwidth=500,outerheight=350,toolbar=no,menubar=no');
    wColor.opener=self;
    wColor.focus();
};
function mostra_pal_fg_color(){
    currentColor= obj_editor.ExecCommand(DECMD_GETFORECOLOR,OLECMDEXECOPT_DODEFAULT);
    currentColor= currentColor.substring(1, currentColor.length);
    openColorPicker(currentColor,'fg_color','Text+color');
    return true;
}
function fg_color_posa(arr){
    if (arr != null){
        obj_editor.ExecCommand(DECMD_SETFORECOLOR,OLECMDEXECOPT_DODEFAULT, arr);
    }
}
function mostra_pal_bg_color(){
    currentColor= obj_editor.ExecCommand(DECMD_GETBACKCOLOR,OLECMDEXECOPT_DODEFAULT);
    currentColor= currentColor.substring(1, currentColor.length);
    openColorPicker(currentColor,'bg_color','Text+background');
    return true;
}
function bg_color_posa(arr){
    if (arr != null){
        obj_editor.ExecCommand(DECMD_SETBACKCOLOR,OLECMDEXECOPT_DODEFAULT, arr);
    }
}
function alin_dreta(){
    obj_editor.ExecCommand(DECMD_JUSTIFYRIGHT,OLECMDEXECOPT_DODEFAULT);
}
function centrat(){
    obj_editor.ExecCommand(DECMD_JUSTIFYCENTER,OLECMDEXECOPT_DODEFAULT);
}
function alin_esq(){
    obj_editor.ExecCommand(DECMD_JUSTIFYLEFT,OLECMDEXECOPT_DODEFAULT);
}
function llista_numerada(){
    obj_editor.ExecCommand(DECMD_ORDERLIST,OLECMDEXECOPT_DODEFAULT);
}
function llista_no_num(){
    obj_editor.ExecCommand(DECMD_UNORDERLIST,OLECMDEXECOPT_DODEFAULT);
}
function indentat(){
    obj_editor.ExecCommand(DECMD_INDENT,OLECMDEXECOPT_DODEFAULT);
}
function deindentat(){
    obj_editor.ExecCommand(DECMD_OUTDENT,OLECMDEXECOPT_DODEFAULT);
}
function fReplace(obj, text1, text2){
    re= new RegExp(text1,'gi');
    return obj.replace(re,text2);
}
function fGetNrOfMatches(re,str){
    var found = str.match(re);
    if (found == null) return 0;
    else return found.length;
};
function getEndTableOffset(startOffset, obj){
    var currentOffset = startOffset+1;
    var countBegin = 1;
    var countEnd =0;
    var nOffsetEndTable = -1;
    var nOffsetBeginTable = 0;
    while (nOffsetBeginTable>-1){
        nOffsetEndTable = obj.substring(currentOffset, obj.length).search('</TABLE');
        if (nOffsetEndTable>-1){
            nOffsetEndTable = nOffsetEndTable + currentOffset;
            nOffsetBeginTable = obj.substring(currentOffset, nOffsetEndTable).search('<TABLE');
            if (nOffsetBeginTable>-1){
                nOffsetBeginTable = nOffsetBeginTable + currentOffset;
            }
            currentOffset = nOffsetEndTable+6;
        }
        else return -1;
    }
    return nOffsetEndTable;
}
function getLastCharOffset(obj, char, startOffset){
    var i=0;
    if (obj.charAt(startOffset)!=char) return startOffset;
    for (i=startOffset+1; i<obj.length; i++){
        if (obj.charAt(i)!=char){
            return i-1;
        }
    }
    return obj.length-1;
}
function getPozitiveMin(offset){
    var min = Number.MAX_VALUE;
    var i=0;
    for (i=0; i<offset.length;i++){
        if ((offset[i]>=0) && (offset[i]<min)) min = offset[i];
    }
    if (min == Number.MAX_VALUE) min = -1;
    return min;
}
function getPozitiveMax(offset){
    var max = Number.MIN_VALUE;
    var i=0;
    for (i=0; i<offset.length;i++){
        if ((offset[i]>=0) && (offset[i]>max)) max = offset[i];
    }
    if (max == Number.MIN_VALUE) max = -1;
    return max;
}
function replaceTableStyleAttr(obj, attr){
    var newobj = obj;
    var nOffsetStyle = newobj.toUpperCase().search("STYLE");
    if (nOffsetStyle > -1){
        var nOffsetStyleAttrBegin = newobj.substring(nOffsetStyle, newobj.length).toUpperCase().search(attr+":");
        if (nOffsetStyleAttrBegin > -1){
            nOffsetStyleAttrBegin = nOffsetStyleAttrBegin + nOffsetStyle;
            var nOffsetStyleAttrValBegin = getLastCharOffset(newobj, ' ', nOffsetStyleAttrBegin+attr.length+1)+1;
            var offset = new Array(0,0);
            offset[0] = newobj.substring(nOffsetStyleAttrValBegin+1, newobj.length).toUpperCase().search(' ')-1;
            offset[1] = newobj.substring(nOffsetStyleAttrValBegin+1, newobj.length).toUpperCase().search('"')-1;
            var nOffsetStyleAttrValEnd = getPozitiveMin(offset);
            nOffsetStyleAttrValEnd = nOffsetStyleAttrValEnd + nOffsetStyleAttrValBegin+1;
            var attrVal = newobj.substring(nOffsetStyleAttrValBegin, nOffsetStyleAttrValEnd+1);
            if (attrVal.charAt(attrVal.length-1)==';') attrVal = attrVal.substring(0, attrVal.length-1);
            newobj = newobj.substring(0,nOffsetStyleAttrBegin) + newobj.substring(nOffsetStyleAttrValEnd+1, newobj.length);
            var nOffsetAttrBegin = newobj.substring(0, nOffsetStyle).toUpperCase().search(attr + " *=");
            if (nOffsetAttrBegin > -1){
                var nOffsetAttrValBegin = getLastCharOffset(newobj, ' ', nOffsetAttrBegin+attr.length);
                nOffsetAttrValBegin = getLastCharOffset(newobj, ' ', nOffsetAttrValBegin+1)+1;
                offset[0] = newobj.substring(nOffsetAttrValBegin+1, nOffsetStyle).toUpperCase().search(' ')-1;
                offset[1] = newobj.substring(nOffsetAttrValBegin+1, nOffsetStyle).toUpperCase().search('"')-1;
                var nOffsetAttrValEnd = getPozitiveMax(offset);
                nOffsetAttrValEnd = nOffsetAttrValEnd + nOffsetAttrValBegin+1;
                newobj = newobj.substring(0,nOffsetAttrValBegin) + '"' + attrVal + '"' + newobj.substring(nOffsetAttrValEnd+1, newobj.length);
            }
            else{
                newobj = ' ' + attr + ' = "' + attrVal + '"' + newobj;
            }
        }
    }
    return newobj;
}
function fReplaceTableBorder(obj, text1, text2){
    var i=0;
    var tmpStr='';
    var tmp1Str = '';
    var newobj = obj;
    var nOffset = 0;
    var nOffset1 = 0;
    var nOffset2 = 0;
    nOffset = newobj.search('<TABLE');
    while (nOffset>-1){
        nOffset1 = getEndTableOffset(nOffset, newobj);
        if (nOffset1>-1){
            nOffset2 = newobj.substring(nOffset+6,nOffset1).search('>') + nOffset +6;
            if (text1!=' *\r* *border *\r* *= *\r* *\"? *\r* *0 *\r* *\"? *\r* *'){
                newobj = newobj.substring(0,nOffset+6) + replaceTableStyleAttr(newobj.substring(nOffset+6,nOffset2), 'WIDTH') +
                newobj.substring(nOffset2, newobj.length);
                nOffset2 = newobj.substring(nOffset+6,nOffset1).search('>') + nOffset +6;
                newobj = newobj.substring(0,nOffset+6) + replaceTableStyleAttr(newobj.substring(nOffset+6,nOffset2), 'HEIGHT') +
                newobj.substring(nOffset2, newobj.length);
            }
            nOffset2 = newobj.substring(nOffset+6,nOffset1).search('>') + nOffset +6;
            tmpStr = newobj.substring(nOffset+6,nOffset2);
            tmp1Str = stripString(fReplace(newobj.substring(nOffset+6,nOffset2), text1, text2));
            if (tmpStr!=tmp1Str){
                newobj = newobj.substring(0,nOffset+6) + tmp1Str + newobj.substring(nOffset2,newobj.length);
                newobj = newobj.substring(0,nOffset+6) +
                fReplaceCellBorder(newobj.substring(nOffset+6,nOffset1+7), text1, text2) +
                newobj.substring(nOffset1+7,newobj.length);
            }
            else{
                if ((text1==' *\r* *border *\r* *= *\r* *\"? *\r* *0 *\r* *\"? *\r* *')
                && (newobj.substring(nOffset+6,nOffset2).toUpperCase().search('BORDER')==-1)){
                    newobj = newobj.substring(0,nOffset+6) + text2 + newobj.substring(nOffset+6, newobj.length);
                    newobj = newobj.substring(0,nOffset+6) +
                    fReplaceCellBorder(newobj.substring(nOffset+6,nOffset1+7), text1, text2) +
                    newobj.substring(nOffset1+7,newobj.length);
                }
            }
            nOffset2 = newobj.substring(nOffset+6,nOffset1).search('>') + nOffset + 6;
            nOffset = newobj.substring(nOffset2,newobj.length).search('<TABLE');
            if (nOffset>-1){
                nOffset = nOffset + nOffset2;
            }
        }
        else{
            nOffset = -1;
        }
    }
    return newobj;
};
function fReplaceCellBorder(obj, text1, text2){
    var newobj = obj;
    var nOffset = 0;
    var nOffset1 = 0;
    var replacedTdStr = '';
    nOffset = newobj.search('<TABLE');
    while (nOffset>-1){
        replacedTdStr = fReplaceTdBorder(newobj.substring(nOffset1,nOffset), text1, text2);
        newobj = newobj.substring(0,nOffset1) + replacedTdStr +
        newobj.substring(nOffset, newobj.length);
        nOffset = nOffset + replacedTdStr.length - (nOffset - nOffset1);
        nOffset1 = getEndTableOffset(nOffset, newobj) + 6;
        if (nOffset1>5){
            nOffset = newobj.substring(nOffset1, newobj.length).search('<TABLE');
            if (nOffset>-1){
                nOffset = nOffset + nOffset1;
            }
        }
        else nOffset = - 1;
    }
    newobj = newobj.substring(0,nOffset1) + fReplaceTdBorder(newobj.substring(nOffset1,newobj.length), text1, text2);
    return newobj;
}
function stripString(string){
    returnString = string;
    var i=0;
    for (i=0; i<returnString.length-1; i++){
        if ((returnString.charAt(i)==' ') || (returnString.charAt(i)=='\t') || (returnString.charCodeAt(i)==10)){
            if ((returnString.charAt(i+1)==' ') || (returnString.charAt(i+1)=='\t') || (returnString.charCodeAt(i+1)==10) ||
            ((i+3<=returnString.length) && (returnString.charCodeAt(i+1)==13) && (returnString.charCodeAt(i+2)==10))){
                returnString = returnString.substring(0,i) + returnString.substring(i+1, returnString.length);
                i=i-1;
            }
        }
        else if ((i+3<=returnString.length) && (returnString.charCodeAt(i)==13) && (returnString.charCodeAt(i+1)==10)){
            if ((returnString.charAt(i+2)==' ') || (returnString.charAt(i+2)=='\t') || (returnString.charCodeAt(i+2)==10) ||
            ((i+4<=returnString.length) && (returnString.charCodeAt(i+2)==13) && (returnString.charCodeAt(i+3)==10))){
                returnString = returnString.substring(0,i) + returnString.substring(i+2, returnString.length);
                i=i-1;
            }
        }
    }
    if ((returnString==' ') || (returnString=='\t') || (returnString.charCodeAt(0)==10) ||
    ((returnString.length==2) && (returnString.charCodeAt(0)==13) && (returnString.charCodeAt(1)==10))){
        returnString = '';
    }
    return returnString;
}
function fReplaceTdBorder(obj, text1, text2){
    var i=0;
    var newobj = "";
    var tableToken = obj.split('<TD');
    for (i=1; i<tableToken.length; i++){
        nOffset = tableToken[i].search('>');
        if (nOffset != -1){
            begin = tableToken[i].substr(0,nOffset);
            end = tableToken[i].substring(nOffset,tableToken[i].length);
            if (text1==' *\r* *border *\r* *= *\r* *\"? *\r* *0 *\r* *\"? *\r* *'){
                tableToken[i] = stripString(text2.substring(0, text2.length-1)) + tableToken[i];
            }
            else{
                tableToken[i] = stripString(fReplace(begin, text1, ' ')).concat(end);
            }
        };
    };
    for (i=0; i<tableToken.length; i++){
        if (i==0)
        newobj = tableToken[i];
        else
        newobj = newobj + '<TD' + tableToken[i];
    };
    return newobj;
};
function fOnClick(){
    try{
        var htmlContent = strip_body(document.tbCE.DocumentHTML);
    }
    catch (e){
        alert('Please wait until the HTML preview is completely loaded!');
        return false;
    };
    if (isCommonElement == 1){
        re = /\[PAGE_CONTENT\]/gi;
        var nr = fGetNrOfMatches(re,htmlContent);
        if (nr==0){
            alert('Please specify the [PAGE_CONTENT] keyword somewhere in the editor field. This keyword will be used in order to identify those parts of each page that does not belong to the General Page Layout!');
            return false;
        };
        if (nr!=1){
            alert('The [PAGE_CONTENT] keyword must appear only once!');
            return false;
        };
    };
    htmlContent = fReplace (htmlContent ,'<td','<TD');
    htmlContent = fReplace (htmlContent ,'</td','</TD');
    htmlContent = fReplace (htmlContent ,'<table','<TABLE');
    htmlContent = fReplace (htmlContent ,'</table','</TABLE');
    htmlContent = fReplace( htmlContent,graphicsCompletePath,graphicsPath);
    htmlContent = fReplaceTableBorder (htmlContent,
    ' *\r* *style=" *\r* *border-right: *\r* *1px *\r* *dashed; *\r* *border-top: *\r* *1px *\r* *dashed; *\r* *border-left: *\r* *1px *\r* *dashed; *\r* *border-bottom: *\r* *1px *\r* *dashed;* *\r* *" *\r* *',
    ' border="0" ');
    htmlContent = fReplaceTableBorder (htmlContent,
    ' *\r* *style=" *\r* *border-bottom: *\r* *1px *\r* *dashed; *\r* *border-left: *\r* *1px *\r* *dashed; *\r* *border-right: *\r* *1px *\r* *dashed; *\r* *border-top: *\r* *1px *\r* *dashed;* *\r* *" *\r* *',
    ' border="0" ');
    htmlContent = fReplace(htmlContent,
    ' *\r* *style=" *\r* *border-right: *\r* *1px *\r* *dashed; *\r* *border-top: *\r* *1px *\r* *dashed; *\r* *border-left: *\r* *1px *\r* *dashed; *\r* *border-bottom: *\r* *1px *\r* *dashed *\r* *" *\r* *',
    ' ');
    htmlContent = fReplace(htmlContent,
    ' *\r* *style=" *\r* *border-bottom: *\r* *1px *\r* *dashed; *\r* *border-left: *\r* *1px *\r* *dashed; *\r* *border-right: *\r* *1px *\r* *dashed; *\r* *border-top: *\r* *1px *\r* *dashed *\r* *" *\r* *',
    ' ');
    htmlContent = fReplace(htmlContent,'<td *>','<TD>');
    htmlContent = fReplace(htmlContent,' style="border: 1px dashed"','');
    htmlContent = fReplace (htmlContent, '1px *\r* *dashed','0px dashed');
    eval('window.opener.document.frmConfig.' + fieldType).value = htmlContent;
    window.close();
};
function tbCE_inicial(){
    if( document["tbCE"]){
        obj_editor = document.tbCE;
        var htmlSource = eval('window.opener.document.frmConfig.' + fieldType).value;
        htmlSource = fReplace (htmlSource ,'<td','<TD');
        htmlSource = fReplace (htmlSource ,'</td','</TD');
        htmlSource = fReplace (htmlSource ,'<table','<TABLE');
        htmlSource = fReplace (htmlSource ,'</table','</TABLE');
        htmlSource = fReplace (htmlSource ,graphicsPath,graphicsCompletePath);
        htmlSource = fReplace (htmlSource, '0px *\r* *dashed','1px dashed');
        htmlSource = fReplaceTableBorder(htmlSource, ' *\r* *border *\r* *= *\r* *\"? *\r* *0 *\r* *\"? *\r* *', ' style="border: 1px dashed" ');
        document.tbCE.DocumentHTML = htmlSource;
        clearInterval(tbCE_timerID);
    }
    return true;
}
