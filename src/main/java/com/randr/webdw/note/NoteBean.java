package com.randr.webdw.note;

import java.math.BigDecimal;
import java.util.Vector;

import com.randr.webdw.mvcAbstract.AbstractDBBean;


/**
 */
public class NoteBean extends AbstractDBBean {
    /**
     * Constructor for NoteBean.
     */
    public NoteBean() {
    }

    /**
     * Method setTableNameAndAlias.
     */
    protected void setTableNameAndAlias() {
        this.table = "note";
        this.tableAlias = "nt";
    }

    /**
     * Method loadElements.
     * @param fillType BigDecimal
     * @param noteDetails NoteDetails
     * @return Vector
     * @throws Exception
     */
    public Vector loadElements(BigDecimal fillType, NoteDetails noteDetails) throws Exception {
        // establish search conditions
        establishSearchConditions(noteDetails);

        // establish the list of properties to be populated
        addColumnList("prospect_id, note_id, user_name, note_date, note");
        setOrderBy("note_date desc , user_name");
        return doSelect(noteDetails);
    }

    /**
     * Method establishSearchConditions.
     * @param noteDetails NoteDetails
     */
    private void establishSearchConditions(NoteDetails noteDetails) {

        if (noteDetails.getProspectId() != null) {
            addCondition("prospect_id= ?");
            addConditionParam(noteDetails.getProspectId());
        }

        if (noteDetails.getUserName() != null) {
            addCondition("user_name = ?");
            addConditionParam(noteDetails.getUserName());
        }

        if (noteDetails.getNoteId() != null) {
            addCondition("note_id = ?");
            addConditionParam(noteDetails.getNoteId());
        }

        if (noteDetails.getProspectIdsVector() != null
                && noteDetails.getProspectIdsVector().size() > 0) {
            String condition = " prospect_id in (";
            for (int i = 0; i < noteDetails.getProspectIdsVector().size(); i++) {
                BigDecimal prospectId = (BigDecimal) noteDetails.getProspectIdsVector().elementAt(i);
                if (i > 0) {
                    condition += ",";
                }
                condition += prospectId.toString();
            }
            condition += ")";
            addCondition(condition);
        }

    }

    /**
     * Method insert.
     * @param fillType BigDecimal
     * @param noteDetails NoteDetails
     * @return NoteDetails
     * @throws Exception
     */
    public NoteDetails insert(BigDecimal fillType, NoteDetails noteDetails) throws Exception {
        noteDetails.setNoteId(getAvailableID("note_id", "note"));
        addColumnList("prospect_id, note_id, user_name, note_date, note");

        return (NoteDetails) doInsert(noteDetails);
    }

    /**
     * Method update.
     * @param fillType BigDecimal
     * @param noteDetails NoteDetails
     * @return NoteDetails
     * @throws Exception
     */
    public NoteDetails update(BigDecimal fillType, NoteDetails noteDetails) throws Exception {
        addCondition("note_id = ? ");
        addConditionParam(noteDetails.getNoteId());

        addColumnList("note");

        return (NoteDetails) doUpdate(noteDetails);
    }

    /**
     * Method delete.
     * @param fillType BigDecimal
     * @param noteDetails NoteDetails
     * @return boolean
     * @throws Exception
     */
    public boolean delete(BigDecimal fillType, NoteDetails noteDetails) throws Exception {
        if (noteDetails.getProspectId() != null) {
            addCondition("prospect_id=?");
            addConditionParam(noteDetails.getProspectId());
        }

        if (noteDetails.getNoteId() != null) {
            addCondition("note_id = ? ");
            addConditionParam(noteDetails.getNoteId());
        }
        
        if(noteDetails.getNoteId() != null || noteDetails.getProspectId() != null){
            doDelete(noteDetails);
            return true;
        }else{
        	return false;
        }
    }

}
