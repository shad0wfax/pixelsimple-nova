/**
 * © PixelSimple 2011-2012.
 */
package controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.mvc.Controller;

/**
 * @author Akshay Sharma
 *
 * Jun 10, 2012
 */
public class Authenticator extends Controller {
	private static final Logger LOG = LoggerFactory.getLogger(Authenticator.class);

    public static void index() {
    	LOG.debug("session id = {}", session.getId());
    	String sessionVal = session.get("akshaytestinrsession");
    	System.out.println("Akshay::the secret configured is::" + sessionVal);
        //render();
    }

}
