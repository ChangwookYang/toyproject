package cache.product.controller;

import cache.product.dto.CategoryDto;
import cache.product.dto.ProductDto;
import cache.product.repository.CategorySearchCondition;
import cache.product.repository.ProductSearchCondition;
import cache.product.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"2. Category"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    private final CategoryService categoryService;

    @ApiOperation(value="카테고리 리스트 조회", notes = "전체 카테고리 리스트를 조회한다.")
    @GetMapping(value = "")
    public Page<CategoryDto> findAllCategory(Pageable pageable) {
        return categoryService.searchCategories(pageable);
    }

    @ApiOperation(value="카테고리에 속한 상품 리스트 조회", notes = "특정 카테고리에 속한 상품 리스트 조회를 조회한다.")
    @GetMapping(value = "/{categoryNo}/items")
    public Page<ProductDto> findCategoryItem(@PathVariable("categoryNo") Long categoryNo, Pageable pageable) {
        ProductSearchCondition condition = new ProductSearchCondition();
        condition.setCategoryNo(categoryNo);
        return categoryService.searchCategoryItems(condition, pageable);
    }

    @ApiOperation(value="카테고리 조회", notes = "특정 카테고리를 조회한다.")
    @GetMapping(value = "/{categoryNo}")
    public CategoryDto findCategory(@PathVariable("categoryNo") Long categoryNo) {
        return categoryService.search(categoryNo);
    }

    @ApiOperation(value="카테고리 추가", notes = "카테고리를 추가한다.")
    @PostMapping(value = "")
    public CategoryDto postCategory(String categoryName, Long parentNo) throws Exception {
        CategorySearchCondition vo = new CategorySearchCondition();
        vo.setCategoryName(categoryName);
        vo.setParentNo(parentNo);
        return categoryService.save(vo);
    }

    @ApiOperation(value="카테고리 수정", notes = "카테고리를 수정한다.")
    @PutMapping(value = "/{categoryNo}")
    public CategoryDto putCategory(CategorySearchCondition vo) throws Exception {
        return categoryService.update(vo);
    }

    @ApiOperation(value="카테고리 삭제", notes = "카테고리를 삭제한다.")
    @DeleteMapping(value = "/{categoryNo}")
    public void deleteCategory(@PathVariable("categoryNo") Long categoryNo) {
        categoryService.delete(categoryNo);
    }
}
