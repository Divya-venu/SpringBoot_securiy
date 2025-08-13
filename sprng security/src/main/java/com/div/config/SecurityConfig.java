package com.div.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.div.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(12);
	 }
	
	@Autowired
	private UserDetailsService userDetailsService;  //interface created class MyuserDetailService
	
	@Autowired
	private JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain sfc(HttpSecurity http) throws Exception {
		return http
		.csrf(customizer -> customizer.disable()) //is used to not to generate csrf token
		.authorizeHttpRequests(request ->request
				.requestMatchers("register" ,"login","students")   //AuthorizedUrl
				.permitAll()          //AuthorizationManagerRequestMatcher
				.anyRequest().authenticated())  //lambda expressions ((no one shud acess without authentication
		.httpBasic(Customizer.withDefaults())  //login for postman
		.sessionManagement(session ->
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
  		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
		//.formLogin(Customizer.withDefaults())   //login form for Browser
		.build();
	}

    @Bean
    AuthenticationProvider ap(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
		
		
	}
	
	
	
	
//	@Bean
//	public UserDetailsService uds() {
//		UserDetails user1= User
//				.withDefaultPasswordEncoder()
//				.username("div")
//				.password("123")
//				.roles("USER")
//				.build(); //it returns the object of userDetails
//		
//		UserDetails user2= User
//				.withDefaultPasswordEncoder()
//				.username("sahana")
//				.password("123")
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user1,user2);
//	}
	
}







//Customizer<CsrfConfigurer<HttpSecurity>> custCsrf=new Customizer<CsrfConfigurer<HttpSecurity>>() {
//	@Override
//	public void customize(CsrfConfigurer<HttpSecurity> customizer)
//	{
//		customizer.disable();
//	}
//};
//https.csrf(custCsrf);

