package com.kevin.springboot.SpringEcommerce.service;

import com.kevin.springboot.SpringEcommerce.model.Orden;
import com.kevin.springboot.SpringEcommerce.model.Usuario;

import java.util.List;
import java.util.Optional;



public interface IOrdenService {
	List<Orden> findAll();
	Optional<Orden> findById(Integer id);
	Orden save (Orden orden);
	String generarNumeroOrden();
	List<Orden> findByUsuario (Usuario usuario);
}
