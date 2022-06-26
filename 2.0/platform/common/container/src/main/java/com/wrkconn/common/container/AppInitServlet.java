package com.wrkconn.common.container;

import java.util.Enumeration;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.LoggerContext;

import com.wrkconn.common.configuration.Config;
import com.wrkconn.common.configuration.ConfigLoader;
import com.wrkconn.common.utils.LoggerUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppInitServlet extends HttpServlet {

	private static final long serialVersionUID = 2244011832733964705L;

	static Config config = null;
	private static String app = null;
	private static AppInitializer appInitializer = null;
	static Logger _log = null;

	public void init() {

		String appInitializerClass = null;
		Enumeration<String> initParams = getServletConfig().getInitParameterNames();
		while (initParams.hasMoreElements()) {
			String initParamName = initParams.nextElement();
			String initParamValue = getServletConfig().getInitParameter(initParamName);
			System.out.println(initParamName + ": " + initParamValue);
			if ("app".equals(initParamName)) {
				app = initParamValue;
			} else if ("appInitializer".equals(initParamName)) {
				appInitializerClass = initParamValue;
			}
		}

		String configFolder = "/opt/wrkconn/config/app/" + app + "/";
		try {
			File configFile = new File(configFolder + "log4j.properties");

			// XXX: FIX ME 
			//PropertyConfigurator.configure(configFolder + "log4j.properties");
			//LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
			//context.setConfigLocation(configFile.toURI());
			
			_log = LogManager.getLogger(AppInitServlet.class);			
			_log.info("Loading configuration");
			ConfigLoader configLoader = new ConfigLoader(configFolder + "app.properties");
			config = configLoader.load();
			_log.info("Done loading configuration");

			// now run app initializer
			if (appInitializerClass != null && appInitializerClass.length() > 0 && appInitializer == null) {
				_log.info("Starting app specific initilization");
				ClassLoader cLoader = this.getClass().getClassLoader();

				@SuppressWarnings("unchecked")
				Class<AppInitializer> initCls = (Class<AppInitializer>) Class.forName(appInitializerClass, true, cLoader);
				appInitializer = (AppInitializer) initCls.newInstance();
				appInitializer.setConfig(config);
				appInitializer.init(app);
			}
			_log.debug("Done intializing " + app);
			
		} catch (Exception ex) {
			if (_log != null) {
				_log.error(ex.toString());
			}
			LoggerUtil.logException(_log, ex);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) {
	}
	
}
