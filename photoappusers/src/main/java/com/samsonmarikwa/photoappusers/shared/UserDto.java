package com.samsonmarikwa.photoappusers.shared;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {
   
   @Serial
   private static final long serialVersionUID = 8396467811663967241L;
   
   private String firstName;
   private String lastName;
   private String password;
   private String email;
   private String userId;
   private String encryptedPassword;
}
