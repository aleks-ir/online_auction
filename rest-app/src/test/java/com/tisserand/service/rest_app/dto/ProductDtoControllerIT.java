package com.tisserand.service.rest_app.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisserand.model.Product;
import com.tisserand.model.User;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.rest_app.controllers.dto.ProductDtoController;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class ProductDtoControllerIT {

    public static final String PRODUCTS_DTO_ENDPOINT = "/products-dto";

    @Autowired
    private ProductDtoController productDtoController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockProductDtoService productDtoService = new MockProductDtoService();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(productDtoController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAllProducts() throws Exception {

        List<ProductDto> products = productDtoService.findAllProductWithNameOwnerByDate();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }


    /////////////////////////////////////////////////////////////////////////////////

    private class MockProductDtoService {

        public List<ProductDto> findAllProductWithNameOwnerByDate() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(PRODUCTS_DTO_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        }

    }
}
