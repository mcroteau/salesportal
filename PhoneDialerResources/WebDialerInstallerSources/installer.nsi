!define VERSION "1.0.0"
!define NAME "WebDialer"
!define ICON_NAME "WebDialer"
!define FOLDER_NAME "WebDialer"
!define REG_LAUNCHINIE_KEY "RockinFewl\LaunchinIE\Approved"
!define REG_LAUNCHINIE_MAIN_KEY "RockinFewl"

Name "${NAME} ${VERSION}"
OutFile "${NAME}.exe"

#----------------------
#Install
#----------------------

Function .onInit
  #MessageBox MB_YESNO "This will install ${NAME}. Do you wish to continue?" IDYES go
  #  Abort
  #go:
FunctionEnd

Function .onInstSuccess
    MessageBox MB_YESNO "${NAME} is now installed. View readme?" IDNO NoReadme
      Exec "notepad.exe $INSTDIR\readme.txt"
    NoReadme:
FunctionEnd
  
XPStyle on
CRCCheck on

Caption "Installing ${NAME} ${VERSION}"

InstallDir "$PROGRAMFILES\${FOLDER_NAME}"

DirText "Install directory"
#DirShow show


ShowInstDetails show


Page directory
Page instfiles

#----------------------
#Main section
#----------------------
Section "WebDialer"

  SectionIn RO
  
  SetOutPath $INSTDIR  
  File /r _files\readme.txt
  
  SetOutPath $SYSDIR  
  File /r res\dial.exe
  File /r res\launchinIE.dll
  RegDLL $SYSDIR\launchinIE.dll
  
 
  DetailPrint "Creating start menu entries ..."
  	
  CreateDirectory "$SMPROGRAMS\${FOLDER_NAME}"
	
  CreateShortCut "$SMPROGRAMS\${FOLDER_NAME}\Uninstall.lnk" "$INSTDIR\uninstall.exe" "" "$INSTDIR\uninstall.exe" 0
  CreateShortCut "$SMPROGRAMS\${FOLDER_NAME}\Readme.lnk" "$INSTDIR\readme.txt" "" "$INSTDIR\readme.txt" 0
  	
  
  #create the uninstaller
  WriteUninstaller $INSTDIR\uninstall.exe
  	
  #add the info to ther registry
WriteRegStr HKLM "SOFTWARE\${REG_LAUNCHINIE_KEY}" "url1" "http://www.randrinc.com"
WriteRegStr HKLM "SOFTWARE\${REG_LAUNCHINIE_KEY}" "url2" "http://10.1.1.1"

  
SectionEnd




#----------------------
#Uninstall
#----------------------

Function un.onInit
    MessageBox MB_YESNO "This will uninstall ${NAME}. Continue?" IDYES NoAbort
        Abort ; causes uninstaller to quit.
    NoAbort:
FunctionEnd

Section "Uninstall"

 UnRegDll $SYSDIR\launchinIE.dll
  
  #delete the application's files
  Delete "$INSTDIR\Uninst.exe" ; delete self (see explanation below why this works)
  Delete "$INSTDIR\*"
  Delete "$SYSDIR\dial.exe"	
  Delete "$SYSDIR\launchinIE.dll"	
    
  Delete "$SMPROGRAMS\${FOLDER_NAME}\*"

  #remove the folders created by this app
  RMDir "$SMPROGRAMS\${FOLDER_NAME}"
  
  RMDir "$INSTDIR"
  
  #delete the registry keys
  DeleteRegKey HKLM "SOFTWARE\${REG_LAUNCHINIE_MAIN_KEY}"

SectionEnd
