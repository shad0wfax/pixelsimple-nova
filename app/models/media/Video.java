/**
 * © PixelSimple 2011-2012.
 */
package models.media;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Lob;

import com.pixelsimple.appcore.media.MediaType;

/**
 * @author Akshay Sharma
 *
 * Jun 15, 2012
 */
@Entity
public class Video extends Media {
	@Lob
	public String info;
	public Date createdDate;
	public String imageUrl;
	public String durationString;
	public String container;
	public String mpaaRatings;
	public String movieName;
	
	/* (non-Javadoc)
	 * @see models.Media#type()
	 */
	@Override
	public MediaType type() {
		return MediaType.VIDEO;
	}
}
