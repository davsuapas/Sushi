package com.vida.sushi.authentication.web;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.elipcero.springsecurity.web.MongoDbUserDetails;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Registration repository
 *
 * @author dav.sua.pas@gmail.com
 */
@RepositoryRestResource(exported = false)
public interface RegistrationRepository extends MongoRepository<MongoDbUserDetails, String> {
	MongoDbUserDetails findByUserName(String userName);
}
