package it.spinningnotes.auth.services;

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

@SpringBootTest
public class TokenServiceTest {
	@Autowired
	TokenService tokenService;
	@Value("${secretkey}")
	private String secretKey;
	
	@DisplayName("Test creation of the token")
	@Test
	public void testProduce() {
		User user = new User();
		user.setEmail("test@test.it");
		String token = tokenService.produce(user);
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT decodedToken = verifier.verify(token);
		assertEquals(true, user.getEmail().compareTo(decodedToken.getClaims().get("email").asString()) == 0);	
	}
}
