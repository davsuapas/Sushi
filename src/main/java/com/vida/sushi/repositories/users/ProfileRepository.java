package com.vida.sushi.repositories.users;

import com.elipcero.springdata.repositories.mongo.MongoExtensionRepository;
import com.vida.sushi.domains.users.Profile;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Optional;

/**
 * 
 * Repository for user profile
 *
 *
 * @author dav.sua.pas@gmail.com
 */
public interface ProfileRepository extends MongoExtensionRepository<Profile, String> {

    @RestResource(exported = false)
	Optional<Profile> findByUserName(String UserName);
}
