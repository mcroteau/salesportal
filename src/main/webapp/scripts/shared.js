var DubleQuote='"';

function fElimQuote(sIn){
    var i=0;
    var sReturn=sIn;
    for (i=0;i<sReturn.length;i++){
        if (sReturn.charAt(i)=="'"){
            sReturn=sReturn.substring(0,i)+sReturn.substring(i+1,sReturn.length);
            i=i-1;
        }
    }
    return sReturn;
}

function fElimChar(sIn, sChr){
    var i=0;
    var sReturn=sIn;
    for (i=0;i<sReturn.length;i++){
        if (sReturn.charAt(i)==sChr){
            sReturn=sReturn.substring(0,i)+sReturn.substring(i+1,sReturn.length);
            i=i-1;
        }
    }
    return sReturn;
}

function textCounter( field, countfield, maxlimit ) {
  if ( field.value.length >= maxlimit )
  {
    alert( 'Textarea value can only be '+maxlimit+' characters in length.' );
    field.value = field.value.substring( 0, maxlimit - 1);
    return false;
  }
  else
  {
    countfield.value = maxlimit - field.value.length - 1;
  }

}


function formatPhoneNumber(strIn)
{
        var strOut = strIn;
        if (parseInt(strIn) != 'NaN')
        {
            if (strIn.length == 7)
            {
                strOut = strIn.substr(0, 3) + "-" + strIn.substr(3, 4);
            }
            else if (strIn.length == 10)
            {
                strOut = strIn.substr(0, 3) + "-" + strIn.substr(3, 3) + "-" + strIn.substr(6, 4);
            }
        }
        return strOut;
}

function isValidInteger(sIn){
    var i=0;
    for(i=0;i<sIn.length;i++){
        if (isNaN(parseInt(sIn.charAt(i)))){
            return false;
        };
    };    
    return true;
};




function isValidDate(str)
{
    theDate = new Date( str );
    strData = new String( str );
    componente = new Array();
    var year;
    componente=strData.split('/');

    if (navigator.appName == 'Netscape'){
        year = 1900 + theDate.getYear();
    } else if (navigator.appName == 'Microsoft Internet Explorer'){

        if(theDate.getYear() < 100) {
            year=1900 + theDate.getYear();
        } else {
            year=theDate.getYear();
        }
    }

    if(( componente.length == 3 ) &&
         ( componente[0] == theDate.getMonth()+1 ) &&
         ( componente[1] == theDate.getDate() ) &&
         ( componente[2] == year )){
        return true;
    }

    return false;
};

function isValidTime(sIn){
    return true;
};

function y2k(number) { return (number < 1000) ? number + 1900 : number; }

function daysElapsed(date1,date2) {
    var difference =
        Date.UTC(y2k(date1.getYear()),date1.getMonth(),date1.getDate(),0,0,0)
      - Date.UTC(y2k(date2.getYear()),date2.getMonth(),date2.getDate(),0,0,0);
    return difference/1000/60/60/24;
}

function getDifference(strDate1, strDate2){
    var res = 0;
    if (isValidDate(strDate1) && isValidDate(strDate2)){
        theDate1 = new Date(strDate1);
        theDate2 = new Date(strDate2);
        res = daysElapsed(theDate2,theDate1) + 1;
    }
    return res;
}


function incrementTime(str, nHours){
    var res = str;
    if (isValidTime(str)){
        strTime = new String( str );
        var compArr = new Array();
        compArr = strTime.split(':');
        theDate = new Date();
        var hours = Math.floor(compArr[0]);
        hours = hours + nHours;

        theDate.setHours(hours);
        theDate.setMinutes(compArr[1]);
        var strHours = '';
        if (theDate.getHours() < 10){
            strHours = '0';
        }
        var strMinutes = '';
        if (theDate.getMinutes() < 10){
            strMinutes = '0';
        }
        res = strHours + theDate.getHours() + ":"+ strMinutes + theDate.getMinutes();
    }

    return res;
}

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
            }
        }
    }
    if (sReturn.charAt(sReturn.length-1)=='.') sReturn=sReturn.substring(0,sReturn.length-1);
    return sReturn;
}
function fTruncToDecimal(sIn,nDec){
    var i=0;
    var decimal=0;
    var sReturn=sIn;
    for(i=0;i<sIn.length;i++){
        if (sIn.charAt(i)=='.'){
        	decimal=i;
            if (sIn.length >= i + nDec + 1){
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
            }
        }
    }
    if (sReturn.charAt(sReturn.length-1)=='.') sReturn=sReturn.substring(0,sReturn.length-1);
    if (sReturn.length-1 < decimal+nDec) {
    	if (decimal == 0 || decimal==sReturn.length) {
    		sReturn += '.';
    		decimal = sReturn.length-1;
    	}
    	var x=0;
    	for(x=sReturn.length-1; x< decimal+nDec; x++){
    		sReturn += '0';
    	}
    }
    return sReturn;
}

function fOpenHelp(helpFile, width, height){	
	help = window.open(''+helpFile+'', '',
		'scrollbars=yes, width='+width+',height='+height+',left=0,top=0,' +
	    'screenX=0,screenY=0,outerwidth='+width+',outerheight='+height+',toolbar=no,menubar=yes, scrollbar=yes, resizable=yes');
	help.opener = self;
	help.focus();
}