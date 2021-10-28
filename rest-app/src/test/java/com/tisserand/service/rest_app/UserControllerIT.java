package com.tisserand.service.rest_app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisserand.model.User;
import com.tisserand.service.rest_app.controllers.UserController;
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
public class UserControllerIT {

    public static final String USERS_ENDPOINT = "/users";

    @Autowired
    private UserController userController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockUserService userService = new MockUserService();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindAllUsers() throws Exception {
        List<User> users = userService.findAll();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    public void shouldFindById() throws Exception {

        List<User> user = userService.findAll();
        Assertions.assertNotNull(user);
        assertTrue(user.size() > 0);

        Integer userId = user.get(0).getUserId();
        User expUser = userService.findById(userId).get();
        Assertions.assertEquals(userId, expUser.getUserId());
        Assertions.assertEquals(user.get(0).getUserId(), expUser.getUserId());
        Assertions.assertEquals(user.get(0).getUserName(), expUser.getUserName());
        Assertions.assertEquals(user.get(0).getUserEmail(), expUser.getUserEmail());
        Assertions.assertEquals(user.get(0).getUserMoney(), expUser.getUserMoney());
        Assertions.assertEquals(user.get(0), expUser);
    }

    @Test
    public void shouldReturnNotFoundOnMissedUser() throws Exception {

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get(
                                USERS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                .andReturn().getResponse();
        assertNotNull(response);
    }


    @Test
    public void shouldUpdateUser() throws Exception {
        String testName = "Some name";
        String testEmail = "some@gm.com";
        Float testMoney = 200F;


        List<User> products = userService.findAll();
        Assertions.assertNotNull(products);
        assertTrue(products.size() > 0);

        User user = products.get(0);
        user.setUserName(testName);
        user.setUserEmail(testEmail);
        user.setUserMoney(testMoney);
        userService.update(user);

        Optional<User> realUser = userService.findById(user.getUserId());
        Assertions.assertEquals(testName, realUser.get().getUserName());
        Assertions.assertEquals(testEmail, realUser.get().getUserEmail());
        Assertions.assertEquals(testMoney, realUser.get().getUserMoney());
    }



    /////////////////////////////////////////////////////////////////////////////////

    private class MockUserService {

        public List<User> findAll() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(USERS_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<>() {});
        }

        public Optional<User> findById(Integer userId) throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(USERS_ENDPOINT + "/" + userId)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), User.class));
        }

        public Integer update(User user) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(put(USERS_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(user))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }


    }
}
