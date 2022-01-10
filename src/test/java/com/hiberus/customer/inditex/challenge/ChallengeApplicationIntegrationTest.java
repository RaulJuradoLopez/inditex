package com.hiberus.customer.inditex.challenge;


import com.hiberus.customer.inditex.challenge.model.Product;
import com.hiberus.customer.inditex.challenge.service.ProductService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ChallengeApplicationIntegrationTest {

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private ProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();
        productService.flushElements();
    }

    @Test
    void whenProductIsCreatedThenIsAvailableFromRestAPI() throws Exception {
        this.productService.createProduct(Product.builder()
                            .id("1")
                            .name("productName")
                            .description("productDescription")
                            .build());

        this.mockMvc.perform(
                        get("/api/v1/products"))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString("1")));
    }

}

