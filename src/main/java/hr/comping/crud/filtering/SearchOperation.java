package hr.comping.crud.filtering;

/**
 * Search operations supported when filtering entities.
 */
public enum SearchOperation {
    LIKE, NOT_LIKE, EQUAL, NOT_EQUAL, BEGINS_WITH,
    DOES_NOT_BEGIN_WITH, ENDS_WITH, DOES_NOT_END_WITH,
    IS_NULL, IS_NOT_NULL, GREATER_THAN, GREATER_THAN_EQUAL, LESS_THAN,
    LESS_THAN_EQUAL, IS_BEFORE, IS_AFTER, IN;
}
