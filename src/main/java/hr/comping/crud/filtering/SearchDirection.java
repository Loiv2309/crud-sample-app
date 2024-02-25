package hr.comping.crud.filtering;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum specifying the search direction when filtering entities.
 */
@AllArgsConstructor
@Getter
public enum SearchDirection {
    ASC("asc"),DESC("desc");

    private final String val;
}
