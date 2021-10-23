package com.tisserand.service.web_app.controllers;

import com.tisserand.service.dto.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParticipantsController {

    private final UserDtoService userDtoService;

    @Autowired
    public ParticipantsController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping(value = "/participants")
    public final String participants(Model model) {
        model.addAttribute("users", userDtoService.findAllUsersWithValueOfAllProducts());
        return "participants";
    }
}
