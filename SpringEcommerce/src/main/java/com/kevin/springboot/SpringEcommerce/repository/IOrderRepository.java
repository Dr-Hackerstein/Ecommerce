package com.kevin.springboot.SpringEcommerce.repository;

import com.kevin.springboot.SpringEcommerce.model.Order;
import com.kevin.springboot.SpringEcommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByUser (User user);
}
