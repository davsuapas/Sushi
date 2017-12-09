package com.vida.sushi.authentication.web;

import com.elipcero.springsecurity.web.MongoDbUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * Registration controller
 *
 * @author dav.sua.pas@gmail.com
 */
@RequiredArgsConstructor
@Controller()
@Profile("!integration-test")
public class RegistrationController {

	private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);
	
	private @NonNull RegistrationRepository repository;
    private @NonNull PasswordEncoder passwordEncoder;
		
	@GetMapping(value="/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new RegistrationUser());
		return "registration";
	}

	@PostMapping(value="/register")
	public String registerUser(@ModelAttribute("user") @Valid RegistrationUser user, BindingResult bindingResult) {
		
		boolean error = false;

		if (bindingResult.hasErrors()) {
			error = true;
		}
		else {
			try {
				if (this.exists(user)) {
					bindingResult.rejectValue("email", "auth.reg.form.emailDuplicated");
					error = true;
				} else {
					this.save(user);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				bindingResult.reject("auth.reg.form.generalError");
				error = true;
			}
		}

		if (error) {
			return "registration";
		}
		else {
			return "redirect:/login.html";
		}
	}

	private void save(RegistrationUser user) {
        MongoDbUserDetails userDetail =
                new MongoDbUserDetails(
                        user.getEmail(),
                        this.passwordEncoder.encode(user.getPassword()));

        this.repository.save(userDetail);
	}

	private boolean exists(RegistrationUser user) {
		return this.repository.findByUserName(user.getEmail()) != null;
	}
}
