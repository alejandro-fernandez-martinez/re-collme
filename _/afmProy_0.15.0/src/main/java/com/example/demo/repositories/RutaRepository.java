package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Ruta;
import com.example.demo.domain.Usuario;


public interface RutaRepository extends JpaRepository <Ruta,Long> {
    
    List<Ruta> findByGestor(Usuario gestor);

    Ruta findByRutaCreada(Boolean rutaCreada);

    @Query("SELECT r FROM Ruta r WHERE r.gestor =?1 AND r.rutaCreada = false")
    Ruta findByGestorAndRutaCreada(Usuario gestor);

}
