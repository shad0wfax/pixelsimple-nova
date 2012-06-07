/**
 * © PixelSimple 2011-2012.
 */
package jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.jobs.Job;
import play.jobs.OnApplicationStop;

/**
 * @author Akshay Sharma
 *
 * Jun 7, 2012
 */
@OnApplicationStop
public class Shutdown extends Job {
	private static final Logger LOG = LoggerFactory.getLogger(Shutdown.class);

    public void doJob() {
    	LOG.info("Shutting down Nova...");
    }

}
