function fValidateForm()
{
    if (document.frmConfig.OPT_TITLE_NEW.value=='') {alert('Please specify the title.'); return false;}
    if (document.frmConfig.OPT_MENU_TEXT_NEW.value=='') {alert('Please specify the label to display on the menu.'); return false;}
    if (document.frmConfig.OPT_CONTENT_NEW.value=='') {alert('Please specify the page content.'); return false;}
}