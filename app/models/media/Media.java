/**
 * © PixelSimple 2011-2012.
 */
package models.media;

import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

import play.db.jpa.Model;

import com.pixelsimple.appcore.media.MediaType;

/**
 * @author Akshay Sharma
 *
 * Jun 26, 2012
 */
@MappedSuperclass
public abstract class Media extends Model {
	@Lob
	public String path;

	public abstract MediaType type();

	public static Media create(String mediaType) {
		// Well someday we might support more than 3 media types.
		if (MediaType.VIDEO.name().equalsIgnoreCase(mediaType)) 
			return new Video();
		else if (MediaType.AUDIO.name().equalsIgnoreCase(mediaType))
			return new Audio();
		else 
			return new Photo();
	}
}
