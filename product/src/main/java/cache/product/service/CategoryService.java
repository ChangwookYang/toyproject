package cache.product.service;


import cache.product.dto.CategoryDto;
import cache.product.dto.ProductDto;
import cache.product.entity.Category;
import cache.product.repository.CategoryRepository;
import cache.product.repository.CategorySearchCondition;
import cache.product.repository.ProductRepository;
import cache.product.repository.ProductSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    public CategoryDto search(Long id) {
        return categoryRepository.search(id);
    }

    public Page<CategoryDto> searchCategories(Pageable pageable) {
        return categoryRepository.searchCategories(new CategorySearchCondition(), pageable);
    }

    public Page<ProductDto> searchCategoryItems(ProductSearchCondition condition, Pageable pageable) {
        return productRepository.search(condition, pageable);
    }

    public CategoryDto save(CategorySearchCondition vo) throws Exception {
        Category findCategory = categoryRepository.findByCategoryName(vo.getCategoryName());
        if(findCategory != null) {
            throw new Exception("이미 존재하는 카테고리입니다.");
        }
        Category parentCategory = null;
        if(vo.getParentNo() != null){
            parentCategory = categoryRepository.findById(vo.getParentNo()).get();
        }
        Category saveCategory = new Category(vo.getCategoryName(), parentCategory);
        categoryRepository.save(saveCategory);
        return search(saveCategory.getId());
    }

    public CategoryDto update(CategorySearchCondition condition) throws Exception {
        Category category = categoryRepository.findById(condition.getCategoryNo()).get();
        Category parentCategory = null;
        if(condition.getParentNo() != null){
            parentCategory = categoryRepository.findById(condition.getParentNo()).get();
        }
        category.changeCategory(condition.getCategoryName(), parentCategory);
        return search(category.getId());
    }

    public void delete(Long categoryNo) {
        categoryRepository.deleteById(categoryNo);
    }
}
