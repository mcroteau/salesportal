package com.randr.webdw.label;

import java.math.BigDecimal;

import com.randr.webdw.mvcAbstract.AbstractDetails;


/**
 * @author randr
 * @version $Revision$
 */
public class LabelDetails extends AbstractDetails {
    protected BigDecimal labelId;
    protected String labelDefault;
    protected String labelActual;
    protected String labelShort;

    public static final BigDecimal FILL_TYPE_ALL = new BigDecimal(1);

    /**
     * Method loadMapping.
     */
    protected void loadMapping() {
        addMapping("label_id", "labelId");
        addMapping("label_default", "labelDefault");
        addMapping("label_actual", "labelActual");
        addMapping("label_short", "labelShort");
    }

	public String getLabelActual() {
		return labelActual;
	}

	public void setLabelActual(String labelActual) {
		this.labelActual = labelActual;
	}

	public String getLabelDefault() {
		return labelDefault;
	}

	public void setLabelDefault(String labelDefault) {
		this.labelDefault = labelDefault;
	}

	public BigDecimal getLabelId() {
		return labelId;
	}

	public void setLabelId(BigDecimal labelId) {
		this.labelId = labelId;
	}

	public String getLabelShort() {
		return labelShort;
	}

	public void setLabelShort(String labelShort) {
		this.labelShort = labelShort;
	}

}
