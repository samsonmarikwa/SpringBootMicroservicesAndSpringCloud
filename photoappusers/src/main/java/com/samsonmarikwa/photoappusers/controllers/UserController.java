package com.samsonmarikwa.photoappusers.controllers;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
   
   @GetMapping("/status/check")
   public String status() {
      return "Hello from " + client.getApplication(appName).getName() + " on port " + env.getProperty("local.server.port");
   }
   
   @PostMapping
   public String createUser() {
      return "Create user";
   }
}
