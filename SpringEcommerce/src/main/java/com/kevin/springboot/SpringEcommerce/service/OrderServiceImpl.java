package com.kevin.springboot.SpringEcommerce.service;

import com.kevin.springboot.SpringEcommerce.model.Order;
import com.kevin.springboot.SpringEcommerce.model.User;
import com.kevin.springboot.SpringEcommerce.repository.IOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Integer id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public String generarNumeroOrden() {
        int numero=0;
        String numeroConcatenado="";

        List<Order> ordenes = findAll();

        List<Integer> numeros= new ArrayList<Integer>();

        ordenes.stream().forEach(o -> numeros.add( Integer.parseInt( o.getNumber())));

        if (ordenes.isEmpty()) {
            numero=1;
        }else {
            numero= numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if (numero<10) { //0000001000
            numeroConcatenado="000000000"+String.valueOf(numero);
        }else if(numero<100) {
            numeroConcatenado="00000000"+String.valueOf(numero);
        }else if(numero<1000) {
            numeroConcatenado="0000000"+String.valueOf(numero);
        }else if(numero<10000) {
            numeroConcatenado="0000000"+String.valueOf(numero);
        }

        return numeroConcatenado;
    }

    @Override
    public List<Order> findByUser(User user) {
        return orderRepository.findByUser(user);
    }
}
