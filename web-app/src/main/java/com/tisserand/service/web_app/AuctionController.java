package com.tisserand.service.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuctionController {

    @GetMapping(value = "/auction")
    public final String auction(Model model) {
        return "auction";
    }

    @GetMapping(value = "/auction")
    public final String gotoAddProductPage(Model model) {
        return "add_product";
    }

    @PostMapping(value = "/add_product")
    public String addProduct(Product product) {
        return "redirect:/auction";
    }

    @GetMapping(value = "/auction/{id}/delete")
    public final String deleteProductById(@PathVariable Integer id, Model model) {
        return "redirect:/auction";
    }

    @GetMapping(value = "/auction/{id}/update/{price}")
    public final String updatePriceById(@PathVariable Integer id, @PathVariable Integer price, Model model) {
        return "redirect:/auction";
    }

    @GetMapping(value = "/auction/sort/data")
    public final String sortProductsByDate(Model model) {
        return "redirect:/auction";
    }

    @GetMapping(value = "/auction/sort/owner")
    public final String sortProductsByOwner(Model model) {
        return "redirect:/auction";
    }
}
