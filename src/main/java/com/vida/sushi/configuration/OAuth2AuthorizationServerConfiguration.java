package com.vida.sushi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.elipcero.springsecurity.oauth2.config.EnabledMongoDbToken;
import com.vida.sushi.repositories.users.ProfileRepository;
import com.vida.sushi.services.users.ProfileConfigurationService;

/**
 * Configure oauth2 authentication server with implicit grand type and store tokens in mongodb
 * 
 * @author David Suárez Pascual
 */
@Configuration
@EnabledMongoDbToken
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	// Inject MongoDbTokenStore by @EnabledMongoDbToken 
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
    private ProfileRepository profileRepository;	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)	throws Exception {
		endpoints
			.authenticationManager(authenticationManager)
			.tokenStore(tokenStore)
			.tokenEnhancer(tokenEnhancer());
	}	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
			.withClient("mobile").authorizedGrantTypes("implicit", "client_credentials"/*test*/)
			.accessTokenValiditySeconds(0)
			.autoApprove(true)
			.scopes("user").resourceIds("mobile");
	} 
	 
	@Bean
	@Primary
	public AuthorizationServerTokenServices tokenServices() {
	    DefaultTokenServices tokenServices = new DefaultTokenServices();
	    tokenServices.setTokenStore(tokenStore);
	    tokenServices.setTokenEnhancer(tokenEnhancer());
	    return tokenServices;
	}
	
	@Bean
	public TokenEnhancer tokenEnhancer() {
		return new CustomTokenEnhancer(new ProfileConfigurationService(profileRepository));
	}	
}
