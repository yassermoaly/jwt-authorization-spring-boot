package demo.project.service.configuration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import demo.project.model.configuration.ModelConfiguration;
import demo.project.repository.configuration.RepositoryConfiguration;

@Configurable
@ComponentScan("demo.project.service.imp")
@Import({ ModelConfiguration.class,RepositoryConfiguration.class})
public class ServiceConfiguration {
	
}

