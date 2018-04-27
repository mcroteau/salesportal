function openHTMLEditor(fieldType,fieldName,isCommonElement){
    if (navigator.appName!='Microsoft Internet Explorer'){
        alert ('The Preview is available only for Microsoft Internet Explorer browsers!');
    }
    else{
        wEditor = window.open('config?formAction=DISPLAY_EDITOR&fieldType='+fieldType+'&fieldName='+fieldName+'&isCommonElement='+isCommonElement,'wEditor','width=800,height=600,left=0,top=0,screenX=0,screenY=0,outerwidth=800,outerheight=600,toolbar=no,menubar=no');
        wEditor.opener=self;
        wEditor.focus();
    }
}

function openEkitEditor(fieldType,fieldName,isCommonElement){
    if (navigator.appName!='Microsoft Internet Explorer'){
        alert ('The Preview is available only for Microsoft Internet Explorer browsers!');
    }
    else{
        wEditor = window.open('config?formAction=DISPLAY_EKIT_EDITOR&fieldType='+fieldType+'&fieldName='+fieldName+'&isCommonElement='+isCommonElement,'wEditor','width=800,height=600,left=0,top=0,screenX=0,screenY=0,outerwidth=800,outerheight=600,toolbar=no,menubar=no');
        wEditor.opener=self;
        wEditor.focus();
    }
}
