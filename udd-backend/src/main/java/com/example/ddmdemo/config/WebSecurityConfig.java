package com.example.ddmdemo.config;

import com.example.ddmdemo.security.RestAuthenticationEntryPoint;
import com.example.ddmdemo.security.TokenAuthenticationFilter;
import com.example.ddmdemo.service.impl.CustomUserDetailsService;
import com.example.ddmdemo.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig{

	private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	private final CustomUserDetailsService customUserDetailsService;
	private final TokenUtils tokenUtils;

//	@Lazy
//	@Bean
//	public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
//
//	@Lazy
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthenticationEntryPoint))
				.authorizeRequests(authorize -> authorize
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/api/auth/**").permitAll()
						.requestMatchers("/api/index/**").permitAll()
						.requestMatchers("/api/search/**").permitAll()
						.requestMatchers("/verify/**").permitAll()
						.anyRequest().authenticated()
				)
				.cors(cors -> cors.disable())
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
				.csrf(csrf -> csrf.disable());

		return http.build();
	}

//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	// Servis koji se koristi za citanje podataka o korisnicima aplikacije
//	@Autowired
//	private CustomUserDetailsService customUserDetailsService;
//
//	// Handler za vracanje 401 kada klijent sa neodogovarajucim korisnickim imenom i lozinkom pokusa da pristupi resursu
//	@Autowired
//	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
//
//	// Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo njene metode za rad sa JWT u TokenAuthenticationFilteru
//	@Autowired
//	private TokenUtils tokenUtils;
//
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
//	}
//
////	@Override
////	@Bean
////	public AuthenticationManager authenticationManagerBean() throws Exception {
////		return super.authenticationManagerBean();
////	}
//
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		http
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.exceptionHandling(ex -> ex.authenticationEntryPoint(restAuthenticationEntryPoint))
//				.authorizeRequests(authorize -> authorize
//						.requestMatchers("/auth/**").permitAll()
//						.requestMatchers("/api/auth/**").permitAll()
//						.requestMatchers("/verify/**").permitAll()
//						.anyRequest().authenticated()
//				)
//				.cors(cors -> cors.disable())
//				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
//				.csrf(csrf -> csrf.disable());
//
//		return http.build();
//	}


}
