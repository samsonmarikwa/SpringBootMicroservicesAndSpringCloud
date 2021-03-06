package com.samsonmarikwa.appws.userservice.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samsonmarikwa.appws.shared.Utils;
import com.samsonmarikwa.appws.ui.model.request.UserDetailsRequestModel;
import com.samsonmarikwa.appws.ui.model.response.UserRest;
import com.samsonmarikwa.appws.userservice.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	Map<String, UserRest> users;
	Utils utils;
	
	public UserServiceImpl() {
	}
	
	@Autowired
	public UserServiceImpl(Utils utils) { // Constructor Based Dependency Injection
		this.utils = utils;
	}
	
	@Override
	public UserRest createUser(UserDetailsRequestModel userDetails) {
		
		UserRest returnValue = new UserRest();
		returnValue.setFirstName(userDetails.getFirstName());
		returnValue.setLastName(userDetails.getLastName());
		returnValue.setEmail(userDetails.getEmail());
		
		String userId = utils.generateUserId();
		returnValue.setUserId(userId);
		
		if (users == null) users = new HashMap<>();
		users.put(userId, returnValue);
		
		return returnValue;
	}

}
