package it.spinningnotes.auth.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import it.spinningnotes.auth.entities.User;

@Service
public class TokenService {


	@Value("${secretkey}")
	private String secretKey;
	
	
	public String produce(User user) {
		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		String token = JWT.create().withClaim("email", user.getEmail()).withIssuer("Spinning Notes").sign(algorithm);
		return token;
	}

}
