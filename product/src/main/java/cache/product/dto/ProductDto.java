package cache.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private String productName;
    private Long price;
    private String categoryName;
    private String brandName;

    @QueryProjection
    public ProductDto(String productName, Long price, String categoryName, String brandName) {
        this.productName = productName;
        this.price = price;
        this.categoryName = categoryName;
        this.brandName = brandName;
    }
}
