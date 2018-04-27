
var sQuote='"';
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
function fRepeatStr(sRepeatStr, nRepeats){
    var i=1;
    var sReturn='';
    for (i=1; i<=nRepeats;i++){
        sReturn=sReturn+sRepeatStr;
    };
    return sReturn;
};
function fSetComboSelectionText(objCombo,sVal, sDefaultVal){
    fSetComboSelection(objCombo,sVal, 'text', sDefaultVal);
};
function fSetComboSelectionValue(objCombo,sVal, sDefaultVal){
    fSetComboSelection(objCombo,sVal, 'value', sDefaultVal);
};
function fSetComboSelection(objCombo,sVal, sComboProperty, sDefaultVal){
    if (objCombo.selectedIndex<=0){
        var i=0;
        var nDefaultIndex=0;
        for (i=0;i<objCombo.length;i++){
            if (eval('objCombo.options[i].'+sComboProperty)==sVal){
                objCombo.options.selectedIndex=i;
                return true;
            }
            else{
                if (eval('objCombo.options[i].'+sComboProperty)==sDefaultVal) nDefaultIndex=i;
            };
        };
        objCombo.options.selectedIndex=nDefaultIndex;
    };
};
function isHexa( n ){
    var test = "" + n;
    if ((test>="0" && test<="9") || (test.toUpperCase()>="A" && test.toUpperCase()<="F")){
        return true;
    }
    else{
        return false;
    };
};
function isValidColor( sColor ){
    sColor+= "";
    if (sColor.length != 6){
        return false;
    }
    else{
        for (i=0;i<6;i++){
            if (!(isHexa(sColor.charAt(i)))){
                return false;
            };
        };
    };
    return true;
};
function fCheckEmail(sIn){
    var first=sIn.indexOf("@");
    if( (first==-1)||(sIn.substring(0, first)=='')||(sIn.substring(first+1, sIn.length)=='')){
        return false;
    };
    if(sIn.substring(first+1, sIn.length).indexOf(".")==-1){
        return false;
    };
    return true;
};
function fOnFieldBlur(objField){
    re= new RegExp(sQuote,'gi');
    objField.value=objField.value.replace(re,'');
};

function fOnTextareaBlur(objTextarea){
    re= new RegExp('%'+'}','gi');
    objTextarea.value=objTextarea.value.replace(re,'}');
};

function openColorPicker(colorField,fieldType,fieldName){
    wColor = window.open('config?formAction=DISPLAY_UPDATE_COLOR&initialColor='+colorField+'&fieldType='+fieldType+'&fieldName='+fieldName,'wColor','width=500,height=350,left=0,top=0,screenX=0,screenY=0,outerwidth=500,outerheight=350,toolbar=no,menubar=no');
    wColor.opener=self;
    wColor.focus();
};
