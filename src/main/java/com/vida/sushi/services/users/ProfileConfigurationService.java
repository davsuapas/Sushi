package com.vida.sushi.services.users;

import java.util.Optional;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.vida.sushi.domains.users.Profile;
import com.vida.sushi.repositories.users.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.NonNull;


/**
 * When the user is registered, it's set to profile the client id and provider 
 *
 * @author dav.sua.pas@gmail.com
 */
@RequiredArgsConstructor
public class ProfileConfigurationService {
	
	@NonNull private final ProfileRepository profileRepository;

	public Profile configure(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		
		String userAuthenticationClientId = ((OAuth2Authentication)authentication.getUserAuthentication()).getOAuth2Request().getClientId();
				
		Optional<Profile> profile =
				this.profileRepository.findByProviderAndUserName(
						userAuthenticationClientId, 
						authentication.getName());
		
		if (!profile.isPresent()) {
			
			return profileRepository.save(
					new Profile(
						userAuthenticationClientId,	
						authentication.getName()
					)
			);
		}
		else {
			return profile.get();
		}
	}
}
