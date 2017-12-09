package com.vida.sushi.authentication.web;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.elipcero.springgeneral.validations.ValidEmail;

import lombok.Data;

/**
 * Registration user entity. Validate password matches and email
 *
 * @author dav.sua.pas@gmail.com
 */
@Data
@PasswordMatches
class RegistrationUser {
	
	@NotNull
	@NotEmpty
	@ValidEmail
	private String email;
	
	@NotNull
	@NotEmpty
	private String password;

	private String matchingPassword;
}
