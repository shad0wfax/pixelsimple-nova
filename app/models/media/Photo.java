/**
 * © PixelSimple 2011-2012.
 */
package models.media;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;

import play.db.jpa.Model;

import com.pixelsimple.appcore.media.MediaType;

/**
 * @author Akshay Sharma
 *
 * Jun 15, 2012
 */
@Entity
public class Photo extends Media {
	@Lob
	public String info;
	public String artist;
	public Date createdDate;
	public String metaData;

	/* (non-Javadoc)
	 * @see models.Media#type()
	 */
	@Override
	public MediaType type() {
		return MediaType.PHOTO;
	}
}
