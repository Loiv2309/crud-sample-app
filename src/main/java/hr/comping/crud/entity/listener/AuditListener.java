package hr.comping.crud.entity.listener;

import hr.comping.crud.entity.superclasses.AuditEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

import java.time.ZonedDateTime;

/**
 * Class used for populating audit data defined in AuditEntity
 */
public class AuditListener {

    /**
     * Method executed before persisting an AuditEntity.
     * Populates the 'created' and 'createdUserId' fields.
     * @param auditEntity The entity being persisted.
     */
    @PrePersist
    private void beforeSave(AuditEntity auditEntity) {
        auditEntity.setCreated(ZonedDateTime.now());
        auditEntity.setCreatedUserId("PUBLIC"); // In real life scenario we would get the ID from the JWT token for example.
    }

    /**
     * Method executed before updating or removing an AuditEntity.
     * Populates the 'modified' and 'modifiedUserId' fields.
     * @param auditEntity The entity being updated or removed.
     */
    @PreUpdate
    @PreRemove
    private void beforeUpdate(AuditEntity auditEntity) {
        auditEntity.setModified(ZonedDateTime.now());
        auditEntity.setModifiedUserId("PUBLIC"); // In real life scenario we would get the ID from the JWT token for example.
    }

}
