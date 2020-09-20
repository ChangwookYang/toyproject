package cache.product.repository;

import cache.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryCustom {
    Category findByCategoryName(String categoryName);
}
