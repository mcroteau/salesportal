function fOnAddWebsite()
{
    document.frmWebsites.formAction.value="DISPLAY_INSERT";
    document.frmWebsites.submit();
}
function fOnUpdateWebsite(websiteId)
{
    document.frmWebsites.formAction.value="DISPLAY_UPDATE";
    document.frmWebsites.dfWebsiteId.value=websiteId;
    document.frmWebsites.submit();
}

function fOnDeleteWebsite(websiteId)
{
    document.frmWebsites.formAction.value="DISPLAY_DELETE";
    document.frmWebsites.dfWebsiteId.value=websiteId;
    document.frmWebsites.submit();
}
function fProcessForm()
{
    if (document.frmWebsites.dfUrl.value == "")
    {
        alert('Please enter the website URL.');
        return false;
    }
    return true;
}