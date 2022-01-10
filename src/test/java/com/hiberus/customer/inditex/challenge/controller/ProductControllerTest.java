package com.hiberus.customer.inditex.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiberus.customer.inditex.challenge.model.Product;
import com.hiberus.customer.inditex.challenge.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;
    private Product product;
    @InjectMocks
    private ProductController productController;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        product = product("productId", "productName", "productDescription");
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @AfterEach
    void tearDown() {
        product = null;
    }

    @Test
    public void createProductWithValidDataShouldReturnTheCreatedProduct() throws Exception{
        when(productService.createProduct(any())).thenReturn(product);
        mockMvc.perform(post("/api/v1/product").
                        contentType(MediaType.APPLICATION_JSON).
                        content(asJsonString(product))).
                andExpect(status().isOk());

        verify(productService,times(1)).createProduct(any());
    }
  @Test
    public void updateAProductShouldReturnTheUpdatedProduct() throws Exception {
        Product updatedProduct = product("updatedId", "updatedName", "updatedDescription");
        when(productService.updateProduct(any(), any())).thenReturn(updatedProduct);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/productId").
                        contentType(MediaType.APPLICATION_JSON).
                        content(asJsonString(updatedProduct))).
                andExpect(status().isOk()).
                andDo(MockMvcResultHandlers.print());
        verify(productService).updateProduct(any(), any());
        verify(productService,times(1)).updateProduct(any(), any());
    }

  @Test
    public void searchProductThroughAllTheParameters () throws Exception {
        when(productService.searchProducts(product)).thenReturn(any());
        mockMvc.perform(post("/api/v1/product/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
      verify(productService).searchProducts(any());
      verify(productService,times(1)).searchProducts(any());
    }

   @Test
    public void deleteProductWithTheProductIdShouldReturnTheDeletedProduct() throws Exception {
        when(productService.deleteProduct(product.getId())).thenReturn(product);
        mockMvc.perform(delete("/api/v1/product/productId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("productId"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
       verify(productService).deleteProduct(any());
       verify(productService,times(1)).deleteProduct(any());
    }


    private Product product(String id, String name, String description) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }

    private static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}