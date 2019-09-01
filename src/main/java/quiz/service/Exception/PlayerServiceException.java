package quiz.service.Exception;

public class PlayerServiceException extends Exception {

    public PlayerServiceException(String message) {
        super(message);
    }

    public PlayerServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
