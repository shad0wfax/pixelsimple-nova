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

    public static void isSecure() {
    	LOG.debug("session id = {}", session.getId());
    	String sessionVal = session.get("akshaytestinrsession");
    	System.out.println("Authenticator::isSecure::the sessionVal is::" + sessionVal);
    	
//    	if ("somevalue".equals(sessionVal))
//    		renderJSON("{ \"security\": { \"isSecure\": \"false\" } }");
//    	else 
//    		renderJSON("{ \"security\": { \"isSecure\": \"true\" } }");
    	
   		renderJSON("{ \"security\": { \"isSecure\": \"true\" } }");

    }

    public static void isValidUser() {
    	LOG.debug("session id = {}", session.getId());
    	String sessionVal = session.get("akshaytestinrsession");
    	System.out.println("Authenticator::isValidUser::the sessionVal is::" + sessionVal);
    	
    	if ("somevalue".equals(sessionVal))
    		renderJSON("{ \"security\": { \"isValidUser\": \"true\" } }");
    	else 
    		renderJSON("{ \"security\": { \"isValidUser\": \"false\" } }");
   }

}
