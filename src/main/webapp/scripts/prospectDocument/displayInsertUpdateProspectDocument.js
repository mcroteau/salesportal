function fOnAddDocument()
{
    document.frmProspectDocument.formAction.value="DISPLAY_INSERT";
    document.frmProspectDocument.submit();
}
function fOnUpdateDocument(prospectDocumentId)
{	
    document.frmProspectDocument.formAction.value="DISPLAY_UPDATE";
    document.frmProspectDocument.dfProspectDocumentId.value=prospectDocumentId;
    document.frmProspectDocument.submit();
}

function fOnDeleteDocument(prospectDocumentId)
{
    document.frmProspectDocument.formAction.value="DISPLAY_DELETE";
    document.frmProspectDocument.dfProspectDocumentId.value=prospectDocumentId;
    document.frmProspectDocument.submit();
}
function fProcessForm()
{
    document.frmProspectDocument.dfOperationType.value=getSelectedRadioValue(document.frmProspectDocument.dfOperationType)

    if (document.frmProspectDocument.dfOperationType.value=="UPLOAD_NEW_FILE"){
        if (document.frmProspectDocument.dfFileName.value == "") {
            alert('Please click on the Browse button to upload a new file.');
            return false;
        }
    } else {
        if ( document.frmProspectDocument.dfDocumentId.value == "") {
            alert('Please select an existing file from the list.');
            return false;
        }
    }
    return true;
}

function disableEnableUploadFields(){
    var value=getSelectedRadioValue(document.frmProspectDocument.dfOperationType);
    if (value == "UPLOAD_NEW_FILE"){
        document.frmProspectDocument.dfDocumentId.disabled=true;
        document.frmProspectDocument.dfFileName.disabled=false;
        document.frmProspectDocument.dfDescription.disabled=false;
        document.frmProspectDocument.dfDocumentType.disabled=false;
    } else {
        document.frmProspectDocument.dfDocumentId.disabled=false;
        document.frmProspectDocument.dfFileName.disabled=true;
        document.frmProspectDocument.dfDescription.disabled=false;
        document.frmProspectDocument.dfDocumentType.disabled=false;
    }
}

function getSelectedRadioValue(objRadio)
{
	var i=0;
	for (i=0; i<objRadio.length; i++) {
		if (objRadio[i].checked) {
			return objRadio[i].value;
		}
	}
	return "";
}