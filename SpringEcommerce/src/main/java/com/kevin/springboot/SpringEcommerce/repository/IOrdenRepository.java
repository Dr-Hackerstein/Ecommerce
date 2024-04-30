package com.kevin.springboot.SpringEcommerce.repository;

import java.util.List;

import com.kevin.springboot.SpringEcommerce.model.Orden;
import com.kevin.springboot.SpringEcommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IOrdenRepository extends JpaRepository<Orden, Integer> {
	List<Orden> findByUsuario (Usuario usuario);
}
