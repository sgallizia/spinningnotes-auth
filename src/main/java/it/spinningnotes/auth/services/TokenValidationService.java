package it.spinningnotes.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import it.spinningnotes.auth.models.Account;

@Service
public class TokenValidationService {
	
	@Value("${secretkey}")
	private String secretKey;
	
	public Account validate(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		JWTVerifier verifier = JWT.require(algorithm).withIssuer("Spinning Notes").build();
		DecodedJWT decodedToken = verifier.verify(token);
		Account account = new Account();
		account.setEmail(decodedToken.getClaims().get("email").asString());
		return account;
	}
}
