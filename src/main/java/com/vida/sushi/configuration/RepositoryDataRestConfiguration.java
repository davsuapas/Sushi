package com.vida.sushi.configuration;

import com.elipcero.springdatarest.webmvc.EnabledRepositoryExtensionRestMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vida.sushi.domains.aquariums.Aquarium;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
@EnabledRepositoryExtensionRestMvc
public class RepositoryDataRestConfiguration {

	@Bean
	public RepositoryRestConfigurer repositoryRestConfigurer() {

		return new RepositoryRestConfigurerAdapter() {
			
			@Override
			public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
				config
                        .setBasePath("/api")
                        .exposeIdsFor(Aquarium.class);
			}
			
			@Override
			public void configureJacksonObjectMapper(ObjectMapper objectMapper) {
				objectMapper.registerModule(new Jdk8Module()); // optional serialize
				objectMapper.registerModule(new JavaTimeModule() );
			}
		};
	}
}
