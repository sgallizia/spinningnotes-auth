package it.spinningnotes.auth.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import ch.qos.logback.core.encoder.Encoder;
import it.spinningnotes.auth.entities.User;
import it.spinningnotes.auth.models.Login;
import it.spinningnotes.auth.repositories.UserRepository;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration
public class UserServiceTest {
	@MockBean
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PasswordEncoder encoder;
	
	
	@DisplayName("Test Sign up with correct information")
	@Test
	public void testSignUp() {
		User user = new User();
		user.setEmail("info@unittest.it");
		user.setPassword("Test!0102");
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(null);
		when(userRepository.save(user)).thenReturn(user);
		User userOutput = userService.signup(user);
		verify(userRepository).save(user);
		verify(userRepository).findByEmail("info@unittest.it");
		assertEquals(userOutput != null, true);
	}
	
	@DisplayName("Test Sign up with email which already exists")
	@Test
	public void testSignUpEmailAlreadyExists() {
		User user = new User();
		user.setEmail("info@unittest.it");
		user.setPassword("Test!0102");
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(user);
		try {
			userService.signup(user);
			assertEquals(true, false);
		} catch (ResponseStatusException e) {
			verify(userRepository).findByEmail("info@unittest.it");
			assertEquals(e.getStatus() == HttpStatus.UNPROCESSABLE_ENTITY , true);
		}
		catch(Exception e) {
			assertEquals(true, false);
		}
	}
	
	@DisplayName("Test sign in with correct data")
	@Test
	public void testSignWithCorrectData() {
		Login loginData = new Login();
		loginData.setEmail("info@unittest.it");
		loginData.setPassword("testpassword");
		User user = new User();
		user.setPassword(encoder.encode("testpassword"));
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(user);
		String token = userService.signin(loginData);
		verify(userRepository).findByEmail("info@unittest.it");
		assertEquals(token != null && token.length() > 0, true);
		
	}
	
	@DisplayName("Test sign in with wrong email")
	@Test
	public void testSignWithWrongEmail() {
		Login loginData = new Login();
		loginData.setEmail("info@unittest.it");
		loginData.setPassword("testpassword");
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(null);
		try {
			userService.signin(loginData);
			assertEquals(true, false);
		} catch (ResponseStatusException e) {
			verify(userRepository).findByEmail("info@unittest.it");
			assertEquals(e.getStatus() == HttpStatus.FORBIDDEN && e.getReason() == "Email does not exist", true);
		}
		catch(Exception e) {
			assertEquals(true, false);
		}
		
	}
	
	@DisplayName("Test sign in with wrong password")
	@Test
	public void testSignWithWrongPassword() {
		Login loginData = new Login();
		loginData.setEmail("info@unittest.it");
		loginData.setPassword("testpassword");
		User user = new User();
		user.setPassword(encoder.encode("testwrongpassword"));
		when(userRepository.findByEmail("info@unittest.it")).thenReturn(user);
		try {
			userService.signin(loginData);
			assertEquals(true, false);
		} catch (ResponseStatusException e) {
			verify(userRepository).findByEmail("info@unittest.it");
			assertEquals(e.getStatus() == HttpStatus.FORBIDDEN && e.getReason() == "Password is incorrect", true);
		}
		catch(Exception e) {
			assertEquals(true, false);
		}
		
	}
}
