package com.data.room.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product {
    //제품 아이디
    @Id
    private int product_id;
    //제품 번호
    private String product_kind;

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_kind() {
        return product_kind;
    }

    public void setProduct_kind(String product_kind) {
        this.product_kind = product_kind;
    }
}
