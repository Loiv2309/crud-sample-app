package hr.comping.crud.controller.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import hr.comping.crud.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;


/**
 * Object that is returned instead of the default message when an exceptions happen.
 * This class wraps error information to be returned in responses.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class APIError {

    // The HTTP status code returned.
    private int statusCode;
    // The timestamp when the error occurred in UTC timezone.
    private ZonedDateTime timestamp = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("UTC"));
    // The URI of the request that caused the error.
    private String uri;
    // The HTTP method of the request that caused the error.
    private String method;
    // The name of the exception class.
    private String exception;
    // The error message, not shown if null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;
    // The list of error messages, not shown if null
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;

    /**
     * Constructor for APIError class using a CustomException.
     * @param exception The Exception.
     * @param httpStatus The HTTP Status to return.
     * @param request The request that caused the exception.
     */
    public APIError(CustomException exception, HttpStatus httpStatus, HttpServletRequest request) {
        this.exception = exception.getClass().getSimpleName();
        this.error = exception.getMessage();
        this.statusCode = httpStatus.value();
        this.uri = request.getRequestURI();
        this.method = request.getMethod();
    }

    /**
     * Constructor for APIError class using Throwable.
     * @param throwable The Throwable object representing the exception.
     * @param httpStatus The HTTP Status to return.
     * @param request The request that caused the exception.
     */
    public APIError(Throwable throwable, String errorMessage, HttpStatus httpStatus, HttpServletRequest request) {
        this.exception = throwable.getClass().getSimpleName();
        this.error = errorMessage;
        this.statusCode = httpStatus.value();
        this.uri = request.getRequestURI();
        this.method = request.getMethod();
    }

    /**
     * Constructor for APIError class when MethodArgumentNotValidException is thrown.
     * Used to handle exception thrown by jakarta.validation.@Valid annotation
     * @param exception The Exception.
     * @param errors List of error messages.
     * @param httpStatus The HTTP Status to return.
     * @param request The request that caused the exception.
     */
    public APIError(MethodArgumentNotValidException exception, List<String> errors, HttpStatus httpStatus, HttpServletRequest request) {
        this.exception = exception.getClass().getSimpleName();
        this.errors = errors;
        this.statusCode = httpStatus.value();
        this.uri = request.getRequestURI();
        this.method = request.getMethod();
    }

    /**
     * Constructor for APIError class when HandlerMethodValidationException is thrown.
     * Used to handle exception thrown by jakarta.validation.@Valid annotation when a List is validated
     * @param exception The Exception.
     * @param errors List of error messages.
     * @param httpStatus The HTTP Status to return.
     * @param request The request that caused the exception.
     */
    public APIError(HandlerMethodValidationException exception, List<String> errors, HttpStatus httpStatus, HttpServletRequest request) {
        this.exception = exception.getClass().getSimpleName();
        this.errors = errors;
        this.statusCode = httpStatus.value();
        this.uri = request.getRequestURI();
        this.method = request.getMethod();
    }
}
