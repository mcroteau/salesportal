package com.randr.webdw.note;

import com.randr.webdw.mvcAbstract.AbstractView;


/**
 */
public class NoteView extends AbstractView {
    /**
     * Constructor for NoteView.
     */
    public NoteView() {
    }

    /**
     * Method setBeanClassName.
     */
    protected void setBeanClassName() {
        this.beanClassName = "com.randr.webdw.note.NoteBean";
    }

    /**
     * Method setDetailClassName.
     */
    protected void setDetailClassName() {
        this.detailClassName = "com.randr.webdw.note.NoteDetails";
    }
}
