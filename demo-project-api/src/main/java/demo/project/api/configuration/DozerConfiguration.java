package demo.project.api.configuration;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfiguration {
	 
	@Bean 
	public DozerBeanMapper helloWorld(){
      return new DozerBeanMapper();
	}
}
