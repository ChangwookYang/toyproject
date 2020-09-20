package cache.product.repository;

import cache.product.dto.CategoryDto;
import cache.product.dto.QCategoryDto;
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
public class CategoryRepositoryImpl implements CategoryRepositoryCustom{

    private final JPQLQueryFactory queryFactory;

    public CategoryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public CategoryDto search(Long id) {

        QCategory parentCategory = new QCategory("parentCategory");
        return queryFactory
                .select(new QCategoryDto(
                        new CaseBuilder()
                                .when(parentCategory.categoryName.isNull()).then(category.categoryName)
                                .otherwise(parentCategory.categoryName.concat("_").concat(category.categoryName))
                ))
                .from(category)
                .leftJoin(category.parent, parentCategory)
                .where(category.id.eq(id))
                .fetchOne();
    }

    @Override
    public Page<CategoryDto> searchCategories(CategorySearchCondition condition, Pageable pageable) {
        QCategory parentCategory = new QCategory("parentCategory");
        List<CategoryDto> content = queryFactory
                .select(new QCategoryDto(
                        new CaseBuilder()
                                .when(parentCategory.categoryName.isNull()).then(category.categoryName)
                                .otherwise(parentCategory.categoryName.concat("_").concat(category.categoryName))
                                .as("categoryName")
                ))
                .from(category)
                .leftJoin(category.parent, parentCategory)
                .where(categoryNoEq(condition.getCategoryNo()))
                .fetch();

        JPQLQuery<Product> countQuery = queryFactory.selectFrom(product);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private BooleanExpression categoryNoEq(Long categoryNo) {
        return categoryNo != null ? category.id.eq(categoryNo): null;
    }


}
