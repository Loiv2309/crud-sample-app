package hr.comping.crud.filtering;

import hr.comping.crud.dto.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Class used to convert SearchCriteria object to EntitySpecification.
 * @param <T> The type of the entity.
 */
public class EntitySpecificationBuilder<T> {

    private final List<SearchCriteria> params;
    private final SearchType searchType;

    public EntitySpecificationBuilder(SearchType searchType, List<SearchCriteria> searchParams) {
        this.searchType = searchType;
        this.params = searchParams;
    }

    public Specification<T> build() {
        if (params.isEmpty()) {
            return null;
        }

        Specification<T> result = new EntitySpecification<>(params.get(0));
        for (int idx = 1; idx < params.size(); idx++) {
            SearchCriteria criteria = params.get(idx);
            result = searchType == SearchType.OR ? Specification.where(result).or(new EntitySpecification<>(criteria)) : Specification.where(result).and(new EntitySpecification<>(criteria));
        }
        return result;
    }
}