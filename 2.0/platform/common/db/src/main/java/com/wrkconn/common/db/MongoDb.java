package com.wrkconn.common.db;

import com.wrkconn.common.configuration.Config;
import com.wrkconn.common.container.Configurator;
import com.wrkconn.common.container.ConfiguratorUtil;
import com.wrkconn.common.utils.exceptions.DbException;
import com.wrkconn.common.utils.exceptions.ConfigNotFoundException;
import com.wrkconn.common.utils.exceptions.InvalidInputException;
import com.wrkconn.common.utils.CommonConfig;
import com.wrkconn.common.utils.StringUtil;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.WriteResult;
import com.mongodb.AggregationOutput;
import com.mongodb.util.JSONParseException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MongoDb implements DbInstance {

	// local logger object
	static Logger _log = LogManager.getLogger(MongoDb.class);
	static int _sOpenClients = 0;

	String _host = null;
	int _port = -1;
	String _username = null;
	String _password = null;
	String _dbName = null;
	String _collectionKey = null;
	String _collectionName = null;

	MongoClient _mongoClient = null;
	DB _mongoDb = null;
	DBCollection _mongoCollection = null;

	public MongoDb() 
		throws InvalidInputException, DbException, ConfigNotFoundException {
		
		this(Configurator.getConfig());
	}

	public MongoDb(Config config) 
		throws InvalidInputException, DbException {
	
		this(config.getString(CommonConfig.P_MONGO_HOST_ADDRESS, "localhost"), 
				 config.getInteger(CommonConfig.P_MONGO_HOST_PORT, 27017), 
				 config.getString(CommonConfig.P_MONGO_USERNAME, ""), 
				 config.getString(CommonConfig.P_MONGO_PASSWORD, ""), 
				 config.getString(CommonConfig.P_MONGO_DB_NAME, "500m_dev"));
	}

	public MongoDb(String host, int port, String username, String password, String dbName) 
			throws InvalidInputException, DbException {

		if ((StringUtil.isNullOrEmpty(host)) || (port < 0) || (port > 65536) ||
				(StringUtil.isNullOrEmpty(dbName))) {
			_log.error("MongoDb: host, port and/or dbName is invalid. host: " + 
					host + ", port: " + port + ", dbName: " +  dbName);
			throw new InvalidInputException("MongoDb: host, port and/or dbName is invalid. host: " + 
					host + ", port: " + port + ", dbName: " + dbName);
		}

		try {
			this._host = host;
			this._port = port;
			this._username = username;
			this._password = password;
			this._dbName = dbName;

			List<ServerAddress> hostList = new ArrayList<ServerAddress>();
			ServerAddress hostAddress = new ServerAddress(host, port);
			hostList.add(hostAddress);

			if ((StringUtil.isNullOrEmpty(username)) && (StringUtil.isNullOrEmpty(password))) {
				_log.debug("MongoDb: attempting to log in without user credentials");
				_mongoClient = new MongoClient(hostList);
			} else {
				_log.trace("MongoDb: attempting to log in with user credentials");
				List<MongoCredential> credentialsList = new ArrayList<MongoCredential>();
				MongoCredential mongoCred = MongoCredential.createMongoCRCredential(username, dbName, password.toCharArray());
				credentialsList.add(mongoCred);
				_mongoClient = new MongoClient(hostList, credentialsList);
			}
			_mongoDb = _mongoClient.getDB(dbName);
		} catch (MongoException e) {
			_log.debug("MongoDb: MongoException while trying to instantiate a new connection with the database");
			throw new DbException("MongoDb: MongoException while trying to instantiate a new connection with the database");
		}

		_sOpenClients++;
		_log.trace("Initialized MongoDb connection: " + _sOpenClients);
	}

	public boolean isInitialized() {
		return (_mongoDb != null);
	}

	public void setCollectionKey(String collectionKey) 
			throws InvalidInputException {

		if (StringUtil.isNullOrEmpty(collectionKey)) {
			_log.error("setCollectionKey: collectionKey is null or empty");
			throw new InvalidInputException("setCollectionKey: collectionKey is null or empty");
		}

		this._collectionKey = collectionKey;
	}

	public void setCollectionName(String collectionName) 
			throws InvalidInputException {

		if (StringUtil.isNullOrEmpty(collectionName)) {
			_log.error("setCollectionName: collectionName is null or empty");
			throw new InvalidInputException("setCollectionName: collectionName is null or empty");
		}

		this._collectionName = collectionName;
		_mongoCollection = _mongoDb.getCollection(collectionName);

	}

	public void close() {
		_log.trace("MongoDb: attempting to close connection");
		if (_mongoClient != null) {
			_mongoClient.close();
			_sOpenClients--;
			_log.trace("MongoDb: connection successfully closed");
		} else {
			_log.debug("MongoDb: could not close connection");
		}
	}

	private DbInstance openConnection(String collectionKey, String collectionName) 
			throws InvalidInputException, DbException, ConfigNotFoundException {

		if (StringUtil.isNullOrEmpty(collectionName)) {
			_log.error("openConnection: collectionName is null or empty.");
			throw new InvalidInputException("openConnection: collectionName is null or empty.");
		}

		MongoDb mongoDb = null;
		if (!isInitialized()) {
			_log.debug("Initiazing mongoDb");
			mongoDb = new MongoDb();
		} else {
			mongoDb = this;
		}
		mongoDb.setCollectionKey(collectionKey);
		mongoDb.setCollectionName(collectionName);

		return (DbInstance) mongoDb;	
	}

	@Override
	public DbInstance openConnectionEntity(String source, String entity)
			throws InvalidInputException, DbException, ConfigNotFoundException {

		if (StringUtil.isNullOrEmpty(source)) {
			_log.error("openConnectionEntity: source is null or empty.");
			throw new InvalidInputException("openConnectionEntity: source is null or empty.");
		}

		String collectionKey = null;
		String collectionName = null;

		if (!StringUtil.isNullOrEmpty(entity)) {
			collectionKey = source + "_" + entity;
		} else {
			collectionKey = source;
		}
		collectionName = collectionKey;

		return openConnection(collectionKey, collectionName);
	}

	@Override
	public DbInstance openConnectionQueue(String source, String entity)
			throws InvalidInputException, DbException, ConfigNotFoundException {

		if (StringUtil.isNullOrEmpty(source)) {
			_log.error("openConnectionQueue: source is null or empty.");
			throw new InvalidInputException("openConnectionQueue: source is null or empty.");
		}

		String collectionKey = null;
		String collectionName = null;

		if (!StringUtil.isNullOrEmpty(entity)) {
			collectionKey = source + "_" + entity;
		} else {
			collectionKey = source;
		}	
		collectionName = collectionKey + "_queue";

		return openConnection(collectionKey, collectionName);
	}

	@Override
	public void insert(Map<String, Object> value) 
		throws InvalidInputException, DbException {

		try {
			if (value != null) {		
				BasicDBObject dbObj = getDBObjectFromMap(value);	
				if ((_mongoCollection != null) && (value != null)) {	
					_mongoCollection.insert(dbObj);
				}
			} else {
				this.insert((String) null);
			}
		} catch (MongoException e) {
			_log.debug("insert: MongoException while trying to insert JSON object");
			throw new DbException("insert: MongoException while trying to insert JSON object");
		}
		_log.trace("Saved to collection: " + _collectionName);
	}
	
	@Override
	public void insert(List<Map<String, Object>> value) 
		throws InvalidInputException, DbException {

		try {
			if (value != null) {
				BasicDBObject[] dbObjList = new BasicDBObject[value.size()];
				for (int i=0; i<value.size(); i++) {
					Map<String, Object> m = value.get(i);
					BasicDBObject dbObj = getDBObjectFromMap(m);
					dbObjList[i] = dbObj;
				}
				if ((_mongoCollection != null) && (dbObjList != null)) {	
					_mongoCollection.insert(dbObjList);
				}
			} else {
				this.insert((String) null);
			}
		} catch (MongoException e) {
			_log.debug("insert: MongoException while trying to insert List<Map<String, Object>>");
			throw new DbException("insert: MongoException while trying to insert List<Map<String, Object>>");
		}
		_log.trace("Saved to collection: " + _collectionName);
	}

	@Override
	public void insert(String value) 
		throws InvalidInputException, DbException {

		_log.debug("About to save to collection: " + _collectionName);
		try {
			BasicDBObject dbObj = new BasicDBObject(this._collectionKey, value);	
			if ((_mongoCollection != null) && (value != null)) {	
				_mongoCollection.insert(dbObj);
			}
		} catch (MongoException e) {
			_log.debug("insert: MongoException while trying to insert String value");
			throw new DbException("insert: MongoException while trying to insert String value");
		}

	}

	public List<JSONObject> find(JSONObject query) 
			throws InvalidInputException, DbException {

		List<JSONObject> retList = null;

		if (_mongoCollection == null) {
			_log.error("find: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.find: _mongoCollection is not initialized");
		}

		DBCursor cursor = null;
		try {
			if (query == null) {
				cursor = _mongoCollection.find();
			} else {
				_log.debug("find: query = " + query.toString());
				Object o = com.mongodb.util.JSON.parse(query.toString());
				DBObject queryDBObj = (DBObject) o;
				cursor = _mongoCollection.find(queryDBObj);
			}

			if (cursor != null) {
				while (cursor.hasNext()) {
					BasicDBObject obj = (BasicDBObject) cursor.next();
					String strObj = (obj != null) ? obj.toString(): "";
					JSONObject jsonObj = new JSONObject(strObj);
					if (retList == null) {
						retList = new ArrayList<JSONObject>();
					}
					retList.add(jsonObj);
				}
			}
		} catch (JSONException e) {  
			_log.error("find: JSONException while trying to get object from the db");
			throw new InvalidInputException("find: MongoException while trying to get objects from the db into a json object");
		} catch (MongoException e) {
			_log.debug("find: MongoException while trying to convert cursor to array " + e.getMessage());
			throw new DbException("find: MongoException while trying to get objects from the cursor");
		} catch (JSONParseException e) {
			_log.error("find: JSONParseException while trying to parse query string into an Object");
			throw new InvalidInputException("find: JSONParseException while trying to parse query string into an Object");
		} finally {
			if (cursor != null)
				cursor.close();
		}

		return retList;
	}


	public List<JSONObject> find(Map<String, Object> query) 
			throws InvalidInputException, DbException {

		List<JSONObject> retList = null;

		if (_mongoCollection == null) {
			_log.error("find: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.find: _mongoCollection is not initialized");
		}

		DBCursor cursor = null;
		try {
			if (query == null) {
				cursor = _mongoCollection.find();
			} else {
				_log.debug("find: query = " + query.toString());
				BasicDBObject queryDBObj = getDBObjectFromMap(query);	
				cursor = _mongoCollection.find(queryDBObj);
			}

			if (cursor != null) {
				while (cursor.hasNext()) {
					BasicDBObject obj = (BasicDBObject) cursor.next();
					String strObj = (obj != null) ? obj.toString(): "";
					JSONObject jsonObj = new JSONObject(strObj);
					if (retList == null) {
						retList = new ArrayList<JSONObject>();
					}
					retList.add(jsonObj);
				}
			}
		} catch (JSONException e) {  
			_log.error("find: JSONException while trying to get object from the db");
			throw new InvalidInputException("find: MongoException while trying to get objects from the db into a json object");
		} catch (MongoException e) {
			_log.debug("find: MongoException while trying to convert cursor to array");
			throw new DbException("find: MongoException while trying to get objects from the cursor");
		} finally {
			if (cursor != null)
				cursor.close();
		}

		return retList;
	}
	
	public List<Map<String, Object>> findMap(Map<String, Object> query) 
			throws InvalidInputException, DbException {

		List<Map<String,Object>> retList = null;

		if (_mongoCollection == null) {
			_log.error("find: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.find: _mongoCollection is not initialized");
		}

		DBCursor cursor = null;
		try {
			if (query == null) {
				cursor = _mongoCollection.find();
			} else {
				_log.debug("find: query = " + query.toString());
				BasicDBObject queryDBObj = getDBObjectFromMap(query);	
				cursor = _mongoCollection.find(queryDBObj);
			}

			if (cursor != null) {
				while (cursor.hasNext()) {
					BasicDBObject obj = (BasicDBObject) cursor.next();
					@SuppressWarnings("unchecked")
					Map<String,Object> mapObj = (Map<String,Object>) ((obj != null) ? obj.toMap(): null);
					if (retList == null) {
						retList = new ArrayList<Map<String,Object>>();
					}
					retList.add(mapObj);
				}
			}
		} catch (ClassCastException | NullPointerException | IllegalArgumentException | UnsupportedOperationException e) {  
			_log.error("find: Exception while trying to get object from the db");
			throw new InvalidInputException("find: Exception while trying to get objects from the db into a Map object");
		} catch (MongoException e) {
			_log.debug("find: MongoException while trying to convert cursor to array");
			throw new DbException("find: MongoException while trying to get objects from the cursor");
		} finally {
			if (cursor != null)
				cursor.close();
		}

		return retList;
	}

	public JSONObject findAndModify(JSONObject query, JSONObject update, boolean upsert) 
			throws InvalidInputException, DbException {

		JSONObject ret = null;

		if (_mongoCollection == null) {
			_log.error("findAndModify: _mongoCollection is not initialized");
			throw new InvalidInputException("findAndModify: _mongoCollection is not initialized");
		}

		DBObject dbObj = null;
		try {
			if ((query != null) && (update != null)) {
				_log.debug("findAndModify: query = " + query.toString() + ", update = " + update.toString());
				Object q = com.mongodb.util.JSON.parse(query.toString());
				Object u = com.mongodb.util.JSON.parse(update.toString());
				DBObject queryDBObj = (DBObject) q;
				DBObject updateDBObj = (DBObject) u;

				dbObj = _mongoCollection.findAndModify(queryDBObj, null, null, false, updateDBObj, true, upsert);
			} else {
				_log.error("findAndModify: query and/or update is null");
				throw new InvalidInputException("findAndModify: query and/or update is null");
			}

			if (dbObj != null) {
				ret = new JSONObject(dbObj.toString());
			} 

		} catch (JSONException e) {  
			_log.error("findAndModify: JSONException while trying to get JSON object from the db object");
			throw new InvalidInputException("findAndModify: JSONException while trying to get JSON object from the db object");
		} catch (MongoException e) {
			_log.debug("findAndModify: MongoException while trying to convert cursor to array");
			throw new DbException("findAndModify: MongoException while trying to get objects from the cursor");
		}

		return ret;
	}

	public Long count(Map<String, Object> query) 
			throws InvalidInputException, DbException {

		Long retCount = Long.valueOf(0);

		if (_mongoCollection == null) {
			_log.error("count: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.count: _mongoCollection is not initialized");
		}

		try {
			if (query == null) {
				retCount = _mongoCollection.count();
			} else {
				_log.debug("count: query = " + query.toString());
				BasicDBObject queryDBObj = getDBObjectFromMap(query);	
				retCount = _mongoCollection.count(queryDBObj);
			}
		} catch (MongoException e) {
			_log.debug("find: MongoException while trying to convert cursor to array");
			throw new DbException("find: MongoException while trying to get objects from the cursor");
		}

		return retCount;
	}

	public void update(String idKey, String idValue, String[] keys, String[] values) 
			throws InvalidInputException, DbException {

		if (_mongoCollection == null) {
			_log.error("MongoDb.update: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.update: _mongoCollection is not initialized");
		}

		if ((StringUtil.isNullOrEmpty(idKey)) || (StringUtil.isNullOrEmpty(idValue)) || (keys == null) || 
				(values == null) || (keys.length == 0) || (values.length == 0) || (keys.length != values.length)) {
			_log.error("MongoDb.update: idKey, idValue, keys or values is null or empty");
			throw new InvalidInputException("MongoDb.update: idKey, idValue, keys or values is null or empty");
		}

		JSONObject queryObj = null;
		JSONObject updateObj = null;
		JSONObject valueObj = null;
		try {
			valueObj = new JSONObject();
			valueObj.put("$oid", idValue);

			queryObj = new JSONObject();
			queryObj.put(idKey, valueObj);

			updateObj = new JSONObject();
			for(int cnt=0 ; cnt<keys.length; cnt++) {
				updateObj.put(keys[cnt], values[cnt]);
			}

		} catch (JSONException e) {
			_log.error("update: JSONException while trying to create queryObj and/or updateObj");
			throw new InvalidInputException("update: JSONException while trying to create queryObj and/or updateObj");
		}

		_log.debug("update: About to update");
		update(queryObj, updateObj, null, false, false);
		_log.debug("update: Update complete");

	}

	public void update(JSONObject query, JSONObject update, Map<String, Date> dateObjs, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException {

		if (_mongoCollection == null) {
			_log.error("update: _mongoCollection is not initialized");
			throw new InvalidInputException("update: _mongoCollection is not initialized");
		}

		if (update != null) {
			_log.debug("update: query = " + query.toString() + ", update = " + update.toString());

			Object q=null;
			DBObject queryDBObj=null;
			if (query != null) {
				q = com.mongodb.util.JSON.parse(query.toString());
				queryDBObj = (DBObject) q;
			} 

			Object u = com.mongodb.util.JSON.parse(update.toString());
			BasicDBObject updateDBObj = (BasicDBObject) u;
			if ((dateObjs != null) && (dateObjs.size() > 0)) {
				Iterator<Entry<String, Date>> it = dateObjs.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, Date> pair = (Map.Entry<String, Date>)it.next();
					updateDBObj.append((String)pair.getKey(), (Date)pair.getValue());
				}
			}	
			BasicDBObject updateDb = new BasicDBObject("$set", updateDBObj);
			try {
				WriteResult wr = _mongoCollection.update(queryDBObj, updateDb, upsert, multi);
				if (wr != null) {
					int nDocs = wr.getN();
					_log.debug("update: " + wr.toString());
					_log.debug("update: number of documents updated: " + nDocs);
					if (nDocs != 1) {
						_log.warn("update: expected number of updated docs: 1, actual affected: " + nDocs);
					}
					boolean updateExistingDoc = wr.isUpdateOfExisting();
					if (!updateExistingDoc) {
						_log.warn("update: expected existing doc update, resulted in that not being the case");
					}
				}
			} catch (MongoException e) {
				_log.debug("update: MongoException while trying to update");
				throw new DbException("update: MongoException while trying to update");
			}

		} else {
			_log.error("update: param update is null or empty");
			throw new InvalidInputException("update: param update is null or empty");
		}

	}
	
	private BasicDBObject getDBObjectFromMap(Map<String, Object> map) 
			throws InvalidInputException {

		BasicDBObject obj = null;
		try {
			if ((map != null) && (map.size() > 0)) {
				obj = new BasicDBObject();
				for (String key : map.keySet()) {
					if (key != null) {
						if (map.get(key) != null) {
							// for known object types, instantiate object of those types
							if (map.get(key).getClass().equals(Date.class)) {
								Date dateField = (Date) map.get(key);
								obj.append(key, dateField);
							} else if (map.get(key).getClass().equals(ObjectId.class)) {
								ObjectId objIdField = (ObjectId) map.get(key);
								obj.append(key, objIdField);
							} else if (map.get(key).getClass().equals(JSONObject.class)){
								JSONObject jsonVal = (JSONObject) map.get(key);					
								Object jsonObj = com.mongodb.util.JSON.parse(jsonVal.toString());
								obj.append(key, (BasicDBObject) jsonObj);
							} else if (map.get(key).getClass().equals(JSONArray.class)) {
								JSONArray jsonArrVal = (JSONArray) map.get(key);
								Object jsonArr = com.mongodb.util.JSON.parse(jsonArrVal.toString());
								obj.append(key, (BasicDBList) jsonArr);
							} else if (map.get(key).getClass().equals(HashMap.class)) {
								@SuppressWarnings("unchecked")
								Map<String, Object> mapField = (Map<String, Object>) map.get(key);
								BasicDBObject mapValue = getDBObjectFromMap(mapField);
								if (mapValue != null) {
									obj.append(key, mapValue);
								}
							} else if (map.get(key).getClass().equals(Pattern.class)) {
								Pattern patternField = (Pattern) map.get(key);
								obj.append(key,  patternField);
							} else {
								// for all other types, get the value as String
								obj.append(key, map.get(key));
							}
						} else {
							// save the "null" value for this key 
							obj.append(key, map.get(key));
						}
					}
				}
			} else {
				// map is null. Nothing to do, return null
			}
		} catch (JSONParseException e) {
			_log.error("getDBObjectFromMap: JSONParseException while trying to parse map: " + map.toString());
			throw new InvalidInputException("getDBObjectFromMap: JSONParseException while trying to parse map: " + 
					((map != null) ? map.toString() : "null"));
		}
		return obj;
	}

	public int push(Map<String, Object> query, String updateNodeKey, List<Map<String, Object>> updateArrValue, 
					Map<String, Object> sortQuery, int sliceCount) 
							throws InvalidInputException, DbException {
		
		return push(query, updateNodeKey, updateArrValue, sortQuery, sliceCount, false, false);
	}	
	
	private int push(Map<String, Object> query, String updateNodeKey, List<Map<String, Object>> updateArrValue, 
					Map<String, Object> sortQuery, int sliceCount, boolean upsert, boolean multi)
		throws InvalidInputException, DbException {
		
		int nDocs = 0;
		
		if (_mongoCollection == null) {
			_log.error("MongoDb.push: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.push: _mongoCollection is not initialized");
		}

		BasicDBObject queryDb = null;
		if ((query != null) && (query.size() > 0)) {
			queryDb = getDBObjectFromMap(query);
		}
		else {
			// query can be null. Returns all objects in the collection
			queryDb = new BasicDBObject();
		}

		BasicDBObject updateDb = null;
		if ((updateArrValue != null) && (updateArrValue.size() > 0) && !StringUtil.isNullOrEmpty(updateNodeKey)) {
			Map<String, Object> eachObj = new HashMap<String, Object>();
			eachObj.put("$each", updateArrValue);
			
			Map<String, Object> updateNodeObj = new HashMap<String, Object>();
			updateNodeObj.put(updateNodeKey, eachObj);
			
			if (sortQuery != null && sortQuery.size() > 0) {
				updateNodeObj.put("$sort", sortQuery);
			}
			if (sliceCount > 0) {
				updateNodeObj.put("$slice", sliceCount);
			}
			Map<String, Object> pushObj = new HashMap<String, Object>();
			pushObj.put("$push", updateNodeObj);
			
			updateDb = getDBObjectFromMap(pushObj);	
			
		} else {
			// update cannot be null or empty in this method
			_log.error("MongoDb.push: updateArrValue or updateNodeKey is null or empty");
			throw new InvalidInputException("MongoDb.push: updateArrValue or updateNodeKey is null or empty");
		}

		try {
			_log.debug("update: query = " + queryDb.toString() + ", update = " + updateDb.toString());		
			WriteResult wr = _mongoCollection.update(queryDb, updateDb, upsert, multi);
			if (wr != null) {
				nDocs = wr.getN();
				_log.trace("update: " + wr.toString());
				_log.debug("update: number of documents updated: " + nDocs);
				if (nDocs != 1) {
					_log.warn("update: expected number of updated docs: 1, actual affected: " + nDocs);
				}
				boolean updateExistingDoc = wr.isUpdateOfExisting();
				if (!upsert && nDocs > 0 && !updateExistingDoc) {
					_log.warn("update: expected existing doc update, resulted in that not being the case");
				}
			}
		} catch (MongoException e) {
			_log.debug("update: MongoException while trying to update");
			throw new DbException("update: MongoException while trying to update");
		}	
		
		return nDocs;
	}
	
	public void update(Map<String, Object> query, Map<String, Object> update, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException {
		
		update(query, update, null, upsert, multi);
	}
	
	public void update(Map<String, Object> query, Map<String, Object> setUpdate, 
					   Map<String, Object> incUpdate, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException {

		if (_mongoCollection == null) {
			_log.error("MongoDb.update: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.update: _mongoCollection is not initialized");
		}

		BasicDBObject queryDb = null;
		if ((query != null) && (query.size() > 0)) {
			queryDb = getDBObjectFromMap(query);
		}
		else {
			// query can be null. Returns all objects in the collection
			queryDb = new BasicDBObject();
		}
		
		BasicDBObject updateDb = null;
		if ((setUpdate != null) && (setUpdate.size() > 0)) {
			BasicDBObject setUpdateObj = getDBObjectFromMap(setUpdate);
			updateDb = new BasicDBObject("$set", setUpdateObj);
		}
		else {
			// update cannot be null or empty in this method
			_log.error("update: param setUpdate is null or empty");
			throw new InvalidInputException("update: param update is null or empty");
		}
		
		if ((incUpdate != null) && (incUpdate.size() > 0)) {
			BasicDBObject incUpdateObj = getDBObjectFromMap(incUpdate);
			updateDb = updateDb.append("$inc", incUpdateObj);
		}
		else {
			_log.trace("update: param incUpdate is null or empty");
		}

		try {
			_log.debug("update: query = " + queryDb.toString() + ", update = " + updateDb.toString());		
			WriteResult wr = _mongoCollection.update(queryDb, updateDb, upsert, multi);
			if (wr != null) {
				int nDocs = wr.getN();
				_log.trace("update: " + wr.toString());
				_log.debug("update: number of documents updated: " + nDocs);
				if (nDocs != 1) {
					_log.warn("update: expected number of updated docs: 1, actual affected: " + nDocs);
				}
				boolean updateExistingDoc = wr.isUpdateOfExisting();
				if (!upsert && nDocs > 0 && !updateExistingDoc) {
					_log.warn("update: expected existing doc update, resulted in that not being the case");
				}
			}
		} catch (MongoException e) {
			_log.debug("update: MongoException while trying to update");
			throw new DbException("update: MongoException while trying to update");
		}
	}

	
	public void replace(Map<String, Object> query, Map<String, Object> replaceWith, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException {

		if (_mongoCollection == null) {
			_log.error("MongoDb.replace: _mongoCollection is not initialized");
			throw new InvalidInputException("MongoDb.replace: _mongoCollection is not initialized");
		}

		BasicDBObject queryDb = null;
		if ((query != null) && (query.size() > 0)) {
			queryDb = getDBObjectFromMap(query);
		}
		else {
			// query can be null. Returns all objects in the collection
			queryDb = new BasicDBObject();
		}

		BasicDBObject replaceDb = null;
		if ((replaceWith != null) && (replaceWith.size() > 0)) {
			replaceDb = getDBObjectFromMap(replaceWith);
		}
		else {
			// replaceWith cannot be null or empty in this method
			_log.error("replace: param replaceWith is null or empty");
			throw new InvalidInputException("replace: param replaceWith is null or empty");
		}

		try {
			_log.debug("replace: query = " + queryDb.toString() + ", replaceWith = " + replaceDb.toString());		
			WriteResult wr = _mongoCollection.update(queryDb, replaceDb, upsert, multi);
			if (wr != null) {
				int nDocs = wr.getN();
				_log.trace("replace: " + wr.toString());
				_log.debug("replace: number of documents replaced: " + nDocs);
				if (nDocs != 1) {
					_log.warn("replace: expected number of replaced docs: 1, actual affected: " + nDocs);
				}
				boolean replaceExistingDoc = wr.isUpdateOfExisting();
				if (!upsert && nDocs > 0 && !replaceExistingDoc) {
					_log.warn("replace: expected existing doc replacement, resulted in that not being the case");
				}
			}
		} catch (MongoException e) {
			_log.debug("replace: MongoException while trying to replace");
			throw new DbException("replace: MongoException while trying to replace");
		}
	}

	

    /**
     * Mongo aggregate method
     * @param query
     * @param pipeline
     * @param limitResults
     * @return
     * @throws InvalidInputException
     */
    public List<JSONObject> aggregate(Map<String, Map<String, Object>> query, String[] pipeline, int limitResults)
    		throws InvalidInputException, DbException {

    	List<JSONObject> retList = new ArrayList<JSONObject>();

    	if (_mongoCollection == null) {
    		_log.error("find: _mongoCollection is not initialized");
    		throw new InvalidInputException("MongoDb.find: _mongoCollection is not initialized");
    	}

    	if (query == null) {
    		return null;
    	} else {
    		_log.debug("aggregate: query = " + query.toString());
    	}

    	List<DBObject> pipelineObjects = new ArrayList<DBObject>();
    	try {

    		for (int i = 0; i < pipeline.length; i++) {

    			DBObject dbObjectItem = new BasicDBObject();
    			Map<String, Object> fieldsJson = query.get(pipeline[i]);
    			for (String key : fieldsJson.keySet()) {
    				Object value;
    				if (key.equals("$or")) {
    					if (fieldsJson.get(key).getClass().equals(ArrayList.class)) {
    						@SuppressWarnings("unchecked")
							ArrayList<Map<String,Object>> arrVal = (ArrayList<Map<String,Object>>) fieldsJson.get(key);
    						value = (Object) arrVal;
    					} else if (fieldsJson.get(key).getClass().equals(JSONArray.class)) {
                            JSONArray jsonArrVal = (JSONArray) fieldsJson.get(key);
                            value = com.mongodb.util.JSON.parse(jsonArrVal.toString());
    					} else {
    						value = fieldsJson.get(key);
    					}
    				} else {
    					value = fieldsJson.get(key);
    				}
    				if (value != "operator") {
    					dbObjectItem.put(key, value);
    				} else {
    					String op = "add";
    					Map<String, Object> opJson = query.get("operator");
    					switch (op) {
    					case "add" :
    						DBObject opObject = new BasicDBObject();
    						opObject.put("$add", opJson.get(key));
    						dbObjectItem.put(key,opObject);
    						break;
    					case "multiply" :
    						break;
    					case "subtract" :
    						break;
    					}
    				}
    			}
    			if (dbObjectItem != null) {
    				pipelineObjects.add(new BasicDBObject(pipeline[i], dbObjectItem));
    			}
    		}
    		if (limitResults >0) {
    			pipelineObjects.add(new BasicDBObject("$limit", limitResults));
    		}

    		AggregationOutput output = _mongoCollection.aggregate(pipelineObjects);
    		_log.debug(" Aggregate db : pipeline objects : " + pipelineObjects.toString());

    		for (DBObject result : output.results()) {
    			JSONObject resJson = new JSONObject(result.toString());
    			retList.add(resJson);
    		}

    	} catch (MongoException e) {
    		_log.debug("aggregate: MongoException while trying to convert cursor to array");
    		throw new DbException("find: MongoException while trying to get objects from the cursor");
    	} catch (NullPointerException e) {
    		_log.error("aggregate: NullPointerException while trying to get object from the db");
    		throw new InvalidInputException("aggregate: NullPointerException while trying to get object from the db");
    	} catch (JSONException e ) {
    		_log.error("aggregate: JSONException while trying to get JSON from the DBObject");
    		throw new InvalidInputException("aggregate: JSONException while trying to get JSON from the DBObject");
    	}
    	
    	//_log.debug("Aggregate db : result set : " + retList.toString());
    	return retList;
    }

    /**
     * Mongo aggregate method
     * @param query
     * @param pipeline
     * @param limitResults
     * @return
     * @throws InvalidInputException
     */
    public List<Map<String, Object>> aggregateMap(Map<String, Map<String, Object>> query, String[] pipeline, int limitResults)
    		throws InvalidInputException, DbException {

    	List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

    	if (_mongoCollection == null) {
    		_log.error("find: _mongoCollection is not initialized");
    		throw new InvalidInputException("MongoDb.find: _mongoCollection is not initialized");
    	}

    	if (query == null) {
    		return null;
    	} else {
    		_log.debug("aggregate: query = " + query.toString());
    	}

    	List<DBObject> pipelineObjects = new ArrayList<DBObject>();
    	try {

    		for (int i = 0; i < pipeline.length; i++) {

    			DBObject dbObjectItem = new BasicDBObject();
    			Map<String, Object> fieldsJson = query.get(pipeline[i]);
    			for (String key : fieldsJson.keySet()) {
    				Object value;
    				if (key.equals("$or")) {
    					if (fieldsJson.get(key).getClass().equals(ArrayList.class)) {
    						@SuppressWarnings("unchecked")
							ArrayList<Map<String,Object>> arrVal = (ArrayList<Map<String,Object>>) fieldsJson.get(key);
    						value = (Object) arrVal;
    					} else if (fieldsJson.get(key).getClass().equals(JSONArray.class)) {
                            JSONArray jsonArrVal = (JSONArray) fieldsJson.get(key);
                            value = com.mongodb.util.JSON.parse(jsonArrVal.toString());
    					} else {
    						value = fieldsJson.get(key);
    					}
    				} else {
    					value = fieldsJson.get(key);
    				}
    				if (value != "operator") {
    					dbObjectItem.put(key, value);
    				} else {
    					String op = "add";
    					Map<String, Object> opJson = query.get("operator");
    					switch (op) {
    					case "add" :
    						DBObject opObject = new BasicDBObject();
    						opObject.put("$add", opJson.get(key));
    						dbObjectItem.put(key,opObject);
    						break;
    					case "multiply" :
    						break;
    					case "subtract" :
    						break;
    					}
    				}
    			}
    			if (dbObjectItem != null) {
    				pipelineObjects.add(new BasicDBObject(pipeline[i], dbObjectItem));
    			}
    		}
    		if (limitResults >0) {
    			pipelineObjects.add(new BasicDBObject("$limit", limitResults));
    		}

    		AggregationOutput output = _mongoCollection.aggregate(pipelineObjects);
    		_log.debug(" Aggregate db : pipeline objects : " + pipelineObjects.toString());

    		for (DBObject result : output.results()) {
    			
				@SuppressWarnings("unchecked")
				Map<String,Object> mapObj = (Map<String,Object>) ((result != null) ? result.toMap(): null);
				if (retList == null) {
					retList = new ArrayList<Map<String,Object>>();
				}
				retList.add(mapObj);
    		}

    	} catch (MongoException e) {
    		_log.debug("aggregate: MongoException while trying to convert cursor to array..." + e.toString());
    		throw new DbException("find: MongoException while trying to get objects from the cursor..." + e.toString());
    	} catch (ClassCastException | NullPointerException | IllegalArgumentException | UnsupportedOperationException e) {
    		_log.error("aggregate: Exception while trying to get Map from the DBObject... " + e.toString());
    		throw new InvalidInputException("aggregate: Exception while trying to get Map from the DBObject..." +  e.toString());
    	}
    	
    	// _log.debug("Aggregate db : result set : " + retList.toString());
    	return retList;
    }

    /**
     * Mongo aggregate method
     * @param query
     * @param pipeline
     * @param limitResults
     * @return
     * @throws InvalidInputException
     */
    public List<Map<String, Object>> aggregateMapMultiple(Map<String, Map<String, Object>> query, String[] pipeline, int limitResults)
            throws InvalidInputException, DbException {

        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();

        if (_mongoCollection == null) {
            _log.error("find: _mongoCollection is not initialized");
            throw new InvalidInputException("MongoDb.find: _mongoCollection is not initialized");
        }

        if (query == null) {
            return null;
        } else {
            _log.debug("aggregate: query = " + query.toString());
        }

        List<DBObject> pipelineObjects = new ArrayList<DBObject>();
        try {

            for (int i = 0; i < pipeline.length; i++) {

                DBObject dbObjectItem = new BasicDBObject();
                Map<String, Object> fieldsJson = query.get(pipeline[i]);

                if (fieldsJson.containsKey("$unwind")) {
                    dbObjectItem.put("$unwind",fieldsJson.get("$unwind"));
                } else {

                    for (String key : fieldsJson.keySet()) {
                        Object value;
                        if (key.equals("$or")) {
        					if (fieldsJson.get(key).getClass().equals(ArrayList.class)) {
        						@SuppressWarnings("unchecked")
    							ArrayList<Map<String,Object>> arrVal = (ArrayList<Map<String,Object>>) fieldsJson.get(key);
        						value = (Object) arrVal;
        					} else if (fieldsJson.get(key).getClass().equals(JSONArray.class)) {
                                JSONArray jsonArrVal = (JSONArray) fieldsJson.get(key);
                                value = com.mongodb.util.JSON.parse(jsonArrVal.toString());
        					} else {
        						value = fieldsJson.get(key);
        					}
                        } else {
                            value = fieldsJson.get(key);
                        }
                        if (value != "operator") {
                            dbObjectItem.put(key, value);
                        } else {
                            String op = "add";
                            Map<String, Object> opJson = query.get("operator");
                            switch (op) {
                                case "add":
                                    DBObject opObject = new BasicDBObject();
                                    opObject.put("$add", opJson.get(key));
                                    dbObjectItem.put(key, opObject);
                                    break;
                                case "multiply":
                                    break;
                                case "subtract":
                                    break;
                            }
                        }
                    }
                }

                if (dbObjectItem != null) {

                    String pipelineObjectType = pipeline[i];
                    Pattern p = Pattern.compile("(^\\$[A-Za-z]+)\\d+");

                    Matcher m = p.matcher(pipeline[i]);
                    if (m.matches()) {
                        pipelineObjectType = m.group(1);
                    }

                    if (pipelineObjectType.equals("$unwind")) {
                        pipelineObjects.add(dbObjectItem);
                    } else {
                        pipelineObjects.add(new BasicDBObject(pipelineObjectType, dbObjectItem));
                    }
                }
            }
            if (limitResults >0) {
                pipelineObjects.add(new BasicDBObject("$limit", limitResults));
            }

            AggregationOutput output = _mongoCollection.aggregate(pipelineObjects);
            _log.debug(" Aggregate db : pipeline objects : " + pipelineObjects.toString());

            for (DBObject result : output.results()) {

                @SuppressWarnings("unchecked")
                Map<String,Object> mapObj = (Map<String,Object>) ((result != null) ? result.toMap(): null);
                if (retList == null) {
                    retList = new ArrayList<Map<String,Object>>();
                }
                retList.add(mapObj);
            }

        } catch (MongoException e) {
            _log.debug("aggregate: MongoException while trying to convert cursor to array");
            throw new DbException("find: MongoException while trying to get objects from the cursor");
        } catch (ClassCastException | NullPointerException | IllegalArgumentException | UnsupportedOperationException e) {
            _log.error("aggregate: Exception while trying to get Map from the DBObject");
            throw new InvalidInputException("aggregate: Exception while trying to get Map from the DBObject");
        }

        // _log.debug("Aggregate db : result set : " + retList.toString());
        return retList;
    }

    @Override
    public void closeConnection () {
        this.close();
    }
    
    public static void main(String args[]) {
    	
    	MongoDb mongoDb = null;
    	try {
    		// initialize config
    		ConfiguratorUtil.initConfig("syncserver");    		
    		mongoDb = new MongoDb();
			mongoDb.setCollectionKey("bootstrap_organizations_list");
			mongoDb.setCollectionName("bootstrap_organizations_list");
			
			Map<String, Object> query = new HashMap<String, Object>();
			try {
				query.put("500m_id", "organization:14230");
				List<Map<String,Object>> list = mongoDb.findMap(query);
				
				if (list != null) {
					for (int i=0; i<=list.size(); i++) {
						Map<String,Object> mapObj = (Map<String, Object>)list.get(i);
						for (Map.Entry<String, Object> entry : mapObj.entrySet()) {
							System.out.println(entry.getKey() + "/" + entry.getValue() + "/" + entry.getValue().getClass());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		} catch (InvalidInputException e) {
			e.printStackTrace();
		} catch (DbException e) {
			e.printStackTrace();
		} catch (ConfigNotFoundException e1) {
			e1.printStackTrace();
		} finally {
			mongoDb.closeConnection();
		}
    	
    }

}
