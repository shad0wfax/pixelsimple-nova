/**
 * © PixelSimple 2011-2012.
 */
package models.config;

import javax.persistence.Entity;

import play.db.jpa.Model;

/**
 * @author Akshay Sharma
 *
 * Jun 26, 2012
 */
@Entity
public class MediaFileSupport extends Model {
	public String extension;
	public String mediaType;
}
