
function fProcessForm()
{
    with (document.frmSize)
    {
    	dfSize.value=fElimChar(fElimChar(dfSize.value,"'"),'"');

        if (dfSize.value=='')
        {
            alert('Please specify the size name.');
            return false;
        }
    }

    return true;
}
