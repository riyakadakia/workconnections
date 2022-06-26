package com.wrkconn.common.configuration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface Config {
  BigDecimal getBigDecimal(String key);
  BigDecimal getBigDecimal(String key, BigDecimal defaultValue);
  BigInteger getBigInteger(String key);
  BigInteger getBigInteger(String key, BigInteger defaultValue);
  Long getLong(String key);
  Long getLong(String key, Long defaultValue);
  boolean getBoolean(String key);
  boolean getBoolean(String key, boolean defaultValue);
  String getString(String key);
  String getString(String key, String defaultValue);
  Integer getInteger(String key);
  Integer getInteger(String key, Integer defaultValue);
  Double getDouble(String key);
  Double getDouble(String key, Double defaultValue);
  List<String> getList(String key);
  List<String> getList(String key, List<String> defaultValue);
  Map<String, String> getMap(String key);
  Map<String, String> getMap(String key, Map<String, String> defaultValue);
  public void dump();
  Config subset(String prefix);
}

