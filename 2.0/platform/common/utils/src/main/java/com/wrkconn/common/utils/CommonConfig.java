package com.wrkconn.common.utils;

public class CommonConfig {

	/**
	 * PROPERTY NAMES
	 */

	/**
	 * DB/MONGO RELATED CONFIGURATION
	 */
	public static final String P_MONGO_HOST_ADDRESS = "mongo.host";
	public static final String P_MONGO_HOST_PORT = "mongo.port";
	public static final String P_MONGO_USERNAME = "mongo.username";
	public static final String P_MONGO_PASSWORD = "mongo.password";
	public static final String P_MONGO_DB_NAME = "mongo.dbname";
	public static final String P_DB_MAX_RETRIES = "db.max.retries";
	
	
	// Default settings that could be overriden by the local config file
	public static final int WAIT_MILLIS_BETWEEN_RETRIES = 2000;
	public static final long THIRTY_DAYS = 2592000000L; // 30*24*60*60*100;
	public static final long FOUR_YEARS = 126144000000L; // 4*365*24*60*60*1000;
	public static final long ONE_WEEK = 604800000L; // 7*24*60*60*1000;
	public static final long TWENTY_FOUR_HOURS = 86400000L; // 24*60*60*1000;
	public static final long TWO_HOURS = 7200000L; // 2*60*60*1000;
	public static final long TWO_MONTHS = 5356800000L ; // 2*31*24*60*60*1000;
	public static final long TWELVE_MONTHS = 31536000000L; // 365*24*60*60*1000;
	public static final long FORTY_EIGHT_HOURS = 172800000L; // 48*60*60*1000;
	public static final long THREE_DAYS = 259200000L; // 3*24*60*60*1000;
	public static final long THREE_MONTHS = 7862400000L; // 91*24*60*60*1000
	
	/*
	 * Database related config
	 */
	public static final String AUDIT_LOG_NAME = "audit_log";
    public static final int COLLECTION_TYPE_ENTITY = 1;
	public static final String DB_COUNTERS = "db_counters";
	public static final String DB_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	
	/*
	 * DB_CONFIG RELATED CONFIG
	 */
	public static final String DB_CONFIG = "db_config";
	public static final String DB_CONFIG_TYPE = "type";
	public static final String DB_CONFIG_KEY = "key";
	public static final String DB_CONFIG_KEY_PARAM_NAME = "key_param_name";

	public static int ORDER_BY_DESC = -1;
	public static int ORDER_BY_ASC = 1;
	public static int LIMIT_RESULTS_TO = 10;

    /*
     * Entity prefixes
     */
    public static final String ENTITY_USERS_ID_PREFIX = "users";
    public static final String ENTITY_STATES_ID_PREFIX = "states";
    public static final String ENTITY_PROGRAMS_ID_PREFIX = "programs";
    public static final String ENTITY_ELIGIBILITY_CRITIERIA_ID_PREFIX = "eligibility_criteria";
}
