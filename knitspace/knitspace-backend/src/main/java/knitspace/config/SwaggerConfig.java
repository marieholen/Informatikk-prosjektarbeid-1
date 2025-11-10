package knitspace.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for Swagger documentation.
 *
 * <p>This class defines the OpenAPI specification used to generate
 * API documentation for the knitspace backend
 *
 * <p>Once knitspace-backend is running, the documentation can be 
 * viewed at: http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {
  /**
   * Creates and configures the OpenAPI specification for the application.
   */
  @Bean
  public OpenAPI knitspaceOpenApi() {
    return new OpenAPI()
    .info(new Info()
        .title("Knitspace API")
        .description("REST API documentation for Knitspace.")
    )
    .servers(List.of(
        new Server().url("http://localhost:8080").description("Local Server")
    ));
  }
}
