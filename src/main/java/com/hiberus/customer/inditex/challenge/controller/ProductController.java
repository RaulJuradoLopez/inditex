package com.hiberus.customer.inditex.challenge.controller;

import com.hiberus.customer.inditex.challenge.exception.ResourceNotFoundException;
import com.hiberus.customer.inditex.challenge.model.Product;
import com.hiberus.customer.inditex.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

        @Autowired
        private ProductService productService;

        @GetMapping("/products")
        public List<Product> getAllProducts() {
            //TODO
            return null;
        }

        @GetMapping("/products/{id}")
        public ResponseEntity < Product > getProductById(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
            //TODO
            return null;
        }

        @PostMapping("/products")
        public Product createProduct(@RequestBody Product product) {
            //TODO
            return null;
        }

        @PutMapping("/products/{id}")
        public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId,
                                                     @RequestBody Product productDetails) throws ResourceNotFoundException {
            //TODO
            return null;
        }

        @DeleteMapping("/products/{id}")
        public Map< String, Boolean > deleteProduct(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
            //TODO
            return null;
        }
    }
