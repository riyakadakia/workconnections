package com.wrkconn.common.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Logger;

import com.wrkconn.common.utils.exceptions.DbException;
import com.wrkconn.common.utils.exceptions.InvalidInputException;
import com.wrkconn.common.utils.exceptions.CounterIncrementException;
import com.wrkconn.common.utils.exceptions.CounterInitializationException;
import com.wrkconn.common.utils.CommonConfig;
import com.wrkconn.common.utils.LoggerUtil;
import com.wrkconn.common.utils.StringUtil;


public class DbCounters {

	// local logger object
	static Logger _log = LogManager.getLogger(DbMgr.class);
	static DbInstance _dbCounters = null;

	static {
		try {
			_dbCounters = DbMgr.initDb(CommonConfig.COLLECTION_TYPE_ENTITY, CommonConfig.DB_COUNTERS);
			
			// Call the various initCounter methods
			initCounter(CommonConfig.ENTITY_USERS_ID_PREFIX);
			initCounter(CommonConfig.ENTITY_STATES_ID_PREFIX);
			initCounter(CommonConfig.ENTITY_PROGRAMS_ID_PREFIX);
			initCounter(CommonConfig.ENTITY_ELIGIBILITY_CRITIERIA_ID_PREFIX);
			
		} catch (InvalidInputException e) {
			_log.error("DbCounters: InvalidInputException while trying to initDb the db_counters database");
			throw new RuntimeException("DbCounters: InvalidInputException while trying to initDb the db_counters database");
		} catch (DbException e) {
			_log.error("DbCounters: DbException while trying to initDb the db_counters database");
			throw new RuntimeException("DbCounters: DbException while trying to initDb the db_counters database");
		} catch (CounterInitializationException e) {
			_log.error("DbCounters: CounterInitializationException while trying to initDb the db_counters database");
			throw new RuntimeException("DbCounters: CounterInitializationException while trying to initDb the db_counters database");
		}
	}

	private static void initCounter(String type) 
		throws CounterInitializationException {
		
		initCounter(type, 1000);
	}
	
	private static void initCounter(String type, int initCounterValue) 
		throws CounterInitializationException {

		List<JSONObject> retList = null;

		if (StringUtil.isNullOrEmpty(type)) {
			_log.info("initCounter: type is either null or empty. Nothing to initialize");
			return;
		}
		
		// check if the counter of this "type" exists
		JSONObject query = new JSONObject();
		try {
			query.put("type", type);
			retList = DbMgr.findDb(_dbCounters, query);

			if ((retList == null) || (retList.size() == 0)) {
				_log.info("initCounter: Nothing returned from database for type : " + type + ". Creating initial entry");

				// create the initial db record
				if (_dbCounters != null) {
					Map<String, Object> initObj = new HashMap<String, Object>();
					try {
						initObj.put("type", type);
						initObj.put("seq", initCounterValue);
						DbMgr.insertDb(_dbCounters, initObj);
						_log.info("initCounter: initialized counter for type: " + type);
					} catch (InvalidInputException e) {
						_log.error("initCounter: InvalidInputException while trying to insert the " + type + " initObj in database");
						throw new CounterInitializationException("initCounter: InvalidInputException while trying to insert the " + type + " initObj in databasee");
					} catch (ClassCastException | NullPointerException | IllegalArgumentException | UnsupportedOperationException e) {
						_log.error("toMap: Exception while trying to put items in Map");
						LoggerUtil.logException(_log, e);
						throw new CounterInitializationException("initCounter: Exception while trying to insert the " + type + " initObj in databasee");
					}
				}
			} else {
				_log.info("initCounter: Number of entries returned from database for type : " + type + " = " + retList.size());
			}

		} catch (InvalidInputException e) {
			_log.error("initCounter: InvalidInputException while trying to query the " + type + " initObj in database");
			throw new CounterInitializationException("initCounter: InvalidInputException while trying to query the " + type + " initObj in databasee");
		} catch (JSONException e) {
			_log.error("initCounter: JSONException while trying to query the initial " + type + " entry in the database");
			throw new CounterInitializationException("initCounter: JSONException while trying to query the initial " + type + " entry in the database");
		} catch (DbException e) {
			_log.error("initCounter: DbException while trying to query the initial " + type + " entry in the database");
			throw new CounterInitializationException("initCounter: DbException while trying to query the initial " + type + " entry in the database");
		}

	}

	public static int getNextSequence(String type) 
			throws CounterIncrementException {

		if (_dbCounters == null) {
			_log.error("getNextSequence: DbCounters is not initialized.");
			throw new CounterIncrementException("getNextSequence: DbCounters is not initialized.");
		}
		
		int ret = -1;
		try {
			JSONObject query = new JSONObject();
			query.put("type", type);

			JSONObject updateValue = new JSONObject();
			updateValue.put("seq", 1);
			JSONObject update = new JSONObject();
			update.put("$inc", updateValue);

			JSONObject obj = DbMgr.findAndModifyDb(_dbCounters, query, update, true);
			if (obj != null) {
				ret = obj.getInt("seq");
			}
			
		} catch (JSONException e) {
			_log.error("getNextSequence: JSONException while trying to put JSONObjects together");
			throw new CounterIncrementException("getNextSequence: JSONException while trying to put JSONObjects together");
			
		} catch (InvalidInputException e) {
			_log.error("getNextSequence: InvalidInputException while trying to findAndModifyDb");
			throw new CounterIncrementException("getNextSequence: InvalidInputException while trying to findAndModifyDb");
		} catch (DbException e) {
			_log.error("getNextSequence: DbException while trying to findAndModifyDb");
			throw new CounterIncrementException("getNextSequence: DbException while trying to findAndModifyDb");
		}

		return ret;
	}

	public static void closeDbCounters() {
		if (_dbCounters != null) {
			_log.debug("closeDbCounters: Closing DB connection for DbCounters");
			_dbCounters.closeConnection();
		}
	}

    public static void main(String[] args) {
    	
    	try {
    		System.out.println("getNextSequence: " + DbCounters.getNextSequence("organization"));
            System.out.println("getNextSequence: " + DbCounters.getNextSequence("university"));
    		System.out.println("getNextSequence: " + DbCounters.getNextSequence("person"));
            System.out.println("getNextSequence: " + DbCounters.getNextSequence("viewer"));
    		System.out.println("getNextSequence: " + DbCounters.getNextSequence("funding-round"));
    		System.out.println("getNextSequence: " + DbCounters.getNextSequence("acquisition"));
    		System.out.println("getNextSequence: " + DbCounters.getNextSequence("events"));
    		System.out.println("getNextSequence: " + DbCounters.getNextSequence("notifications"));
    	} catch (CounterIncrementException e) {
    		System.out.println("Caught CounterIncrementException " + e.toString());
    	}
    } 
    	
}
