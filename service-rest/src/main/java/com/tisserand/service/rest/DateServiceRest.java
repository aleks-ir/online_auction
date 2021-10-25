package com.tisserand.service.rest;

import com.tisserand.service.DateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class DateServiceRest implements DateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DateServiceRest.class);

    private final String url;
    private final RestTemplate restTemplate;

    public DateServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public String getDate() {
        LOGGER.debug("DateServiceRest: getDate()");
        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(url, String.class);
        return responseEntity.getBody();
    }

    @Override
    public Integer update(String date) {
        LOGGER.debug("DateServiceRest: update({})", date);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        HttpEntity<String> entity = new HttpEntity<>(date, headers);
        ResponseEntity<Integer> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Integer.class);
        return result.getBody();
    }
}
