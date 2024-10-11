package com.data.room.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import com.data.room.domain.Product;

import java.util.List;

@Repository
public class ProductRepository {
    //ProductRepository Log 설정
    //Log Format [ProductRepository] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(ProductRepository.class);

    //JPA는 entitymanager 사용
    private final EntityManager em;

    public ProductRepository(EntityManager em) {
        this.em = em;
    }

    public List<Product> findAll(){
        List<Product> productList = em.createQuery("select p from Product p")
                .getResultList();
        if(productList == null){
            logger.error("[ProductRepository] [findAll]: 제품 리스트 조회 실패");
        }
        return productList;
    }


}
