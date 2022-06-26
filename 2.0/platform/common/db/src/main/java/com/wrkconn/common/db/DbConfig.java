package com.wrkconn.common.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wrkconn.common.utils.CommonConfig;
import com.wrkconn.common.utils.StringUtil;
import com.wrkconn.common.utils.exceptions.DbException;
import com.wrkconn.common.utils.exceptions.InvalidInputException;

public class DbConfig {

	// local logger object
	static Logger _log = LogManager.getLogger(DbConfig.class);
	static DbInstance _dbConfig = null;

	static {
		try {
			_dbConfig = DbMgr.initDb(CommonConfig.COLLECTION_TYPE_ENTITY, CommonConfig.DB_CONFIG);

		} catch (InvalidInputException e) {
			_log.error("DbConfig: InvalidInputException while trying to initDb the db_config database");
			throw new RuntimeException("DbConfig: InvalidInputException while trying to initDb the db_config database");
		} catch (DbException e) {
			_log.error("DbConfig: DbException while trying to initDb the db_config database");
			throw new RuntimeException("DbConfig: DbException while trying to initDb the db_config database");
		}	
	}

	public static void addToConfig(String key, Map<String, Object> valueMap) 
			throws InvalidInputException, DbException {

		if (_dbConfig == null) {
			_log.error("addtoConfig: _dbConfig is not initialized (null)");
			throw new DbException("addtoConfig: _dbConfig is not initialized (null)");
		}

		if (StringUtil.isNullOrEmpty(key)) {
			_log.error("addtoConfig: key is either null or empty");
			throw new InvalidInputException("addtoConfig: key is either null or empty");
		}

		if (valueMap == null) 
			valueMap = new HashMap<String, Object>();

		valueMap.put(CommonConfig.DB_CONFIG_TYPE, key);
		DbMgr.insertDb(_dbConfig, valueMap);
		_log.debug("addtoConfig: added config for key: " + key);
	}

	public static List<JSONObject> getFromConfig(String key) 
			throws InvalidInputException, DbException {

		List<JSONObject> retList = null;

		Map<String, Object> query = new HashMap<String, Object>();
		query.put(CommonConfig.DB_CONFIG_KEY, key);
		retList = getFromConfig(query);

		return retList;
	}

	public static List<JSONObject> getFromConfig(Map<String, Object> query) 
			throws InvalidInputException, DbException {

		List<JSONObject> retList = DbMgr.findDb(_dbConfig, query);

		return retList;
	}

	public static void updateConfig(Map<String, Object> queryObj, Map<String, Object> updateObj) 
			throws InvalidInputException, DbException {

		if ((queryObj == null) || (updateObj == null)) {
			_log.error("updateConfig: queryObj or updateObj is null");
			throw new InvalidInputException("updateConfig: queryObj or updateObj is null");
		}

		DbMgr.updateDb(_dbConfig, queryObj, updateObj, false, false);

	}

	public static void closeConfig() {

		_log.debug("closeConfig: Closing the config db connection");
		if (_dbConfig != null) {
			DbMgr.closeDb(_dbConfig);
			_dbConfig = null;
		}
	}

}
