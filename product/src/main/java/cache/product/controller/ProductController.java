package cache.product.controller;


import cache.product.dto.ProductDto;
import cache.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1. Product"})
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value="상품 정보 조회", notes = "특정 상품에 대하여 상품명, 카테고리, 가격을 조회한다.")
    @GetMapping(value = "/{productNo}")
    public ProductDto findProductById(@PathVariable("productNo") Long id) {
        return productService.search(id);
    }

    @ApiOperation(value="상품 추가", notes = "상품을 추가한다.")
    @PostMapping(value = "")
    public ProductDto postProduct(ProductDto productDto) throws Exception {
        return productService.save(productDto);
    }

    @ApiOperation(value="상품 변경", notes = "상품 속성을 수정한다.")
    @PutMapping(value = "/{productNo}")
    public ProductDto putProduct(@PathVariable("productNo") Long id, ProductDto productDto) throws Exception {
        return productService.update(id, productDto);
    }

    @ApiOperation(value="상품 삭제", notes = "상품을 삭제한다.")
    @DeleteMapping(value = "/{productNo}")
    public void deleteProduct(@PathVariable("productNo") int productNo) {
        productService.delete(productNo);
    }

}
