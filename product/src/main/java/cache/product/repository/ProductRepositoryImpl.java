package cache.product.repository;

import cache.product.dto.ProductDto;
import cache.product.dto.QProductDto;
import cache.product.entity.Product;
import cache.product.entity.QCategory;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static cache.product.entity.QCategory.category;
import static cache.product.entity.QProduct.product;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom{

    private final JPQLQueryFactory queryFactory;

    public ProductRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public ProductDto search(Long id) {

        QCategory parentCategory = new QCategory("parentCategory");
        return queryFactory
                .select(new QProductDto(
                        product.name.as("productName"),
                        product.price,
                        new CaseBuilder()
                                .when(parentCategory.categoryName.isNull()).then(category.categoryName)
                                .otherwise(parentCategory.categoryName.concat("_").concat(category.categoryName)),
                        product.brand.as("brandName")
                ))
                .from(product)
                .leftJoin(product.category, category)
                .leftJoin(category.parent, parentCategory)
                .where(product.id.eq(id))
                .fetchOne();
    }

    @Override
    public Page<ProductDto> search(ProductSearchCondition condition, Pageable pageable) {
        QCategory parentCategory = new QCategory("parentCategory");
        List<ProductDto> content = queryFactory
                .select(new QProductDto(
                        product.name.as("productName"),
                        product.price,
                        new CaseBuilder()
                                .when(parentCategory.categoryName.isNull()).then(category.categoryName)
                                .otherwise(parentCategory.categoryName.concat("_").concat(category.categoryName)),
                        product.brand.as("brandName")
                ))
                .from(product)
                .leftJoin(product.category, category)
                .leftJoin(category.parent, parentCategory)
                .where(categoryNoEq(condition.getCategoryNo()))
                .fetch();

        JPQLQuery<Product> countQuery = queryFactory.selectFrom(product);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);

    }

    private BooleanExpression categoryNoEq(Long categoryNo) {
        return StringUtils.hasText(String.valueOf(categoryNo)) ? category.id.eq(categoryNo): null;
    }

}
