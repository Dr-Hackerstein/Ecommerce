package com.kevin.springboot.SpringEcommerce.repository;

import com.kevin.springboot.SpringEcommerce.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IProductoRepository extends JpaRepository<Producto, Integer> {

}
