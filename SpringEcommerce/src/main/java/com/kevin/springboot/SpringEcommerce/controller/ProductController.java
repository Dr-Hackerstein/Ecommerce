package com.kevin.springboot.SpringEcommerce.controller;

import com.kevin.springboot.SpringEcommerce.model.User;
import com.kevin.springboot.SpringEcommerce.service.ProductService;
import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kevin.springboot.SpringEcommerce.model.Product;

import java.util.Optional;
import java.util.PrimitiveIterator;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String show(Model model) {
        model.addAttribute("products",productService.findAll());
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

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Product> optionalProduct = productService.get(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            LOGGER.info("Producto buscado: {}", product);
            model.addAttribute("product",product);
            return "products/edit";
        } else {
            // Handle case where product with the given ID does not exist
            // You might want to return an error page or redirect to a different URL
            return "error"; // Or any other appropriate response
        }
    }

    @PostMapping("/update")
    public String update(Product product){
        productService.update(product);
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        productService.delete(id);
        return "redirect:/products";
    }

}
