
function fProcessForm()
{
    with (document.frmCountry)
    {
    	dfCountry.value=fElimChar(fElimChar(dfCountry.value,"'"),'"');

        if (dfCountry.value=='')
        {
            alert('Please specify the country name.');
            return false;
        }

        if (dfCountryCode.value=='')
        {
            alert('Please specify the country code.');
            return false;
        }

    }

    return true;
}
