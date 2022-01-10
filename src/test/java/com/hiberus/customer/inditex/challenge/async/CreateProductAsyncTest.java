package com.hiberus.customer.inditex.challenge.async;

import com.hiberus.customer.inditex.challenge.model.Product;
import com.hiberus.customer.inditex.challenge.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class CreateProductAsyncTest {

    private ApplicationEventPublisher publisher;
    private ProductService productService;

    @Autowired
    CreateProductAsyncTest(ApplicationEventPublisher publisher, ProductService productService) {
        this.publisher = publisher;
        this.productService = productService;
    }

    @BeforeEach
    void setup() {
        productService.flushElements();
    }

    @Test
    public void whenProductEventIsPublishedANewProductIsCreated() throws InterruptedException {

        //GIVEN an event raised from our controller async method
        ProductCreationEvent event = new ProductCreationEvent(this, product("someId", "someName", "someDescription"));

        //WHEN the event is published
        publisher.publishEvent(event);
        //We need to wait until async listener calls the service (we have 1 sec to wait)
        Thread.sleep(2000);

        //THEN a new product is created
        assertThat(productService.getAllProducts()).isNotNull();
        assertEquals(1, productService.getAvailableProducts());

    }

    private Product product(String id, String name, String description) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}