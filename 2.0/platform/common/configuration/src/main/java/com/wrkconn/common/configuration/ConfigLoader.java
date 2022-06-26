package com.wrkconn.common.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigLoader {
  String _filename;
  private static Logger log = LogManager.getLogger(ConfigLoader.class);

  public ConfigLoader(String filename) {
    _filename = filename;
  }

  public Config load() {
    PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
    try {
      propertiesConfiguration.load(_filename);
    } catch (ConfigurationException e) {
      log.error("Could not load the configuration ", e);
    }
    return new DefaultConfigImpl(propertiesConfiguration);
  }
}
