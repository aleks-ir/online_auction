package com.tisserand.service.rest_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisserand.model.Product;
import com.tisserand.service.rest_app.controllers.ProductController;
import com.tisserand.service.rest_app.exception.CustomExceptionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class ProductControllerIT {

    public static final String PRODUCTS_ENDPOINT = "/products";

    @Autowired
    private ProductController productController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockProductService productService = new MockProductService();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAllProducts() throws Exception {

        List<Product> products = productService.findAll();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    public void shouldFindById() throws Exception {

        List<Product> product = productService.findAll();
        Assertions.assertNotNull(product);
        assertTrue(product.size() > 0);

        Integer productId = product.get(0).getProductId();
        Product expProduct = productService.findById(productId).get();
        Assertions.assertEquals(productId, expProduct.getProductId());
        Assertions.assertEquals(product.get(0).getProductName(), expProduct.getProductName());
        Assertions.assertEquals(product.get(0).getProductDate(), expProduct.getProductDate());
        Assertions.assertEquals(product.get(0).getProductPrice(), expProduct.getProductPrice());
        Assertions.assertEquals(product.get(0).getSalesmanId(), expProduct.getSalesmanId());
        Assertions.assertEquals(product.get(0), expProduct);
    }

    @Test
    public void shouldReturnNotFoundOnMissedProduct() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(
                                PRODUCTS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn().getResponse();
        assertNotNull(response);
    }

    @Test
    public void shouldCreateProduct() throws Exception {

        Integer countBefore = productService.count();

        productService.create(createProduct(1));

        // verify database size
        Integer countAfter = productService.count();
        Assertions.assertEquals(countBefore + 1, countAfter);
    }


    @Test
    public void shouldUpdateProduct() throws Exception {
        Float testPrice = 25F;
        Integer testCustomerId = 2;

        List<Product> products = productService.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        Product product = products.get(0);
        product.setProductPrice(testPrice);
        product.setCustomerId(testCustomerId);
        productService.update(product);

        Optional<Product> realDepartment = productService.findById(product.getProductId());
        Assertions.assertEquals(testCustomerId, realDepartment.get().getCustomerId());
        Assertions.assertEquals(testPrice, realDepartment.get().getProductPrice());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {


        Integer id = 1;
        productService.create(createProduct(id));
        Integer countBefore = productService.count();

        productService.delete(id);

        Integer countAfter = productService.count();
        Assertions.assertEquals(countBefore - 1, countAfter);
    }

    @Test
    public void shouldReturnNotFoundOnDeleteMissedProduct() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete(
                                PRODUCTS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn().getResponse();
        assertNotNull(response);
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

    /////////////////////////////////////////////////////////////////////////////////

    private class MockProductService {

        public List<Product> findAll() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(PRODUCTS_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        }

        public Optional<Product> findById(Integer productId) throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(PRODUCTS_ENDPOINT + "/" + productId)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Product.class));
        }

        public Integer create(Product product) throws Exception {
            String json = objectMapper.writeValueAsString(product);
            MockHttpServletResponse response =
                    mockMvc.perform(post(PRODUCTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(json)
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Integer update(Product product) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(put(PRODUCTS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(product))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Integer delete(Integer productId) throws Exception {
            MockHttpServletResponse response = mockMvc.perform(
                            MockMvcRequestBuilders.delete(new StringBuilder(PRODUCTS_ENDPOINT).append("/")
                                            .append(productId).toString())
                                    .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();

            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }

        public Integer count() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(PRODUCTS_ENDPOINT + "/count")
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }
    }

}
