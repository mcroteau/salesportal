function fSetActive(objActive, sActive){
    var i=0;
    for (i=0;i<objActive.length;i++){
        if (objActive.options[i].value==sActive){
            objActive.options.selectedIndex=i;
            return true;
        };
    };
};
function isValidInteger(sIn){
    var i=0;
    for(i=0;i<sIn.length;i++){
        if (isNaN(parseInt(sIn.charAt(i)))){
            return false;
        };
    };
    return true;
};
function isValidDim(sIn, nUpperLimit){
    if (parseInt(sIn)<200 || parseInt(sIn)>nUpperLimit){
        return false;
    };
    return true;
}
function fValidF(){
    var sMissingStr='';
    if (document.frmConfig.OPT_MESSAGE_W_WIDTH.value=='') sMissingStr=sMissingStr+' - Message Window Width;\n';
    if (document.frmConfig.OPT_MESSAGE_W_HEIGHT.value=='') sMissingStr=sMissingStr+' - Message Window Height;\n';
    if (document.frmConfig.OPT_MESSAGE_W_HEADING.value=='') sMissingStr=sMissingStr+' - Message Window Heading;\n';
    if (document.frmConfig.OPT_MESSAGE_W_CONTENT.value=='') sMissingStr=sMissingStr+' - Message content;\n';
    if ((sMissingStr!='')&&(document.frmConfig.OPT_MESSAGE_W_ACTIVE.options[0].selected)){
        alert('Please specify a value for the following fields:\n'+sMissingStr);
        return false;
    };
    if (isValidInteger(document.frmConfig.OPT_MESSAGE_W_WIDTH.value)==false){
        alert('Please enter a valid Width (integer)');
        return false;
    };
    if (isValidInteger(document.frmConfig.OPT_MESSAGE_W_HEIGHT.value)==false){
        alert('Please enter a valid Height (integer)');
        return false;
    };
    if ((isValidDim(document.frmConfig.OPT_MESSAGE_W_WIDTH.value,1024)==false)&& (document.frmConfig.OPT_MESSAGE_W_ACTIVE.options[0].selected)){
        alert('Please enter a valid Width (integer between 200 and 1024)');
        return false;
    };
    if ((isValidDim(document.frmConfig.OPT_MESSAGE_W_HEIGHT.value,768)==false)&&(document.frmConfig.OPT_MESSAGE_W_ACTIVE.options[0].selected)){
        alert('Please enter a valid Height (integer between 200 and 768)');
        return false;
    };
    return true;
};
