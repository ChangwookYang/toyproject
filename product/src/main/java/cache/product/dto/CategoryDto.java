package cache.product.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {
    private String categoryName;

    @QueryProjection
    public CategoryDto(String categoryName) {
        this.categoryName = categoryName;
    }
}
