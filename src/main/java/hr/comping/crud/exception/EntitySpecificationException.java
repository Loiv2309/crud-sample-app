package hr.comping.crud.exception;

/**
 * Exception related to EntitySpecification functionality for filtering entities using SearchCriteria.
 * Doesn't extend Custom Exception because we don't need to handle with specifically with AOP, we don't want for the user to see the message.
 */
public class EntitySpecificationException extends RuntimeException{
    public EntitySpecificationException() {
    }

    public EntitySpecificationException(String message) {
        super(message);
    }

    public EntitySpecificationException(String message, Throwable cause) {
        super(message, cause);
    }
}
