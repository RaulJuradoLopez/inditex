package com.hiberus.customer.inditex.challenge.controller;

import com.hiberus.customer.inditex.challenge.async.ProductCreationEvent;
import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.exception.ResourceNotFoundException;
import com.hiberus.customer.inditex.challenge.model.Product;
import com.hiberus.customer.inditex.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    ApplicationEventPublisher publisher;

    @GetMapping("/products")
    public int getAvailableProducts() {
        return productService.getAvailableProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity < Product > getProductById(@PathVariable(value = "id") String productId) throws ResourceNotFoundException {
        //TODO
        return null;
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        //TODO
        return null;
    }

    /**
     * Asynchronous method to create a product.
     * @param product
     * @return
     */
    @PostMapping(value = "/publish")
    public String publishProduct(@RequestBody Product product) {
        ProductCreationEvent event = new ProductCreationEvent(this, product);
        publisher.publishEvent(event);
        return "Published the product :" + product.getId();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") String productId,
                                                 @RequestBody Product productDetails) throws ResourceNotFoundException {
        //TODO
        return null;
    }

    @DeleteMapping("/products/{id}")
    public Map< String, Boolean > deleteProduct(@PathVariable(value = "id") String productId)
                                                throws ResourceNotFoundException, InvalidDataException {
        if (ObjectUtils.isEmpty(productId)){
            throw new InvalidDataException("Can not delete a Product without a valid key");
        }
        if (productService.deleteProduct(productId) == null){
            throw new ResourceNotFoundException("Product not found for this id :: "+ productId);
        }

        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
