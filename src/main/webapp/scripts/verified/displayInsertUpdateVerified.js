
function fProcessForm()
{
    with (document.frmVerified)
    {
    	dfVerified.value=fElimChar(fElimChar(dfVerified.value,"'"),'"');

        if (dfVerified.value=='')
        {
            alert('Please specify the verified name.');
            return false;
        }
    }

    return true;
}
