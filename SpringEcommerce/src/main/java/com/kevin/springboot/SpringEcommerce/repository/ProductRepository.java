package com.kevin.springboot.SpringEcommerce.repository;

import com.kevin.springboot.SpringEcommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

}
