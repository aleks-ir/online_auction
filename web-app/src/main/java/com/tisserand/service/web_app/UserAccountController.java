package com.tisserand.service.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserAccountController {

    /**
     * Goto employees list page.
     *
     * @return view name
     */
    @GetMapping(value = "/user_account")
    public final String userAccount(Model model) {
        return "user_account";
    }

}
