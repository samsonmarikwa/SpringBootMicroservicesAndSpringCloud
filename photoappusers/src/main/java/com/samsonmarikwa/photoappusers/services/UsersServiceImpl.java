package com.samsonmarikwa.photoappusers.services;

import com.samsonmarikwa.photoappusers.data.UserEntity;
import com.samsonmarikwa.photoappusers.repositories.UsersRepository;
import com.samsonmarikwa.photoappusers.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
