package cache.product.repository;

import cache.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

}
