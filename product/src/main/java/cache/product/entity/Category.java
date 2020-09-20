package cache.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "category")
@SequenceGenerator(name="category_generator", sequenceName = "category_seq", initialValue = 1, allocationSize=1)
public class Category {

    @Id
    @Column(name = "category_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_generator")
    private Long id;

    @Column(name = "category_name", nullable = false)
    String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="parent_no")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    int depth;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Category(String categoryName, Category parent) throws Exception {
        changeCategory(categoryName, parent);
    }

    public void changeCategory(String categoryName, Category parent) throws Exception{
        if(categoryName == null){
            throw new Exception("카테고리명은 필수입니다.");
        }

        this.categoryName = categoryName;
        if(parent != null){
            changeCategoryParent(parent);
            this.depth = 2;
        } else {
            this.depth = 1;
        }
    }
    private void changeCategoryParent(Category category){
        this.parent = category;
        category.getChild().add(this);
    }
}
