
function fProcessForm(){
    with (document.frmSalesAction){
    	dfSalesAction.value=fElimChar(fElimChar(dfSalesAction.value,"'"),'"');
		
		dfColor.value=fElimChar(fElimChar(dfColor.value,"'"),'"');
 
//        if (dfColor.value==''){
//           alert('Please specify the action color.');
//           return false;
//       }
        

        if (dfSalesAction.value=='')
        {
            alert('Please specify the action name.');
            return false;
        }
    }

    return true;
}
