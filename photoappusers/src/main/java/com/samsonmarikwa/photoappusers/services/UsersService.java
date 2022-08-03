package com.samsonmarikwa.photoappusers.services;

import com.samsonmarikwa.photoappusers.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
   
   UserDto createUser(UserDto userDetails);
   UserDto getUserDetailsbyEmail(String email);
   
   UserDto getUserByUserId(String userId);
}
