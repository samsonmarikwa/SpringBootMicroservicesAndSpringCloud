package com.samsonmarikwa.photoappusers.services;

import com.samsonmarikwa.photoappusers.data.UserEntity;
import com.samsonmarikwa.photoappusers.repositories.UsersRepository;
import com.samsonmarikwa.photoappusers.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
   
   UsersRepository usersRepository;
   BCryptPasswordEncoder bCryptPasswordEncoder;
   
   ModelMapper modelMapper;
   
   @Autowired
   public UsersServiceImpl(UsersRepository usersRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper) {
      this.usersRepository = usersRepository;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
      this.modelMapper = modelMapper;
   }
   
   @Override
   public UserDto createUser(UserDto userDetails) {
      
      userDetails.setUserId(UUID.randomUUID().toString());
      userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
      
      usersRepository.save(userEntity);
      
      UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
      
      return returnValue;
   }
   
   @Override
   public UserDto getUserDetailsbyEmail(String username) {
      UserEntity userEntity = usersRepository.findByEmail(username);
   
      if (userEntity == null) throw new UsernameNotFoundException(username);
      
      return modelMapper.map(userEntity, UserDto.class);
      
   }
   
   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      UserEntity userEntity = usersRepository.findByEmail(username);
      
      if (userEntity == null) throw new UsernameNotFoundException(username);
      
      return new User(
            userEntity.getEmail(), userEntity.getEncryptedPassword(),
            true, true, true, true, new ArrayList<>());
   }
}
