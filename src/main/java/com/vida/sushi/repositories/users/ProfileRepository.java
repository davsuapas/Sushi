package com.vida.sushi.repositories.users;

import java.util.Optional;

import com.elipcero.springdata.repositories.mongo.MongoExtensionRepository;
import com.vida.sushi.domains.users.Profile;

/**
 * 
 * Repository for user profile
 *
 *
 * @author dav.sua.pas@gmail.com
 */
public interface ProfileRepository extends MongoExtensionRepository<Profile, String> {
	public Optional<Profile> findByProviderAndUserName(String provider, String UserName); 
}
