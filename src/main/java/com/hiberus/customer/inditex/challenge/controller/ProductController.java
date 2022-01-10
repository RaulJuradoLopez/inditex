package com.hiberus.customer.inditex.challenge.controller;

import com.hiberus.customer.inditex.challenge.async.ProductCreationEvent;
import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.exception.ResourceNotFoundException;
import com.hiberus.customer.inditex.challenge.model.Product;
import com.hiberus.customer.inditex.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ApplicationEventPublisher publisher;

    @PostMapping(value = "/product", consumes = "application/json")
    public ResponseEntity <Product> createProduct(@RequestBody Product product) throws InvalidDataException {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PostMapping(value = "/product/publish")
    public String publishProduct(@RequestBody Product product) {
        ProductCreationEvent event = new ProductCreationEvent(this, product);
        publisher.publishEvent(event);
        return "Published the product :" + product.getId();
    }

    @PutMapping("/product/{id}")
    public ResponseEntity <Product> updateProduct(@PathVariable(value = "id") String productId,
                                                  @RequestBody Product productDetails) throws InvalidDataException {
        Product updatedProduct = productService.updateProduct(productId, productDetails);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable(value = "id") String productId)
            throws ResourceNotFoundException, InvalidDataException {
        if (ObjectUtils.isEmpty(productId)){
            throw new InvalidDataException("Can not delete a Product without a valid key");
        }
        Product deletedProduct = productService.deleteProduct(productId);
        if (deletedProduct == null){
            throw new ResourceNotFoundException("Product not found for this id :: "+ productId);
        }

        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

    @GetMapping("/products")
    public int getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    @GetMapping("/products/all")
    public Collection<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/product/search")
    public List<Product> getFilteredProducts(@RequestBody Product searchCriteriaProduct) {
        return productService.searchProducts(searchCriteriaProduct);
    }
}
