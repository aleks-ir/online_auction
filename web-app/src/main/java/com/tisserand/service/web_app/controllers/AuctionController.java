package com.tisserand.service.web_app.controllers;

import com.tisserand.model.Product;
import com.tisserand.model.dto.ProductDto;
import com.tisserand.service.DateService;
import com.tisserand.service.dto.ProductDtoService;
import com.tisserand.service.ProductService;
import com.tisserand.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;


@Controller
public class AuctionController {

    private final ProductDtoService productDtoService;

    private final ProductService productService;

    private final UserService userService;

    private final DateService dateService;

    private Integer testUserId = 1;

    @Autowired
    public AuctionController(ProductDtoService productDtoService,
                                ProductService productService, UserService userService, DateService dateService) {
        this.productDtoService = productDtoService;
        this.productService = productService;
        this.userService = userService;
        this.dateService = dateService;
    }

    @GetMapping(value = "/auction/sort")
    public String findAllWithSortByDate(@RequestParam(name = "startDate", required = false) String startDate,
                          @RequestParam(name = "endDate", required = false) String endDate,
                          Model model) {
        if(startDate == ""){
            startDate = "1100-01-01";
        }else if(endDate == ""){
            endDate = "2100-01-01";
        }
        model.addAttribute("user", userService.findById(testUserId).get());
        model.addAttribute("products", productDtoService.findAllProductWithNameOwnerByDate(startDate, endDate));
        model.addAttribute("date", dateService.getDate());
        return "auction";
    }

    @GetMapping(value = "/auction")
    public String findAll(Model model) {
        model.addAttribute("user", userService.findById(testUserId).get());
        model.addAttribute("products", productDtoService.findAllProductWithNameOwner());
        model.addAttribute("date", dateService.getDate());
        return "auction";
    }



    @GetMapping(value = "/add_product")
    public final String gotoAddProductPage(Model model) {
        model.addAttribute("isNew", true);
        model.addAttribute("product", new Product());
        return "add_product";
    }

    @PostMapping(value = "/add_product")
    public String addProduct(@Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "add_product";
        }
        product.setSalesmanId(testUserId);
        productService.create(product);
        return "redirect:/auction";
    }

    @PostMapping(value = "/product/update")
    public String updatePriceAndCustomer(@RequestParam(name = "productId") Integer productId,
                                         @RequestParam(name = "price") Float price,
                                         @RequestParam(name = "customerId") Integer customerId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductPrice(price);
        product.setCustomerId(customerId);
        this.productService.updatePriceAndCustomer(product);
        return "redirect:/auction";
    }


    @PostMapping(value = "/date")
    public String updateDate(@RequestParam(name = "date") String date) {
        this.dateService.update(date);
        return "redirect:/auction";
    }

    @GetMapping(value = "/product/{id}/delete")
    public final String deleteProductById(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/auction";
    }


}
