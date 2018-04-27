function fOnAddDocument()
{
    document.frmDocuments.formAction.value="DISPLAY_INSERT";
    document.frmDocuments.submit();
}
function fOnUpdateDocument(documentId)
{
    document.frmDocuments.formAction.value="DISPLAY_UPDATE";
    document.frmDocuments.dfDocumentId.value=documentId;
    document.frmDocuments.submit();
}

function fOnDeleteDocument(documentId)
{
    document.frmDocuments.formAction.value="DISPLAY_DELETE";
    document.frmDocuments.dfDocumentId.value=documentId;
    document.frmDocuments.submit();
}
function fProcessForm()
{

    if (document.frmDocuments.formAction.value == "INSERT" && document.frmDocuments.dfFileName.value == "")
    {
        alert('Please click on the Browse button to upload a new file.');
        return false;
    }

    return true;
}