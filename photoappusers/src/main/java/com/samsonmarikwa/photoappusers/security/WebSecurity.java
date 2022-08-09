package com.samsonmarikwa.photoappusers.security;

import com.samsonmarikwa.photoappusers.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
   
   private UsersService usersService;
   private BCryptPasswordEncoder bCryptPasswordEncoder;
   private Environment env;
   
   @Autowired
   public WebSecurity(UsersService usersService,
                      BCryptPasswordEncoder bCryptPasswordEncoder,
                      Environment env) {
      this.usersService = usersService;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
      this.env = env;
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable();
      http.authorizeRequests()
            .antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
            .antMatchers(HttpMethod.GET, "/actuator/health").hasIpAddress(env.getProperty("gateway.ip"))
            // we can allow any IP address by having permitAll() instead of restricting to a specific IP address
            .antMatchers(HttpMethod.GET, "/actuator/circuitbreakerevents").hasIpAddress(env.getProperty("gateway.ip"))
            .and()
            .addFilter(getAuthenticationFilter());
      http.headers().frameOptions().disable(); // required to allow h2-console
   }
   
   private Filter getAuthenticationFilter() throws Exception {
      AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, env, authenticationManager());
//      authenticationFilter.setAuthenticationManager(authenticationManager());
      authenticationFilter.setFilterProcessesUrl(env.getProperty("login.url.path"));
      return authenticationFilter;
   }
   
   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
   }
}
