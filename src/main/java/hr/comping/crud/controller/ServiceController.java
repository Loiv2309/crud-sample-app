package hr.comping.crud.controller;

import com.fasterxml.jackson.annotation.JsonView;
import hr.comping.crud.dto.ServiceRequest;
import hr.comping.crud.entity.ServiceEntity;
import hr.comping.crud.entity.jsonview.JsonViewFilter;
import hr.comping.crud.entity.projection.ServiceEntityDescription;
import hr.comping.crud.service.ServiceEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling specific Service entity related operations.
 * Extends AbstractController for common CRUD operations.
 */
@RestController
@RequestMapping("/services")
public class ServiceController extends AbstractController<ServiceEntity>{

    @Autowired
    ServiceEntityService serviceEntityService;

    /**
     * Endpoint for creating a new service entity.
     * @param serviceRequest The request object containing data for creating the Service entity.
     * @return ResponseEntity containing the created Service entity.
     */
    @PostMapping
    @JsonView(JsonViewFilter.Basic.class)
    public ResponseEntity<ServiceEntity> createEntity(@RequestBody @Valid ServiceRequest serviceRequest){
        return new ResponseEntity<>(serviceEntityService.createEntity(serviceRequest), HttpStatus.OK);
    }

    /**
     * Endpoint for updating an existing service entity.
     * @param id The ID of the Service entity to update.
     * @param serviceRequest The request object containing updated data for the Service entity.
     * @return ResponseEntity containing the updated Service entity.
     */
    @PutMapping("/{id}")
    @JsonView(JsonViewFilter.Basic.class)
    public ResponseEntity<ServiceEntity> updateEntity(@PathVariable String id, @RequestBody @Valid ServiceRequest serviceRequest){
        return new ResponseEntity<>(serviceEntityService.updateEntity(id, serviceRequest), HttpStatus.OK);
    }

    /**
     * Endpoint for getting Service entity description field.
     * @param id The ID of the Service entity.
     * @return ResponseEntity containing the ServiceEntityDescription projection
     */
    @GetMapping("/{id}/description")
    public ResponseEntity<ServiceEntityDescription> getServiceDescription(@PathVariable String id){
        return new ResponseEntity<>(serviceEntityService.getServiceDescription(id), HttpStatus.OK);
    }

}
