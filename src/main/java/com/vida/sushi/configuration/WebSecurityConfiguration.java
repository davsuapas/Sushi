package com.vida.sushi.configuration;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.elipcero.springsecurity.oauth2.config.OAuth2ClientResources;
import com.elipcero.springsecurity.oauth2.config.OAuth2SsoConfigurerAdapter;

/**
 * Configure the authentication to protect /oauth/authorize, etc
 * The authentication is done across social network
 * 
 * <p>
 * Setting order equal to 6 to apply WebSecurityConfigurerAdapter before ResourceServerConfiguration
 * <p>
 * 
 * @author dav.sua.pas@gmail.com
 */
@Order(6)  
@Configuration
@Profile("!integration-test")
public class WebSecurityConfiguration extends OAuth2SsoConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.antMatcher("/**").authorizeRequests().antMatchers("/", "/login**", "/webjars/**").permitAll()
				.anyRequest()
				.authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/")).and()
				.addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
	}
	 
	private Filter ssoFilter() {
		return ssoFilter(
				Arrays.asList(
						ssoFilter(google(), "/login/google")
				)
		);
	}
	
	@Bean
	@ConfigurationProperties("google")
	OAuth2ClientResources google() {
		return new OAuth2ClientResources();
	}
}
