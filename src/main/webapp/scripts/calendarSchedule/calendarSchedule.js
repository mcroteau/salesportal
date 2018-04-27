// calendarSchedule.js

	function getRadioButtonSelectedValue(objRadio) {
	    for (var i=0; i<objRadio.length;i++) {
	        if (objRadio[i].checked) {
	            return objRadio[i].value;
	        }
	    }
	
	    return '';
	}
	
	function fOnSearchResults(){
		//if(getRadioButtonSelectedValue(document.frmCalendar.calendarType) == 'DAY' && 
		//	document.frmCalendar.dfTerritoryId.value == 'all') {
		//	alert('Day view cannot be viewed will all territories');
		//} else if(getRadioButtonSelectedValue(document.frmCalendar.calendarType) == 'DAY' && 
		//	document.frmCalendar.dfTerritoryId.value != 'all') {
		if(getRadioButtonSelectedValue(document.frmCalendar.calendarType) == 'DAY'){
			document.frmCalendar.action='prospect_search';
			document.frmCalendar.dfSearchTerritoryId.value=document.frmCalendar.dfCalTerritoryId.value;
			document.frmCalendar.dfSearchSalesActionDate.value=document.frmCalendar.dfSelectDate.value;
			document.frmCalendar.dfSearchStatusId.value=document.frmCalendar.dfCalStatusId.value;		
			document.frmCalendar.formAction.value='DISPLAY_FROM_CALENDAR';
			document.frmCalendar.submit();
			
		} else {
			document.frmCalendar.formAction.value='DISPLAY';
			document.frmCalendar.submit();
		}
	}	
	
