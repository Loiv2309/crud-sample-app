package hr.comping.crud.entity;

import com.fasterxml.jackson.annotation.JsonView;
import hr.comping.crud.dto.ServiceProviderRequest;
import hr.comping.crud.entity.jsonview.JsonViewFilter;
import hr.comping.crud.entity.listener.AuditListener;
import hr.comping.crud.entity.superclasses.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity class representing a Service Provider.
 * Extends AuditEntity for auditing functionality.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="service_provider")
@EntityListeners(AuditListener.class)
public class ServiceProvider extends AuditEntity {

    @NotEmpty
    @JsonView(JsonViewFilter.Basic.class)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "ln_service_provider_service",
            joinColumns = @JoinColumn(name = "service_provider_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    @JsonView(JsonViewFilter.Basic.class)
    @Fetch(FetchMode.SUBSELECT)
    private Set<ServiceEntity> services = new HashSet<>();

    /**
     * Constructor for creating a new Service Provider entity
     * @param serviceProviderRequest The request object containing data for creating the Service Provider entity.
     * @param services List of Service entities related to the Service Provider.
     */
    public ServiceProvider(ServiceProviderRequest serviceProviderRequest, Set<ServiceEntity> services) {
        this.name = serviceProviderRequest.getName();
        this.services = services;
    }

    /**
     * Constructor for updating an existing Service Provider entity
     * @param serviceProviderRequest The request object containing data for updating the Service Provider entity.
     * @param services List of Service entities related to the Service Provider.
     * @return Returns the updated Service Provider entity. 
     */
    public ServiceProvider update(ServiceProviderRequest serviceProviderRequest, Set<ServiceEntity> services){
        this.name = serviceProviderRequest.getName();
        this.services = services;
        return this;
    }
}
