     function fReplace(obj, text1, text2){
        re= new RegExp(text1,'gi');
        return obj.replace(re,text2);
     };

     function fGetNrOfMatches(re,str){
        var found = str.match(re);
        if (found == null) return 0;
        else return found.length;
     };

// This function passes the htmlContent to the Ekit Editor Applet by
// calling a public method
     function establishBrowserContent() {
         var htmlContent = eval('window.opener.document.frmConfig.' + fieldType).value;
         htmlContent = fReplace(htmlContent, graphicsPath, graphicsCompletePath);
         document.applets.Ekit.setDocumentText(htmlContent);
     };


// This function is called from the Ekit Editor Applet
     function openUploadImageWindow(){
        www=window.open('upload?ActionType=5&PIC_TYPE=Picture','www','width=450,height=300,left=0,top=0,screenX=0,screenY=0,outerwidth=450,outerheight=300,toolbar=no,menubar=no');
        www.opener=self;
        www.focus();
     };

// This function calls a method in the Ekit Editor Applet
// that returns the document body
     function fOnClick(){
        try{
            var htmlContent = document.applets.Ekit.getDocumentBody();
        }
        catch (e){
            alert('Please wait until the HTML preview is completely loaded!');
            return false;
        };

        if (isCommonElement == 1){
            re = /\[PAGE_CONTENT\]/gi;
            var nr = fGetNrOfMatches(re,htmlContent);
            if (nr==0){
                alert('Please specify the [PAGE_CONTENT] keyword somewhere in the editor field. This keyword will be used in order to identify those parts of each page that does not belong to the General Page Layout!');
                return false;
            };
            if (nr!=1){
                alert('The [PAGE_CONTENT] keyword must appear only once!');
                return false;
            };
        };

        htmlContent = fReplace( htmlContent, graphicsCompletePath, graphicsPath);
        eval('window.opener.document.frmConfig.' + fieldType).value = htmlContent;
        window.close();
     };
