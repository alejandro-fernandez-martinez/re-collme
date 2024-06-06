package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Usuario;

public interface UsuarioRepository extends JpaRepository <Usuario,Long> {
    
    Usuario findByNomUser (String nomUser);
    
    @Query("SELECT u FROM Usuario u WHERE u.gestor = True")
    List<Usuario> findByGestorTrue();

}
