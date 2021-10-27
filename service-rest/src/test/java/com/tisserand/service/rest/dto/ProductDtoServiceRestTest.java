package com.tisserand.service.rest.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.dto.ProductDtoService;
import com.tisserand.service.dto.UserDtoService;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ProductDtoServiceRestTest {

    private static final String PRODUCTS_DTO_URL = "http://localhost:8088/products-dto";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ProductDtoService productDtoService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();


    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }
    @Test
    void findAllProductWithNameOwner() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_DTO_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                new ProductDto(),
                                new ProductDto())))
                );

        List<ProductDto> products = productDtoService.findAllProductWithNameOwner();

        mockServer.verify();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

}
