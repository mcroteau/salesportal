
function fProcessForm()
{
    with (document.frmCommision)
    {
    	dfDescription.value=fElimChar(fElimChar(dfDescription.value,"'"),'"');
 

    	if (dfInternalDescription.value!=''){
    		dfInternalDescription.value=fElimChar(fElimChar(dfInternalDescription.value,"'"),'"');
    	}

        if (dfUserId.value=='')
        {
            alert('Please specify the salesman.');
            return false;
        }

        if (dfAmount.value=='' && dfRevenue.value=='')
        {
            alert('Please specify either Revenue or Commission amount.');
            return false;
        }

        if (dfCheckAmount.value!='' && dfDatePaid.value == '')
        {
            alert('Please specify the date the commission was paid.');
            return false;
        }
       
        dfAmount.value=fElimChar(fElimChar(dfAmount.value,","),'');
        dfAmount.value=fElimChar(fElimChar(dfAmount.value,"$"),'');
        dfRevenue.value=fElimChar(fElimChar(dfRevenue.value,","),'');
        dfRevenue.value=fElimChar(fElimChar(dfRevenue.value,"$"),'');
        
    }
    
    
    return true;
}
