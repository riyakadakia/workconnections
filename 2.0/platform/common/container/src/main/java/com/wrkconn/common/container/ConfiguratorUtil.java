package com.wrkconn.common.container;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wrkconn.common.configuration.Config;
import com.wrkconn.common.configuration.ConfigLoader;

public class ConfiguratorUtil {
	
	// local static objects
	static Logger _log = LogManager.getLogger(ConfiguratorUtil.class);
	
	/*
	 * Load the app.properties file and setup the Configurator
	 */
	public static void initConfig(String app) {

		String configFolder = "/opt/wrkconn/config/app/" + app + "/";
		_log.info("Loading configuration");
		ConfigLoader configLoader = new ConfigLoader(configFolder + "app.properties");
		Config config = configLoader.load();
		_log.info("Done loading configuration");

		Configurator cfgr = new Configurator();
		cfgr.setConfig(config);
		_log.info("Done setting configuration");
	}

}
