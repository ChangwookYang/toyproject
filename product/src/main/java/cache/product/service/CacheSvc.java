package cache.product.service;

import cache.product.repository.CategoryRepository;
import cache.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@RequiredArgsConstructor
public class CacheSvc implements InitializingBean {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private static CacheSvc instance;
    private final Lock lock = new ReentrantLock();

    public static CacheSvc getInstance() {
        return instance;
    }

    /** 캐시 수용량*/
    private LRUCache cache;
    private final static int CAPACITY = 1500;

    /**
     * 초기 구동시에 데이터를 원본 데이터베이스로부터 loading
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
        cache = new LRUCache(CAPACITY);
//        setCacheAll();
    }

    /**
     * 전체 캐시 재설정
     */
    /*public void setCacheAll() {
        lock.lock();
        try {
            *//*PRODUCT, CATEGORY의 PK에 대한 캐시저장*//*
            productJpaRepo.findAll().stream().forEach(v -> cache.put(getKey(PRODUCT, v.getProductNo()), v));
            categoryJpaRepo.findAll().stream().forEach(v -> cache.put(getKey(CATEGORY, v.getCategoryNo()), v));

            *//*전체 카테고리 및 카테고리에 매핑되어 있는 아이템 캐시저장*//*
            getCategoryList().stream().forEach(v -> setCategoryItemList(v.getCategoryNo()));
        } catch (Exception e) {
            lock.unlock();
        }
    }*/
}
