package com.hiberus.customer.inditex.challenge.async;

import com.hiberus.customer.inditex.challenge.model.Product;
import org.springframework.context.ApplicationEvent;

public class ProductCreationEvent extends ApplicationEvent {

    private Product product;

    public ProductCreationEvent(Object source, Product product) {
        super(source);
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}