package com.samsonmarikwa.photoappusers.services;

import com.samsonmarikwa.photoappusers.data.AlbumsServiceClient;
import com.samsonmarikwa.photoappusers.data.UserEntity;
import com.samsonmarikwa.photoappusers.repositories.UsersRepository;
import com.samsonmarikwa.photoappusers.shared.UserDto;
import com.samsonmarikwa.photoappusers.ui.model.AlbumResponseModel;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {
   
   Logger logger = LoggerFactory.getLogger(this.getClass());
   
   UsersRepository usersRepository;
   BCryptPasswordEncoder bCryptPasswordEncoder;
   
//   RestTemplate restTemplate;
   AlbumsServiceClient albumsServiceClient;
   ModelMapper modelMapper;
   
   Environment env;
   
   @Autowired
   public UsersServiceImpl(UsersRepository usersRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           ModelMapper modelMapper,
//                           RestTemplate restTemplate,
                           AlbumsServiceClient albumsServiceClient,
                           Environment env) {
      this.usersRepository = usersRepository;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
      this.modelMapper = modelMapper;
//      this.restTemplate = restTemplate;
      this.albumsServiceClient = albumsServiceClient;
      this.env = env;
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
   
   @Override
   public UserDto getUserByUserId(String userId) {
      UserEntity userEntity = usersRepository.findByUserId(userId);
      
      if (userEntity == null) throw new UsernameNotFoundException(userId);
   
      UserDto userDto = modelMapper.map(userEntity, UserDto.class);
      /*
      String albumsUrl = String.format(env.getProperty("albums.url"), userId);
   
      // RestTemplate supports client-side load balancing
      ResponseEntity<List<AlbumResponseModel>> albumsListResponse =
            restTemplate.exchange(
                  albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumResponseModel>>() {});
      List<AlbumResponseModel> albumsList = albumsListResponse.getBody();
      */
   
      // Use Feign Client
      logger.info("Before calling albums microservice");
      List<AlbumResponseModel> albumsList = albumsServiceClient.getAlbums(userId);
      logger.info("After calling albums microservice");
      userDto.setAlbums(albumsList);
      
      return userDto;
   }
}
