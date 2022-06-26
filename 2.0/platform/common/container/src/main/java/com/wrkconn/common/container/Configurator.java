package com.wrkconn.common.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wrkconn.common.configuration.Config;
import com.wrkconn.common.utils.exceptions.ConfigNotFoundException;

public class Configurator {
	
	// local static objects
	static Logger _log = LogManager.getLogger(Configurator.class);

	private static Config _config = null;
	private static Map<String, AppFactory> _appFactories = new HashMap<String, AppFactory>();

	public static Config getConfig() throws ConfigNotFoundException {
		if (_config == null) {
			_log.debug("getConfig: _config is not initialized.");
			throw new ConfigNotFoundException("getConfig: _config is not initialized.");
		}
		return _config;
	}

	public void setConfig(Config config) {
		_config = config;
	}

	protected void registerAppFactory(String name, AppFactory appFactory) {
		_appFactories.put(name, appFactory);
	}
	
	public static AppFactory getAppFactory(String name) {
		return _appFactories.get(name);
	}
	
	protected List<AppFactory> getAllFactories() {
		return new ArrayList<AppFactory>( _appFactories.values());
	}
	
}
