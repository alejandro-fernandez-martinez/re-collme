package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.PedidoRepository;

@Service
public class PedidoServiceImplBD implements PedidoService {
    @Autowired
    PedidoRepository repositorio;

    @Autowired
    ProductoService productoService;

    public Pedido añadir(Pedido pedido){
        return repositorio.save(pedido);
    }
    public List<Pedido> obtenerTodos(){
        return repositorio.findAll();
    }
    public Pedido obtenerPorId(long id){
        return repositorio.findById(id).orElse(null); //pq devuelve un optional
    }
    public Pedido editar(Pedido pedido){
        return repositorio.save(pedido);
    }
    public void borrar(Pedido pedido){
        productoService.vaciarCarrito(pedido);
        repositorio.delete(pedido);
    }
    public void borrarPorId(Long id){
        repositorio.deleteById(id);
    }

    public List<Pedido> obtenerPorComprador (Usuario comprador){
        return repositorio.findByComprador(comprador);
    }

    public Pedido obtenerPedidoPdte(){
        return repositorio.findByPedRealizado(false);
    }

    public Pedido obtenerPedidoPorCompradorYPedRealizado(Usuario comprador) {
        return repositorio.findByCompradorAndPedRealizado(comprador);
    }
    

    public Pedido verPedidoPdte(Usuario comprador){
        Pedido pedPdte = obtenerPedidoPorCompradorYPedRealizado(comprador);
        if (pedPdte == null){ //si no hay pedido abierto creo uno
            pedPdte = new Pedido();
            pedPdte.setUdTotalPed(0d);
            pedPdte.setPrecioTotalPed(0d);
            pedPdte.setPedRealizado(false);
            pedPdte.setComprador(comprador);
            añadir(pedPdte); //añadir si no existe y editar si existe, son los dos save :)
        }
        return pedPdte;
    }

    public void finalizarPedido(Pedido pedido){    
        // productoService.comprar(pedido);  // esto si quisieramos poner vendido = true al confirmar pedido
        pedido.setFechaPedRealizado(LocalDateTime.now());
        pedido.setPedRealizado(true);
        editar(pedido);
    }
}
