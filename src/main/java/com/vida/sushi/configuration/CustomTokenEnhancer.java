package com.vida.sushi.configuration;

import com.vida.sushi.services.users.ProfileConfigurationService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * Creating profile from user auth and set the response with profile identifier
 */
@RequiredArgsConstructor
class CustomTokenEnhancer implements TokenEnhancer {
	
	@NonNull private final ProfileConfigurationService profileService;
	
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    	        
        final Map<String, Object> additionalInfo = new HashMap<>();
        
        additionalInfo.put("profile", this.profileService.configure(authentication).getId());

        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }
}
