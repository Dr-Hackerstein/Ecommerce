package com.kevin.springboot.SpringEcommerce.repository;

import com.kevin.springboot.SpringEcommerce.model.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailsRepository extends JpaRepository<OrderDetails,Integer> {

}
