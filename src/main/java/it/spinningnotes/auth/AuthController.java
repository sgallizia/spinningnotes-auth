package it.spinningnotes.auth;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import it.spinningnotes.auth.entities.User;
import it.spinningnotes.auth.models.Account;
import it.spinningnotes.auth.models.Login;
import it.spinningnotes.auth.repositories.UserRepository;
import it.spinningnotes.auth.services.TokenValidationService;
import it.spinningnotes.auth.services.UserService;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenValidationService tokenValidationService;
	
	
	@PostMapping("/sign-up")
	public User signUp(@Valid @RequestBody User user) {
		return userService.signup(user);
	}
	
	@PostMapping("/sign-in")
	public String signIn(@Valid @RequestBody Login login) throws GeneralSecurityException, IOException {
		return userService.signin(login);
	}
	
	@PostMapping("/validate")
	public Account validate(@RequestBody @NotBlank String token) {
		return tokenValidationService.validate(token);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
