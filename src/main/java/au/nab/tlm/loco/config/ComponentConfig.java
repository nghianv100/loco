package au.nab.tlm.loco.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ComponentConfig {

  @Bean
  @ConfigurationProperties(prefix = "components")
  public Map<String, Properties> locoComponents() {
    return new HashMap<>();
  }

  @Data
  public static class Properties {
    private String version;
    private String explicitImage;
  }

}
