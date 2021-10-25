package com.tisserand.service.web_app.controllers;

import com.tisserand.service.dto.UserDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ParticipantsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParticipantsController.class);

    private final UserDtoService userDtoService;

    @Autowired
    public ParticipantsController(UserDtoService userDtoService) {
        this.userDtoService = userDtoService;
    }

    @GetMapping(value = "/participants")
    public final String participants(Model model) {
        LOGGER.debug("ParticipantsController: participants()");
        model.addAttribute("users", userDtoService.findAllUsersWithValueOfAllProducts());
        return "participants";
    }
}
