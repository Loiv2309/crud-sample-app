package hr.comping.crud.dto;

import hr.comping.crud.constants.messages.validations.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Request object for creating or updating a Service entity.
 */
@Getter
@NoArgsConstructor
public class ServiceRequest {

    // The description of the service.
    @NotEmpty(message = "{" + ValidationMessages.SERVICE_DESCRIPTION + "}")
    private String description;
}
