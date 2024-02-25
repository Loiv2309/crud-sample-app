package hr.comping.crud.filtering;

import hr.comping.crud.dto.SearchCriteria;
import hr.comping.crud.entity.superclasses.BaseEntity;
import hr.comping.crud.exception.EntitySpecificationException;
import jakarta.persistence.criteria.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Generic class used for querying entities.
 * @param <T> Entity type.
 */
@Slf4j
public class EntitySpecification<T> implements Specification<T> {

    //The separator used to define joins. E.g. 'services.description' -> filterKey="services.description" (join 'services' entity table and search for 'description' field)
    private static final String CHILD_ENTITY_SEPARATOR = ".";

    private final transient SearchCriteria searchCriteria;

    public EntitySpecification(final SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }


    /**
     * Maps SearchCriteria to criteria builder to build predicates for JPA criteria queries.
     * @param root The Query root.
     * @param query Not used.
     * @param cb Used to construct criteria queries.
     * @return The predicate representing the SearchCriteria.
     */
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        String searchValue = searchCriteria.getValue() != null ? searchCriteria.getValue().toString() : "";
        List<String> searchValues = searchCriteria.getValues();

        Object searchObject;
        if (searchValues != null) {
            if (joinDefined()) {
                searchObject = castToRequiredType(joinTable(root).get(getJoinFilterKey()).getModel().getBindableJavaType(), searchValues);
            } else {
                searchObject = castToRequiredType(root.get(searchCriteria.getFilterKey()).getModel().getBindableJavaType(), searchValues);
            }
        } else {
            if (joinDefined()) {
                searchObject = castToRequiredType(joinTable(root).get(getJoinFilterKey()).getModel().getBindableJavaType(), searchValue);
            } else {
                searchObject = castToRequiredType(root.get(searchCriteria.getFilterKey()).getModel().getBindableJavaType(), searchValue);
            }
        }

        switch (Objects.requireNonNull(searchCriteria.getOperation())) {
            case LIKE -> {
                searchValue = "%" + searchValue.toLowerCase() + "%";
                if (joinDefined()) {
                    return cb.like(cb.lower(joinTable(root).get(getJoinFilterKey())), searchValue);
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), searchValue);
            }
            case NOT_LIKE -> {
                searchValue = "%" + searchValue.toLowerCase() + "%";
                if (joinDefined()) {
                    return cb.notLike(cb.lower(joinTable(root).get(getJoinFilterKey())), searchValue);
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), searchValue);
            }
            case EQUAL -> {
                if (joinDefined()) {
                    return cb.equal(joinTable(root).get(getJoinFilterKey()), searchObject);
                }
                return cb.equal(root.get(searchCriteria.getFilterKey()), searchObject);

            }
            case NOT_EQUAL -> {
                if (joinDefined()) {
                    return cb.notEqual(joinTable(root).get(getJoinFilterKey()), searchObject);
                }
                return cb.notEqual(root.get(searchCriteria.getFilterKey()), searchObject);
            }
            case IS_NULL -> {
                if (joinDefined()) {
                    return cb.isNull(joinTable(root).get(getJoinFilterKey()));
                }
                return cb.isNull(root.get(searchCriteria.getFilterKey()));
            }
            case IS_NOT_NULL -> {
                if (joinDefined()) {
                    return cb.isNotNull(joinTable(root).get(getJoinFilterKey()));
                }
                return cb.isNotNull(root.get(searchCriteria.getFilterKey()));
            }
            case BEGINS_WITH -> {
                searchValue = searchValue.toLowerCase() + "%";
                if (joinDefined()) {
                    return cb.like(cb.lower(joinTable(root).get(getJoinFilterKey())), searchValue);
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), searchValue);
            }
            case ENDS_WITH -> {
                searchValue = "%" + searchValue.toLowerCase();
                if (joinDefined()) {
                    return cb.like(cb.lower(joinTable(root).get(getJoinFilterKey())), searchValue);
                }
                return cb.like(cb.lower(root.get(searchCriteria.getFilterKey())), searchValue);
            }
            case DOES_NOT_BEGIN_WITH -> {
                searchValue = searchValue.toLowerCase() + "%";
                if (joinDefined()) {
                    return cb.notLike(cb.lower(joinTable(root).get(getJoinFilterKey())), searchValue);
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), searchValue);
            }
            case DOES_NOT_END_WITH -> {
                searchValue = "%" + searchValue.toLowerCase();
                if (joinDefined()) {
                    return cb.notLike(cb.lower(joinTable(root).get(getJoinFilterKey())), searchValue);
                }
                return cb.notLike(cb.lower(root.get(searchCriteria.getFilterKey())), searchValue);
            }
            case GREATER_THAN -> {
                if (joinDefined()) {
                    return cb.greaterThan(joinTable(root).get(getJoinFilterKey()), searchValue);
                }
                return cb.greaterThan(root.get(searchCriteria.getFilterKey()), searchValue);
            }
            case LESS_THAN -> {
                if (joinDefined()) {
                    return cb.lessThan(joinTable(root).get(getJoinFilterKey()), searchValue);
                }
                return cb.lessThan(root.get(searchCriteria.getFilterKey()), searchValue);
            }
            case GREATER_THAN_EQUAL -> {
                if (joinDefined()) {
                    return cb.greaterThanOrEqualTo(joinTable(root).get(getJoinFilterKey()), searchValue);
                }
                return cb.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchValue);
            }
            case LESS_THAN_EQUAL -> {
                if (joinDefined()) {
                    return cb.lessThanOrEqualTo(joinTable(root).get(getJoinFilterKey()), searchValue);
                }
                return cb.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()), searchValue);
            }
            case IS_BEFORE -> {
                if (joinDefined()) {
                    return cb.lessThanOrEqualTo(joinTable(root).get(getJoinFilterKey()).as(ZonedDateTime.class), ZonedDateTime.parse(searchValue));
                }
                return cb.lessThanOrEqualTo(root.get(searchCriteria.getFilterKey()).as(ZonedDateTime.class), ZonedDateTime.parse(searchValue));
            }
            case IS_AFTER -> {
                if (joinDefined()) {
                    return cb.greaterThanOrEqualTo(joinTable(root).get(getJoinFilterKey()).as(ZonedDateTime.class), ZonedDateTime.parse(searchValue));
                }
                return cb.greaterThanOrEqualTo(root.get(searchCriteria.getFilterKey()).as(ZonedDateTime.class), ZonedDateTime.parse(searchValue));
            }
            case IN -> {
                if (searchValues == null || searchValues.isEmpty()) {
                    throw new EntitySpecificationException("Must provide 'searchValues' parameter when using IN operation"); //This should not happen because there are validations in place but just in case
                }

                if (joinDefined()) {
                    return cb.in(joinTable(root).get(getJoinFilterKey())).value(searchObject);
                }
                return cb.in(root.get(searchCriteria.getFilterKey())).value(searchObject);
            }
            default ->
                    throw new EntitySpecificationException("Filter operation: " + searchCriteria.getOperation() + "is not supported!");

        }
    }

    /**
     * Defines if join is defined.
     *
     * @return True if filter key contains CHILD_ENTITY_SEPARATOR, otherwise false
     */
    private boolean joinDefined() {
        return searchCriteria.getFilterKey().contains(CHILD_ENTITY_SEPARATOR);
    }

    /**
     * Helper function to get the filter key if join was defined in SearchCriteria.
     *
     * @return Filter key after removing joins.
     */
    private String getJoinFilterKey() {
        if (joinDefined()) {
            String[] filterKeySplit = searchCriteria.getFilterKey().split(Pattern.quote(CHILD_ENTITY_SEPARATOR));
            return filterKeySplit[filterKeySplit.length - 1];
        }
        throw new EntitySpecificationException("Join filter key called but SearchCriteria don't have a join defined!");
    }

    /**
     * Helper function to join tables if defined in SearchCriteria.
     *
     * @param root The Query root.
     * @return Entity join defined by SearchCriteria using CHILD_ENTITY_SEPARATOR.
     */
    private Join<?, ?> joinTable(Root<?> root) {
        if (joinDefined()) {
            String[] filterKeySplit = searchCriteria.getFilterKey().split(Pattern.quote(CHILD_ENTITY_SEPARATOR));
            Join<?, ?> join = root.join(filterKeySplit[0]);
            for (int i = 1; i < filterKeySplit.length - 1; i++) {
                join = join.join(filterKeySplit[i]);
            }
            return join;
        }
        throw new EntitySpecificationException("Join table called but SearchCriteria don't have a join defined!");
    }

    /**
     * Helper function to cast an object to the required class.
     *
     * @param fieldType The entity attribute class.
     * @param value     The search value.
     * @return The cast value.
     */
    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(String.class)) {
            return value;
        } else if (BaseEntity.class.isAssignableFrom(fieldType)) {
            return value;
        } else if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        } else if (fieldType.isAssignableFrom(Long.class)) {
            return Long.valueOf(value);
        } else if (fieldType.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(value);
        } else if (fieldType.isAssignableFrom(boolean.class)) {
            return Boolean.valueOf(value);
        }
        throw new EntitySpecificationException("EntitySpecification - Could not cast to required type: " + fieldType.getName());
    }

    /**
     * Helper function to cast a list of objects to the list of required class.
     *
     * @param fieldType The entity attribute class.
     * @param value     The search value.
     * @return A list of cast values.
     */
    private Object castToRequiredType(Class<?> fieldType, List<String> value) {
        if (fieldType == String.class) {
            return value;
        }
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }
}
