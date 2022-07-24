package com.samsonmarikwa.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SpringCloudConfig {
   
//   @Bean
   public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
      return builder.routes()
            .route(predicateSpec -> predicateSpec.path("/users/**")
                  .uri("lb://USERS-WS"))
            
            .route(predicateSpec -> predicateSpec.path("/accounts/**")
                  .uri("lb://ACCOUNT-WS"))
            .build();
            
   }
}
