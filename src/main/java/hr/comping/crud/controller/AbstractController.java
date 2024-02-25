package hr.comping.crud.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hr.comping.crud.entity.jsonview.JsonViewFilter;
import hr.comping.crud.entity.superclasses.BaseEntity;
import hr.comping.crud.dto.SearchCriteria;
import hr.comping.crud.filtering.SearchType;
import hr.comping.crud.filtering.SortDirection;
import hr.comping.crud.service.AbstractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Abstract controller providing common CRUD operations for entities.
 * @param <T> The entity type, must extend BaseEntity.
 */
@RestController
public abstract class AbstractController<T extends BaseEntity> {

    @Autowired
    AbstractService<T> abstractService;

    /**
     * Retrieves all entities.
     * @return List of all entities.
     */
    @GetMapping
    @JsonView(JsonViewFilter.Basic.class)
    public List<T> findAll(){
        return abstractService.findAll();
    }


    /**
     * Retrieves an entity by its ID.
     * @param id The ID of the entity to retrieve.
     * @return Optional containing the entity if found, otherwise empty.
     */
    @GetMapping("/{id}")
    @JsonView(JsonViewFilter.Basic.class)
    public Optional<T> findById(@PathVariable String id){
        return abstractService.findById(id);
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
    @GetMapping("/page")
    @JsonView(JsonViewFilter.Basic.class)
    public Page<T> pagination(@RequestParam int skip,
                              @RequestParam int take,
                              @RequestParam(required = false, defaultValue = "id") String sort,
                              @RequestParam(required = false, defaultValue = "DESC") SortDirection direction,
                              @RequestParam(required = false, defaultValue = "AND") SearchType searchType,
                              @RequestBody(required = false) @Valid List<SearchCriteria> searchCriteria) {

        return abstractService.findAllPage(skip,take,sort,direction,searchCriteria,searchType);
    }

    /**
     * Deletes an entity by its ID.
     * @param id The ID of the entity to delete.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        abstractService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
