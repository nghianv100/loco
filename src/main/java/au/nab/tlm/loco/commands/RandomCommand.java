package au.nab.tlm.loco.commands;

import org.springframework.shell.command.annotation.Command;

import java.util.UUID;

@Command(command = "random")
public class RandomCommand {

  @Command(command = "uuid")
  public String uuid() {
    return UUID.randomUUID().toString();
  }

}
