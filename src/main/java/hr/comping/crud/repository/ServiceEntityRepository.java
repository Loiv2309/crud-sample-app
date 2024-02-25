package hr.comping.crud.repository;

import hr.comping.crud.entity.ServiceEntity;
import hr.comping.crud.entity.projection.ServiceEntityDescription;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Provides Service entity specific functionalities.
 * Extends AbstractRepository.
 */
@Repository
public interface ServiceEntityRepository extends AbstractRepository<ServiceEntity> {

    Optional<ServiceEntityDescription> findServiceEntityById(String id);
}
