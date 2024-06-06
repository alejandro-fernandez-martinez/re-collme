package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Residuo;
import com.example.demo.domain.Usuario;

public interface ResiduoRepository extends JpaRepository <Residuo,Long> {
    
    Residuo findByNomResiduo (String nomResiduo);
    List<Residuo> findByReservado(Boolean reservado);
    List<Residuo> findBySolicitado(Boolean solicitado);
    
    List<Residuo> findByCategoria(Categoria categoria);
    List<Residuo> findByProductor(Usuario productor);
    
    // List<Residuo> findByRuta(Ruta ruta);

    @Query("SELECT r FROM Residuo r WHERE r.reservado = false AND r.solicitado = false")
    List<Residuo> findByResiduoReservadoAndSolicitadoFalse();
    @Query("SELECT r FROM Residuo r WHERE r.productor = productor AND r.solicitado = True AND r.reservado = False")
    List<Residuo> findByProductorAndSolicitado(Usuario productor);
    
}
