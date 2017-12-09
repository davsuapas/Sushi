package com.vida.sushi.configuration;

import com.elipcero.springdata.repositories.mongo.MongoExtendedFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Configure base packages for mongo repositories and custom extension
 *
 * @author dav.sua.pas@gmail.com
 */
@Configuration
@EnableMongoRepositories(
	basePackages = {"com.vida.sushi.repositories", "com.vida.sushi.authentication.web"},
	repositoryFactoryBeanClass = MongoExtendedFactoryBean.class
)
public class DataConfiguration {
}

