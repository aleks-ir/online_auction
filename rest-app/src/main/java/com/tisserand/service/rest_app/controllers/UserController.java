package com.tisserand.service.rest_app.controllers;

import com.tisserand.model.User;
import com.tisserand.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.Collection;
import java.util.Optional;

@CrossOrigin
@RestController
@Validated
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users")
    public Collection<User> users() {
        return userService.findAll();
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> findById(@PathVariable @Positive(message = "Path variable should be positive") Integer id) {
        Optional<User> optional = userService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> update(@Valid @RequestBody User user) {
        Integer id = userService.update(user);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @GetMapping(value = "/users/count")
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(userService.count(), HttpStatus.OK);
    }
}
