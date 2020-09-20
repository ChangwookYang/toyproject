1. 프로젝트명 : product
2. 개발내용 : In-Memory Cache 기능을 갖는 상품 카테고리와 상품정보 API Service
3. 시스템환경 
   * 개발환경 : Spring Data JPA / Querydsl
   * DBMS : H2

4. 내용

   * 캐싱 기반  API조회

     * 캐시 알고리즘 LRU(Least Recently Used) 알고리즘 적용

     * 제한 리소스보다 캐시의 양이 클 경우 가장 적게 사용된 데이터 Eviction 처리

     * Cache Miss 처리 최소화 처리 – 캐시 갱신 스케줄 구현
   * API 목록
       * 카테고리 리스트 조회 
       * 카테고리에 속한 상품 리스트 조회
       * 상품에 대한 상품명, 카테고리, 가격 조회
       * 상품/카테고리 CRUD
