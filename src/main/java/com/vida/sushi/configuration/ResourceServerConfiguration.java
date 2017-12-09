package com.vida.sushi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

/**
 * Configure security for server resources
 * 
 * @author dav.sua.pas@gmail.com
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.requestMatcher(new OrRequestMatcher(
                new AntPathRequestMatcher("/logout"),
				new AntPathRequestMatcher("/profiles/**")
			)
		)
		.authorizeRequests()
		.anyRequest().access("#oauth2.hasScope('user')");
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId("mobile");
	}
}
