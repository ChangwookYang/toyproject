package cache.product.service;

import cache.product.repository.CategoryRepository;
import cache.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CacheSvc implements InitializingBean {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    /* 캐시 수용량 */
    private final static int CAPACITY = 1500;
    /* 상품 구분 상수 */
    private final static String PRODUCT = "product";
    /* 카테고리 구분 상수 */
    private final static String CATEGORY = "category";

    /* LRU 캐시 */
    private LRUCache cache;
    private static CacheSvc instance;
    public static CacheSvc getInstance() {
        return instance;
    }

    /**
     * 초기 구동시 초기화
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
        cache = new LRUCache(CAPACITY);
        setCacheAll();
    }

    /**
     * 전체 캐시 설정
     */
    public void setCacheAll() {
        /**PRODUCT, CATEGORY 캐시저장*/
        productRepository.findAll().stream().forEach(v -> setCacheData(PRODUCT, v.getId(), v));
        categoryRepository.findAll().stream().forEach(v -> setCacheData(CATEGORY, v.getId(), v));
    }

    /**
     * PK에 따른 캐시 설정
     */
    public void setCacheData(String gbn, Long key, Object data) {
        cache.put(getKey(gbn, key), data);
    }

    public Object getCacheData(String gbn, Long key) {
        Object result = cache.get(getKey(gbn, key));
        if(result == null){
            if(gbn.equals(CATEGORY))
                return categoryRepository.findById(key).get();
            else
                return productRepository.findById(key).get();
        }
        return result;
    }

    /**
     * 캐시 제거
     */
    public void remove(String gbn, Long key) {
        cache.remove(getKey(gbn, key));
    }

    /**
     * 키값 생성
     */
    private String getKey(String gbn, Long key){
        StringBuilder str = new StringBuilder();
        str.append(gbn);
        str.append(key);
        return str.toString();
    }
}
