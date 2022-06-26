package com.wrkconn.common.configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.configuration.Configuration;

public class DefaultConfigImpl implements Config {

  private Configuration _configuration;
  private final static Logger log = LogManager.getLogger(DefaultConfigImpl.class);

  // Cache the keys that are configured as maps and lists
  private HashMap<String, Map<String, String>> _maps = new HashMap<String, Map<String, String>>();
  private HashMap<String, List<String>> _lists = new HashMap<String, List<String>>();
  private HashMap<String, Config> _subsets = new HashMap<String, Config>();

  public DefaultConfigImpl(Configuration configuration) {
    _configuration = configuration;
  }

  @Override
  public BigDecimal getBigDecimal(String key) {
    return _configuration.getBigDecimal(key);
  }

  @Override
  public BigDecimal getBigDecimal(String key, BigDecimal defaultValue) {
    return _configuration.getBigDecimal(key, defaultValue);
  }

  @Override
  public BigInteger getBigInteger(String key) {
    return _configuration.getBigInteger(key);
  }

  @Override
  public BigInteger getBigInteger(String key, BigInteger defaultValue) {
    return _configuration.getBigInteger(key, defaultValue);
  }

  @Override
  public Long getLong(String key) {
	return _configuration.getLong(key);  
  }
  
  @Override
  public Long getLong(String key, Long defaultValue) {
	  return _configuration.getLong(key, defaultValue);
  }
  
  @Override
  public boolean getBoolean(String key) {
    return _configuration.getBoolean(key);
  }

  @Override
  public boolean getBoolean(String key, boolean defaultValue) {
    return _configuration.getBoolean(key, defaultValue);
  }

  @Override
  public String getString(String key) {
    return _configuration.getString(key);
  }

  @Override
  public String getString(String key, String defaultValue) {
    return _configuration.getString(key, defaultValue);
  }

  @Override
  public Integer getInteger(String key) {
    return _configuration.getInt(key);
  }

  @Override
  public Integer getInteger(String key, Integer defaultValue) {
    return _configuration.getInt(key, defaultValue);
  }
  
  @Override
  public Double getDouble(String key) {
    return _configuration.getDouble(key);
  }

  @Override
  public Double getDouble(String key, Double defaultValue) {
    return _configuration.getDouble(key, defaultValue);
  } 

  @Override
  public List<String> getList(String key) {
    List<String> list = _lists.get(key);
    if (list == null) {
      this.convertToList(key, _configuration.getList(key));
      list = _lists.get(key);
    }
    return list;
  }

  @Override
  public List<String> getList(String key, List<String> defaultValue) {
    List<String> list = getList(key);
    return list == null ? defaultValue : list;
  }

  @Override
  public Map<String, String> getMap(String key) {
    Map<String, String> map = _maps.get(key);
    if (map == null) {
      Properties properties = _configuration.getProperties(key);
      convertToMap(key, properties);
      map = _maps.get(key);
    }
    return map;
  }

  @Override
  public Map<String, String> getMap(String key, Map<String, String> defaultValue) {
    Map<String, String> map = getMap(key);
    return map == null ? defaultValue : map;
  }

  @Override
  public Config subset(String prefix) {
    Config config = _subsets.get(prefix);
    if (config == null) {
      synchronized(_subsets) {
        config = new DefaultConfigImpl(_configuration.subset(prefix));
        _subsets.put(prefix, config);
      }
    }
    return config;
  }

  private synchronized void convertToMap(String key, Properties props) {
    if (props == null || props.isEmpty()) {
      _maps.put(key, Collections.<String, String> emptyMap());
    } else {
      Map<String, String> map = new HashMap<String, String>();
      for (Entry<Object, Object> entry : props.entrySet()) {
        map.put((String) entry.getKey(), (String) entry.getValue());
      }
      _maps.put(key, map);
    }
  }

  private synchronized void convertToList(String key, List<Object> values) {
    if (values == null) {
      _lists.put(key, Collections.<String> emptyList());
    } else {
      List<String> sValues = new ArrayList<String>();
      for (Object v : values) {
        sValues.add(v.toString());
      }
      _lists.put(key, sValues);
    }
  }
  public void dump() {
    for (Iterator<String> keys = _configuration.getKeys(); keys.hasNext();) {
      String key = keys.next();
      log.info(key);
    }
  }
}
