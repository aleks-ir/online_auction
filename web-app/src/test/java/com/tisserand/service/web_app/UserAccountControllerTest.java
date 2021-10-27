package com.tisserand.service.web_app;

import com.tisserand.model.Product;
import com.tisserand.model.User;
import com.tisserand.model.dto.UserDto;
import com.tisserand.service.ProductService;
import com.tisserand.service.UserService;
import com.tisserand.service.dto.ProductDtoService;
import com.tisserand.service.web_app.controllers.AuctionController;
import com.tisserand.service.web_app.controllers.UserAccountController;
import org.apache.commons.lang3.RandomStringUtils;
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
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@WebMvcTest(UserAccountController.class)
public class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Captor
    private ArgumentCaptor<User> captor;

    @Test
    public void shouldReturnUserAccountPage() throws Exception {
        User u = createUser(1, "Test name", "test@gmail.com", 54F);
        when(userService.findById(1)).thenReturn(java.util.Optional.of(u));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/user_account")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("user_account"))
                .andExpect(model().attribute("user", hasProperty("userName", is(u.getUserName()))))
                .andExpect(model().attribute("user", hasProperty("userEmail", is(u.getUserEmail()))))
                .andExpect(model().attribute("user", hasProperty("userMoney", is(u.getUserMoney()))));
    }

    @Test
    public void shouldOpenEditUserPageById() throws Exception {
        User u = createUser(1, "Test name", "test@gmail.com", 54F);
        when(userService.findById(any())).thenReturn(Optional.of(u));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/edit_user")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("edit_user"))
                .andExpect(model().attribute("user", hasProperty("userId", is(u.getUserId()))))
                .andExpect(model().attribute("user", hasProperty("userName", is(u.getUserName()))))
                .andExpect(model().attribute("user", hasProperty("userEmail", is(u.getUserEmail()))))
                .andExpect(model().attribute("user", hasProperty("userMoney", is(u.getUserMoney()))));
    }

    @Test
    public void shouldUpdateUserAfterEdit() throws Exception {
        Integer testId = 1;
        String testName = "Test name";
        String testEmail = "test@gmail.com";
        Float testMoney = 300F;
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/edit_user")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("userId", testId.toString())
                                .param("userName", testName)
                                .param("userEmail", testEmail)
                                .param("userMoney", testMoney.toString())
                ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/user_account"))
                .andExpect(redirectedUrl("/user_account"));

        verify(userService).update(captor.capture());

        User u = captor.getValue();
        assertEquals(testId, u.getUserId());
        assertEquals(testName, u.getUserName());
        assertEquals(testEmail, u.getUserEmail());
        assertEquals(testMoney, u.getUserMoney());
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
