package it.spinningnotes.auth.verification;

public interface TokenVerifier<T> {

	public T verify();
}
