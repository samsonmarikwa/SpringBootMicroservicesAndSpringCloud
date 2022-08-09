package com.samsonmarikwa.photoappusers.data;

import com.samsonmarikwa.photoappusers.ui.model.AlbumResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
@FeignClient(name = "albums-ws")
public interface AlbumsServiceClient {
   
   @GetMapping("/users/{id}/albums")
   public List<AlbumResponseModel> getAlbums(@PathVariable String id);
}



/*@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {
   
   @Override
   public AlbumsServiceClient create(Throwable cause) {
      return new AlbumsServiceClientFallback(cause);
   }
}*/

/*class AlbumsServiceClientFallback implements AlbumsServiceClient {
   
   Logger logger = LoggerFactory.getLogger(this.getClass());
   
   private final Throwable cause;
   
   public AlbumsServiceClientFallback(Throwable cause) {
      this.cause = cause;
   }
   
   @Override
   public List<AlbumResponseModel> getAlbums(String id) {
      if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
         logger.error(
               "404 error took place when getAlbums was called with {}. Error message: {}",
               id,
               cause.getLocalizedMessage());
      } else {
         logger.error("Other error took place: {}", cause.getLocalizedMessage());
      }
      return new ArrayList<>();
   }
}*/
