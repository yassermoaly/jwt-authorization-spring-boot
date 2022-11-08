package demo.project.model.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("demo.project.model.*")
@EntityScan("demo.project.model.*")
public class ModelConfiguration {

}
