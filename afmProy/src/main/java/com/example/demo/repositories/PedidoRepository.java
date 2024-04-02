package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Usuario;


public interface PedidoRepository extends JpaRepository <Pedido,Long> {
    
    List<Pedido> findByComprador(Usuario comprador);

    Pedido findByPedRealizado(Boolean pedRealizado);

    @Query("SELECT p FROM Pedido p WHERE p.comprador =?1 AND p.pedRealizado = false")
    Pedido findByCompradorAndPedRealizado(Usuario comprador);

}
