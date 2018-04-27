package com.randr.webdw.note;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 */
public class NoteDetails extends AbstractDetails {
    protected BigDecimal prospectId;
    protected BigDecimal noteId;
    protected String userName;
    protected Timestamp noteDate;
    protected String note;

    protected Vector prospectIdsVector;
    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Constructor for NoteDetails.
     */
    public NoteDetails() {
    }

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("prospect_id", "prospectId");
        addMapping("note_id", "noteId");
        addMapping("user_name", "userName");
        addMapping("note_date", "noteDate");
        addMapping("note", "note");
    }

    /**
     * Method getProspectId.
     * @return BigDecimal
     */
    public BigDecimal getProspectId() {
        return prospectId;
    }

    /**
     * Method setProspectId.
     * @param prospectId BigDecimal
     */
    public void setProspectId(BigDecimal prospectId) {
        this.prospectId = prospectId;
    }

    /**
     * Method getNoteId.
     * @return BigDecimal
     */
    public BigDecimal getNoteId() {
        return noteId;
    }

    /**
     * Method setNoteId.
     * @param noteId BigDecimal
     */
    public void setNoteId(BigDecimal noteId) {
        this.noteId = noteId;
    }

    /**
     * Method getUserName.
     * @return String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Method setUserName.
     * @param user String
     */
    public void setUserName(String user) {
        this.userName = user;
    }

    /**
     * Method getNoteDate.
     * @return Timestamp
     */
    public Timestamp getNoteDate() {
        return noteDate;
    }

    /**
     * Method setNoteDate.
     * @param noteDate Timestamp
     */
    public void setNoteDate(Timestamp noteDate) {
        this.noteDate = noteDate;
    }

    /**
     * Method getNote.
     * @return String
     */
    public String getNote() {
        return note;
    }

    /**
     * Method setNote.
     * @param note String
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * Method getUserAndDate.
     * @return String
     */
    public String getUserAndDate() {
        return this.userName.toString() + "#" + String.valueOf(this.noteDate.getTime());
    }

    /**
     * Method getProspectIdsVector.
     * @return Vector
     */
    public Vector getProspectIdsVector() {
        return prospectIdsVector;
    }

    /**
     * Method setProspectIdsVector.
     * @param prospectIdsVector Vector
     */
    public void setProspectIdsVector(Vector prospectIdsVector) {
        this.prospectIdsVector = prospectIdsVector;
    }

}
