
function fProcessForm()
{
    with (document.frmLob)
    {
    	dfLob.value=fElimChar(fElimChar(dfLob.value,"'"),'"');

        if (dfLob.value=='')
        {
            alert('Please specify the Line of Business name.');
            return false;
        }
    }

    return true;
}
