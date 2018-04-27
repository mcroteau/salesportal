function displayPrintReport(territoryId) {
   var objPrintMonthlyForm = document.frmPrintMonthlyForm;
   objPrintMonthlyForm.dfCalTerritoryId.value=territoryId;
   var width = 1100;
   var height = screen.availHeight - 25;			
   wTestReportChild = window.open('', objPrintMonthlyForm.target,
    	'scrollbars=yes, width='+width+',height='+height+',left=0,top=0,' +
        'screenX=0,screenY=0,outerwidth='+width+',outerheight='+height+',toolbar=no,menubar=yes, scrollbar=yes, resizable=yes');
   objPrintMonthlyForm.submit();
   wTestReportChild.opener = self;
   wTestReportChild.focus();
}

function displayPrintReportNote(territoryId) {
   var objPrintMonthlyFormNote = document.frmPrintMonthlyFormNote;
   objPrintMonthlyFormNote.dfCalTerritoryId.value=territoryId;
   var width = 1100;
   var height = screen.availHeight - 25;			
   wTestReportChild = window.open('', objPrintMonthlyFormNote.target,
    	'scrollbars=yes, width='+width+',height='+height+',left=0,top=0,' +
        'screenX=0,screenY=0,outerwidth='+width+',outerheight='+height+',toolbar=no,menubar=yes, scrollbar=yes, resizable=yes');
   objPrintMonthlyFormNote.submit();
   wTestReportChild.opener = self;
   wTestReportChild.focus();
}
 
function fOnUpdate(prospectId){
    document.frmProspect.target="";
	document.frmProspect.action="prospect_search";
	document.frmProspect.formAction.value='DISPLAY_FROM_CALENDAR';
	document.frmProspect.dfSearchProspectId.value=prospectId;
	document.frmProspect.submit();
}
	
function fChangeView(direction){
    document.frmProspect.target="";
	document.frmProspect.formAction.value='CHANGE_VIEW_MONTH_MULTIPLE';
	document.frmProspect.direction.value=direction;
	document.frmProspect.action="territory_calendar";
	document.frmProspect.submit();
}