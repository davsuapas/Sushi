package com.vida.sushi.authentication.web;

import com.elipcero.springsecurity.web.MongoDbUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * Registration controller
 *
 * @author dav.sua.pas@gmail.com
 */
@Slf4j
@RequiredArgsConstructor
@Controller()
@RequestMapping(value="/login")
@Profile("production")
public class RegistrationController {

	private @NonNull RegistrationRepository repository;
    private @NonNull PasswordEncoder passwordEncoder;
		
	@GetMapping(value="/registration")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new RegistrationUser());
		return "/login/registration";
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
				    log.debug("Email duplicated: {}", user.getEmail());
					bindingResult.rejectValue("email", "auth.reg.form.emailDuplicated");
					error = true;
				} else {
                    log.debug("Saving user: {}", user.getEmail());
					this.save(user);
				}
			} catch (Exception ex) {
				log.error(ex.getMessage());
				bindingResult.reject("auth.reg.form.generalError");
				error = true;
			}
		}

		if (error) {
			return "/login/registration";
		}
		else {
			return "redirect:/login/login.html";
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
