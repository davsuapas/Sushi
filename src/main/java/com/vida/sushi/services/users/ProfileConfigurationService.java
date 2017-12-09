package com.vida.sushi.services.users;

import com.vida.sushi.domains.users.Profile;
import com.vida.sushi.repositories.users.ProfileRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.Optional;


/**
 * When the user is registered, it's set to profile the client id and provider 
 *
 * @author dav.sua.pas@gmail.com
 */
@RequiredArgsConstructor
public class ProfileConfigurationService {
	
	@NonNull private final ProfileRepository profileRepository;

	public Profile configure(OAuth2Authentication authentication) {
		Optional<Profile> profile =	this.profileRepository.findByUserName(authentication.getName());
        return profile.orElseGet(() -> profileRepository.save(new Profile(authentication.getName())));
	}
}
