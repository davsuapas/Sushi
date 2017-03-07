package com.vida.sushi.repositories.users;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.vida.sushi.entities.users.Profile;

/**
 * @author dsuarez
 * 
 * Repository for user profile
 *
 */
public interface ProfileRepository extends MongoRepository<Profile, String> {
	public Optional<Profile> findByProviderAndUserName(String provider, String UserName); 
}
