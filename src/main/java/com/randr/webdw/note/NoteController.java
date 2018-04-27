package com.randr.webdw.note;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import com.randr.webdw.mvcAbstract.AbstractController;
import com.randr.webdw.mvcAbstract.exception.ModelException;
import com.randr.webdw.prospect.ProspectDetails;
import com.randr.webdw.prospect.ProspectView;
import com.randr.webdw.util.DateUtilities;


/**
 */
public class NoteController extends AbstractController {
    /**
     * Method doAction.
     * @throws ServletException
     * @throws IOException
     */
    public void doAction() throws ServletException, IOException {
        if ((formAction == null) || (formAction.equals("DISPLAY"))) {
            displayNotes();
        } else if (formAction.equals("INSERT")) {
        	insertNote();
        } else if (formAction.equals("UPDATE")) {
            updateNote();
        } else if (formAction.equals("DELETE")) {
            deleteNote();
        } else if (formAction.equals("DISPLAY_INSERT")) {
            displayInsertNote();
        } else if (formAction.equals("DISPLAY_UPDATE")) {
            displayUpdateNote();
        } else if (formAction.equals("DISPLAY_DELETE")) {
            displayDeleteNote();
        }
    }

    /**
     * Method displayInsertNote.
     */
    private void displayInsertNote() {
        try {
            request.getRequestDispatcher("/jsp/shared/note/displayInsertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayUpdateNote.
     */
    private void displayUpdateNote() {
        try {
            openConnection();
            NoteDetails noteDetails = new NoteDetails();
            noteDetails.setNoteId(new BigDecimal(request.getParameter("dfNoteId")));
            NoteView noteView = new NoteView();
            noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
            if (noteView.getElements().size() == 1) {
                noteDetails = (NoteDetails) noteView.getElements().elementAt(0);
                request.setAttribute("noteDetails", noteDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/note/displayInsertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayDeleteNote.
     */
    private void displayDeleteNote() {
        try {
            openConnection();
            NoteDetails noteDetails = new NoteDetails();
            noteDetails.setNoteId(new BigDecimal(request.getParameter("dfNoteId")));
            NoteView noteView = new NoteView();
            noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
            if (noteView.getElements().size() == 1) {
                noteDetails = (NoteDetails) noteView.getElements().elementAt(0);
                request.setAttribute("noteDetails", noteDetails);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/note/displayInsertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


    /**
     * Method insertNote.
     */
    private void insertNote() {
        try {
            openConnection();

            NoteDetails noteDetails = new NoteDetails();
            NoteView noteView = new NoteView();
            try {
                noteDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
                // have to add user offset to teh time
                noteDetails.setNoteDate(DateUtilities.getCurrentSQLTimestamp());
                noteDetails.setUserName(this.userProfile.getUserName());
                noteDetails.setNote(request.getParameter("dfNote"));

                noteView.setConnection(connection);
                noteView.setAction("INSERT");
                noteView.setFillType(NoteDetails.FILL_TYPE_ALL);
                noteView.setCurrent(noteDetails);
                noteView.doAction();

                //also, change the prospect Update Date and User
                ProspectDetails prospectUpdateDetails = new ProspectDetails();
                ProspectView prospectUpdateView = new ProspectView();
                prospectUpdateDetails.setProspectId(noteDetails.getProspectId());
                prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
                if(prospectUpdateView.getElements().size()>0){
                	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
                	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
                	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
                }
                
                ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");

                if (sessionProspectView != null) {
                    int index = sessionProspectView.getElementIndex(noteDetails.getProspectId());
                    if (index >= 0) {
                        ProspectDetails prospectDetails = (ProspectDetails) sessionProspectView.getElements().elementAt(index);
                        //change update date and user for view:
                        prospectDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
                        prospectDetails.setChangeUserId(userProfile.getUserId());
                        if(prospectDetails.getCountNotes() == null){
                        	prospectDetails.setCountNotes(new Integer(1));
                        }else{
                            prospectDetails.setCountNotes(new Integer(prospectDetails.getCountNotes().intValue() + 1));                       	
                        }
                    }
                }
                commit();
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }

            request.getRequestDispatcher("/jsp/shared/note/insertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method updateNote.
     */
    private void updateNote() {
        try {
            openConnection();

            NoteDetails noteDetails = new NoteDetails();
            NoteView noteView = new NoteView();

            noteDetails.setNoteId(new BigDecimal(request.getParameter("dfNoteId")));
            noteDetails.setNoteDate(DateUtilities.getCurrentSQLTimestamp());
            noteDetails.setUserName(this.userProfile.getUserName());
            noteDetails.setNote(request.getParameter("dfNote"));

            noteView.setConnection(connection);
            noteView.setAction("UPDATE");
            noteView.setFillType(NoteDetails.FILL_TYPE_ALL);
            noteView.setCurrent(noteDetails);
            noteView.doAction();
            
            //also, change the prospect Update Date and User
            ProspectDetails prospectUpdateDetails = new ProspectDetails();
            ProspectView prospectUpdateView = new ProspectView();
            prospectUpdateDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));
            prospectUpdateView.fillWithElements(connection, ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            if(prospectUpdateView.getElements().size()>0){
            	prospectUpdateDetails = (ProspectDetails)prospectUpdateView.getElement(0);
            	prospectUpdateDetails.setChangeDate(DateUtilities.getCurrentSQLTimestamp());
            	prospectUpdateDetails.setChangeUserId(userProfile.getUserId());
            	prospectUpdateView.doAction(connection, "UPDATE", ProspectView.FILL_TYPE_BASIC, prospectUpdateDetails);
            }

            commit();
            request.getRequestDispatcher("/jsp/shared/note/insertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method deleteNote.
     */
    private void deleteNote() {
        try {
            openConnection();

            NoteDetails noteDetails = new NoteDetails();
            NoteView noteView = new NoteView();

            noteDetails.setNoteId(new BigDecimal(request.getParameter("dfNoteId")));

            noteView.setConnection(connection);
            noteView.setAction("DELETE");
            noteView.setFillType(NoteDetails.FILL_TYPE_ALL);
            noteView.setCurrent(noteDetails);
            noteView.doAction();
            ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");

            if (sessionProspectView != null) {
                int index = sessionProspectView.getElementIndex(new BigDecimal(request.getParameter("dfProspectId")));

                if (index >= 0) {
                    ProspectDetails prospectDetails = (ProspectDetails) sessionProspectView.getElements().elementAt(index);
                    prospectDetails.setCountNotes(new Integer(prospectDetails.getCountNotes().intValue() - 1));
                }
            }

            commit();
            request.getRequestDispatcher("/jsp/shared/note/insertUpdateNote.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }

    /**
     * Method displayNotes.
     */
    private void displayNotes() {
        try {
            openConnection();
            try {
                ProspectView sessionProspectView = (ProspectView) request.getSession().getAttribute("prospectView");
                if (sessionProspectView != null) {
                    int elementIndex = sessionProspectView.getElementIndex(new BigDecimal(request.getParameter("dfProspectId")));
                    if (elementIndex >= 0) {
                        ProspectDetails prospectDetails = (ProspectDetails) ((ProspectDetails) sessionProspectView.getElements().elementAt(elementIndex));
                        request.setAttribute("prospectDetails", prospectDetails);
                    }
                }
                NoteView noteView = new NoteView();
                NoteDetails noteDetails = new NoteDetails();

                noteDetails.setProspectId(new BigDecimal(request.getParameter("dfProspectId")));

                noteView.fillWithElements(connection, NoteDetails.FILL_TYPE_ALL, noteDetails);
                if (noteView.getElements().size() < 1) {
                    throw new ModelException("No records found.", ModelException.RECORD_NOT_FOUND);
                }
                request.setAttribute("noteView", noteView);
            } catch (ModelException modelException) {
                request.setAttribute("modelException", modelException);
            }
            commit();
            request.getRequestDispatcher("/jsp/shared/note/displayNotes.jsp").forward(request, response);
        } catch (Exception e) {
            doCatch(e);
        } finally {
            finallyMethod();
        }
    }


}
