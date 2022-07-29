package com.samsonmarikwa.photoappusers.controllers;

import com.netflix.discovery.EurekaClient;
import com.samsonmarikwa.photoappusers.services.UsersService;
import com.samsonmarikwa.photoappusers.shared.UserDto;
import com.samsonmarikwa.photoappusers.ui.model.CreateUserRequestModel;
import com.samsonmarikwa.photoappusers.ui.model.CreateUserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
   @Autowired
   @Lazy
   private EurekaClient client;
   
   @Autowired
   private Environment env;
   
   @Value("${spring.application.name}")
   private String appName;
   
   @Autowired
   UsersService usersService;
   
   @GetMapping("/status/check")
   public String status() {
      return "Hello from " + client.getApplication(appName).getName()
            + " on port " + env.getProperty("local.server.port") + " with token = " + env.getProperty("token.secret");
   }
   
   @PostMapping(
         consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
         produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
   public ResponseEntity<CreateUserResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
      ModelMapper modelMapper = new ModelMapper();
      modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
   
      UserDto userDto = modelMapper.map(userDetails, UserDto.class);
      UserDto createdUser = usersService.createUser(userDto);
   
      CreateUserResponseModel returnValue = modelMapper.map(createdUser, CreateUserResponseModel.class);
      return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
   }
}
