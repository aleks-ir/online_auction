package com.tisserand.service.rest;

import com.tisserand.service.DateService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class DateServiceRestTest {

    public static final String DATE_URL = "http://localhost:8088/date";

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DateService dateService;

    private MockRestServiceServer mockServer;



    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void shouldGetDate() throws Exception {
        String testDate = "2000-01-01";
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DATE_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(testDate)
                );

        String date = dateService.getDate();

        mockServer.verify();
        assertNotNull(date);
        assertEquals(testDate, date);
    }

    @Test
    public void shouldUpdateDate() throws Exception {
        String testDate = "2000-01-01";
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DATE_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("1")
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DATE_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(testDate)
                );

        // when
        int result = dateService.update(testDate);
        String updatedDate = dateService.getDate();

        // then
        mockServer.verify();
        assertTrue(1 == result);

        assertEquals(updatedDate, testDate);
    }
}
