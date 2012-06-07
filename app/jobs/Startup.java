/**
 * © PixelSimple 2011-2012.
 */
package jobs;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.init.AppInitializer;
import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.commons.util.LogUtils;
import com.pixelsimple.commons.util.OSUtils;
import com.pixelsimple.commons.util.StringUtils;
import com.pixelsimple.transcoder.init.TranscoderInitializer;

import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * @author Akshay Sharma
 *
 * Jun 6, 2012
 */
@OnApplicationStart
public class Startup extends Job {
	private static final Logger LOG = LoggerFactory.getLogger(Startup.class);
	private static final String NOVA_LOGBACK_CONFIG_FILE = "nova-logConfig.xml";


	// Bootstrap nova here!
	public void doJob() {
		
		try {
			// First thing to do is always init the log. This helps log even the app core bootstrapping. 
			initLog();
			
			LOG.info("Initializing Nova...");
			
			BootstrapInitializer initializer = new BootstrapInitializer();
			Map<String, String> configMap = initializer.bootstrap();

			AppInitializer appInitializer = new AppInitializer();

			//Add all dependent initializers. A module initializing the app should know this.
			// Depends on Transcode initializer
			appInitializer.addModuleInitializable(new TranscoderInitializer());

			appInitializer.init(configMap);
		} catch (Exception e) {
			LOG.error("Error occurred initalizing the app with system properties passed as : \n\n Exiting the app", e);
			
			// TODO: Hook up a different way to indicate to the user that the system is going down.- Listener?
			System.exit(0);
		}
	}

	/*
	 * The log initialization happens with the assumption that a system property is set/passed during startup.
	 * Each app (ex: framezap/nova) can have its own log folders set. 
	 */
	private void initLog() {
		String appDir = System.getProperty(BootstrapInitializer.JAVA_SYS_ARG_APP_HOME_DIR);
		
		// Do nothing, so logging is not initialized and will log to the console (logback's default way)
		if (StringUtils.isNullOrEmpty(appDir))
			return;
		
		// The log config for framezap will be in config folder.
		String logDir = OSUtils.appendFolderSeparator(appDir) + "config" + OSUtils.folderSeparator();
		
		// For framezap we will asume that the log configuration file is also located in the config directory.
		String logConfigFile = logDir + NOVA_LOGBACK_CONFIG_FILE;
		LogUtils.initLogFromConfigFile(logConfigFile);
	}
}
