
function fProcessForm() {
    with (document.frmLabel) {
    	dfLabelActual.value=fElimChar(fElimChar(dfLabelActual.value,"'"),'"');

        if (dfLabelActual.value=='') {
            alert('Please specify the label.');
            return false;
        }
    }
    return true;
}
