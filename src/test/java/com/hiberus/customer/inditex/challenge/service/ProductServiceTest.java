package com.hiberus.customer.inditex.challenge.service;

import com.hiberus.customer.inditex.challenge.exception.InvalidDataException;
import com.hiberus.customer.inditex.challenge.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private final String PRODUCT_ID = "1";
    private final String ANOTHER_PRODUCT_ID = "another id";

    private final String PRODUCT_NAME = "name";
    private final String ANOTHER_PRODUCT_NAME = "another name";

    private final String PRODUCT_DESCRIPTION = "description";
    private final String ANOTHER_PRODUCT_DESCRIPTION = "another description";


    @BeforeEach
    void setup() {
        this.productService.flushElements();
    }

    @Test
    void whenProductIsInsertedWithValidKeyCanBeRetrievedById() throws InvalidDataException {
        //GIVEN a valid product
        Product product = product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION);

        //WHEN is stored
        this.productService.createProduct(product);

        //THEN can be retrieved by id
        List<Product> retrievedProducts = this.productService.searchProducts(product);
        assertThat(retrievedProducts).isNotNull();
        assertEquals(1, retrievedProducts.size());
        assertEquals(PRODUCT_ID, retrievedProducts.get(0).getId());
    }

    @Test
    void whenProductIsInsertedWithAnInvalidKeyThenIsInsertedWithAGeneratedKey() throws InvalidDataException {
        //GIVEN a product with an invalid key
        Product product = product(null, PRODUCT_NAME, PRODUCT_DESCRIPTION);

        //WHEN is stored
        this.productService.createProduct(product);

        //THEN can be retrieved with a <generated> valid id
        List<Product> retrievedProducts = this.productService.searchProducts(product);
        assertThat(retrievedProducts).isNotNull();
        assertEquals(1, retrievedProducts.size());
        assertNotEquals( null, retrievedProducts.get(0).getId());
    }

    @Test
    public void whenProductIsInsertedWithAnInvalidNameThenControlledExceptionIsLaunched() {
        //GIVEN a product with an invalid name
        Product product = product(PRODUCT_ID, null, PRODUCT_DESCRIPTION);

        //WHEN is stored
        //THEN a controlled exception is launched
        assertThrows(InvalidDataException.class,
                () -> this.productService.createProduct(product),
                "Product name is mandatory");

    }

    @Test
    public void whenProductIsInsertedAllThePropertiesCanBeModified() throws InvalidDataException {
        //GIVEN a stored product with valid values
        Product product = product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION);
        this.productService.createProduct(product);

        //WHEN is updated
        Product updatedProduct = product(ANOTHER_PRODUCT_ID, ANOTHER_PRODUCT_NAME, ANOTHER_PRODUCT_DESCRIPTION);
        this.productService.updateProduct(PRODUCT_ID, updatedProduct);

        //THEN old product doesn't exist and the updated product can be retrieved with the updated properties
        List<Product> productsWithOldData = this.productService.searchProducts(product);
        assertEquals(0, productsWithOldData.size());
        List<Product> productsWithUpdatedData = this.productService.searchProducts(updatedProduct);
        assertEquals(1, productsWithUpdatedData.size());
        assertEquals( ANOTHER_PRODUCT_ID, productsWithUpdatedData.get(0).getId());
    }

    @Test
    void whenProductIsInsertedCanBeDeletedByKey() throws InvalidDataException {
        //GIVEN a product with valid values
        Product product = product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION);

        //WHEN is stored
        this.productService.createProduct(product);
        assertEquals(1,  this.productService.getAvailableProducts());

        //THEN can be deleted by id
        this.productService.deleteProduct(PRODUCT_ID);
        assertEquals(0, this.productService.getAvailableProducts());

    }

    @Test
    void whenSomeProductsAreInsertedCanBeSearchedByAllTheProperties() throws InvalidDataException {
        //GIVEN a couple of valid products
        Product product = product(PRODUCT_ID, PRODUCT_NAME, PRODUCT_DESCRIPTION);
        Product anotherProduct = product(ANOTHER_PRODUCT_ID, ANOTHER_PRODUCT_NAME, ANOTHER_PRODUCT_DESCRIPTION);

        //WHEN are stored
        this.productService.createProduct(product);
        this.productService.createProduct(anotherProduct);
        assertEquals(2,  this.productService.getAvailableProducts());

        //THEN can be retrieved by id, name and description
        List<Product> retrievedProductsByProductId = this.productService.searchProducts(product(PRODUCT_ID, "Non-existing name", "Non-existing desc"));
        assertEquals(1, retrievedProductsByProductId.size());

        List<Product> retrievedProductsByProductName = this.productService.searchProducts(product("Non-existing id", PRODUCT_NAME, "Non-existing desc"));
        assertEquals(1, retrievedProductsByProductName.size());

        List<Product> retrievedProductsByProductDescription = this.productService.searchProducts(product("Non-existing id", "Non-existing name", PRODUCT_DESCRIPTION));
        assertEquals(1, retrievedProductsByProductDescription.size());

        List<Product> retrievedProductsByProductIdAndProductName = this.productService.searchProducts(product(PRODUCT_ID, ANOTHER_PRODUCT_NAME, "Non-existing desc"));
        assertEquals(2, retrievedProductsByProductIdAndProductName.size());
    }

    private Product product(String id, String name, String description) {
        return Product.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}
