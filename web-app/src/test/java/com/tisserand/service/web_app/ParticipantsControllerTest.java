package com.tisserand.service.web_app;

import com.tisserand.model.dto.UserDto;
import com.tisserand.service.dto.UserDtoService;
import com.tisserand.service.web_app.controllers.ParticipantsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;


@WebMvcTest(ParticipantsController.class)
public class ParticipantsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDtoService userDtoService;

    @Test
    public void shouldReturnParticipantsPage() throws Exception {
        UserDto u1 = createUser(1, "Test name 1", "test1@gmail.com", 54F, 20F);
        UserDto u2 = createUser(1, "Test name 2", "test2@gmail.com", 12F, 23F);
        when(userDtoService.findAllUsersWithValueOfAllProducts()).thenReturn(Arrays.asList(u1, u2));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/participants")
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("participants"))
                .andExpect(model()
                        .attribute("users", hasItem(
                                allOf(
                                        hasProperty("userId", is(u1.getUserId())),
                                        hasProperty("userName", is(u1.getUserName())),
                                        hasProperty("userEmail", is(u1.getUserEmail())),
                                        hasProperty("userMoney", is(u1.getUserMoney()))
                                )
                        )))
                .andExpect(model().attribute("users", hasItem(
                        allOf(
                                hasProperty("userId", is(u2.getUserId())),
                                hasProperty("userName", is(u2.getUserName())),
                                hasProperty("userEmail", is(u2.getUserEmail())),
                                hasProperty("userMoney", is(u2.getUserMoney()))
                        )
                )));
    }


    private UserDto createUser(Integer userId, String userName, String userEmail, Float userMoney, Float valueOfAllProducts) {
        UserDto user = new UserDto();
        user.setUserId(userId);
        user.setUserName(userName);
        user.setUserEmail(userEmail);
        user.setUserMoney(userMoney);
        user.setValueOfAllProducts(valueOfAllProducts);
        return user;
    }

}
