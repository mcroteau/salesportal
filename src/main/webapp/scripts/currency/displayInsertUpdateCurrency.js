
function fProcessForm()
{
    with (document.frmCurrency)
    {	
    	
    	dfCurrencyCode.value=fElimChar(fElimChar(dfCurrencyCode.value,"'"),'"');

        if (dfCurrencyCode.value=='')
        {
            alert('Please specify the Currency code.');
            return false;
        }
        if (dfCurrencyDescription.value=='')
        {
            alert('Please specify the Currency description.');
            return false;
        }
    }

    return true;
}
