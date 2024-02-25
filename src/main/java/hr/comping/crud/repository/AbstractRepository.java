package hr.comping.crud.repository;

import hr.comping.crud.entity.superclasses.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Abstract repository
 * Extends JpaRepository for common CRUD operations and JpaSpecificationExecutor for filtering and paging.
 * @param <T> The type of entity, must extend BaseEntity.
 */
@NoRepositoryBean
public interface AbstractRepository<T extends BaseEntity> extends JpaRepository<T, String>, JpaSpecificationExecutor<T>{
}