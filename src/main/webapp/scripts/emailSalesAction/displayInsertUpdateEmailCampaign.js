
function fProcessForm()
{
    with (document.frmEmailCampaign)
    {		
		dfEmailCampaignDescription.value=fElimChar(fElimChar(dfEmailCampaignDescription.value,"'"),'"');
 
        if (dfEmailCampaignDescription.value==''){
            alert('Please specify the Description.');
            return false;
        }
        
         if (dfStartEmailDate.value=='')
        {
            alert('Please specify the Start Date to Use.');
            return false;
        }
        
         if (dfEndEmailDate.value=='')
        {
            alert('Please specify the End Date to Use.');
            return false;
        }
        
        
    }

    return true;
}
