function fOnAddContact()
{
    document.frmContacts.formAction.value="DISPLAY_INSERT";
    document.frmContacts.submit();
}
function fOnUpdateContact(contactId)
{
    document.frmContacts.formAction.value="DISPLAY_UPDATE";
    document.frmContacts.dfContactId.value=contactId;
    document.frmContacts.submit();
}

function fOnDeleteContact(contactId)
{
    document.frmContacts.formAction.value="DISPLAY_DELETE";
    document.frmContacts.dfContactId.value=contactId;
    document.frmContacts.submit();
}
function fProcessForm()
{
    with (document.frmContacts)
    {
        dfContactName.value=fElimChar(fElimChar(dfContactName.value,"'"),'"');
        dfContactTitle.value=fElimChar(fElimChar(dfContactTitle.value,"'"),'"');
        dfPhone.value=fElimChar(fElimChar(dfPhone.value,"'"),'"');
        dfPhoneExt.value=fElimChar(fElimChar(dfPhoneExt.value,"'"),'"');
        dfCellPhone.value=fElimChar(fElimChar(dfCellPhone.value,"'"),'"');
        dfEmail.value=fElimChar(fElimChar(dfEmail.value,"'"),'"');
        dfFax.value=fElimChar(fElimChar(dfFax.value,"'"),'"');
        if ((dfContactName.value == "") &&
            (dfContactTitle.value == "") &&
            (dfPhone.value == "") &&
            (dfPhoneExt.value == "") &&
            (dfCellPhone.value == "") &&
            (dfFax.value == "") &&
            (dfEmail.value == ""))
       {
            alert('Please enter the contact content.');
            return false;
        }
    }
    return true;
}

