package cache.product.repository;

import cache.product.entity.Category;
import cache.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProductRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void basicTest() {

        em.flush();
        em.clear();


        Category category = new Category("스킨케어", 1, null);
        Category category2 = new Category("스킨케어2", 1, category);

        em.persist(category);
        em.persist(category2);

        Product product = new Product("ET.미씽유 핸드크림(핑크돌고래)30ml.",4500000, category, "미장센");
        Product product2 = new Product("ET.미씽유 1111",330000, category2, "미장센2");
        productRepository.save(product);
        productRepository.save(product2);
//        em.persist(product);
//        em.persist(product2);

        Product findProduct = productRepository.findById(product.getId()).get();
        assertThat(findProduct).isEqualTo(product);

    }

}