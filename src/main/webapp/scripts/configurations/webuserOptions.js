function fValidWholesaleConfig(){
    var sMissingStr='';
    var sSectionMissingStr='';
    var s2Tabs=fRepeatStr(' ',8);
    var s1Tab=fRepeatStr(' ',4);
    if (document.frmConfig.OPT_ACCOUNT_CREATED_NOTIFICATION_EMAIL_SUBJECT.value=='') sSectionMissingStr=sSectionMissingStr+s2Tabs+'- Notification email subject\n';
    if (sSectionMissingStr!='') sMissingStr=sMissingStr+s1Tab+'- Account created\n'+sSectionMissingStr;
    sSectionMissingStr='';
    if (sMissingStr!=''){
        alert('Please specify a value for the following fields:\n'+sMissingStr);
        return false;
    };
};
