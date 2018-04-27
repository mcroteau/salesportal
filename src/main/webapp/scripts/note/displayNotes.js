function fOnDisplayNotes()
{
    var wndNotes = window.open('', 'wndNotes', 'status=yes,scrollbars=yes,width=350,height=350,top=0,left=0');
    document.frmNotes.target='wndNotes';
    document.frmNotes.formAction.value='DISPLAY';
    document.frmNotes.submit();
}

