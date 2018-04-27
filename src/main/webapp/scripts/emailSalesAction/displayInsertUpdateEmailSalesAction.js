
function fProcessForm()
{
    with (document.frmEmailSalesAction)
    {		
		dfEmailSalesActionDescription.value=fElimChar(fElimChar(dfEmailSalesActionDescription.value,"'"),'"');
		dfEmailDraftToUse.value=fElimChar(fElimChar(dfEmailDraftToUse.value,"'"),'"');
 
        if (dfEmailSalesActionDescription.value==''){
            alert('Please specify the Description.');
            return false;
        }
        

        if (dfEmailDraftToUse.value=='')
        {
            alert('Please specify the OP Email Draft to Use.');
            return false;
        }
        
         if (dfSendEmailDate.value=='')
        {
            alert('Please specify the Send Date to Use.');
            return false;
        }
        
        
    }

    return true;
}
