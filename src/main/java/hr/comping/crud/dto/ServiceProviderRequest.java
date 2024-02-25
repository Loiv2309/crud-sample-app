package hr.comping.crud.dto;

import hr.comping.crud.constants.messages.validations.ValidationMessages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Request for creating or updating a Service Provider entity.
 */
@Getter
@NoArgsConstructor
public class ServiceProviderRequest {

    // The name of the service provider.
    @NotEmpty(message = "{" + ValidationMessages.SERVICE_PROVIDER_NAME + "}")
    private String name;
    // The set of service IDs associated with the service provider.
    private Set<String> serviceIds;
}
