package au.nab.tlm.loco;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.CommandScan;

import static au.nab.tlm.loco.common.Constants.LOCO_HOME;

@SpringBootApplication
@CommandScan
public class LocoApplication {

  public static void main(String[] args) {
    var locoPath = System.getenv().get(LOCO_HOME);
    if (StringUtils.isBlank(locoPath)) {
      System.err.println("'LOCO_HOME' environment variable must be defined");
      System.exit(1);
    }
    SpringApplication.run(LocoApplication.class, args);
  }
}
