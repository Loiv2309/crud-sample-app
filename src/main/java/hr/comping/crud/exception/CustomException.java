package hr.comping.crud.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base custom exception class for application-specific exceptions.
 */
@Getter
public abstract class CustomException extends RuntimeException{

    private int statusCode;

    private HttpStatus httpStatus;

    /**
     * Constructor for a new custom exception with only a message provided.
     * @param message The error message.
     */
    protected CustomException(String message) {
        super(message);
    }

    /**
     * Constructor for a new custom exception with a message and a Throwable object
     * @param message The error message.
     * @param e The Throwable.
     */
    protected CustomException(String message, Throwable e) {
        super(message, e);
    }

    /**
     * Constructor for a new custom exception with a message and HttpStatus object
     * @param message The error message.
     * @param httpStatus The http status to return.
     */
    protected CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
    }

    /**
     * Constructor for a new custom exception with a message, HttpStatus object and a Throwable object
     * @param message The error message.
     * @param httpStatus The http status to return.
     * @param e The Throwable.
     */
    protected CustomException(String message, Throwable e, HttpStatus httpStatus) {
        super(message, e);
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
    }

}
