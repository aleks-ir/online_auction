package com.tisserand.service.web_app.controllers;

import com.tisserand.model.User;
import com.tisserand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserAccountController {

    private final UserService userService;

    private Integer testUserId = 1;

    @Autowired
    public UserAccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/user_account")
    public final String currentUser(Model model) {
        model.addAttribute("user", userService.findById(testUserId).get());
        return "user_account";
    }

    @GetMapping(value = "/edit_user")
    public final String gotoEditUserPage(Model model) {
        model.addAttribute("user", userService.findById(testUserId).get());
        return "edit_user";
    }


    @PostMapping(value = "/edit_user")
    public String updateUser(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "edit_user";
        }
        userService.update(user);
        return "redirect:/user_account";
    }
}
