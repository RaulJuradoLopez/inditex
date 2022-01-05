package com.hiberus.customer.inditex.challenge.service;

import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.model.Product;

import java.util.Collection;

public interface ProductService {
    public abstract void createProduct(Product product) throws InvalidDataException;
    public abstract void updateProduct(String id, Product product);
    public abstract void deleteProduct(String id);
    public abstract Collection<Product> getProducts();
}
