<%@ page import="com.randr.webdw.AppSettings,
                 com.randr.webdw.user.UserDetails,
                 com.randr.webdw.label.LabelView"%>
                 
<jsp:useBean id="userProfile" class="com.randr.webdw.user.UserProfile" scope="session"/>
<jsp:useBean id="labelView" class="com.randr.webdw.label.LabelView" scope="application"/>

<% try { %>
<%=AppSettings.getHeader(request, userProfile, AppSettings.getParm("COMPANY_NAME") + ": Admin", "","", "")%>

     	<link rel="stylesheet" type="text/css" href="<%=AppSettings.getAppWebPath()%>css/<%=AppSettings.getParm("ADMIN_SITE_CSS")%>">

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/shared.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

	 	<script language="JavaScript" src="<%=AppSettings.getAppWebPath()%>scripts/admin.js?Timestamp=<%=String.valueOf((new java.util.Date()).getTime())%>">
	 	</script>

        <script>
            function openAdminPage(page, attributes, formActionVal) {
                window.name='wndMainAdmin';
                if (attributes==''){
                    attributes='resizable=yes,toolbar=yes,location=no,status=yes,scrollbars=yes';
                }

                if (navigator.appName=='Netscape'){
                    attributes += ',scrollbars=yes';
                }

                var wAdmin = window.open('',page,attributes);
                wAdmin.opener=window;
                document.frmAdmin.action='<%=AppSettings.getAppCompleteWebPath(request)%>admin/' + page;
                document.frmAdmin.formAction.value=formActionVal;

                document.frmAdmin.target = page;
                document.frmAdmin.submit();
                wAdmin.focus();
            }

        </script>
</head>

<body>

    <center>


    <form name="frmAdmin" method="get" action="">
        <input type=hidden name="formAction" value="">
        <input type=hidden name="userType" value="<%=UserDetails.USER_TYPE_SALESMAN%>">
        <script language="JavaScript">
            var B = '';
            if (navigator.appName=='Netscape')
            {
                B = 'N';
            }
            document.write('<input type=hidden name="B" value="' + B + '">');
        </script>
    </form>

    <table>
    <tr>
    <td><h1>Administration Site</h1></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('company','width=400,height=300','DISPLAY_INSERT')">Create New Company</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('company','','DISPLAY')">Display/Update Companies</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('territory','width=800,height=800,resizable=yes','DISPLAY_INSERT')">Create New Territory</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('territory','width=900,height=800','DISPLAY')">Display/Update Territories</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('lob','width=400,height=300','DISPLAY_INSERT')">Create New Line Of Business</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('lob','','DISPLAY')">Display/Update Lines Of Business</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('country','width=400,height=300','DISPLAY_INSERT')">Create New Country</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('country','','DISPLAY')">Display/Update Countries</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('state','width=400,height=300','DISPLAY_INSERT')">Create New State</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('state','','DISPLAY_SEARCH')">Display/Update States</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('status','width=400,height=300','DISPLAY_INSERT')">Create Status Code</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('status','','DISPLAY')">Display/Update Status Codes</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('verified','width=400,height=300','DISPLAY_INSERT')">Create Verification Status</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('verified','','DISPLAY')">Display/Update Verification Statuses</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('system_type','width=400,height=300','DISPLAY_INSERT')">*Create <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('system_type','','DISPLAY')">*Display/Update <%=labelView.getLabel(LabelView.USER_DROPDOWN_1)%></a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('software_type','width=400,height=300','DISPLAY_INSERT')">*Create <%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('software_type','','DISPLAY')">*Display/Update <%=labelView.getLabel(LabelView.USER_DROPDOWN_2)%></a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>
        <tr>
            <td><a href="javascript:openAdminPage('size','width=400,height=300','DISPLAY_INSERT')">Create Size</a></td>
        </tr>
        <tr>
            <td><a href="javascript:openAdminPage('size','','DISPLAY')">Display/Update Sizes</a></td>
        </tr>

        <tr><td>&nbsp;</td></tr>
  <tr>
        <td><a href="javascript:openAdminPage('currency','width=400,height=300','DISPLAY_INSERT')">Create New Currency Code</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('currency','','DISPLAY')">Display/Update Curency Codes</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        
        <td><a href="javascript:openAdminPage('sales_action','width=400,height=300','DISPLAY_INSERT')">Create Sales Action</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('sales_action','','DISPLAY')">Display/Update Sales Actions</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        
        <td><a href="javascript:openAdminPage('email_sales_action','width=850,height=650','DISPLAY_INSERT')">Create Email Sales Action</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('email_sales_action','','DISPLAY')">Display/Update Email Sales Actions</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

  	<tr>
        <td><a href="javascript:openAdminPage('campaign','width=400,height=300','DISPLAY_INSERT')">Create a Campaign</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('campaign','','DISPLAY')">Display/Update Campaign</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>
	<tr>
       <td><a href="javascript:openAdminPage('email_campaign','width=850,height=650','DISPLAY_INSERT')">Create Automated Campaign</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('email_campaign','','DISPLAY')">Display/Update Automated Campaigns</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
        <td><a href="javascript:openAdminPage('user','width=900,height=1000,resizable=yes,toolbar=yes,location=no,status=yes,scrollbars=yes','DISPLAY_INSERT')">Create Salesman</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('user','width=1400, height=600, resizable=yes,toolbar=yes,location=no,status=yes,scrollbars=yes','DISPLAY')">Display Salesmen</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

  	<tr>
        <td><a href="javascript:openAdminPage('email_template','width=500,height=500','DISPLAY_INSERT')">Create an Email Template</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('email_template','','DISPLAY')">Display/Update Email Templates</a></td>
    </tr>
 <tr><td>&nbsp;</td></tr>
    <tr>
        <td><a href="javascript:openAdminPage('commision','width=500,height=600,resizable=yes,toolbar=yes,location=no,status=yes,scrollbars=yes','DISPLAY_INSERT')">Create Revenue/Commission</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('commision','','DISPLAY_SEARCH')">Display Revenue/Commissions</a></td>
    </tr>

    <tr><td>&nbsp;</td></tr>

    <tr>
    <td><a href="javascript:openAdminPage('custom_queries','','DISPLAY_UPLOAD')">Upload custom query</a></td>
    </tr>
    <tr>
    <td><a href="javascript:openAdminPage('custom_queries','','DISPLAY_QUERIES')">Update/execute custom queries</a></td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    <tr>
        <td><a href="javascript:openAdminPage('import_data','width=1000,height=900,resizable=yes,location=no,status=yes,scrollbars=yes','DISPLAY_UPLOAD')">Import Data</a></td>
    </tr>
    <tr>
        <td><a href="javascript:openAdminPage('import_data','width=700,height=700,resizable=yes,location=no,status=yes,scrollbars=yes','DISPLAY_DELETE')">Delete Imported Data</a></td>
    </tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
        <td><a href="javascript:openAdminPage('label','width=600,height=500,resizable=yes,location=no,status=yes,scrollbars=yes','DISPLAY')">*Update User Defined Fields</a></td>
    </tr>
    <tr><td>&nbsp;</td></tr>
    </table>

    </center><CENTER>* User Defined Fields</CENTER>
<%=AppSettings.getFooter(userProfile)%>
<% } catch (Exception e) { %>
<b>Error:</b> <%=e.getMessage()%>
<% } %>