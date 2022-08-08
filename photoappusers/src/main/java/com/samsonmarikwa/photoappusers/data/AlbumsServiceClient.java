package com.samsonmarikwa.photoappusers.data;

import com.samsonmarikwa.photoappusers.ui.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "albums-ws", fallback = AlbumsFallback.class)
public interface AlbumsServiceClient {
   
   @GetMapping("/users/{id}/albums")
   public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}

@Component
class AlbumsFallback implements AlbumsServiceClient {
   
   @Override
   public List<AlbumResponseModel> getAlbums(String id) {
      // The fallback method returns an empty list.
      return new ArrayList<>();
   }
}
