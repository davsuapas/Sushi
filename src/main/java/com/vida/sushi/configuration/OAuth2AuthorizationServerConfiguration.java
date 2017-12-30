package com.vida.sushi.configuration;

import com.vida.sushi.authentication.web.UserService;
import com.vida.sushi.services.users.ProfileConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Configure oauth2 authentication server with implicit grand type and stateless token
 *
 * @author dav.sua.pas@gmail.com
 */
@Configuration
@EnableAuthorizationServer
@Profile("production")
public class OAuth2AuthorizationServerConfiguration extends OAuth2ServerConfigurationBase {

    @Autowired
    private ProfileConfigurationService profileService;

    @Autowired
    private UserService userService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                .withClient("mobile").authorizedGrantTypes("implicit")
                .accessTokenValiditySeconds(0)
                .autoApprove(true)
                .scopes("user").resourceIds("mobile");
    }

    public JwtAccessTokenConverter jwtTokenConverter() {
        Assert.notNull(profileService, "OAuth2AuthorizationServerConfiguration: ProfileConfigurationService can be null");
        Assert.notNull(userService, "OAuth2AuthorizationServerConfiguration: UserService can be null");
        return new CustomJwtAccessTokenConverter(profileService, userService);
    }

    public static class CustomJwtAccessTokenConverter extends JwtAccessTokenConverter {

        private final ProfileConfigurationService profileService;
        private final UserService userService;

        public CustomJwtAccessTokenConverter(ProfileConfigurationService profileService, UserService userService) {
            this.profileService = profileService;
            this.userService = userService;
        }

        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
            Map<String, Object> info = new LinkedHashMap<>();
            info.put("profile", profileService.configure(authentication).getId());
            ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(info);
            userService.updateToken(authentication.getName(), accessToken.getValue());

            return super.enhance(accessToken, authentication);
        }
    }
}