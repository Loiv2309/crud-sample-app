package hr.comping.crud.service;

import hr.comping.crud.constants.messages.exceptions.ExceptionMessages;
import hr.comping.crud.dto.ServiceRequest;
import hr.comping.crud.entity.ServiceEntity;
import hr.comping.crud.entity.projection.ServiceEntityDescription;
import hr.comping.crud.exception.EntityNotFoundException;
import hr.comping.crud.repository.ServiceEntityRepository;
import hr.comping.crud.util.Translator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing Service entities.
 */
@Service
@Transactional
public class ServiceEntityService extends AbstractService<ServiceEntity> {

    @Autowired
    ServiceEntityRepository serviceRepository;

    /**
     * Used to save a new Service entity.
     * @param serviceRequest The data used to create the Service entity.
     * @return The saved entity.
     */
    public ServiceEntity createEntity(ServiceRequest serviceRequest){
        return save(new ServiceEntity(serviceRequest));
    }

    /**
     * Used to update a Service entity.
     * @param id The ID of the entity to be updated.
     * @param serviceRequest The data used to update the Service entity.
     * @throws EntityNotFoundException if entity is not found.
     * @return The updated entity.
     */
    public ServiceEntity updateEntity(String id, ServiceRequest serviceRequest) {
        Optional<ServiceEntity> serviceOptional = abstractRepository.findById(id);
        if(serviceOptional.isEmpty()){
            throw new EntityNotFoundException(Translator.translate(ExceptionMessages.ENTITY_NOT_FOUND_W_PARAMS,id));
        }
        ServiceEntity serviceToUpdate = serviceOptional.get();

        return save(serviceToUpdate.update(serviceRequest));
    }

    /**
     * Used for getting Service entity description field.
     * @param id The ID of the Service entity.
     * @throws EntityNotFoundException if the Service with the provided ID is not found.
     * @return ServiceEntityDescription projection
     */
    public ServiceEntityDescription getServiceDescription(String id) {
        Optional<ServiceEntityDescription> projectionOptional = serviceRepository.findServiceEntityById(id);

        if(projectionOptional.isEmpty()){
            throw new EntityNotFoundException(Translator.translate(ExceptionMessages.ENTITY_NOT_FOUND_W_PARAMS,id));
        }

        return projectionOptional.get();
    }
}
