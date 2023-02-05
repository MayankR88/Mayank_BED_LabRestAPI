package com.glearning.students.config;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.glearning.students.service.DomainUserDetailsService;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("deprecation")
@Configuration
@RequiredArgsConstructor
public class WebApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private final DomainUserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//user authentication
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	//user authorization
	 @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http.authorizeRequests()
	        	.antMatchers("/login", "/h2-console/**", "/h2-console**", "/h2-console/login**")
	        		.permitAll()
	            .antMatchers("/student/save/**","/student/save**","/student/showFormForAdd","/student/403")
	            	.hasAnyRole("USER","ADMIN")
	            .antMatchers(HttpMethod.POST, "/student/save**")
		            .hasRole("ADMIN")
	            .antMatchers("/student/showFormForUpdate","/student/delete")
	            	.hasRole("ADMIN")
	            .anyRequest().authenticated()
	            .and()
	            .formLogin().loginProcessingUrl("/login").successForwardUrl("/student/list").permitAll()
	            .and()
	            .logout()
	            .logoutSuccessUrl("/login").permitAll()
	            .and()
	            .exceptionHandling().accessDeniedPage("/student/403")
	            .and()
	            .cors().disable().csrf().disable().headers().frameOptions().disable();
	    }


}
