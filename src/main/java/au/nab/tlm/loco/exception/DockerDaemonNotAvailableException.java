package au.nab.tlm.loco.exception;

public class DockerDaemonNotAvailableException extends RuntimeException {

  public DockerDaemonNotAvailableException(String message) {
    super(message);
  }

  public DockerDaemonNotAvailableException(String message, Throwable cause) {
    super(message, cause);
  }
}
