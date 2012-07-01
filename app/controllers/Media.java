/**
 * © PixelSimple 2011-2012.
 */
package controllers;

import models.media.MediaDirectory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pixelsimple.appcore.Resource.RESOURCE_TYPE;

import play.db.jpa.JPA;
import play.mvc.Controller;

/**
 * @author Akshay Sharma
 *
 * Jun 10, 2012
 */
public class Media extends Controller {
	private static final Logger LOG = LoggerFactory.getLogger(Media.class);

	public static void addMediaDirectory(String dir) {
    	LOG.debug("dir = {}", dir);
    	
    	MediaDirectory directory = new MediaDirectory();
    	directory.resource = dir;
    	directory.resourceType = RESOURCE_TYPE.DIRECTORY.name();
    	directory.save();

    	directory.scanAndAddMediaResource();
    	LOG.debug("addMediaDirectory::invoked scan, returning immediately");
    	
    	renderJSON("{ \"media scan called for " + dir + " }");
   }

}
