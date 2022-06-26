package com.wrkconn.common.container;

public abstract class AppInitializer extends Configurator {
	
  abstract public void init(String appName);
  abstract public void shutdown();
  
}
