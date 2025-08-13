package com.projectash.gameview.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import com.projectash.gameview.services.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Enable method-level security annotations like @PreAuthorize and @Secured
public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**","/loginToGame", "/register", "/static/**", "/css/**", "/js/**").permitAll() // Public endpoints
				.anyRequest().authenticated() // All other requests require authentication
		)
		.formLogin(form -> form
				.loginPage("/loginToGame")
                .loginProcessingUrl("/login") // URL to submit the login form
                .defaultSuccessUrl("/", true) // Redirect to home after successful login
				.permitAll() // Allow everyone to access the login page
		)
		.logout(logout -> logout
				.logoutUrl("/logout") // Custom logout URL
				.logoutSuccessUrl("/loginToGame?logout") // Redirect after logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll() // Allow everyone to access the logout
		);
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(CustomUserDetailsService userDetailsService) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
}
