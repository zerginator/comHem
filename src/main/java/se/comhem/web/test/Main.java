package se.comhem.web.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;

public class Main   {

    private static Logger LOGGER = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
    	LOGGER.info("Starting Jetty Server ...");
    	SpringApplication.run(AppConfig.class, args);
    }
    
    
}
