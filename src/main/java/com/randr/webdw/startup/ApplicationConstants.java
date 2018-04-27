package com.randr.webdw.startup;

import com.randr.webdw.AppSettings;

public interface ApplicationConstants {
	
	public static final String MESSAGE_NAME = "applicationMessage";
	public static final Integer MESSAGE_DB_CONNECTION_PROBLEM = new Integer(1);
	public static final Integer MESSAGE_INITIAL_START_UP = new Integer(2);
	public static final String DATABASE_CREATION_FOLDER = "db_scripts/initial_database_scripts";
	public static final String DATABASE_UPDATE_FOLDER = "db_scripts/update_wizard_scripts";
	public static final String TABLE_SCRIPT = "1_table_script.sql";
	public static final String ALTER_SCRIPT = "2_alter_script.sql";
	public static final String INDEX_SCRIPT = "3_index_script.sql";
	public static final String INITIAL_DATA_SCRIPT = "4_initial_data_script.sql";

	
	//Next constants for Update Wizard
	public static final String UPDATE_FROM_1_0 = "update_from_1.0.sql";
	public static final String UPDATE_FROM_1_1 = "update_from_1.1.sql";
	public static final String UPDATE_FROM_1_1_1 = "update_from_1.1.1.sql";
	public static final String UPDATE_FROM_1_1_2 = "update_from_1.1.2.sql";
	public static final String UPDATE_FROM_1_1_3 = "update_from_1.1.3.sql";
	public static final String UPDATE_FROM_1_1_4 = "update_from_1.1.4.sql";
	public static final String UPDATE_FROM_1_2_0 = "update_from_1.2.0.sql";
	public static final String UPDATE_FROM_1_2_3 = "update_from_1.2.3.sql";
	public static final String UPDATE_FROM_1_2_5 = "update_from_1.2.5.sql";

}
