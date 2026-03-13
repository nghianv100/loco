package au.nab.tlm.loco.printer;

import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ContainerMount;
import com.github.dockerjava.api.model.ContainerPort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DockerCommandPrinter {

  private static final DateTimeFormatter DATE_FMT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

  public void printContainers(List<Container> containers) {
    int total = containers.size();
    for (int i = 0; i < total; i++) {
      var container = containers.get(i);
      String name = container.getNames() != null && container.getNames().length > 0
          ? container.getNames()[0].replaceFirst("^/", "")
          : container.getId().substring(0, 12);
      var state = "running".equals(container.getState()) ? "✅ Running" : "❌ " + container.getState();

      System.out.printf("[%d/%d] %s (%s)%n", i + 1, total, name, container.getImage());
      System.out.printf("    State    : %s, %s, Created at %s%n", state, container.getStatus(), DATE_FMT.format(Instant.ofEpochSecond(container.getCreated())));
      System.out.printf("    Ports    : %s%n", formatPorts(container.getPorts()));
      System.out.printf("    Labels   : %n");
      formatLabels(container.getLabels()).forEach(l -> System.out.println("        " + l));
      System.out.printf("    Mounts   : %s%n", formatMounts(container.getMounts()));
      System.out.println();
    }
  }

  private String formatPorts(ContainerPort[] ports) {
    if (ports == null || ports.length == 0) return "none";
    return Arrays.stream(ports)
        .map(p -> p.getIp() != null
            ? String.format("(Host) %s:%d -> (Private) %d/%s", p.getIp(), p.getPublicPort(), p.getPrivatePort(), p.getType())
            : String.format("(Private) %d/%s", p.getPrivatePort(), p.getType()))
        .collect(Collectors.joining(", "));
  }

  private List<String> formatLabels(Map<String, String> labels) {
    if (labels == null || labels.isEmpty()) return List.of("none");
    return labels.entrySet().stream()
        .map(e -> e.getKey() + "-" + e.getValue())
        .collect(Collectors.toList());
  }

  private String formatMounts(List<ContainerMount> mounts) {
    if (mounts == null || mounts.isEmpty()) return "none";
    return mounts.stream()
        .map(m -> String.format("%s %s -> %s (%s)", inferMountType(m), m.getSource(), m.getDestination(), Optional.ofNullable(m.getRw()).orElse(false) ? "rw" : "ro"))
        .collect(Collectors.joining(", "));
  }

  private String inferMountType(ContainerMount mount) {
    if (mount.getName() != null && !mount.getName().isBlank()) return "[volume]";
    if (mount.getSource() == null || mount.getSource().isBlank()) return "[tmpfs]";
    return "[bind]";
  }
}
