package cache.product.service;

public interface Cache {
    /**
     * key값을 가지고 있는지 확인한다.
     */
    public boolean containsKey(Object key);

    /**
     * key값에 해당하는 값을 가져온다.
     */
    public Object get(Object key);

    /**
     * key값에 해당하는 데이터를 입력한다.
     */
    public void put(Object key, Object value);

    /**
     * key값에 해당하는 데이터를 삭제한다.
     */
    public void remove(Object key);

    /**
     * Cache에 존재하는 모든 데이터를 삭제한다.
     *
     */
    public void removeAll();
}
