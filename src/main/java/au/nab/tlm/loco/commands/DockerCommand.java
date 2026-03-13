package au.nab.tlm.loco.commands;

import au.nab.tlm.loco.printer.DockerCommandPrinter;
import au.nab.tlm.loco.service.DockerService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

@RequiredArgsConstructor
@Command(command = "dock")
public class DockerCommand {

  private final DockerService dockerService;
  private final DockerCommandPrinter dockerCommandPrinter;

  @Command(command = "status")
  public String status() {
    return dockerService.isDaemonRunning() ? "✅ Docker Engine is running" : "❌ Docker Engine is not working";
  }

  @Command(command = "ps")
  public void ps(@Option(longNames = "all", shortNames = 'a', defaultValue = "false") boolean allOpt) {
    var containers = dockerService.getLocoContainers(allOpt);
    if (containers.isEmpty()) {
      System.out.println("No containers found");
      return;
    }
    dockerCommandPrinter.printContainers(containers);
  }
}
