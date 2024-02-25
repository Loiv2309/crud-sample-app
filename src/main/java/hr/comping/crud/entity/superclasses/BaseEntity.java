package hr.comping.crud.entity.superclasses;

import com.fasterxml.jackson.annotation.JsonView;
import hr.comping.crud.entity.jsonview.JsonViewFilter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

/**
 * Base entity class for other entity classes.
 * Contains the ID field.
 */
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "uuid2")
    @Column(name = "id")
    @NotNull
    @JsonView(JsonViewFilter.Basic.class)
    private String id;
}