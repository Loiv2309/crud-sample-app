package hr.comping.crud.exception;

import org.springframework.http.HttpStatus;

/**
 * Custom exception class when an entity is not found.
 * Extends CustomException to inherit common exception handling functionality.
 */
public class EntityNotFoundException extends CustomException {

    //The default HTTP Status for this exception
    private static final HttpStatus defaultHttpStatus = HttpStatus.BAD_REQUEST;


    /**
     * Constructor for a new exception with only a message provided.
     * @param message The error message.
     */
    public EntityNotFoundException(String message) {
        super(message, defaultHttpStatus);
    }

    /**
     * Constructor for a new exception with a message and a Throwable object
     * @param message The error message.
     * @param e The Throwable.
     */
    public EntityNotFoundException(String message, Throwable e) {
        super(message, e, defaultHttpStatus);
    }

    /**
     * Constructor for a new exception with a message and HttpStatus object
     * @param message The error message.
     * @param httpStatus The http status to return overriding the default one.
     */
    public EntityNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    /**
     * Constructor for a new exception with a message, HttpStatus object and a Throwable object
     * @param message The error message.
     * @param httpStatus The http status to return overriding the default one.
     * @param e The Throwable.
     */
    public EntityNotFoundException(String message, Throwable e, HttpStatus httpStatus) {
        super(message, e, httpStatus);
    }

}
