
function fProcessForm()
{
    with (document.frmStatus)
    {
    	dfStatus.value=fElimChar(fElimChar(dfStatus.value,"'"),'"');

        if (dfStatus.value=='')
        {
            alert('Please specify the status name.');
            return false;
        }
    }

    return true;
}
