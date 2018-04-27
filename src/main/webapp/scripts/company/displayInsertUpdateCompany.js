
function fProcessForm()
{
    with (document.frmCompany)
    {
    	dfCompany.value=fElimChar(fElimChar(dfCompany.value,"'"),'"');

        if (dfCompany.value=='')
        {
            alert('Please specify the company name.');
            return false;
        }
    }

    return true;
}
