package com.wrkconn.common.db;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wrkconn.common.utils.CommonConfig;
import com.wrkconn.common.utils.exceptions.DbException;
import com.wrkconn.common.utils.exceptions.InvalidInputException;
import com.wrkconn.common.utils.exceptions.ConfigNotFoundException;

import com.wrkconn.common.utils.DateUtil;
import com.wrkconn.common.utils.LoggerUtil;
import com.wrkconn.common.utils.StringUtil;

import com.wrkconn.common.container.Configurator;
import com.wrkconn.common.db.MongoDb;

public class DbMgr {

	// local logger object
	static Logger _log = LogManager.getLogger(DbMgr.class);

	/*
	 * DbMgr.initDbEntity() is called once by each sync task. Params include type (ENTITY or QUEUE)
	 * and tableName (e.g. audit_log). 
	 * Returns a handle to the database for subsequent saveDb operations.
	 */
	public static DbInstance initDb(int type, String tableName) 
			throws InvalidInputException, DbException {

		Date start = DateUtil.getTimestampDate();
		
		_log.debug("initDb: Opening connection with database for tableName: " + tableName);
		if (StringUtil.isNullOrEmpty(tableName)) {
			_log.error("initDb: tableName is null or empty");
			throw new InvalidInputException("initDb: tableName is null or empty");
		}

		DbInstance db = null;
		try {
			if (type == CommonConfig.COLLECTION_TYPE_ENTITY) {
				MongoDb mongoDb = new MongoDb();
				db = mongoDb.openConnectionEntity(tableName, "");
			} else {
				_log.error("initDb: type is undefined, type: " + type);
				throw new InvalidInputException("initDb: type is undefined, type: " + type);
			}			
		} catch (ConfigNotFoundException e) {
			_log.error("initDb: ConfigNotFoundException while trying to initialize db");
			throw new InvalidInputException("initDb: ConfigNotFoundException while trying to initialize db");		
		}
		
		Date end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "initDb: " + tableName);
		
		return db;  	
	}

	/*
	 * DbMgr.initDb() is called once by each sync task. Params include type (ENTITY or QUEUE), 
	 * source (e.g. crunchbase) and entity (e.g. organization or person). Returns a handle to the 
	 * database for subsequent saveDb operations.
	 */
	public static DbInstance initDb(int type, String source, String entity) 
			throws InvalidInputException, DbException {
		
		Date start = DateUtil.getTimestampDate();
		
		_log.debug("initDb: Opening connection with database for source: " + source + ", entity: " + entity);
		if ((StringUtil.isNullOrEmpty(source)) || (StringUtil.isNullOrEmpty(entity))) {
			_log.error("initDb: Either source or entity or both are null or empty");
			throw new InvalidInputException("initDb: Either source or entity or both are null or empty");
		}

		DbInstance db = null;
		try {
			if (type == CommonConfig.COLLECTION_TYPE_ENTITY) {
				MongoDb mongoDb = new MongoDb();
				db = mongoDb.openConnectionEntity(source, entity);
			} else {
				_log.error("initDb: type is undefined, type: " + type);
				throw new InvalidInputException("initDb: type is undefined, type: " + type);
			}	
		} catch (ConfigNotFoundException e) {
			_log.error("initDb: ConfigNotFoundException while trying to initialize db");
			throw new InvalidInputException("initDb: ConfigNotFoundException while trying to initialize db");		
		}	
		
		Date end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "initDb: " + source + "_" + entity);
		
		return db;
	}

	public static void insertDb(DbInstance db, Map<String, Object> value) 
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
				
		if (db == null) {
			_log.error("insertDb: DbInstance param is null");
			throw new InvalidInputException("insertDb: DbInstance param is null");
		}

		int retries = 0;
		boolean inserted = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("insertDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((inserted == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("insertDb: Try # " + retries);
				start = DateUtil.getTimestampDate();
				db.insert(value);
				inserted=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}
		if (inserted == false) {
			_log.error("insertDb: insert failed despite " + retries + " retries");
			throw new DbException("insertDb: insert failed despite " + retries + " retries");
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "insertDb: " + value.toString());
	}
	
	public static void insertDb(DbInstance db, List<Map<String, Object>> value) 
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
				
		if (db == null) {
			_log.error("insertDbArr: DbInstance param is null");
			throw new InvalidInputException("insertDbArr: DbInstance param is null");
		}

		int retries = 0;
		boolean inserted = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("insertDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((inserted == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("insertDb: Try # " + retries);
				start = DateUtil.getTimestampDate();
				db.insert(value);
				inserted=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}
		if (inserted == false) {
			_log.error("insertDb: insert failed despite " + retries + " retries");
			throw new DbException("insertDb: insert failed despite " + retries + " retries");
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "insertDb: " + value.toString());
	}

	public static void insertDb(DbInstance db, String value)
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
		
		if (db == null) {
			_log.error("insertDb: DbInstance param is null");
			throw new InvalidInputException("insertDb: DbInstance param is null");
		}

		int retries = 0;
		boolean inserted = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("insertDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((inserted == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("insertDb: Try # " + retries);
				start = DateUtil.getTimestampDate();
				db.insert(value);
				inserted=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			}
		}
		if (inserted == false) {
			_log.error("insertDb: insert failed despite " + retries + " retries");
			throw new DbException("insertDb: insert failed despite " + retries + " retries");
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "insertDb: " + value.toString());
	}

	public static List<JSONObject> findDb(DbInstance db, JSONObject query) 
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
		List<JSONObject> ret = null;
		
		if (db == null) {
			_log.error("findDb: DbInstance param is null");
			throw new InvalidInputException("findDb: DbInstance param is null");
		}

		_log.debug("findDb: About to find for query: " + query);
		
		int retries = 0;
		boolean findDone = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("findDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((findDone == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("findDb: Try # " + retries);
				start = DateUtil.getTimestampDate();
				ret = db.find(query);
				findDone = true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			}
		}		
		if (findDone == false) {
			_log.error("findDb: find failed despite " + retries + " retries");
			throw new DbException("findDb: find failed despite " + retries + " retries");
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "findDb: " + query.toString());
		
		return ret;
	}

	public static List<JSONObject> findDb(DbInstance db, Map<String, Object> query) 
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
		List<JSONObject> ret = null;

		if (db == null) {
			_log.error("findDb: DbInstance param is null");
			throw new InvalidInputException("findDb: DbInstance param is null");
		}

		_log.debug("findDb: About to find for query: " + query);

		int retries = 0;
		boolean findDone = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("findDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((findDone == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("findDb: Try # " + retries);
				start = DateUtil.getTimestampDate();
				ret = db.find(query);
				findDone = true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			}
		}
		if (findDone == false) {
			_log.error("findDb: find failed despite " + retries + " retries");
			throw new DbException("findDb: find failed despite " + retries + " retries");
		}

		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "findDb: " + query.toString());
		
		return ret;
	}
	
	public static List<Map<String, Object>> findDbMap(DbInstance db, Map<String, Object> query) 
			throws InvalidInputException, DbException {
		
		Date start = null;
		Date end = null;
		List<Map<String, Object>> ret = null;

		if (db == null) {
			_log.error("findDbMap: DbInstance param is null");
			throw new InvalidInputException("findDb: DbInstance param is null");
		}

		_log.debug("findDbMap: About to find for query: " + query);
		
		int retries = 0;
		boolean findDone = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("findDbMap: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((findDone == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("findDbMap: Try # " + retries);
				start = DateUtil.getTimestampDate();
				ret = db.findMap(query);
				findDone = true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			}
		}
		if (findDone == false) {
			_log.error("findDbMap: find failed despite " + retries + " retries");
			throw new DbException("findDbMap: find failed despite " + retries + " retries");
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "findDbMap: " + query.toString());

		return ret;
		
	}

	public static JSONObject findAndModifyDb(DbInstance db, JSONObject query, JSONObject update, boolean upsert) 
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
		JSONObject ret=null;

		if (db == null) {
			_log.error("findAndModifyDb: DbInstance param is null");
			throw new InvalidInputException("findAndModifyDb: DbInstance param is null");
		}

		_log.debug("findAndModifyDb: About to findAndModify for query: " + query + ", update: " + update);
		
		int retries = 0;
		boolean updated = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("findAndModifyDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((updated == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("findAndModifyDb: Try # " + retries+1);
				start = DateUtil.getTimestampDate();
				ret = db.findAndModify(query, update, upsert);
				updated=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!updated) {
			_log.error("findAndModifyDb: DbException while updating document for query: " + query);
			throw new DbException("findAndModifyDb: DbException while updating document for query: " + query);
		}

		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "findAndModifyDb: " + query.toString() + ", update: " + update.toString());
		
		return ret;
	}
	
	public static Long countDb(DbInstance db, Map<String, Object> query)
		throws InvalidInputException, DbException {
		
		Date start = null;
		Date end = null;
		Long retCount = Long.valueOf(0);

		if (db == null) {
			_log.error("countDb: DbInstance param is null");
			throw new InvalidInputException("countDb: DbInstance param is null");
		}

		_log.debug("countDb: About to count for query: " + query);
		
		int retries = 0;
		boolean countDone = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("countDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((countDone == false) && (retries < dbMaxRetries)) {
			try {
				_log.debug("countDb: Try # " + retries+1);
				start = DateUtil.getTimestampDate();
				retCount = db.count(query);
				countDone=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!countDone) {
			_log.error("countDb: DbException while counting documents for query: " + query);
			throw new DbException("countDb: DbException while counting documents for query: " + query);
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "countDb: " + query.toString());
		
		return retCount;		
	}

	public static void updateDb(DbInstance db, Map<String, Object> query, Map<String, Object> update, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException {
		
		updateDb(db, query, update, null, upsert, multi);
	}
	
	public static void updateDb(DbInstance db, Map<String, Object> query, Map<String, Object> setUpdate, 
								Map<String, Object> incUpdate, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
		
		if (db == null) {
			_log.error("updatedDb: DbInstance param is null");
			throw new InvalidInputException("updateDb: DbInstance param is null");
		}

		int retries = 0;
		boolean updated = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("updateDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((updated == false) && (retries < dbMaxRetries)) {
			try {
				start = DateUtil.getTimestampDate();
				db.update(query, setUpdate, incUpdate, upsert, multi);
				updated=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!updated) {
			_log.error("updateDb: DbException while updating document for query: " + query);
			throw new DbException("updateDb: DbException while updating document for query: " + query);
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "updateDb: " + query.toString() + ", update: " + setUpdate.toString());
	}

	public static void replaceDb(DbInstance db, Map<String, Object> query, Map<String, Object> replaceWith, boolean upsert, boolean multi) 
		throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;

		if (db == null) {
			_log.error("replaceDb: DbInstance param is null");
			throw new InvalidInputException("replaceDb: DbInstance param is null");
		}

		int retries = 0;
		boolean replaced = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("replaceDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}

		while ((replaced == false) && (retries < dbMaxRetries)) {
			try {
				start = DateUtil.getTimestampDate();
				db.replace(query, replaceWith, upsert, multi);
				replaced=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!replaced) {
			_log.error("replaceDb: DbException while replacing document for query: " + query);
			throw new DbException("updateDb: DbException while replacing document for query: " + query);
		}

		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "replaceDb: " + query.toString() + ", replaceWith: " + replaceWith.toString());
	}
	
	public static int pushDb(DbInstance db, Map<String, Object> query, String updateNodeKey, List<Map<String, Object>> updateArrValue, 
							 Map<String, Object> sortQuery, int sliceCount) 
			throws InvalidInputException, DbException {
	
		Date start = null;		
		Date end = null;
		int nDocs = 0;
		
		if (db == null) {
			_log.error("pushDb: DbInstance param is null");
			throw new InvalidInputException("pushDb: DbInstance param is null");
		}

		int retries = 0;
		boolean updated = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("pushDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		while ((updated == false) && (retries < dbMaxRetries)) {
			try {
				start = DateUtil.getTimestampDate();
				nDocs = db.push(query, updateNodeKey, updateArrValue, sortQuery, sliceCount);
				updated=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!updated) {
			_log.error("pushDb: DbException while updating document for query: " + query);
			throw new DbException("pushDb: DbException while updating document for query: " + query);
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "pushDb: " + query.toString() + ", update: " + updateNodeKey.toString());
		
		return nDocs;
	}

	public static List<JSONObject> aggregateDb(DbInstance db, Map<String,Map<String,Object>> query, String[] pipeline, int limitResults)
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
		List<JSONObject> retList = null;

		if (db == null) {
			_log.error("aggregateDb: DbInstance param is null");
			throw new InvalidInputException("aggregateDb: DbInstance param is null");
		}

		int retries = 0;
		boolean aggregateDone = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("aggregateDb: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
		_log.debug("aggregateDb: About to find for query: " + query);
		
		while ((aggregateDone == false) && (retries < dbMaxRetries)) {
			try {
				start = DateUtil.getTimestampDate();
				retList = db.aggregate(query, pipeline, limitResults);
				aggregateDone=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!aggregateDone) {
			_log.error("aggregateDb: DbException while updating document for query: " + query);
			throw new DbException("aggregateDb: DbException while updating document for query: " + query);
		}
		
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "aggregateDb: " + query.toString());
		
		return retList;
	}

	public static List<Map<String, Object>> aggregateDbMap(DbInstance db, Map<String,Map<String,Object>> query, String[] pipeline, int limitResults)
			throws InvalidInputException, DbException {

		Date start = null;
		Date end = null;
		List<Map<String, Object>> retList = null;

		if (db == null) {
			_log.error("aggregateDbMap: DbInstance param is null");
			throw new InvalidInputException("aggregateDbMap: DbInstance param is null");
		}
		
		int retries = 0;
		boolean aggregateDone = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("aggregateDbMap: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}

		_log.debug("aggregateDbMap: About to find for query: " + query);
		
		while ((aggregateDone == false) && (retries < dbMaxRetries)) {
			try {
				start = DateUtil.getTimestampDate();
				retList = db.aggregateMap(query, pipeline, limitResults);
				aggregateDone=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!aggregateDone) {
			_log.error("aggregateDbMap: DbException while updating document for query: " + query);
			throw new DbException("aggregateDbMap: DbException while updating document for query: " + query);
		}

		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "aggregateDbMap: " + query.toString());
		
		return retList;
	}

    // Added this method to support the multiple instances of the same operators in the pipeline
    public static List<Map<String, Object>> aggregateDbMapMultiple(DbInstance db, Map<String,Map<String,Object>> query, String[] pipeline, int limitResults)
            throws InvalidInputException, DbException {

    	Date start = null;
    	Date end = null;
        List<Map<String, Object>> retList = null;

        if (db == null) {
            _log.error("aggregateDbMapMultiple: DbInstance param is null");
            throw new InvalidInputException("aggregateDbMapMultiple: DbInstance param is null");
        }

		int retries = 0;
		boolean aggregateDone = false;
		int dbMaxRetries = 10;
		try {
			dbMaxRetries = Configurator.getConfig().getInteger(CommonConfig.P_DB_MAX_RETRIES);
		} catch (ConfigNotFoundException e1) {
			_log.warn("aggregateDbMapMultiple: ConfigNotFoundException while trying to get " + CommonConfig.P_DB_MAX_RETRIES);
		}
		
        _log.debug("aggregateDbMapMultiple: About to find for query: " + query);
        
		while ((aggregateDone == false) && (retries < dbMaxRetries)) {
			try {
				start = DateUtil.getTimestampDate();
		        retList = db.aggregateMapMultiple(query, pipeline, limitResults);
				aggregateDone=true;
			} catch (DbException e) {
				retries++;
				end = DateUtil.getTimestampDate();
				try { Thread.sleep(CommonConfig.WAIT_MILLIS_BETWEEN_RETRIES); } catch (InterruptedException ex) {;}
			} 
		}

		if (!aggregateDone) {
			_log.error("aggregateDbMapMultiple: DbException while updating document for query: " + query);
			throw new DbException("aggregateDbMapMultiple: DbException while updating document for query: " + query);
		}
        
		end = DateUtil.getTimestampDate();
		LoggerUtil.logTimeTaken(_log, start, end, "aggregateDbMapMultiple: " + query.toString());
		
        return retList;
    }

	public static void closeDb(DbInstance db) {
		_log.debug("Closing connection with database");
		if (db != null) {
			db.closeConnection();
		}
	}


}
