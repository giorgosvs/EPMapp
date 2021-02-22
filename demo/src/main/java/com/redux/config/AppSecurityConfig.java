package com.redux.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.redux.services.UserDetailServiceImpl;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailServiceImpl();
    }
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}
	
	@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
         
        return authProvider;
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		String[] staticResources  =  {
		        "/css/**",
		        "/images/**",
		        "/fonts/**",
		        "/scripts/**",
		    };
		
		
		http.cors().and().csrf().disable();
		
		http.authorizeRequests() // authorize
			.antMatchers("/").permitAll() // allow "/" to be seen without authentication
			.antMatchers("/signup").permitAll()
			.antMatchers("/process_registration").permitAll()
			.antMatchers("/activate").permitAll()
			.antMatchers("/api/**").hasAnyRole("ADMIN","EDITOR")
			.antMatchers("/users/**").hasRole("ADMIN")
			.antMatchers("/petitions/**").hasAnyRole("ADMIN","EDITOR")
			.antMatchers(staticResources).permitAll()
			.anyRequest().authenticated() // all requests are authenticated
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()// allow "/login"
			.defaultSuccessUrl("/home", true) // set default page for success login
			// .failureUrl("/login.html?error=true")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/");

	}
	
	
}
