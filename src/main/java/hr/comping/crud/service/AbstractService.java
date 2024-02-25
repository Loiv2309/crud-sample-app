package hr.comping.crud.service;

import hr.comping.crud.constants.messages.exceptions.ExceptionMessages;
import hr.comping.crud.entity.superclasses.BaseEntity;
import hr.comping.crud.exception.EntityNotFoundException;
import hr.comping.crud.filtering.EntitySpecificationBuilder;
import hr.comping.crud.dto.SearchCriteria;
import hr.comping.crud.filtering.SearchType;
import hr.comping.crud.repository.AbstractRepository;
import hr.comping.crud.util.Translator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Abstract service class providing common CRUD operations.
 * @param <T> The entity type, must extend BaseEntity.
 */
@Service
@Transactional
public abstract class AbstractService<T extends BaseEntity> {

    @Autowired
    AbstractRepository<T> abstractRepository;

    /**
     * Retrieves all entities of type T.
     * @return List of all entities.
     */
    public List<T> findAll() {
        return abstractRepository.findAll();
    }

    /**
     * Retrieves an entity by its ID.
     * @param id The ID of the entity to retrieve.
     * @return Optional containing the entity if found, otherwise empty.
     */
    public Optional<T> findById(String id) {
        return abstractRepository.findById(id);
    }

    /**
     * Retrieves all entities by a List of ids.
     * @param ids The List of IDs of the entities to retrieve.
     * @return List of found entities.
     */
    public List<T> findByIds(List<String> ids) {
        return abstractRepository.findAllById(ids);
    }

    /**
     * Retrieves all entities by a List of ids.
     * @param ids The Set of IDs of the entities to retrieve.
     * @return List of found entities.
     */
    public List<T> findByIds(Set<String> ids) {
        return abstractRepository.findAllById(ids);
    }


    /**
     * Saves or updates an entity.
     * @param entity The entity to save or update.
     * @return The saved or updated entity.
     */
    public T save(T entity) {
        return abstractRepository.save(entity);
    }

    /**
     * Deletes an entity by its ID.
     * @param id The ID of the entity to delete.
     * @throws EntityNotFoundException If the entity with the provided ID is not found.
     */
    public void deleteById(String id) {
        Optional<T> entity = findById(id);
        if(entity.isPresent()) {
            abstractRepository.deleteById(id);
        }
        else {
            throw new EntityNotFoundException(Translator.translate(ExceptionMessages.ENTITY_NOT_FOUND_W_PARAMS,id));
        }
    }

    /**
     * Retrieves a page of entities with pagination and optional sorting and filtering.
     * @param skip Number of items to skip.
     * @param take Number of items to take (page size).
     * @param sort Field to sort by.
     * @param direction Sorting direction (asc or desc).
     * @param searchType Type of search (AND or OR) for multiple search criteria.
     * @param searchCriteria List of search criteria to filter entities.
     * @return Page of entities matching the pagination and filtering criteria.
     */
    public Page<T> findAllPage(int skip, int take, String sort, String direction, List<SearchCriteria> searchCriteria, SearchType searchType) {
        Sort customSort = Sort.by(sort).descending();
        if (direction.equalsIgnoreCase("asc") || direction.equalsIgnoreCase("ascending")) {
            customSort = Sort.by(sort).ascending();
        }
        Pageable pageable = PageRequest.of(skip == 0 ? 0 : skip / take, take, customSort);
        if (searchCriteria != null) {
            EntitySpecificationBuilder<T> builder = new EntitySpecificationBuilder<>(searchType, searchCriteria);
            return abstractRepository.findAll(builder.build(), pageable);
        }
        return abstractRepository.findAll(pageable);
    }
}
