package hr.comping.crud.entity.superclasses;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
 * Class for entities requiring auditing functionality.
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class AuditEntity extends BaseEntity {

    /**
     * The time the entity was created.
     */
    @Column(name="created")
    private ZonedDateTime created;

    /**
     * The ID of the user that created the entity.
     */
    @Column(name="created_user_id")
    private String createdUserId;

    /**
     * The time the entity was modified.
     */
    @Column(name = "modified")
    private ZonedDateTime modified;

    /**
     * The ID of the user that modified the entity.
     */
    @Column(name = "modified_user_id")
    private String modifiedUserId;
}
