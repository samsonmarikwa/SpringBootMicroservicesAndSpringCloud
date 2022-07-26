package com.samsonmarikwa.photoappusers.services;

import com.samsonmarikwa.photoappusers.data.UserEntity;
import com.samsonmarikwa.photoappusers.repositories.UsersRepository;
import com.samsonmarikwa.photoappusers.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
   
   UsersRepository usersRepository;
   
   @Autowired
   ModelMapper modelMapper;
   
   @Autowired
   public UsersServiceImpl(UsersRepository usersRepository) {
      this.usersRepository = usersRepository;
   }
   
   @Override
   public UserDto createUser(UserDto userDetails) {
      
      userDetails.setUserId(UUID.randomUUID().toString());
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
      UserEntity userEntity = modelMapper.map(userDetails, UserEntity.class);
      
      userEntity.setEncryptedPassword("test"); // will replace with Spring Security encryption
   
      usersRepository.save(userEntity);
      
      UserDto returnValue = modelMapper.map(userEntity, UserDto.class);
      
      return returnValue;
   }
}
