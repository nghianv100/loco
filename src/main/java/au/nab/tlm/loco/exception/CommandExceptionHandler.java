package au.nab.tlm.loco.exception;

import org.springframework.shell.command.CommandExceptionResolver;
import org.springframework.shell.command.CommandHandlingResult;
import org.springframework.stereotype.Component;

@Component
public class CommandExceptionHandler implements CommandExceptionResolver {

  @Override
  public CommandHandlingResult resolve(Exception ex) {
    if (ex instanceof DockerDaemonNotAvailableException) {
      return CommandHandlingResult.of("❌ Docker Engine is not working");
    }
    return null;
  }
}
