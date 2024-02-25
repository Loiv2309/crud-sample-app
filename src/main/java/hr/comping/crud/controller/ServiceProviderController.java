package hr.comping.crud.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hr.comping.crud.dto.ServiceProviderRequest;
import hr.comping.crud.entity.ServiceProvider;
import hr.comping.crud.entity.jsonview.JsonViewFilter;
import hr.comping.crud.service.ServiceProviderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller for handling specific Service Provider entity related operations.
 * Extends AbstractController for common CRUD operations.
 */
@RestController
@RequestMapping("/service-providers")
public class ServiceProviderController extends AbstractController<ServiceProvider>{

    @Autowired
    ServiceProviderService serviceProviderService;

    /**
     * Endpoint for creating a new Service Provider entity.
     * @param serviceProviderRequest The request object containing data for creating the Service Provider entity.
     * @return ResponseEntity containing the created Service Provider entity.
     */
    @PostMapping
    @JsonView(JsonViewFilter.Basic.class)
    public ResponseEntity<ServiceProvider> createEntity(@RequestBody @Valid ServiceProviderRequest serviceProviderRequest){
        return new ResponseEntity<>(serviceProviderService.createEntity(serviceProviderRequest), HttpStatus.OK);
    }


    /**
     * Endpoint for updating an existing Service Provider entity.
     * @param id The ID of the Service Provider entity to update.
     * @param serviceProviderRequest The request object containing updated data for the Service Provider entity.
     * @return ResponseEntity containing the updated Service Provider entity.
     */
    @PutMapping("/{id}")
    @JsonView(JsonViewFilter.Basic.class)
    public ResponseEntity<ServiceProvider> updateEntity(@PathVariable String id, @RequestBody @Valid ServiceProviderRequest serviceProviderRequest){
        return new ResponseEntity<>(serviceProviderService.updateEntity(id, serviceProviderRequest), HttpStatus.OK);
    }
}
