function fValidAppConfig(){
    var sMissingStr='';
    var sSectionMissingStr='';
    var s2Tabs=fRepeatStr(' ',8);
    var s1Tab=fRepeatStr(' ',4);
    with (document.frmConfig){
        if (OPT_COMPANY_NAME.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Company name\n';
        if (OPT_COMPANY_PHONE.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Phone\n';
        if (OPT_COMPANY_ADDRESS.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Address\n';
        if (OPT_COMPANY_STATE_ABREV.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- State abreviation\n';
        if (OPT_COMPANY_CITY.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- City\n';
        if (OPT_COMPANY_ZIP.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Zip\n';
        if (OPT_CONTACT_EMAIL.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Contact email address\n';
        if (OPT_AUTOMATED_EMAIL_FROM_ADRESS.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Email address for automated messages\n';
        if (OPT_SEND_ORDER_NOTIFICATION_TO_EMAIL_ADDRESS.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Send order notification to email address\n';
        if (OPT_SEND_LEAD_NOTIFICATION_TO_EMAIL_ADDRESS.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Send lead notification to email address\n';
        if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Company info\n'+sSectionMissingStr;
        sSectionMissingStr='';
        if (OPT_COOKIE_NAME_FOR_ANONYMOUS.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Cookie name for anonymous users\n';
        if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Basic web configurations\n'+sSectionMissingStr;
        sSectionMissingStr='';
        if ((!CB_RETAIL_CC_ENABLED.checked) && (!CB_RETAIL_PAYPAL_ENABLED.checked) && (!CB_RETAIL_CHECK_ENABLED.checked) &&
        (!CB_RETAIL_CASH_ENABLED.checked) && (!CB_RETAIL_MONEY_ENABLED.checked) &&
        (!CB_RETAIL_COD_ENABLED.checked)) sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Payment types for Retail\n';
        if ((!CB_WHOLESALE_CC_ENABLED.checked) && (!CB_WHOLESALE_PAYPAL_ENABLED.checked) && (!CB_WHOLESALE_CHECK_ENABLED.checked) &&
        (!CB_WHOLESALE_CASH_ENABLED.checked) && (!CB_WHOLESALE_MONEY_ENABLED.checked) &&
        (!CB_WHOLESALE_COD_ENABLED.checked)) sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Payment types for Wholesale\n';
        if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Payment Types\n'+sSectionMissingStr;
        sSectionMissingStr='';
        if (OPT_MERCHANT_NAME.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Merchant name\n';
        if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Checkout\n'+sSectionMissingStr;
        sSectionMissingStr='';
        if (OPT_ORDER_FORM_EMAIL_SUBJECT_1.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Email subject for retail\n';
        if (OPT_ORDER_FORM_EMAIL_SUBJECT_1.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Email subject for wholesale\n';
        if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Order form\n'+sSectionMissingStr;
        sSectionMissingStr='';
        if (sMissingStr!=''){
            alert('Please specify a value for the following fields:\n'+sMissingStr);
            return false;
        };
/*
        if (isValidColor(SITE_BACKGROUND.value)==false){
            alert('Please specify the Background color (Basic web configurations section) in the right format (six hexadecimal characters)\n');
            return false;
        };
        if (isValidColor(DEFAULT_TEXT_COLOR.value)==false){
            alert('Please specify the Default text color (Basic web configurations section) in the right format (six hexadecimal characters)\n');
            return false;
        };
        if (isValidColor(DEFAULT_LINK_COLOR.value)==false){
            alert('Please specify the Default link color (Basic web configurations section) in the right format (six hexadecimal characters)\n');
            return false;
        };
        if (isValidColor(DEFAULT_VISITED_LINK_COLOR.value)==false){
            alert('Please specify the Default visited link color (Basic web configurations section) in the right format (six hexadecimal characters)\n');
            return false;
        };
*/
        var thumbRatio = OPT_DEFAULT_THUMBNAIL_RATIO.value;
        if ((thumbRatio!='') && ((parseInt(thumbRatio)>=100) || (parseInt(thumbRatio)==0))){
            alert('Please specify a default thumbnail ratio between 1 and 99\n');
            return false;
        };
        if (fCheckEmail(OPT_CONTACT_EMAIL.value)==false){
            alert('Invalid Contact email address (Company Info section) was entered!');
            return false;
        };
        if (fCheckEmail(OPT_AUTOMATED_EMAIL_FROM_ADRESS.value)==false){
            alert('Invalid Email address for automated messages (Company Info section) was entered!');
            return false;
        };
        if (fCheckEmail(OPT_SEND_ORDER_NOTIFICATION_TO_EMAIL_ADDRESS.value)==false){
            alert('Invalid Send order notification to email address (Company Info section) was entered!');
            return false;
        };
        if (fCheckEmail(OPT_SEND_LEAD_NOTIFICATION_TO_EMAIL_ADDRESS.value)==false){
            alert('Invalid Send lead notification to email address (Company Info section) was entered!');
            return false;
        };

//        OPT_SITE_BACKGROUND.value = '#' + SITE_BACKGROUND.value;
//        OPT_DEFAULT_TEXT_COLOR.value = '#' + DEFAULT_TEXT_COLOR.value;
//        OPT_DEFAULT_LINK_COLOR.value = '#' + DEFAULT_LINK_COLOR.value;
//        OPT_DEFAULT_VISITED_LINK_COLOR.value = '#' + DEFAULT_VISITED_LINK_COLOR.value;
        setPaymentTypesOptions();
    };
};
function setPaymentTypesOptions(){
    setCbOption("RETAIL_CC_ENABLED");
    setCbOption("RETAIL_PAYPAL_ENABLED");
    setCbOption("RETAIL_CHECK_ENABLED");
    setCbOption("RETAIL_CASH_ENABLED");
    setCbOption("RETAIL_MONEY_ENABLED");
    setCbOption("RETAIL_COD_ENABLED");
    setCbOption("WHOLESALE_CC_ENABLED");
    setCbOption("WHOLESALE_PAYPAL_ENABLED");
    setCbOption("WHOLESALE_CHECK_ENABLED");
    setCbOption("WHOLESALE_CASH_ENABLED");
    setCbOption("WHOLESALE_MONEY_ENABLED");
    setCbOption("WHOLESALE_COD_ENABLED");
}
function setCbOption(optName){
    if (eval('document.frmConfig.CB_' + optName).checked){
        eval('document.frmConfig.OPT_' + optName).value='YES';
    }
    else{
        eval('document.frmConfig.OPT_' + optName).value='NO';
    }
}
function openBkgUploadWindow(){
    wBkg=window.open('upload?ActionType=5&PIC_TYPE=Background+Image',
    'wBkg',
    'width=450,height=300,left=0,top=0,screenX=0,screenY=0,outerwidth=450,outerheight=300,toolbar=no,menubar=no');
    wBkg.opener=self;
    wBkg.focus();
}
