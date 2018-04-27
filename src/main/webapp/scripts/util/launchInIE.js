function launchApp(strCmdLine){
    try {
        var obj = new ActiveXObject("LaunchinIE.Launch");
        obj.LaunchApplication(strCmdLine);
    } catch (e){
        var browserName=navigator.appName;
        if (browserName=="Microsoft Internet Explorer") {
            if (confirm('The phone dialer ActiveX is not (correctly) installed on your PC.\nWould you like to install it?')){
                window.location = downloadLocation;
                //downloadLocation must be a global variabled declared onthe screen where this script is called from
            }
        } else {
            alert('The Phone Dialer can be runned from the web only if you are using Microsoft Internet Explorer.');
        }
    }
}
