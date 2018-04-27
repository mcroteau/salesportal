
//selectTime();
//var selectAmPm =  getRadioButtonSelectedValue(time);
  
       
function selectTime(){
        //var selectedViewType =  getRadioButtonSelectedValue();
        var amEnabled = (selectAmPm == 'AM') ? true : false;
        var pmEnabled = (selectAmPm == 'PM') ? true : false;
}
	
function getRadioButtonSelectedValue(objRadio) {
    for (var i=0; i < objRadio.length; i++) {
        if (objRadio[i].checked) {
            return objRadio[i].value;
        }
    }
	
    return '';
}

function fDisplayHidden(){
    document.getElementById('insertSalesActionDiv').style.display = 'none';
	document.getElementById('hiddenDiv').style.display = 'block';
}
function fHideSalesAction(){
	document.getElementById('hiddenDiv').style.display = 'none';
	document.getElementById('insertSalesActionDiv').style.display = 'block';
}	

var dateIsMandatory = 0;
function fPopulateStates(objParent, objChild){
	var nSel=objParent.options[objParent.options.selectedIndex].value;
	objChild.length=0;
	objChild.length=eval('arrState'+nSel+'.length')/2;
	var i=0;
	
	for (i=0;i<eval('arrState'+nSel+'.length');i=i+2){
		objChild.options[i/2].value=eval('arrState'+nSel+'['+i+']');
		objChild.options[i/2].text=eval('arrState'+nSel+'['+(i+1)+']');
	};
	
	objChild.options.selectedIndex=0;
};

function fSelectState(objChild, selectValue){
    var i = 0;
    for (i = 0; i <  objChild.length; i++){
        if (objChild.options[i].value == selectValue){
            objChild.options.selectedIndex=i;
        }
    }
};

function fSelectSalesAction(){
   var i = 0;
   var selected = document.frmProspect.cmbSalesAction.options.selectedIndex - 1;

   if (selected >= 0) {
   document.getElementById('hiddenDiv').style.display = 'block';
   document.getElementById('hideSales').style.display = 'none';
   document.getElementById('removeSalesActionDiv').style.display = 'block';
       if (arrMandatoryDateFlags[selected]==0){
           document.frmProspect.dfSalesActionDate.style.background='#c0c0c0';
           document.frmProspect.dfSalesActionDate.disabled = true;
           document.frmProspect.dfSalesActionDate.value = '';
           dateIsMandatory = 0;
       } else {
           document.frmProspect.dfSalesActionDate.style.background='#ffffff';
           document.frmProspect.dfSalesActionDate.disabled = false;
           dateIsMandatory = 1;
       }
   } else {
   document.getElementById('hideSales').style.display = 'block';
   document.getElementById('removeSalesActionDiv').style.display = 'none';
         document.frmProspect.dfSalesActionDate.style.background='#c0c0c0';
         document.frmProspect.dfSalesActionDate.disabled = true;
         document.frmProspect.dfSalesActionDate.value = '';
         dateIsMandatory = 0;
   }
}  
   
function isValidForm(){
	var selected = document.frmProspect.cmbSalesAction.options.selectedIndex - 1;
	var dateField = document.frmProspect.dfSalesActionDate.value;
	var hourField = document.frmProspect.dfHour.value;
	
	if (arrMandatoryDateFlags[selected]==1){
		if(dateField ==''){
			alert('Date is Mandatory for the Sales Action you have Selected!');
			return false;
		}
		if(hourField ==''){
			alert('Hour is Mandatory for the Sales Action you have Selected!');
			return false;
    	}
    	return true;
    } else {
    	return true;		
    }
}

function updateCollection(){
	if(isValidForm()){
		document.frmProspect.submit();
	}
}

function removeSalesActionInfo(){
	document.frmProspect.cmbSalesAction.options.selectedIndex = 0;
	document.frmProspect.dfSalesActionDate.value = '';
	document.frmProspect.dfHour.value = '';
	document.frmProspect.dfMinute.value = '';
	document.frmProspect.dfActionNote.value = '';
	document.frmProspect.counter.value = 2000;
	document.getElementById('hideSales').style.display = 'block';
	document.getElementById('removeSalesActionDiv').style.display = 'none';
}

function fOnAddCampaign() {
    window.name = "mainWnd";
    var wndContacts = window.open('', 'wndCampaignAction', 'status=yes,scrollbars=yes,width=400,height=400,top=0,left=0, resizable=yes');
    document.frmProspect.target='wndCampaignAction';
    document.frmProspect.action='prospect';
    document.frmProspect.formAction.value="DISPLAY_SELECT_CAMPAIGN_COLLECTION";
    document.frmProspect.submit();
}

function fOnInsertIntoRoundRobin() {
    document.frmProspect.action='prospect';
    document.frmProspect.formAction.value="INSERT_ROUND_ROBIN_COLLECTION";
    document.frmProspect.submit();
}