package com.kevin.springboot.SpringEcommerce.controller;

import com.kevin.springboot.SpringEcommerce.model.User;
import com.kevin.springboot.SpringEcommerce.service.ProductService;
import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kevin.springboot.SpringEcommerce.model.Product;
@Controller
@RequestMapping("/products")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String show() {
        return "products/show";
    }

    @GetMapping("/create")
    public String create() {
        return "products/create";
    }

    @PostMapping("/save")
    public String save(Product product) {
        LOGGER.info("Este es el objeto producto {}",product);
        User user = new User(1,"","","","","","","");
        product.setUser(user);
        productService.save(product);
        return "redirect:/products";
    }



}
