package com.hiberus.customer.inditex.challenge.service;

import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.model.Product;

import java.util.List;

public interface ProductService {
    void createProduct(Product product) throws InvalidDataException;
    void updateProduct(String id, Product product) throws InvalidDataException;
    Product deleteProduct(String id);
    int getAvailableProducts();
    List<Product> searchProducts(Product searchCriteria);
}
