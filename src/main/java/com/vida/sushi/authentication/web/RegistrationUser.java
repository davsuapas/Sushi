package com.vida.sushi.authentication.web;

import com.elipcero.springgeneral.validations.ValidEmail;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

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
    @ValidPassword
	private String password;

	private String matchingPassword;
}
