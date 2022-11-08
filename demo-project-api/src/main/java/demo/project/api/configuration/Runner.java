package demo.project.api.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import demo.project.service.configuration.ServiceConfiguration;
import demo.project.utility.configuration.UtilityConfiguration;
import demo.project.persistence.configuration.PersistenceConfiguration;
@SpringBootApplication
@Import({ ServiceConfiguration.class,SwaggerConfig.class,DozerConfiguration.class,PersistenceConfiguration.class,UtilityConfiguration.class})
@ComponentScan("demo.project.api")

public class Runner {
	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}
}
