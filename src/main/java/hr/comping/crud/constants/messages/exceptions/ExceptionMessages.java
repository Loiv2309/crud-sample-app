package hr.comping.crud.constants.messages.exceptions;

/**
 * Class used to store I18N message codes for Exceptions
 */
public class ExceptionMessages {
    private ExceptionMessages() {
        throw new IllegalStateException("Constants class cannot be initialized!");
    }
    public static final String ENTITY_NOT_FOUND_W_PARAMS =  "exception.EntityNotFound.params.message";
    public static final String DEFAULT_ERROR =  "exception.Default.message";
}
