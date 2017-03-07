package com.vida.sushi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author dsuarez
 * 
 * Configure base packages for mongo repositories
 *
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.vida.sushi.repositories")
public class DataConfiguration {
}

