/**
 * © PixelSimple 2011-2012.
 */
package models.media;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;

import jobs.MediaScannerJob;
import models.config.MediaFileSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.db.jpa.Model;

import com.pixelsimple.appcore.Resource;
import com.pixelsimple.appcore.Resource.RESOURCE_TYPE;
import com.pixelsimple.appcore.media.MediaType;

/**
 * @author Akshay Sharma
 *
 * Jun 23, 2012
 */
@Entity
public class MediaDirectory extends Model {
	private static final Logger LOG = LoggerFactory.getLogger(MediaDirectory.class);

	public String resource;
	public String resourceType;
	// 3 values - pending / processing / completed
	public String scanned;
//	public Resource resource;
	
	public List<Media> findAllMediaFilesInResource() {
		if (this.scanned == null || "pending".equals(this.scanned)) {
			// TODO: Should we call scan here?
			return new ArrayList<Media>();
		} 
		
		return null;
	}
	
	public void scanAndAddMediaResource() {
		// Fetch from media scanner. Then insert it to DB.
		LOG.debug("scanAndAddMediaResource::will invoke scan as a job");
		Resource res = new Resource(resource, RESOURCE_TYPE.valueOf(resourceType));
		List<MediaFileSupport> supportedFormats = MediaFileSupport.findAll();
		new MediaScannerJob(res, supportedFormats, this.id).now();
	}

	public List<Media> findMediaFilesInResource(MediaType type) {
		return null;
	}
	
	public static Map<String, String> extensionToTypeMapping() {
		// Create a simple map with extension to media type (reverse lookup) - done in this call for optimization!
		Map<String, String> extensionTypeMap = new HashMap<String, String>();
		List<MediaFileSupport> supportedFormats = MediaFileSupport.findAll();
		
		for (MediaFileSupport support : supportedFormats) {
			extensionTypeMap.put(support.extension.toLowerCase(), support.mediaType.toLowerCase());
		}
		return extensionTypeMap;
	}

}
