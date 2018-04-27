package com.randr.webdw.background;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ImportBackgroundResult {
	
	public static final int IMPORT_TYPE_PROSPECT = 1;
	public static final int IMPORT_TYPE_EMAIL_OPTOUT = 2;
	//3 = email opt out?
	public static final int IMPORT_TYPE_OEPINVOICE = 4;
	public static final int IMPORT_TYPE_OPORDER = 5;
	
	//***** if we add any additiona import type, we have to add an entry into the importTypeDescription arrray below or we error out
	
	
	private List importProcessorResultList;
	private Timestamp importProcessDate;
	private int importType;
	private String[] importTypeDescription = {"Prospects, EmailOptOut, Type3NotUsed, OEPInvoice, OPOrder"};
	
	public ImportBackgroundResult() {
		importProcessorResultList = new ArrayList();
	}

	public Timestamp getImportProcessDate() {
		return importProcessDate;
	}

	public void setImportProcessDate(Timestamp importProcessDate) {
		this.importProcessDate = importProcessDate;
	}

	public List getImportProcessorResultList() {
		return importProcessorResultList;
	}

	public void setImportProcessorResultList(List importProcessorResultList) {
		this.importProcessorResultList = importProcessorResultList;
	}

	public String getImportTypeDescription(int importType) {
		return importTypeDescription[importType-1];
	}

	public void addImportProcessorResult(Object obj) {
		importProcessorResultList.add(obj);
	}

	public int getImportType() {
		return importType;
	}

	public void setImportType(int importType) {
		this.importType = importType;
	}
}
