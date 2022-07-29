package com.samsonmarikwa.photoappusers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.samsonmarikwa.photoappusers.services.UsersService;
import com.samsonmarikwa.photoappusers.shared.UserDto;
import com.samsonmarikwa.photoappusers.ui.model.LoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
   
   private UsersService usersService;
   private Environment env;
   
   @Autowired
   public AuthenticationFilter(UsersService usersService,
                               Environment env,
                               AuthenticationManager authenticationManager) {
      this.usersService = usersService;
      this.env = env;
      super.setAuthenticationManager(authenticationManager);
   }
   
   @Override
   public Authentication attemptAuthentication(HttpServletRequest req,
                                               HttpServletResponse res) throws AuthenticationException {
      try {
         LoginRequestModel creds = new ObjectMapper()
               .readValue(req.getInputStream(), LoginRequestModel.class);
         
         return getAuthenticationManager()
               .authenticate(new UsernamePasswordAuthenticationToken(
                     creds.getEmail(),
                     creds.getPassword(),
                     new ArrayList<>()));
         
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }
   
   @Override
   protected void successfulAuthentication(HttpServletRequest req,
                                           HttpServletResponse res,
                                           FilterChain chain,
                                           Authentication authResult) throws IOException, ServletException {
      
      String userName = ((User) authResult.getPrincipal()).getUsername();
      UserDto userDto = usersService.getUserDetailsbyEmail(userName);
      
      String token = Jwts.builder()
            .setSubject(userDto.getUserId())
            .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("token.expiration.time"))))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact();
      
      res.addHeader("token", token);
      res.addHeader("userId", userDto.getUserId());
      
   }
}
