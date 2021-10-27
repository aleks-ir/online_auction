package com.tisserand.service.web_app;

import com.tisserand.model.Product;
import com.tisserand.model.User;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.DateService;
import com.tisserand.service.ProductService;
import com.tisserand.service.UserService;
import com.tisserand.service.dto.ProductDtoService;
import com.tisserand.service.web_app.controllers.AuctionController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(AuctionController.class)
public class AuctionControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductDtoService productDtoService;

    @MockBean
    private UserService userService;

    @MockBean
    private DateService dateService;


    @Captor
    private ArgumentCaptor<Product> captor;

    @Captor
    private ArgumentCaptor<String> captorStr;

    @Test
    public void shouldReturnProductsPage() throws Exception {
        String d1 = "2000-01-01";
        ProductDto p1 = createProductDto(1, "Test name", 1F, "2001-01-01", "Some name");
        ProductDto p2 = createProductDto(2, "Test name", 3.1F, "2001-01-01", "Some name");
        User u1 = createUser(1, "Test name", "test@gmail.com", 50F);
        when(userService.findById(1)).thenReturn(Optional.of(u1));
        when(productDtoService.findAllProductWithNameOwner()).thenReturn(Arrays.asList(p1, p2));
        when(dateService.getDate()).thenReturn(d1);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/auction")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("auction"))
                .andExpect(model().attribute("user", hasProperty("userName", is(u1.getUserName()))))
                .andExpect(model()
                        .attribute("products", hasItem(
                                allOf(
                                        hasProperty("productId", is(p1.getProductId())),
                                        hasProperty("productName", is(p1.getProductName())),
                                        hasProperty("productPrice", is(p1.getProductPrice())),
                                        hasProperty("productDate", is(p1.getProductDate())),
                                        hasProperty("nameOwner", is(p1.getNameOwner()))
                                )
                        )))
                .andExpect(model().attribute("products", hasItem(
                        allOf(
                                hasProperty("productId", is(p2.getProductId())),
                                hasProperty("productName", is(p2.getProductName())),
                                hasProperty("productPrice", is(p2.getProductPrice())),
                                hasProperty("productDate", is(p2.getProductDate())),
                                hasProperty("nameOwner", is(p2.getNameOwner()))
                        )
                ))).andExpect(model().attribute("date", is(d1)
                ));
    }

    @Test
    public void shouldOpenNewProductPage() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/add_product")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("add_product"))
                .andExpect(model().attribute("product", new Product()));
        verifyNoMoreInteractions(productDtoService, productService);
    }

    @Test
    public void shouldAddNewProduct() throws Exception {
        String testName = "Some name";
        Float testPrice = 20F;
        String testDate = "2000-01-01";
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/add_product")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("productName", testName)
                                .param("productPrice", testPrice.toString())
                                .param("productDate", testDate)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/auction"))
                .andExpect(redirectedUrl("/auction"));

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/add_product")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("productName", testName)
                ).andExpect(status().isOk())
                .andExpect(view().name("add_product"));

        verify(productService).create(captor.capture());

        Product d = captor.getValue();
        assertEquals(testName, d.getProductName());
        assertEquals(testPrice, d.getProductPrice());
        assertEquals(testDate, d.getProductDate());
    }

    @Test
    public void shouldDeleteProduct() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/product/3/delete")
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/auction"))
                .andExpect(redirectedUrl("/auction"));

        verify(productService).delete(3);
    }


    @Test
    public void shouldUpdateProduct() throws Exception {

        Integer testProductId = 1;
        Float testPrice = 20F;
        Integer testCustomerId = 2;
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/product/update")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("productId", testProductId.toString())
                                .param("price", testPrice.toString())
                                .param("customerId", testCustomerId.toString())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/auction"))
                .andExpect(redirectedUrl("/auction"));

        verify(productService).updatePriceAndCustomer(captor.capture());

        Product p = captor.getValue();
        assertEquals(testProductId, p.getProductId());
        assertEquals(testPrice, p.getProductPrice());
        assertEquals(testCustomerId, p.getCustomerId());
    }

    @Test
    public void shouldUpdateDate() throws Exception {

        String testDate = "2000-01-01";
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/date")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("date", testDate)
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/auction"))
                .andExpect(redirectedUrl("/auction"));

        verify(dateService).update(captorStr.capture());

        String d = captorStr.getValue();
        assertEquals(testDate, d);
    }

//    @Test
//    public void shouldUpdateProduct() throws Exception {
//        mockServer.expect(ExpectedCount.once(), requestTo(new URI(PRODUCTS_URL)))
//                .andExpect(method(HttpMethod.PUT))
//                .andRespond(withStatus(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .body("1")
//                );
//        String testPrice = "20";
//        String testSalesmenId = "2";
//        mockMvc.perform(
//                        MockMvcRequestBuilders.post("/product/update")
//                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                                .param("productId", "1")
//                                .param("price", testPrice)
//                                .param("customerId", testSalesmenId)
//                ).andExpect(status().isFound())
//                .andExpect(view().name("redirect:/auction"))
//                .andExpect(redirectedUrl("/auction"));
//
//        mockServer.verify();
//    }


    private ProductDto createProductDto(Integer productId, String productName, Float productPrice, String productDate, String nameOwner) {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(productId);
        productDto.setProductName(productName);
        productDto.setProductPrice(productPrice);
        productDto.setProductDate(productDate);
        productDto.setNameOwner(nameOwner);
        return productDto;
    }

    private User createUser(Integer userId, String userName, String userEmail, Float userMoney) {
        User user = new User();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserMoney(userMoney);
        return user;
    }

}
