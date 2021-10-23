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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/auction")
    public String findAll(@RequestParam(name = "startDate", required = false)
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  String startDate,
                          @RequestParam(name = "endDate", required = false)
                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                  String endDate,
                          Model model) {



        List<ProductDto> resultList;
        boolean sortByDate = false;
        if(startDate!=null && endDate!=null) {
            if(startDate.length()!=0 && endDate.length()!=0) {
                sortByDate = true;
            }
        }
        if(sortByDate){
            resultList = productDtoService.findAllProductWithNameOwnerByDate(startDate, endDate);
        }else {
            resultList = productDtoService.findAllProductWithNameOwner();
        }

        model.addAttribute("user", userService.findById(testUserId).get());
        model.addAttribute("products", resultList);
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
    public String addProduct(Product product) {
        product.setSalesmanId(testUserId);
        productService.create(product);
        return "redirect:/auction";
    }

    @PostMapping(value = "/product/update")
    public String updatePriceAndCustomer(@RequestParam(name = "productId") Integer productId, @RequestParam(name = "price") Float price, @RequestParam(name = "customerId") Integer customerId) {
        Product product = new Product();
        product.setProductId(productId);
        product.setProductPrice(price);
        product.setCustomerId(customerId);
        this.productService.updatePriceAndCustomer(product);
        return "redirect:/auction";
    }


    @PostMapping(value = "/date")
    public String updateDate(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) String date) {
        this.dateService.update(date);
        return "redirect:/auction";
    }

    @GetMapping(value = "/product/{id}/delete")
    public final String deleteDepartmentById(@PathVariable Integer id) {
        productService.delete(id);
        return "redirect:/auction";
    }


}
