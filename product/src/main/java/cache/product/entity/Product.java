package cache.product.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product")
@SequenceGenerator(name="product_generator", sequenceName = "product_seq", initialValue = 1, allocationSize=1)
public class Product {
    @Id
    @Column(name = "product_no")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_generator")
    private Long id;

    @Column(name = "product_name")
    private String name;
    @Column(name = "product_price")
    private long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_no")
    private Category category;

    @Column(name = "brand_name")
    private String brand;

    public Product(String name, long price, Category category, String brand) throws Exception {
        if(name == null){
            throw new Exception("상품명은 필수입니다.");
        }
        if(price <= 0){
            throw new Exception("상품 가격은 0이상 입니다.");
        }
        this.name = name;
        this.price = price;
        this.brand = brand;
        if(category != null){
            changeCategory(category);
        }
    }

    private void changeCategory(Category category){
        this.category = category;
        category.getProducts().add(this);
    }

    public void changeProduct(String productName, Long price, String brand, Category category) {
        if(productName !=null) this.name = productName;
        if(price !=null) this.price = price;
        if(brand !=null) this.brand = brand;
        if(category != null) changeCategory(category);

    }
}
