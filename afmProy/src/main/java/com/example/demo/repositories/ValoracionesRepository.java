package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoraciones;


public interface ValoracionesRepository extends JpaRepository <Valoraciones,Long> {
    
    List<Valoraciones> findByUsuarioAValorar(Usuario usuarioAValorar);
    List<Valoraciones> findByUsuarioQueValora(Usuario usuarioQueValora);

}
