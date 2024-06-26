package com.example.ddmdemo.security;

import com.example.ddmdemo.util.TokenUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private UserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenHelper, UserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}

//	@Override
//	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//
//		String username;
//		String authToken = tokenUtils.getToken(request);
//
//		if (authToken != null) {
//
//			username = tokenUtils.getUsernameFromToken(authToken);
//
//			if (username != null) {
//
//				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//
//				if (tokenUtils.validateToken(authToken, userDetails)) {
//
//					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
//					authentication.setToken(authToken);
//					SecurityContextHolder.getContext().setAuthentication(authentication);
//				}
//			}
//		}
//
//		chain.doFilter(request, response);
//	}

	@Override
	public void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain chain) throws jakarta.servlet.ServletException, IOException {
		String username;
		String authToken = tokenUtils.getToken(request);

		if (authToken != null) {

			username = tokenUtils.getUsernameFromToken(authToken);

			if (username != null) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);

				if (tokenUtils.validateToken(authToken, userDetails)) {

					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		}

		chain.doFilter(request, response);
	}
}
