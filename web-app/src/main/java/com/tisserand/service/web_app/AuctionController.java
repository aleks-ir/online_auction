package com.tisserand.service.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuctionController {

    @GetMapping(value = "/auction")
    public final String auction(Model model) {
        return "auction";
    }


}
