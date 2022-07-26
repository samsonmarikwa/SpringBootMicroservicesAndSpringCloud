package com.samsonmarikwa.photoappusers.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {
   
   @Serial
   private static final long serialVersionUID = -2841848067932391548L;
   
   @Id
   @GeneratedValue
   private long id;
   
   @Column(nullable = false, length = 50)
   private String firstName;
   
   @Column(nullable = false, length = 50)
   private String lastName;
   
   @Column(nullable = false, length = 120, unique = true)
   private String email;
   
   @Column(nullable = false, unique = true)
   private String userId;
   
   @Column(unique = true)
   private String encryptedPassword;

}
