package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Incidencia;


public interface IncidenciaRepository extends JpaRepository <Incidencia,Long> {
    
    List<Incidencia> findByUsuarioReceptor(Usuario usuarioReceptor);
    List<Incidencia> findByUsuarioEmisor(Usuario usuarioEmisor);
    List<Incidencia> findByUsuarioReceptorOrUsuarioEmisor(Usuario receptor, Usuario emisor);

}
