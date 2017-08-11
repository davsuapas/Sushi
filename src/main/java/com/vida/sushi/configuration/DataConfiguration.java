package com.vida.sushi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.elipcero.springdata.repositories.mongo.MongoExtendedFactoryBean;

/**
 * Configure base packages for mongo repositories and custom extension
 *
 * @author dav.sua.pas@gmail.com
 */
@Configuration
@EnableMongoRepositories(
	basePackages = "com.vida.sushi.repositories",
	repositoryFactoryBeanClass = MongoExtendedFactoryBean.class
)
public class DataConfiguration {
}

