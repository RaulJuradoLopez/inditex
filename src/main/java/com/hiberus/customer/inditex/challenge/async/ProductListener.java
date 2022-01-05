package com.hiberus.customer.inditex.challenge.async;

import com.hiberus.customer.inditex.challenge.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@Configuration
public class ProductListener {

    @Autowired
    private ProductService productService;

    @Async
    @EventListener
    public void listener(ProductCreationEvent event) throws Exception {
        Thread.sleep(2000);
        //TODO : Call productService and create the product
        productService.createProduct(event.getProduct());
    }
}
