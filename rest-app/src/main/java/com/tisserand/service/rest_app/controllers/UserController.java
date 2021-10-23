package com.tisserand.service.rest_app.controllers;

import com.tisserand.model.User;
import com.tisserand.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@CrossOrigin
@RestController
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
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        Optional<User> optional = userService.findById(id);
        return optional.isPresent()
                ? new ResponseEntity<>(optional.get(), HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(value = "/users", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<Integer> update(@RequestBody User user) {
        Integer id = userService.update(user);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }


    @GetMapping(value = "/users/count")
    public ResponseEntity<Integer> count() {
        return new ResponseEntity<>(userService.count(), HttpStatus.OK);
    }
}
