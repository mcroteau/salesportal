
function fProcessForm()
{
    with (document.frmSoftwareType)
    {
    	dfSoftwareType.value=fElimChar(fElimChar(dfSoftwareType.value,"'"),'"');

        if (dfSoftwareType.value=='')
        {
            alert('Please specify the name.');
            return false;
        }
    }

    return true;
}
