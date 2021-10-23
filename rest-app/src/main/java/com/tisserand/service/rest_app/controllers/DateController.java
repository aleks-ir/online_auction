package com.tisserand.service.rest_app.controllers;

import com.tisserand.service.DateService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class DateController {
    private DateService dateService;

    public DateController(DateService dateService) {
        this.dateService = dateService;
    }


    @GetMapping(value = "/date")
    public ResponseEntity<String> getDate() {
        String date = dateService.getDate();
        return new ResponseEntity<>(date, HttpStatus.OK);
    }

    @PutMapping(value = "/date")
    public ResponseEntity<Integer> update(@RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
        Integer id = dateService.update(date);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
