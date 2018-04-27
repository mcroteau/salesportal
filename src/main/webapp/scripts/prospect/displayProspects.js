
function fOnUpdate(prospectId){
    document.frmProspect.target="";
	document.frmProspect.action="prospect";
	document.frmProspect.formAction.value='DISPLAY_UPDATE';
	document.frmProspect.dfProspectId.value=prospectId;
	document.frmProspect.submit();
}

function fOnInsert(popup, formAction)
{
    document.frmProspect.target="";
    if (popup == true)
    {
	    var wndInsert = window.open("","wndInsert","width=500,height=600,scrollbars=yes,status=yes");
	    wndInsert.focus();
	    wndInsert.opener=window;
	    document.frmProspect.target="wndInsert";
	}
	document.frmProspect.action="prospect";
	document.frmProspect.formAction.value=formAction;

	document.frmProspect.submit();
}

function displayViewOne() {
	var displayElement = '';
 	if(navigator.appName == "Microsoft Internet Explorer") {
		displayElement = "block";
	} else {
		displayElement = "table";	
	}
	document.getElementById('viewOne').style.display = displayElement;
	document.getElementById('viewTwo').style.display = 'none';
	//document.getElementById('viewThree').style.display = 'none';
}

function displayViewTwo() {
	var displayElement = '';
 	if(navigator.appName == "Microsoft Internet Explorer") {
		displayElement = "block";
	} else {
		displayElement = "table";	
	}
	document.getElementById('viewOne').style.display = 'none';
	document.getElementById('viewTwo').style.display = displayElement;
	//document.getElementById('viewThree').style.display = 'none';
}

function displayViewThree() {
	var displayElement = '';
 	if(navigator.appName == "Microsoft Internet Explorer") {
		displayElement = "block";
	} else {
		displayElement = "table";	
	}
	
	document.getElementById('viewOne').style.display = 'none';
	document.getElementById('viewTwo').style.display = 'none';
	document.getElementById('viewThree').style.display = displayElement;
			
}
function fOnDelete(prospectId)
{
    document.frmProspect.target="";
	document.frmProspect.action="prospect";
	document.frmProspect.dfProspectId.value=prospectId;
	document.frmProspect.formAction.value='DELETE';

	document.frmProspect.submit();
}
function fOnDeleteCascade(prospectId)
{
    document.frmProspect.target="";
	document.frmProspect.action="prospect";
	document.frmProspect.dfProspectId.value=prospectId;
	document.frmProspect.formAction.value='DELETE_CASCADE';

	document.frmProspect.submit();
}

function fOnBackToSearch(){
    document.frmProspect.target="";
	document.frmProspect.action="prospect_search";
	document.frmProspect.formAction.value='DISPLAY_SEARCH';

	document.frmProspect.submit();
}

function fSendEmailQue(){
	
    document.frmProspect.target="";
	document.frmProspect.action="email_que";
	document.frmProspect.formAction.value='DISPLAY_UPLOAD_EMAIL_QUE';

	document.frmProspect.submit();
}

function fOnNotes(prospectId, popup)
{
    document.frmProspect.target="";
    if (popup == true)
    {
	    var wndUpdate = window.open("","wndUpdate","width=500,height=600,scrollbars=yes,status=yes");
	    window.name = "mainWnd";
	    wndUpdate.focus();
	    wndUpdate.opener=window;
	    document.frmProspect.target="wndUpdate";
	}
	document.frmProspect.dfProspectId.value=prospectId;
	document.frmProspect.action="note";
	document.frmProspect.formAction.value='DISPLAY';
	document.frmProspect.submit();
}

function fOnWebsites(prospectId, popup)
{
    document.frmProspect.target="";
    if (popup == true)
    {
	    var wndUpdateWebsites = window.open("","wndUpdateWebsites","width=500,height=600,scrollbars=yes,status=yes");
	    window.name = "mainWnd";
	    wndUpdateWebsites.focus();
	    wndUpdateWebsites.opener=window;
	    document.frmProspect.target="wndUpdateWebsites";
	}
	document.frmProspect.dfProspectId.value=prospectId;
	document.frmProspect.action="website";
	document.frmProspect.formAction.value='DISPLAY';
	document.frmProspect.submit();
}

function fOnAddNote(prospectId, popup)
{
    if (popup == true)
    {
	    var wndNotes = window.open('', 'wndNotes', 'status=yes,scrollbars=yes,width=500,height=500,top=0,left=0');
        window.name = "mainWnd";
        document.frmProspect.target='wndNotes';
	    wndUpdate.focus();
	    wndUpdate.opener=window;
	}
    document.frmProspect.action="note";
    document.frmProspect.dfProspectId.value = prospectId;
    document.frmProspect.formAction.value="DISPLAY_INSERT";
    document.frmProspect.submit();
}

function fOnPrint(prospectId)
{
    var wndPrint = window.open('', 'wndPrint', 'status=yes,scrollbars=yes,width=780,height=500,top=0,left=0');
    window.name = "mainWnd";
    document.frmProspect.target='wndPrint';
    document.frmProspect.action="prospect_print";
    document.frmProspect.dfProspectId.value = prospectId;
    document.frmProspect.formAction.value="DISPLAY_PRINT_FRAMESET";

    document.frmProspect.submit();
}


function fOnDownload(filename){
    document.frmProspectDownload.action='prospects.csv';
    document.frmProspectDownload.formAction.value='DOWNLOAD';
    document.frmProspectDownload.submit();
}

function fOnRemoveChecked(){
    document.frmProspect.formAction.value='REMOVE_SELECTED';
    document.frmProspect.target="";
    document.frmProspect.action="prospect_search";
    document.frmProspect.submit();
}

function fOnEditCollection() {
    document.frmProspect.formAction.value='DISPLAY_UPDATE_COLLECTION';
    document.frmProspect.target="";
    document.frmProspect.action="prospect";
    document.frmProspect.submit();
}

function uncheckCheckallCheckBox(){
	document.frmProspect.checkall.checked = false;
}
							
function fChangeAllChecked(){
	document.frmProspect.formAction.value='DISPLAY_UPDATE_CHECKED_COLLECTION';
	document.frmProspect.target="";
    document.frmProspect.action="prospect";
    document.frmProspect.submit();								
}

function checkUncheckAll(){
	var globalCheckbox = document.frmProspect.checkall;
	if(globalCheckbox.checked == false){
		clear();
	}else{
		checkAll();
	}
}

function clear(){
	inputArray = document.frmProspect.getElementsByTagName('input');
	for(i=0;i<inputArray.length;i++) {
		if(inputArray[i].className=='prospectCheckbox') {
			inputArray[i].checked = false;
		}
	} 
}

function checkAll(){
	inputArray = document.frmProspect.getElementsByTagName('input');
	for(i=0;i<inputArray.length;i++) {
		if(inputArray[i].className=='prospectCheckbox') {
			inputArray[i].checked = true;
		}
	} 
}


function fProspectsAreChecked(){
	var total = 0;
	inputArray = document.frmProspect.getElementsByTagName('input');
	for(i=0;i<inputArray.length;i++) {
		if(inputArray[i].className=='prospectCheckbox' && inputArray[i].checked == true){
			total++;
		} 
	} 
	
	if(total > 0){
		return true;
	}else{
		alert('You have not selected any prospects to edit');
		return false;
	}
}