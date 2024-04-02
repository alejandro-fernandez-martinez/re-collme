package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;

public interface ProductoRepository extends JpaRepository <Producto,Long> {
    
    Producto findByNomProd (String nomProd);
    List<Producto> findByVendido(Boolean vendido);
    
    List<Producto> findByCategoria(Categoria categoria);
    List<Producto> findByVendedor(Usuario vendedor);
    List<Producto> findByPedido(Pedido pedido);

    @Query("SELECT p FROM Producto p WHERE p.vendido = false AND p.reservado = false")
    List<Producto> findByVendidoAndReservadoFalse();
    
}
