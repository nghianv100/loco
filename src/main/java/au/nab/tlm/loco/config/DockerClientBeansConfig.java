package au.nab.tlm.loco.config;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class DockerClientBeansConfig {

  @Value("${docker.host}")
  private String dockerHost;

  @Bean
  public DefaultDockerClientConfig dockerClientConfig() {
    return DefaultDockerClientConfig.createDefaultConfigBuilder()
        .withDockerHost(dockerHost)
        .withDockerTlsVerify(false)
        .withRegistryUsername("")
        .withRegistryPassword("")
        .withRegistryEmail("")
        .withRegistryUrl("")
        .build();
  }

  @Bean
  public DockerHttpClient dockerHttpClient(DefaultDockerClientConfig config) {
    return new ApacheDockerHttpClient.Builder()
        .dockerHost(config.getDockerHost())
        .sslConfig(config.getSSLConfig())
        .maxConnections(100)
        .connectionTimeout(Duration.ofSeconds(30))
        .responseTimeout(Duration.ofSeconds(45))
        .build();
  }

  @Bean
  public DockerClient dockerClient(DefaultDockerClientConfig dockerClientConfig, DockerHttpClient dockerHttpClient) {
    return DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
  }
}
