function fGetNrOfMatches(re,str){
    var found = str.match(re);
    if (found == null) return 0;
    else return found.length;
};
function fTrunc(sIn,nDec){
    var i=0;
    var sReturn=sIn;
    for(i=0;i<sIn.length;i++){
        if (sIn.charAt(i)=='.'){
            if (sIn.length>=i+nDec+1){
                sReturn=sIn.substring(0,i+nDec+1);
            }
            else{
                sReturn=sIn;
            }
        }
        else{
            if (isNaN(parseInt(sIn.charAt(i)))){
                sReturn='';
                return sReturn;
            };
        };
    };
    if (sReturn.charAt(sReturn.length-1)=='.') sReturn=sReturn.substring(0,sReturn.length-1);
    return sReturn;
};
function fValidAppConfig(){
    fSelectAllMenuEntries();

    var sMissingStr='';
    var s2Tabs=fRepeatStr(' ',8);
    var s1Tab=fRepeatStr(' ',4);
    with (document.frmConfig){
    ///menu validation
        var sSectionMissingStr='';
        if (OPT_MENU_VERTICAL_POSITION.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu vertical position (px)\n';
        if (OPT_MENU_HORIZONTAL_POSITION.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu horizontal position (px)\n';
        if (OPT_MENU_STYLE.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu style\n';
        if (OPT_MENU_OPTION_WIDTH.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu option width (px)\n';
        if (OPT_SUBMENU_OPTION_WIDTH.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Submenu option width (px)\n';
        if (MENU_LOW_BACKGROUND_COLOR.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu low background color\n';
        if (MENU_HIGH_BACKGROUND_COLOR.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu high background color\n';
        if (MENU_TEXT_LOW_COLOR.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu text low color\n';
        if (MENU_TEXT_HIGH_COLOR.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu text high color\n';
        if (MENU_BORDER_COLOR.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu border color\n';
        if (OPT_MENU_TEXT_FONT_SIZE.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Menu text font size\n';
        if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Menu configuration\n'+sSectionMissingStr;
        //menu colors validation
        if (isValidColor(MENU_LOW_BACKGROUND_COLOR.value)==false){
            alert('Please specify the Menu low background color in the right format (six hexadecimal characters)\n');
            return false;
        };
        if (isValidColor(MENU_HIGH_BACKGROUND_COLOR.value)==false){
            alert('Please specify the Menu high background color in the right format (six hexadecimal characters)\n');
            return false;
        };
        if (isValidColor(MENU_TEXT_LOW_COLOR.value)==false){
            alert('Please specify the Menu text low color in the right format (six hexadecimal characters)\n');
            return false;
        };
        if (isValidColor(MENU_TEXT_HIGH_COLOR.value)==false){
            alert('Please specify the Menu text high color in the right format (six hexadecimal characters)\n');
            return false;
        };
        if (isValidColor(MENU_BORDER_COLOR.value)==false){
            alert('Please specify the Menu border color in the right format (six hexadecimal characters)\n');
            return false;
        };
        OPT_MENU_LOW_BACKGROUND_COLOR.value = '#' + MENU_LOW_BACKGROUND_COLOR.value;
        OPT_MENU_HIGH_BACKGROUND_COLOR.value = '#' + MENU_HIGH_BACKGROUND_COLOR.value;
        OPT_MENU_TEXT_LOW_COLOR.value = '#' + MENU_TEXT_LOW_COLOR.value;
        OPT_MENU_TEXT_HIGH_COLOR.value = '#' + MENU_TEXT_HIGH_COLOR.value;
        OPT_MENU_BORDER_COLOR.value = '#' + MENU_BORDER_COLOR.value;

        //home page content validation
        var sSectionMissingStr='';
        if (OPT_HOME_PAGE_CONTENT_1 && OPT_HOME_PAGE_CONTENT_1.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Home Page content for Anonymous Users\n';
        if (OPT_HOME_PAGE_CONTENT_2 && OPT_HOME_PAGE_CONTENT_2.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Home Page content for Wholesale Customers\n';
        if (OPT_HOME_PAGE_CONTENT_3 && OPT_HOME_PAGE_CONTENT_3.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Home Page content for Affiliate Users\n';
        if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Home Page\n'+sSectionMissingStr;

        var sSectionMissingStr='';
        if (sMissingStr!=''){
            alert('Please specify a value for the following fields:\n'+sMissingStr);
            return false;
        };

        var re = /\[PAGE_CONTENT\]/gi;
        var nr1 = fGetNrOfMatches( re, COMMON_BODY_1.value);
        if ( nr1==0){
            alert('Please specify the [PAGE_CONTENT] keyword somewhere in the General Page Layout for Anonymous Users field. This keyword will be used in order to identify those parts of each page that does not belong to the General Page Layout!');
            return false;
        };
        if ( nr1!=1){
            alert('The [PAGE_CONTENT] keyword must appear only once in the General Page Layout for Anonymous Users field!');
            return false;
        };
        var offset = COMMON_BODY_1.value.search(re);
        OPT_COMMON_PAGE_HEADER_1.value = COMMON_BODY_1.value.substring(0,offset);
        OPT_COMMON_PAGE_FOOTER_1.value = COMMON_BODY_1.value.substring(offset+14);

        if (document.frmConfig.COMMON_BODY_2){
            var nr2 = fGetNrOfMatches( re, COMMON_BODY_2.value);
            if ( nr2==0){
                alert('Please specify the [PAGE_CONTENT] keyword somewhere in the General Page Layout for Wholesale field. This keyword will be used in order to identify those parts of each page that does not belong to the General Page Layout!');
                return false;
            };
            if ( nr2!=1){
                alert('The [PAGE_CONTENT] keyword must appear only once in the General Page Layout for Wholesale field!');
                return false;
            };
            offset = COMMON_BODY_2.value.search(re);
            OPT_COMMON_PAGE_HEADER_2.value = COMMON_BODY_2.value.substring(0,offset);
            OPT_COMMON_PAGE_FOOTER_2.value = COMMON_BODY_2.value.substring(offset+14);
        }

        if (document.frmConfig.COMMON_BODY_3){
            var nr2 = fGetNrOfMatches( re, COMMON_BODY_3.value);
            if ( nr2==0){
                alert('Please specify the [PAGE_CONTENT] keyword somewhere in the General Page Layout for Affiliate Users field. This keyword will be used in order to identify those parts of each page that does not belong to the General Page Layout!');
                return false;
            };
            if ( nr2!=1){
                alert('The [PAGE_CONTENT] keyword must appear only once in the General Page Layout for Affiliate Users field!');
                return false;
            };
            offset = COMMON_BODY_3.value.search(re);
            OPT_COMMON_PAGE_HEADER_3.value = COMMON_BODY_3.value.substring(0,offset);
            OPT_COMMON_PAGE_FOOTER_3.value = COMMON_BODY_3.value.substring(offset+14);
        }

        if (document.frmConfig.COMMON_BODY_4){
            var nr2 = fGetNrOfMatches( re, COMMON_BODY_4.value);
            if ( nr2==0){
                alert('Please specify the [PAGE_CONTENT] keyword somewhere in the General Page Layout for Salesmen field. This keyword will be used in order to identify those parts of each page that does not belong to the General Page Layout!');
                return false;
            };
            if ( nr2!=1){
                alert('The [PAGE_CONTENT] keyword must appear only once in the General Page Layout for Salesmen field!');
                return false;
            };
            offset = COMMON_BODY_4.value.search(re);
            OPT_COMMON_PAGE_HEADER_4.value = COMMON_BODY_4.value.substring(0,offset);
            OPT_COMMON_PAGE_FOOTER_4.value = COMMON_BODY_4.value.substring(offset+14);
        }

    }
};

function fUp()
{
    if ( document.frmConfig.MENU_SEQUENCE.selectedIndex > 0)
    {
        var saveText = document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].text;
        var saveValue = document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].value;

        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].value =
            document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex - 1].value
        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].text =
            document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex - 1].text

        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex - 1].text = saveText;
        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex - 1].value = saveValue;
        document.frmConfig.MENU_SEQUENCE.selectedIndex--;


    }
    else
    {
        //alert ('Invalid option selected.');
    }
}

function fDown()
{
    if ( document.frmConfig.MENU_SEQUENCE.selectedIndex >= 0
            && document.frmConfig.MENU_SEQUENCE.selectedIndex < document.frmConfig.MENU_SEQUENCE.options.length - 1)
    {
        var saveText = document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].text;
        var saveValue = document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].value;

        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].value =
            document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex + 1].value
        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex].text =
            document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex + 1].text

        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex + 1].text = saveText;
        document.frmConfig.MENU_SEQUENCE.options[document.frmConfig.MENU_SEQUENCE.selectedIndex + 1].value = saveValue;
        document.frmConfig.MENU_SEQUENCE.selectedIndex++;
    }
    else
    {
        //alert ('Invalid option selected.');
    }
}

function fSelectAllMenuEntries()
{
    var n = document.frmConfig.MENU_SEQUENCE.options.length;
    for (var i = 0; i < n; i ++)
    {
        document.frmConfig.MENU_SEQUENCE.options[i].selected = true;
    }
}