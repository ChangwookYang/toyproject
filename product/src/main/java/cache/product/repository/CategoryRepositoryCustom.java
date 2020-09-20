package cache.product.repository;

import cache.product.dto.CategoryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryRepositoryCustom {

    CategoryDto search(Long id);
    Page<CategoryDto> searchCategories(CategorySearchCondition condition, Pageable pageable);

}
