package com.vida.sushi.configuration;

import com.elipcero.springsecurity.web.configuration.EnabledMongoDbUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * Configure the authentication to protect /oauth/authorize, etc
 * The authentication is done across form
 *
 * <p>
 * Setting order equal to -1 to apply WebSecurityConfigurerAdapter
 * before ResourceServerConfiguration. ResourceServerConfiguration has order equal 3
 * <p>
 *
 * @author dav.sua.pas@gmail.com
 */
@Order(-1)
@Configuration
@Profile("!integration-test")
@EnabledMongoDbUserDetails
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.requestMatchers() // Only requests login matching are handled by this security configurer
			    .antMatchers("/login/**", "/logout", "/oauth/**")
            .and()
            .authorizeRequests()
                .antMatchers("/login/login*", "/login/regis*", "/login/logout/callback").permitAll()
                .anyRequest().authenticated()
            .and()
			.formLogin()
			    .loginPage("/login/login.html")
                .loginProcessingUrl("/login/loginProcess")
			    .failureUrl("/login/login.html?error=true")
		    .and()
            .logout()
                .logoutSuccessUrl("/login/logout/callback")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("auth_code", "JSESSIONID");
	}

    @Autowired
    private UserDetailsService mongoDbUserDetails;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        Assert.notNull(mongoDbUserDetails, "WebSecurityConfiguration: Mongo user details cannot be null");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(mongoDbUserDetails);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
}
