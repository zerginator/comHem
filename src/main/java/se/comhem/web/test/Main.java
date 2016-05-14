package se.comhem.web.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("se.comhem.web.test")
public class Main   {

    private static Logger LOGGER = LogManager.getLogger(Main.class);
    
    public static void main(String[] args) {
    	LOGGER.info("Starting Jetty Server ...");
    	SpringApplication.run(Main.class, args);
    }
}
