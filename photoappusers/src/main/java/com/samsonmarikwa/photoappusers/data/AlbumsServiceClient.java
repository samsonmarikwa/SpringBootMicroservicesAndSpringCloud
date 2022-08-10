package com.samsonmarikwa.photoappusers.data;

import com.samsonmarikwa.photoappusers.ui.model.AlbumResponseModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

//@FeignClient(name = "albums-ws", fallbackFactory = AlbumsFallbackFactory.class)
@FeignClient(name = "albums-ws")
public interface AlbumsServiceClient {
   
   @GetMapping("/users/{id}/albums")
   @Retry(name="albums-ws")
   @CircuitBreaker(name="albums-ws", fallbackMethod="getAlbumsFallback")
   public List<AlbumResponseModel> getAlbums(@PathVariable String id);
   
   default List<AlbumResponseModel> getAlbumsFallback(String id, Throwable exception) {
      System.out.println("Param = " + id);
      System.out.println("Exception took place: " + exception.getMessage());
      return new ArrayList<>();
   }
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
