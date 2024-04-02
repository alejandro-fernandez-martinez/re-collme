package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;

public interface ProductoService {
    
    Producto añadir(Producto producto);
    List <Producto> obtenerTodos();
    Producto obtenerPorId(long id);
    Producto editar(Producto producto);
    void borrar(Producto producto);
    void borrarPorId(Long id);

    Producto obtenerPorNombre(String nombre);
    List <Producto> obtenerNoVendidos();

    List <Producto> obtenerPorCategoria (Long idCat);
    List <Producto> obtenerPorVendedor (Usuario vendedor);
    List <Producto> obtenerPorPedido (Pedido pedido);

    void añadirACarrito (Producto producto, Pedido pedido, Usuario usuario);
    void quitarDelCarrito (Producto producto, Pedido pedido);
    void vaciarCarrito(Pedido pedido);
    void comprar (Pedido pedido);
}
