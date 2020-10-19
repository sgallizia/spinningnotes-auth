package it.spinningnotes.auth.verification;

import it.spinningnotes.auth.entities.User;

public interface TokenExtractor {
	public User extract();
}
