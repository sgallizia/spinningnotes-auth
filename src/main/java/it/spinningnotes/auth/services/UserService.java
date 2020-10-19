package it.spinningnotes.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import it.spinningnotes.auth.entities.User;
import it.spinningnotes.auth.models.Login;
import it.spinningnotes.auth.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	protected UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	TokenService tokenService;
	public User signup(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		if(userRepository.findByEmail(user.getEmail()) == null) {
			return userRepository.save(user);
		}
		else {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Email already exists");
		}
	}
	
	public String signin(Login loginData) {
		User user = userRepository.findByEmail(loginData.getEmail());
		if(user != null) {
			if(encoder.matches(loginData.getPassword(), user.getPassword())) {
				return tokenService.produce(user);
			}
			else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Password is incorrect");
			}
		}
		else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Email does not exist");
		}		
	}
}
