package com.wrkconn.common.db;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.wrkconn.common.utils.exceptions.ConfigNotFoundException;
import com.wrkconn.common.utils.exceptions.DbException;
import com.wrkconn.common.utils.exceptions.InvalidInputException;

public interface DbInstance {
	
	public DbInstance openConnectionEntity(String source, String entity) 
			throws InvalidInputException, DbException, ConfigNotFoundException;
	
	public DbInstance openConnectionQueue(String source, String entity) 
			throws InvalidInputException, DbException, ConfigNotFoundException;
	
	public void insert(Map<String, Object> value) 
			throws InvalidInputException, DbException;
	
	public void insert(List<Map<String, Object>> value) 
			throws InvalidInputException, DbException;
	
	public void insert(String value) 
			throws InvalidInputException, DbException;
	
	public List<JSONObject> find(Map<String, Object> query) 
			throws InvalidInputException, DbException;
	
	public List<JSONObject> find(JSONObject query) 
			throws InvalidInputException, DbException;
	
	public List<Map<String,Object>> findMap(Map<String, Object> query)
			throws InvalidInputException, DbException;	
	
	public JSONObject findAndModify(JSONObject query, JSONObject update, boolean upsert) 
			throws InvalidInputException, DbException;
	
	public Long count(Map<String, Object> query) 
			throws InvalidInputException, DbException;

	public void update(Map<String, Object> query, Map<String, Object> update, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException;
	
	public void update(Map<String, Object> query, Map<String, Object> setUpdate, 
			   		   Map<String, Object> incUpdate, boolean upsert, boolean multi)
			throws InvalidInputException, DbException;	
	
	public void replace(Map<String, Object> query, Map<String, Object> replaceWith, boolean upsert, boolean multi) 
			throws InvalidInputException, DbException;
	
	public int push(Map<String, Object> query, String updateNodeKey, List<Map<String, Object>> updateArrValue, 
			Map<String, Object> sortQuery, int sliceCount) 
					throws InvalidInputException, DbException;
	
    public List<JSONObject> aggregate(Map<String,Map<String,Object>> query, String[] pipeline, int limitResults) 
    		throws InvalidInputException, DbException;
    
    public List<Map<String, Object>> aggregateMap(Map<String, Map<String, Object>> query, String[] pipeline, int limitResults)
    		throws InvalidInputException, DbException;

    public List<Map<String, Object>> aggregateMapMultiple(Map<String, Map<String, Object>> query, String[] pipeline, int limitResults)
            throws InvalidInputException, DbException;

    public void closeConnection();
	
} 
