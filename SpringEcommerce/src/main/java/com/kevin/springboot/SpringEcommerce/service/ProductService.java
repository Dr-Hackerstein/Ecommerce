package com.kevin.springboot.SpringEcommerce.service;

import com.kevin.springboot.SpringEcommerce.model.Product;

import java.util.Optional;

public interface ProductService {
    // Method to save a Product
    public Product save(Product product);

    // Method to retrieve a Product by its ID, returning an Optional
    //  This allows the method to indicate if the product was found or not.
    public Optional<Product> get(Integer id);

    // Method to update a Product
    public void update(Product product);

    // Method to delete a Product by its ID
    public void delete(Integer id);
}
