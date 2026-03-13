package au.nab.tlm.loco.service;

import au.nab.tlm.loco.exception.DockerDaemonNotAvailableException;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static au.nab.tlm.loco.common.Constants.LOCO_COMPONENT_LABEL;

@Service
@RequiredArgsConstructor
public class DockerService {

  private final DockerClient dockerClient;

  public void ping() {
    try {
      dockerClient.pingCmd().exec();
    } catch (Exception e) {
      throw new DockerDaemonNotAvailableException(e.getMessage(), e.getCause());
    }
  }

  public boolean isDaemonRunning() {
    try {
      ping();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public List<Container> getLocoContainers(boolean all) {
    ping();
    return dockerClient.listContainersCmd()
        .withShowAll(all)
        .withLabelFilter(List.of(LOCO_COMPONENT_LABEL))
        .exec();
  }
}
