package com.tisserand.service.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisserand.model.Product;
import com.tisserand.model.User;
import com.tisserand.service.UserService;
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
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceRestTest {

    private static final String USERS_URL = "http://localhost:8088/users";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserService userService;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }


    @Test
    public void shouldFindAllUsers() throws Exception {
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(USERS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(
                                createUser(1),
                                createUser(2),
                                createUser(3))))
                );

        List<User> products = userService.findAll();

        mockServer.verify();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @Test
    public void shouldFindUserById() throws Exception {
        User user = createUser(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(USERS_URL + "/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(user))
                );

        Optional<User> userOptional = userService.findById(1);

        mockServer.verify();
        assertTrue(userOptional.isPresent());
        assertEquals(userOptional.get().getUserId(), user.getUserId());
        assertEquals(userOptional.get().getUserName(), user.getUserName());
        assertEquals(userOptional.get().getUserEmail(), user.getUserEmail());
        assertEquals(userOptional.get().getUserMoney(), user.getUserMoney());
    }


    @Test
    public void shouldUpdateUser() throws Exception {
        User user = createUser(1);

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(USERS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(USERS_URL + "/1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(user))
                );

        int result = userService.update(user);
        Optional<User> updatedProductOptional = userService.findById(1);


        mockServer.verify();
        assertTrue(1 == result);

        assertTrue(updatedProductOptional.isPresent());
        assertEquals(updatedProductOptional.get().getUserId(), user.getUserId());
        assertEquals(updatedProductOptional.get().getUserName(), user.getUserName());
        assertEquals(updatedProductOptional.get().getUserEmail(), user.getUserEmail());
        assertEquals(updatedProductOptional.get().getUserMoney(), user.getUserMoney());

    }

    private User createUser(int index) {
        User user = new User();
        user.setUserId(index);
        user.setUserName("u" + index);
        user.setUserEmail("u" + index + "@gm.com");
        user.setUserMoney(Float.valueOf(index));
        return user;
    }
}
