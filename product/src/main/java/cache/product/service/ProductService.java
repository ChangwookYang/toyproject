package cache.product.service;

import cache.product.dto.ProductDto;
import cache.product.entity.Category;
import cache.product.entity.Product;
import cache.product.repository.CategoryRepository;
import cache.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductDto search(Long id) {
        Product product = (Product) CacheSvc.getInstance().getCacheData("product", id);
        if(product != null){
            return new ProductDto(product.getName()
                                , product.getPrice()
                                , productRepository.searchCategoryName(product.getId()).getCategoryName()
                                , product.getBrand());
        }
        return productRepository.search(id);
    }

    public ProductDto save(ProductDto productDto) throws Exception {

        Category findCategory = categoryRepository.findByCategoryName(productDto.getCategoryName());
        if(findCategory == null) {
            throw new Exception("일치하는 카테고리가 없습니다.");
        }
        Product product = new Product(productDto.getProductName()
                                    , productDto.getPrice()
                                    , findCategory
                                    , productDto.getBrandName());
        productRepository.save(product);
        return search(product.getId());
    }


    public ProductDto update(Long id, ProductDto productDto) throws Exception {
        Category findCategory = categoryRepository.findByCategoryName(productDto.getCategoryName());
        if(findCategory == null && productDto.getCategoryName() != null) {
            throw new Exception("일치하는 카테고리가 없습니다.");
        }
        Product product = productRepository.findById(id).get();

        product.changeProduct(productDto.getProductName()
                            , productDto.getPrice()
                            , productDto.getBrandName()
                            , findCategory);
        return search(product.getId());
    }

    public void delete(long productNo) {
        productRepository.deleteById(productNo);
    }
}
