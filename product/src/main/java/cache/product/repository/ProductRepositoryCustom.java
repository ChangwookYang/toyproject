package cache.product.repository;

import cache.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {
    ProductDto search(Long id);
    Page<ProductDto> search(ProductSearchCondition condition, Pageable pageable);
}
