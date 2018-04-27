function displayPrintReport(territoryId) {
   var objPrintWeeklyForm = document.frmPrintWeeklyForm;
   frmPrintWeeklyForm.dfCalTerritoryId.value=territoryId;
   var width = 980;
   var height = screen.availHeight - 200;			
   wTestReportChild = window.open('', objPrintWeeklyForm.target,
		'scrollbars=yes, width='+width+',height='+height+',left=0,top=0,' +
		'screenX=0,screenY=0,outerwidth='+width+',outerheight='+height+',toolbar=no,menubar=yes,scrollbar=yes,resizable=yes');
   objPrintWeeklyForm.submit();
   wTestReportChild.opener = self;
   wTestReportChild.focus();
}

function displayPrintReportNote(territoryId) {
   var objPrintWeeklyFormNote = document.frmPrintWeeklyFormNote;
   frmPrintWeeklyFormNote.dfCalTerritoryId.value=territoryId;
   var width = 980;
   var height = screen.availHeight - 200;			
   wTestReportChild = window.open('', objPrintWeeklyFormNote.target,
		'scrollbars=yes, width='+width+',height='+height+',left=0,top=0,' +
		'screenX=0,screenY=0,outerwidth='+width+',outerheight='+height+',toolbar=no,menubar=yes,scrollbar=yes,resizable=yes');
   objPrintWeeklyFormNote.submit();
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
	document.frmProspect.formAction.value='CHANGE_VIEW_WEEK_MULTIPLE';
	document.frmProspect.direction.value=direction;
	document.frmProspect.action="territory_calendar";
	document.frmProspect.submit();
}	