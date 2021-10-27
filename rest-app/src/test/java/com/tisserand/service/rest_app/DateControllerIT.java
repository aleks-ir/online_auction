package com.tisserand.service.rest_app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tisserand.service.rest_app.controllers.DateController;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Transactional
public class DateControllerIT {
    public static final String DATE_ENDPOINT = "/date";

    @Autowired
    private DateController dateController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    @Autowired
    protected ObjectMapper objectMapper;

    protected MockMvc mockMvc;

    protected MockDateService dateService = new MockDateService();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(dateController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldFindDate() throws Exception {
        String date = dateService.getDate();
        assertNotNull(date);
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        String testDate = "2000-01-01";
        Integer result = dateService.update(testDate);

        String realDate = dateService.getDate();
        Assertions.assertEquals(testDate, realDate);
    }



    /////////////////////////////////////////////////////////////////////////////////

    private class MockDateService {

        public String getDate() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(DATE_ENDPOINT)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), String.class);
        }


        public Integer update(String date) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(put(DATE_ENDPOINT)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(date))
                                    .accept(MediaType.APPLICATION_JSON)
                            ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Integer.class);
        }


    }
}
