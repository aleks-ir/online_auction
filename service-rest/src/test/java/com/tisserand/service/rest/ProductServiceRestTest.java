package com.tisserand.service.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisserand.model.Product;
import com.tisserand.service.ProductService;
import com.tisserand.service.rest.config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProductServiceRestTest {

    private static final String PRODUCTS_URL = "http://localhost:8088/products";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductService productService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void shouldFindAllProducts() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                createProduct(1),
                                createProduct(2),
                                createProduct(3))))
                );

        List<Product> products = productService.findAll();

        mockServer.verify();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    public void shouldCreateProduct() throws Exception {

        Product product = createProduct(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        Integer id = productService.create(product);

        mockServer.verify();
        assertNotNull(id);
    }

    @Test
    public void shouldFindProductById() throws Exception {
        Product product = createProduct(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_URL + "/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(product))
                );

        Optional<Product> productOptional = productService.findById(1);

        mockServer.verify();
        assertTrue(productOptional.isPresent());
        assertEquals(productOptional.get().getProductId(), product.getProductId());
        assertEquals(productOptional.get().getProductName(), product.getProductName());
        assertEquals(productOptional.get().getProductDate(), product.getProductDate());
        assertEquals(productOptional.get().getProductPrice(), product.getProductPrice());
        assertEquals(productOptional.get().getSalesmanId(), product.getSalesmanId());
    }


    @Test
    public void shouldUpdateProduct() throws Exception {
        Product product = createProduct(1);
        product.setCustomerId(2);
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_URL + "/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(product))
                );

        int result = productService.updatePriceAndCustomer(product);
        Optional<Product> updatedProductOptional = productService.findById(1);


        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedProductOptional.isPresent());
        assertEquals(updatedProductOptional.get().getProductId(), product.getProductId());
        assertEquals(updatedProductOptional.get().getProductName(), product.getProductName());
        assertEquals(updatedProductOptional.get().getProductDate(), product.getProductDate());
        assertEquals(updatedProductOptional.get().getProductPrice(), product.getProductPrice());
        assertEquals(updatedProductOptional.get().getSalesmanId(), product.getSalesmanId());
        assertEquals(updatedProductOptional.get().getCustomerId(), product.getCustomerId());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );
        int result = productService.delete(id);

        mockServer.verify();
        assertTrue(1 == result);
    }

    private Product createProduct(int index) {
        Product product = new Product();
        product.setProductId(index);
        product.setProductName("p" + index);
        product.setProductPrice(Float.valueOf(index));
        product.setProductDate("2000-01-01");
        product.setSalesmanId(index);
        return product;
    }

}
