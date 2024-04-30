package com.kevin.springboot.SpringEcommerce.repository;

import java.util.Optional;

import com.kevin.springboot.SpringEcommerce.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{
	Optional<Usuario> findByEmail(String email);
}
