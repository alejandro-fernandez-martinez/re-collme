package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Usuario;

public interface PedidoService {
    
    Pedido añadir(Pedido pedido);
    List<Pedido> obtenerTodos();
    Pedido obtenerPorId(long id);
    Pedido editar(Pedido pedido);
    void borrar(Pedido pedido);
    void borrarPorId(Long id);

    List<Pedido> obtenerPorComprador (Usuario comprador);

    Pedido obtenerPedidoPdte();

    Pedido obtenerPedidoPorCompradorYPedRealizado(Usuario comprador);

    Pedido verPedidoPdte(Usuario comprador);

    void finalizarPedido(Pedido pedido);

}
