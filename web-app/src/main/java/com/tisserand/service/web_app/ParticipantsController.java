package com.tisserand.service.web_app;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class ParticipantsController {
    @GetMapping(value = "/participants")
    public final String participants(Model model) {
        return "participants";
    }

}
