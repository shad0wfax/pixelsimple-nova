/**
 * © PixelSimple 2011-2012.
 */
package jobs;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.config.MediaFileSupport;
import models.media.Media;
import models.media.MediaDirectory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.db.jpa.JPA;
import play.jobs.Job;

import com.pixelsimple.appcore.Resource;
import com.pixelsimple.commons.mediascanner.MediaScanner;

/**
 * @author Akshay Sharma
 *
 * Jun 26, 2012
 */
public class MediaScannerJob extends Job {
	private static final Logger LOG = LoggerFactory.getLogger(MediaScannerJob.class);
	
	private Resource resource;
	private List<MediaFileSupport> fileExtensionFilters;
	private Long mediaDirectoryScannedId;
	
	public MediaScannerJob(Resource resource, List<MediaFileSupport> fileExtensionFilters, Long mediaDirectoryScannedId) {
		this.resource = resource;
		this.fileExtensionFilters = fileExtensionFilters;
		this.mediaDirectoryScannedId = mediaDirectoryScannedId;
	}
	
	@Override
	public void doJob() {
		// First set the scanned status to processing
		this.updateMediaDirectory("processing");
		try {
			LOG.debug("Going to scan media for resources: {} ", this.resource);

			String[] fileNameExtensionFilters = this.getFileFilters();
			List<String> matchingFiles = new MediaScanner().create(this.resource).recursive(true)
				.filterOnFileExtensions(fileNameExtensionFilters).scan();
			
			LOG.debug("Scanned matchingFiles: {} ", matchingFiles);
			
			if (matchingFiles == null || matchingFiles.size() == 0)
				return;
			
			// Insert the found matching files into DB as media (Video/audio/photo)
			this.insertMedia(matchingFiles);
			// Finally set the scanned status to completed
			this.updateMediaDirectory("completed");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Error occurred scanning media for resources - " + resource, e);
			this.updateMediaDirectory("failed");
			throw new RuntimeException(e);
		} finally {
			LOG.debug("doJob::Going to updae dir's scanned status to completed for {}", resource);
		}
	}

	/**
	 * @return
	 */
	private String[] getFileFilters() {
		if (this.fileExtensionFilters == null)
			return null;
		
		String[] fileNameExtensionFilters = new String[this.fileExtensionFilters.size()];
		int i = 0;

		// Also compute the reverse map - done in this call for optimization!
		for (MediaFileSupport support : fileExtensionFilters) {
			fileNameExtensionFilters[i++] = support.extension;
		}
		return fileNameExtensionFilters;
	}

	// TODO: Multi-threaded insert if too many? Also flush often? - Check performance
	private void insertMedia(List<String> matchingFiles) {
		Map<String, String> extensionTypeMap = MediaDirectory.extensionToTypeMapping();
				
		for (String file : matchingFiles) {
			String extension = file.substring(file.lastIndexOf(".") + 1);
			// Get the reverse lookup value - media type
			String mediaType = extensionTypeMap.get(extension.trim().toLowerCase());
			Media media = Media.create(mediaType);
			media.path = file;
			media.save();
		}
	}
	
	// Update the media directory table in a separate transaction to indicate the state of processing.
	private void updateMediaDirectory(String scanStatus) {
		EntityManager newem = JPA.newEntityManager();
		newem.getTransaction().begin();

		try {
			Query q = newem.createNativeQuery("UPDATE MEDIADIRECTORY SET SCANNED = ? WHERE ID = ?");
			q.setParameter(1, scanStatus);
			q.setParameter(2, this.mediaDirectoryScannedId);
	
			LOG.debug("updateMediaDirectory::Going to updae dir's scanned status to {}", scanStatus);
			q.executeUpdate();
			newem.getTransaction().commit();
		} catch (Exception e) {
			if (newem.getTransaction().isActive())
				newem.getTransaction().rollback();
		} finally {
			if (newem.isOpen())
				newem.close();
		}
		
		
	}
		
//	080 23089435
//	9632575740
	
}
