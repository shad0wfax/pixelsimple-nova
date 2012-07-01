/**
 * © PixelSimple 2011-2012.
 */
package jobs;

import java.util.Calendar;
import java.util.Map;

import models.config.MediaFileSupport;
import models.media.Audio;
import models.media.MediaDirectory;
import models.media.Photo;
import models.media.Video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.jobs.Job;
import play.jobs.OnApplicationStart;

import com.pixelsimple.appcore.Resource.RESOURCE_TYPE;
import com.pixelsimple.appcore.init.AppInitializer;
import com.pixelsimple.appcore.init.BootstrapInitializer;
import com.pixelsimple.appcore.media.MediaType;
import com.pixelsimple.commons.mediascanner.init.MediaScannerInitializer;
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

			//Add all dependent initializers. An initializing app should know the modules it depends on.
			// Depends on Transcode initializer
			appInitializer.addModuleInitializable(new TranscoderInitializer());
			appInitializer.addModuleInitializable(new MediaScannerInitializer());

			appInitializer.init(configMap);
			
			
			MediaFileSupport sup = new MediaFileSupport();
			sup.extension = "mov";
			sup.mediaType = MediaType.VIDEO.name();
			sup.save(); 
			
			sup = new MediaFileSupport();
			sup.extension = "m4a";
			sup.mediaType = MediaType.AUDIO.name();
			sup.save(); 

			sup = new MediaFileSupport();
			sup.extension = "mp3";
			sup.mediaType = MediaType.AUDIO.name();
			sup.save(); 

			sup = new MediaFileSupport();
			sup.extension = "mp4";
			sup.mediaType = MediaType.VIDEO.name();
			sup.save(); 

			sup = new MediaFileSupport();
			sup.extension = "jpg";
			sup.mediaType = MediaType.PHOTO.name();
			sup.save(); 

			sup = new MediaFileSupport();
			sup.extension = "png";
			sup.mediaType = MediaType.PHOTO.name();
			sup.save(); 
		} catch (Exception e) {
			LOG.error("Error occurred initalizing the app with system properties passed as : \n\n Exiting the app", e);
			
			// TODO: Hook up a different way to indicate to the user that the system is going down.- Listener?
			System.exit(0);
		}
	}
}
