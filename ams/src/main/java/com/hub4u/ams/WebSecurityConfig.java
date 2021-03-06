package com.hub4u.ams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Autowired
	UserDetailsService userDetailsService;
	
	public WebSecurityConfig() {
	}

	public WebSecurityConfig(boolean disableDefaults) {
		super(disableDefaults);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/css/**", "/js/**", "/images/**").permitAll()
				.antMatchers("/admin/**").permitAll()
				.antMatchers("/workbench/**").permitAll()
				//.antMatchers("/workbench/**").hasAnyRole("ADMIN", "MANAGER", "ACCOUNTING", "STAFF")
				//.antMatchers("/admin/**").hasRole("ADMIN")
				//.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.exceptionHandling().accessDeniedPage("/403")
				.and()
			.logout()
				.permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	
	
//	@Bean
//	@Override 
//	protected UserDetailsService userDetailsService() {
//		String uname = "admin";
//		String passwd = bCryptPasswordEncoder().encode("admin");
//		
//		UserDetails user = 
//				User.withDefaultPasswordEncoder() 
//				.username(uname)
//				.password(passwd)
//				.roles("ADMIN")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
}
