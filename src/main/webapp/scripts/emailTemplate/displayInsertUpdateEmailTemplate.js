function fOnAddEmailTemplate()
{
    document.frmEmailTemplates.formAction.value="DISPLAY_INSERT";
    document.frmEmailTemplates.submit();
}
function fOnUpdateEmailTemplate(emailTemplateId)
{
    document.frmEmailTemplates.formAction.value="DISPLAY_UPDATE";
    document.frmEmailTemplates.dfEmailTemplateId.value=emailTemplateId;
    document.frmEmailTemplates.submit();
}

function fOnDeleteEmailTemplate(emailTemplateId)
{
    document.frmEmailTemplates.formAction.value="DISPLAY_DELETE";
    document.frmEmailTemplates.dfEmailTemplateId.value=emailTemplateId;
    document.frmEmailTemplates.submit();
}
function fProcessForm()
{
    if (document.frmEmailTemplates.dfText.value == "" || document.frmEmailTemplates.dfDescription.value == "" )
    {
        alert('Please enter the content.');
        return false;
    }
    return true;
}