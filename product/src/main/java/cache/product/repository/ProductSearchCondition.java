package cache.product.repository;

import lombok.Data;

@Data
public class ProductSearchCondition {
    private Long productNo;
    private String productName;
    private Long price;
    private Long categoryNo;
}
