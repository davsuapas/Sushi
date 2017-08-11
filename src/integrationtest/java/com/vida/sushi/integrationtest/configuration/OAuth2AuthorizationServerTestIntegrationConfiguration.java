package com.vida.sushi.integrationtest.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
@Profile("integration-test")
public class OAuth2AuthorizationServerTestIntegrationConfiguration extends AuthorizationServerConfigurerAdapter {
	
	public static final String OAUTH_MOBILE_USER_CLIENTID = "mobile_user";
	public static final String OAUTH_MOBILE_SECRET = "secret";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// Remove http basic authentication
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.allowFormAuthenticationForClients();
	}	
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints)	throws Exception {
		endpoints.authenticationManager(authenticationManager);
	}	

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients
			.inMemory()
			.withClient(OAUTH_MOBILE_USER_CLIENTID).secret(OAUTH_MOBILE_SECRET).authorizedGrantTypes("client_credentials")
			.accessTokenValiditySeconds(0)
			.autoApprove(true)
			.scopes("user");
	} 
}

