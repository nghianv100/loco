package au.nab.tlm.loco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

@SpringBootApplication
@CommandScan
public class LocoApplication {

	public static void main(String[] args) {
		SpringApplication.run(LocoApplication.class, args);
	}
}
