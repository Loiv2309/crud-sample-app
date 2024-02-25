package hr.comping.crud.filtering;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enum specifying the sort direction when filtering entities.
 */
@AllArgsConstructor
@Getter
public enum SortDirection {
    ASC("asc"),DESC("desc");

    private final String val;
}
