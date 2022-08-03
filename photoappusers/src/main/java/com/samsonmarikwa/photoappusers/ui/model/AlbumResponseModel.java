package com.samsonmarikwa.photoappusers.ui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseModel {
   private String albumId;
   private String userId;
   private String name;
   private String description;
   
}
