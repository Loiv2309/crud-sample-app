package hr.comping.crud.controller.aop;

import hr.comping.crud.constants.messages.exceptions.ExceptionMessages;
import hr.comping.crud.exception.CustomException;
import hr.comping.crud.util.Translator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.List;

/**
 * Global controller advice to handle exceptions.
 * Exception handling and response entity with APIError generation for exceptions.
 */
@Slf4j
@ControllerAdvice
public class ExceptionControllerAdvice {

    // Default HTTP status to be used if case no status is provided.
    private static final HttpStatus DEFAULT_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * Exception handler for CustomException.
     * @param exception The CustomException thrown.
     * @param request The request that caused the exception.
     * @return ResponseEntity containing APIError object with a DEFAULT_STATUS if none is provided.
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<APIError> handleCustomException(CustomException exception, HttpServletRequest request) {
        HttpStatus httpStatus = exception.getHttpStatus() == null ? DEFAULT_STATUS : exception.getHttpStatus();
        APIError apiError = new APIError(exception, httpStatus, request);
        log.error("APIError:{} \nERROR:{}",apiError,exception);
        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * Exception handler for MethodArgumentNotValidException.
     * Used to handle exception thrown by jakarta.validation.@Valid annotation
     * @param exception The MethodArgumentNotValidException thrown.
     * @param request The request that caused the exception.
     * @return ResponseEntity containing APIError object and Bad Request(400) status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIError> handleValidationErrors(MethodArgumentNotValidException exception, HttpServletRequest request) {
        // Extracting field errors from the exception and mapping them to a list of error messages.
        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(FieldError::getDefaultMessage).toList();
        APIError apiError = new APIError(exception, errors, HttpStatus.BAD_REQUEST, request);
        log.error("ValidationErrors: {} \nAPIError:{} \nERROR:",errors,apiError,exception);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for HandlerMethodValidationException.
     * Used to handle exception thrown by jakarta.validation.@Valid annotation when a List is validated
     * @param exception The HandlerMethodValidationException thrown.
     * @param request The request that caused the exception.
     * @return ResponseEntity containing APIError object and Bad Request(400) status.
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<APIError> handleValidationErrors(HandlerMethodValidationException exception, HttpServletRequest request) {
        // Extracting field errors from the exception and mapping them to a list of error messages.
        List<String> errors = exception.getAllErrors().stream().map(MessageSourceResolvable::getDefaultMessage).toList();
        APIError apiError = new APIError(exception, errors, HttpStatus.BAD_REQUEST, request);
        log.error("ValidationErrors: {} \nAPIError:{} \nERROR:",errors,apiError,exception);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for any Throwable that is not specifically handled.
     * This intercepts all the exceptions not specifically handled.
     * @param throwable The Throwable thrown.
     * @param request The request that caused the exception.
     * @return ResponseEntity containing APIError object with default status and a default error message.
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<APIError> handleUnknownException(Throwable throwable, HttpServletRequest request) {
        APIError apiError = new APIError(throwable, Translator.translate(ExceptionMessages.DEFAULT_ERROR), DEFAULT_STATUS, request);
        log.error("APIError:{} \nERROR: ",apiError,throwable);
        return new ResponseEntity<>(apiError, DEFAULT_STATUS);
    }
}
