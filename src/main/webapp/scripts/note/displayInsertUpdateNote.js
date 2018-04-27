function fOnAddNote()
{
    document.frmNotes.formAction.value="DISPLAY_INSERT";
    document.frmNotes.submit();
}
function fOnUpdateNote(noteId)
{
    document.frmNotes.formAction.value="DISPLAY_UPDATE";
    document.frmNotes.dfNoteId.value=noteId;
    document.frmNotes.submit();
}

function fOnDeleteNote(noteId)
{
    document.frmNotes.formAction.value="DISPLAY_DELETE";
    document.frmNotes.dfNoteId.value=noteId;
    document.frmNotes.submit();
}
function fProcessForm()
{
    if (document.frmNotes.dfNote.value == "")
    {
        alert('Please enter the note content.');
        return false;
    }
    return true;
}