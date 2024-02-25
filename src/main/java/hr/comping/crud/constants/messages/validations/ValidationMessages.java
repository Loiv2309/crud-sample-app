package hr.comping.crud.constants.messages.validations;

/**
 * Class used to store I18N message codes for validations used by jakarta.validation.@Valid annotation
 */
public abstract class ValidationMessages {
    private ValidationMessages() {
        throw new IllegalStateException("Constants class cannot be initialized!");
    }
    public static final String SERVICE_PROVIDER_NAME ="validation.ServiceProvider.name.message";
    public static final String SERVICE_DESCRIPTION= "validation.Service.description.message";
    public static final String SEARCH_CRITERIA_OPERATION = "validation.SearchCriteria.operation.message";
    public static final String SEARCH_CRITERIA_FILTER_KEY = "validation.SearchCriteria.filterKey.message";
    public static final String SEARCH_CRITERIA_VALUE_AND_VALUES_PROVIDED = "validation.SearchCriteria.valueAndValuesProvided.message";
    public static final String SEARCH_CRITERIA_INCORRECT_COMBINATION = "validation.SearchCriteria.incorrectCombination.message";
}
