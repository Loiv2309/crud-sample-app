package hr.comping.crud.dto;

import hr.comping.crud.constants.messages.validations.ValidationMessages;
import hr.comping.crud.filtering.SearchOperation;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Search criteria object used for filtering entities.
 */
@Getter
@Setter
@NoArgsConstructor
public class SearchCriteria {

    @NotEmpty(message = "{" + ValidationMessages.SEARCH_CRITERIA_FILTER_KEY + "}")
    private String filterKey;
    @NotNull(message = "{" + ValidationMessages.SEARCH_CRITERIA_OPERATION + "}")
    private SearchOperation operation;

    private Object value;
    private List<String> values;


    //Custom validations
    @AssertTrue(message = "{" + ValidationMessages.SEARCH_CRITERIA_INCORRECT_COMBINATION + "}")
    public boolean isCorrectDataCombinationSent() {
        if (this.operation.equals(SearchOperation.IN)) {
            return this.values != null && !this.values.isEmpty();
        }
        else {
            return this.value != null;
        }
    }

    @AssertTrue(message = "{" + ValidationMessages.SEARCH_CRITERIA_VALUE_AND_VALUES_PROVIDED + "}")
    public boolean isValueAndValuesProvided() {
        return this.value == null || this.values == null;
    }


    /**
     * Constructor for filtering by a single value
     *
     * @param filterKey The entity attribute name
     * @param operation The Search operation
     * @param value     The search value
     */
    public SearchCriteria(String filterKey, SearchOperation operation, Object value) {
        this.filterKey = filterKey;
        this.operation = operation;
        this.value = value;
    }

    /**
     * Constructor for filtering by a list of values
     *
     * @param filterKey The entity attribute name
     * @param operation The Search operation
     * @param values    The search values
     */
    public SearchCriteria(String filterKey, SearchOperation operation,
                          List<String> values) {
        this.filterKey = filterKey;
        this.operation = operation;
        this.values = values;
    }

}