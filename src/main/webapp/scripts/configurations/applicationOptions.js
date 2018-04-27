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
    return true;
};
