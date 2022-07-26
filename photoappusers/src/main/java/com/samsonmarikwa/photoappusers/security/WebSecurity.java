package com.samsonmarikwa.photoappusers.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
   
   @Value("${gateway.ip}")
   private String ipAddress;
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable();
      http.authorizeRequests().antMatchers("/**").hasIpAddress(ipAddress)
                  .and()
                        .addFilter(getAuthenticationFilter());
      http.headers().frameOptions().disable(); // required to allow h2-console
   }
   
   private Filter getAuthenticationFilter() throws Exception {
      AuthenticationFilter authenticationFilter = new AuthenticationFilter();
      authenticationFilter.setAuthenticationManager(authenticationManager());
      return authenticationFilter;
   }
}
