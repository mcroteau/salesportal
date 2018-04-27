function fOnDisplayWebsites()
{
    var wndWebsites = window.open('', 'wndWebsites', 'status=yes,scrollbars=yes,width=350,height=350,top=0,left=0');
    document.frmWebsites.target='wndWebsites';
    document.frmWebsites.formAction.value='DISPLAY';
    document.frmWebsites.submit();
}

