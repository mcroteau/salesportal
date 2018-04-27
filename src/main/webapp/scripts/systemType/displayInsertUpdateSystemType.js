
function fProcessForm()
{
    with (document.frmSystemType)
    {
    	dfSystemType.value=fElimChar(fElimChar(dfSystemType.value,"'"),'"');

        if (dfSystemType.value=='')
        {
            alert('Please specify the name.');
            return false;
        }
    }

    return true;
}
