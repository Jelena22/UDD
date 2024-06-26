package com.example.ddmdemo.service.impl;

import com.example.ddmdemo.model.User;
import com.example.ddmdemo.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println("Loading user by username: " + email);
		User user = userRepository.findByEmail(email);
		if (user == null) {
			System.out.println("User not found: " + email);
			throw new UsernameNotFoundException(String.format("User with email '%s' not found.", email));
		} else {
			System.out.println("User found: " + email);
			return (UserDetails) user;
		}
	}
}
