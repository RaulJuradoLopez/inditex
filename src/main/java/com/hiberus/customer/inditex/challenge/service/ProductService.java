package com.hiberus.customer.inditex.challenge.service;

import com.hiberus.customer.inditex.challenge.model.Product;

import java.util.Collection;

public interface ProductService {
    public abstract void createProduct(Product product);
    public abstract void updateProduct(Long id, Product product);
    public abstract void deleteProduct(Long id);
    public abstract Collection<Product> getProducts();
}
