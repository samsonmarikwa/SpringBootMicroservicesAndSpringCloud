package com.samsonmarikwa.photoappusers.ui.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class LoginRequestModel {
   @NotNull(message="Email cannot be null")
   @Email
   private String email;
   
   @NotNull(message="Password cannot be null")
   private String password;
}
