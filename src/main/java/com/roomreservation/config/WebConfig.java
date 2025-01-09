package com.roomreservation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration class for web-related settings in the room reservation system.
 * Handles Cross-Origin Resource Sharing (CORS) configuration and other web-specific settings.
 *
 * <p>This configuration:
 * - Enables CORS for specified endpoints
 * - Configures allowed origins, methods, and headers
 * - Sets maximum age for CORS preflight responses
 *
 * <p>The current configuration allows:
 * - All origins (*) - Should be restricted in production
 * - Common HTTP methods (GET, POST, PUT, DELETE, OPTIONS)
 * - All headers
 * - 3600 seconds (1 hour) max age for preflight responses
 *
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 * @see org.springframework.web.servlet.config.annotation.CorsRegistry
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
  /**
   * Configures CORS mappings for the application.
   * Allows cross-origin requests with specified constraints.
   *
   * @param registry CORS configuration registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")  // Or specify your frontend URL
      .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
      .allowedHeaders("*")
      .maxAge(3600);
  }
}