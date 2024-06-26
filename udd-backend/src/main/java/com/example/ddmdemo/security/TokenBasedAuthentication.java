package com.example.ddmdemo.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenBasedAuthentication extends AbstractAuthenticationToken {
	
	private static final long serialVersionUID = -330053294031758776L;

	private String token;
	private final UserDetails principle;

	public TokenBasedAuthentication(UserDetails principle) {
		super(principle.getAuthorities());
		this.principle = principle;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	@Override
	public UserDetails getPrincipal() {
		return principle;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

}
