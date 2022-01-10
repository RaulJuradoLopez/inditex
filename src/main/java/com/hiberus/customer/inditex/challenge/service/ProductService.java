package com.hiberus.customer.inditex.challenge.service;

import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.model.Product;

import java.util.Collection;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product) throws InvalidDataException;
    Product updateProduct(String id, Product product) throws InvalidDataException;
    Product deleteProduct(String id);
    int getAvailableProducts();
    Collection<Product> getAllProducts();
    List<Product> searchProducts(Product searchCriteria);
    //For testing purposes
    void flushElements();
}
