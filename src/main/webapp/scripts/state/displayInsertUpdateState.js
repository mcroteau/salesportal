
function fProcessForm()
{
    with (document.frmState)
    {
    	dfState.value=fElimChar(fElimChar(dfState.value,"'"),'"');

        if (dfState.value=='')
        {
            alert('Please specify the state name.');
            return false;
        }
        if (dfStateCode.value=='')
        {
            alert('Please specify the state code.');
            return false;
        }

    }

    return true;
}
