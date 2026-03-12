package au.nab.tlm.loco.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent // marks this class as a container for shell commands
public class HelloCommand {

    @ShellMethod(key = "hello", value = "Prints a personalized greeting.")
    public String sayHello(@ShellOption(defaultValue = "World") String name) {
        return "👋 Hello, " + name + "!";
    }
}

