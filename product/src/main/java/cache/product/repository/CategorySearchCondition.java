package cache.product.repository;

import lombok.Data;

@Data
public class CategorySearchCondition {
    private Long categoryNo;
    private String categoryName;
    private Long parentNo;
}
