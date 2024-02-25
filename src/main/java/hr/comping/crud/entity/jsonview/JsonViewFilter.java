package hr.comping.crud.entity.jsonview;

/**
 * Interface for JSON view filters used by @JsonView annotation to filter attributes mapped to JSON response
 */
public interface JsonViewFilter {

    /**
     * Inner interface representing a basic JSON view.
     * Used to show only the basic information (eg. id,name,description,..)
     */
    interface Basic {
    }
}
