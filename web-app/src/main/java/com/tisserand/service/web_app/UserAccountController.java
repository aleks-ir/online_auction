package com.tisserand.service.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping(value = "/user_account")
    public final String gotoEditEmployeePage(Model model) {
        return "redirect:/edit_user";
    }

    @PostMapping(value = "/edit_user")
    public String addProduct(User user) {
        return "redirect:/user_account";
    }

}
