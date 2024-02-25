package hr.comping.crud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import hr.comping.crud.dto.ServiceRequest;
import hr.comping.crud.entity.jsonview.JsonViewFilter;
import hr.comping.crud.entity.listener.AuditListener;
import hr.comping.crud.entity.superclasses.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


/**
 * Entity class representing a Service.
 * Extends AuditEntity for auditing functionality.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="service")
@EntityListeners(AuditListener.class)
public class ServiceEntity extends AuditEntity {

    @NotEmpty
    @JsonView(JsonViewFilter.Basic.class)
    private String description;

    //@JsonIgnore used to prevent infinite recursion
    @ManyToMany(mappedBy = "services", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<ServiceProvider> serviceProviders = new HashSet<>();

    /**
     * Constructor for creating a new Service entity
     * @param serviceRequest The request object containing data for creating the Service entity.
     */
    public ServiceEntity(ServiceRequest serviceRequest) {
        this.description = serviceRequest.getDescription();
    }

    /**
     * Constructor for updating an existing Service entity
     * @param serviceRequest The request object containing data for updating the Service entity.
     * @return Returns the updated Service entity.
     */
    public ServiceEntity update(ServiceRequest serviceRequest) {
        this.description = serviceRequest.getDescription();
        return this;
    }

    @PreRemove
    private void removeServiceFromServiceProviders() {
        for (ServiceProvider serviceProvider : serviceProviders) {
            serviceProvider.getServices().remove(this);
        }
    }
}
