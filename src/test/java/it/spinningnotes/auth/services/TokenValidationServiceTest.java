package it.spinningnotes.auth.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.spinningnotes.auth.entities.User;
import it.spinningnotes.auth.models.Account;

@SpringBootTest
public class TokenValidationServiceTest {
	@Autowired
	TokenValidationService tokenValidatonService;
	@Value("${secretkey}")
	private String secretKey;
	
	@DisplayName("Test the validation of a valid token")
	@Test
	public void testValidateValidToken() {
		User user = new User();
		user.setEmail("test@test.it");
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String token = JWT.create().withClaim("email", user.getEmail()).withIssuer("Spinning Notes").sign(algorithm);
		Account account = tokenValidatonService.validate(token);
		assertEquals(true, user.getEmail().compareTo(account.getEmail()) == 0);	
	}
	
	@DisplayName("Test the validation of an invalid token")
	@Test
	public void testValidateInvalidToken() {
		try {
			tokenValidatonService.validate("wrongtoken");
			assert(false);
		} catch (Exception e) {
			assertThat(true);
		}
	}
}
