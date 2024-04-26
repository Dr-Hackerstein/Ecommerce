package com.kevin.springboot.SpringEcommerce.controller;

import com.kevin.springboot.SpringEcommerce.model.User;
import com.kevin.springboot.SpringEcommerce.service.ProductService;
import com.kevin.springboot.SpringEcommerce.service.UploadFileService;
import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.kevin.springboot.SpringEcommerce.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.PrimitiveIterator;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private ProductService productService;
    @Autowired
    private UploadFileService upload;
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
    public String save(Product product, @RequestParam("img") MultipartFile file) throws IOException {
        LOGGER.info("Este es el objeto producto {}",product);
        User user = new User(1,"","","","","","","");
        product.setUser(user);

        //image
        if(product.getId()==null){ // when a product is created
            String imageName = upload.saveImage(file);
            product.setImage(imageName);
        }else {
            if(file.isEmpty()){ //edit the product but not change the image
                Product p = new Product();
                p = productService.get(product.getId()).get();
                product.setImage(p.getImage());
            }else {
                String imageName = upload.saveImage(file);
                product.setImage(imageName);
            }
        }

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
    public String update(Product product, @RequestParam("img") MultipartFile file ) throws IOException {
        Product p= new Product();
        p=productService.get(product.getId()).get();

        if (file.isEmpty()) { // editamos el producto pero no cambiamos la imagem

            product.setImage(p.getImage());
        }else {// cuando se edita tbn la imagen
            //eliminar cuando no sea la imagen por defecto
            if (!p.getImage().equals("default.jpg")) {
                upload.deleteImage(p.getImage());
            }
            String nombreImagen= upload.saveImage(file);
            product.setImage(nombreImagen);
        }
        product.setUser(p.getUser());
        productService.update(product);
        return "redirect:/productos";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {

        Product p = new Product();
        p=productService.get(id).get();

        //delete when not the image
        if (!p.getImage().equals("default.jpg")) {
            upload.deleteImage(p.getImage());
        }

        productService.delete(id);
        return "redirect:/products";
    }



}
