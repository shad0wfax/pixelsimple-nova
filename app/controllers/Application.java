package controllers;

import jobs.Startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.Play;
import play.mvc.Controller;

public class Application extends Controller {
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void index() {
    	LOG.debug("session id = {}", session.getId());
    	session.put("akshaytestinrsession", "somevalue");
    	flash.put("akshaytestinrflash", "somevalue");
    	String secret = Play.configuration.getProperty("application.secret");
    	System.out.println("Application::session id is::" + session.getId());
    	System.out.println("Application::the secret configured is::" + secret);
        render(secret);
    }

}