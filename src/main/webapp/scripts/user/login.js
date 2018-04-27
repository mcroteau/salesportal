function fValidLoginForm(){
	if (document.frmLogin.userName.value=='') {
   		alert('Please specify your User Name.'); 
   		
   		loginClicked = false; 
   		fDisplayLoading();
   		return false
    }
   
   	if (document.frmLogin.password.value=='') {
   		alert('Please specify your Password.'); 
   		
   		loginClicked = false; 
   		fDisplayLoading();
   		return false
    }
    return true;
}
     
function fDisplayLoading(){
	if( document.getElementById("loading").style.display=='none' ){
  		document.getElementById("loading").style.display = '';
	}else{
  		document.getElementById("loading").style.display = 'none';
	}
}

function fShowError(){
	if( document.getElementById("loginError").style.display=='none' ){
  		document.getElementById("loginError").style.display = '';
	}else{
  		document.getElementById("loginError").style.display = 'none';
	}
}

function keydown(e){
     if (navigator.appName == 'Netscape'){
		if (okToLogin) {
  			if (e.which == 13){
    			document.frmLogin.submit();
  			}
		}
     } else {
		if ((event.keyCode==13) && (okToLogin==false)) {
  			return false;
		}
     }
}
