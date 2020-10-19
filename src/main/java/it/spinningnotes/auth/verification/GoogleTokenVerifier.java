package it.spinningnotes.auth.verification;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public class GoogleTokenVerifier implements TokenVerifier<GoogleIdToken> {
	protected String idToken;
	public GoogleTokenVerifier(String idToken) {
		super();
		this.idToken = idToken;
	}
	@Override
	public GoogleIdToken verify() {
		// TODO Auto-generated method stub
		return null;
	}

}
