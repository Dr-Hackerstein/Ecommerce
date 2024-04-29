package com.kevin.springboot.SpringEcommerce.service;

import com.kevin.springboot.SpringEcommerce.model.Order;
import com.kevin.springboot.SpringEcommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    List<Order> findAll();
    Optional<Order> findById(Integer id);
    Order save(Order order);
    String generarNumeroOrden();
    List<Order> findByUser(User user);
}
