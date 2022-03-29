package mb.seeme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NumberFormatException extends RuntimeException {

    public NumberFormatException() {
        super();
    }

    public NumberFormatException(String message) {
        super(message);
    }

    public NumberFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}

