package com.data.room.service;

import com.data.room.domain.Product;
import com.data.room.repository.ProductRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    //ProductService Log 설정
    //Log Format [ProductService] [Function Name]: Message
    private final static Logger logger = LogManager.getLogger(ProductService.class);

    public List<Product> getProductList(){
        List<Product> productList = productRepository.findAll();
        if(productList == null){
            logger.error("[ProductService] [getProductList]: 제품 리스트 조회 실패");
        }
        return productList;

    }
}
