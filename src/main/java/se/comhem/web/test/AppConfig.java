package se.comhem.web.test;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan("se.comhem.web.test")
public class AppConfig {
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer classes() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
