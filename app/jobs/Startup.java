/**
 * © PixelSimple 2011-2012.
 */
package jobs;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.jobs.Job;
import play.jobs.OnApplicationStart;

import com.pixelsimple.appcore.init.AppInitializer;
import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.transcoder.init.TranscoderInitializer;

/**
 * @author Akshay Sharma
 *
 * Jun 6, 2012
 */
@OnApplicationStart
public class Startup extends Job {
	private static final Logger LOG = LoggerFactory.getLogger(Startup.class);

	// Bootstrap nova here!
	public void doJob() {
		
		try {
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
}
