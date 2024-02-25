package hr.comping.crud.service;

import hr.comping.crud.constants.messages.exceptions.ExceptionMessages;
import hr.comping.crud.dto.ServiceProviderRequest;
import hr.comping.crud.entity.ServiceEntity;
import hr.comping.crud.entity.superclasses.BaseEntity;
import hr.comping.crud.entity.ServiceProvider;
import hr.comping.crud.exception.EntityNotFoundException;
import hr.comping.crud.util.Translator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class ServiceProviderService extends AbstractService<ServiceProvider> {

    @Autowired
    ServiceEntityService serviceEntityService;

    /**
     * Used to create a new Service Provider entity.
     * @param serviceProviderRequest The data used to crete a new Service Provider entity.
     * @throws EntityNotFoundException If the Service entities defined in serviceProviderRequest are not found.
     * @return The created entity.
     */
    public ServiceProvider createEntity(ServiceProviderRequest serviceProviderRequest) {
        return save(new ServiceProvider(serviceProviderRequest,getServices(serviceProviderRequest)));
    }

    /**
     * Used to update a Service Provider entity.
     * @param id The ID of the entity to be updated.
     * @param serviceProviderRequest The data used to update the Service Provider entity.
     * @throws EntityNotFoundException if the Service Provider entity is not found OR if the Service entities defined in serviceProviderRequest are not found.
     * @return The updated entity.
     */
    public ServiceProvider updateEntity(String id, ServiceProviderRequest serviceProviderRequest) {
        Optional<ServiceProvider> serviceProviderOptional = abstractRepository.findById(id);

        if(serviceProviderOptional.isEmpty()){
            throw new EntityNotFoundException(Translator.translate(ExceptionMessages.ENTITY_NOT_FOUND_W_PARAMS,id));
        }

        ServiceProvider serviceProviderToUpdate = serviceProviderOptional.get();

        return save(serviceProviderToUpdate.update(serviceProviderRequest,getServices(serviceProviderRequest)));
    }

    /**
     * Helper function to get Service entities provided in the serviceProviderRequest
     * @param serviceProviderRequest The data used to get the Service entities.
     * @throws EntityNotFoundException If the service entity was not found.
     * @return Set of Service entities.
     */
    private Set<ServiceEntity> getServices(ServiceProviderRequest serviceProviderRequest){
        List<ServiceEntity> services = serviceEntityService.findByIds(serviceProviderRequest.getServiceIds());

        if(services.size()!=serviceProviderRequest.getServiceIds().size()){
            throw new EntityNotFoundException(Translator.translate(ExceptionMessages.ENTITY_NOT_FOUND_W_PARAMS, serviceProviderRequest.getServiceIds().stream().filter(element -> !services.stream().map(BaseEntity::getId).toList().contains(element)).toList()));
        }

        return new HashSet<>(services);
    }
}
