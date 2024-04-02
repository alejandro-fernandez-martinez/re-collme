package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.repositories.ProductoRepository;

@Service
public class ProductoServiceImplBD implements ProductoService {
    @Autowired
    ProductoRepository repositorio;

    @Autowired
    CategoriaRepository rC;

    @Autowired
    UsuarioService usuarioService;

    public Producto añadir(Producto producto){
        producto.setFechaProd(LocalDateTime.now());
        producto.setReservado(false);
        producto.setVendido(false);
        return repositorio.save(producto);
    }
    public List<Producto> obtenerTodos(){
        return repositorio.findAll();
    }
    public Producto obtenerPorId(long id){
        return repositorio.findById(id).orElse(null); //pq devuelve un optional
    }
    public Producto editar(Producto producto){
        // todo el mundo puede editar :/
        // no puedo hacer esto porque cuando se añade a un carrito o se finaliza pedido, se modifican atributos del producto, sin tener pq ser su vendedor
        // if (producto.getVendedor() == usuarioService.obtenerUsuarioConectado()) //solo puede editar el producto su creador
        //     return repositorio.save(producto);
        // else return producto;
        return repositorio.save(producto);
    }
    public void borrar(Producto producto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]") || //el admin puede borrar tambien :)
            producto.getVendedor() == usuarioService.obtenerUsuarioConectado()) //solo puede borrar el producto su creador
            repositorio.delete(producto);
    }
    public void borrarPorId(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]") || //el admin puede borrar tambien :)
            obtenerPorId(id).getVendedor() == usuarioService.obtenerUsuarioConectado()) //solo puede borrar el producto su creador
        repositorio.deleteById(id);
    }
    public Producto obtenerPorNombre(String nombre){
        return repositorio.findByNomProd(nombre);
    }

    public List<Producto> obtenerNoVendidos (){
        return repositorio.findByVendido(false);
    }

    public List<Producto> obtenerPorCategoria (Long idCat){
        Categoria cat = rC.findById(idCat).orElse(null);
        if (cat != null) return repositorio.findByCategoria(cat);
        return null;
    }
    public List <Producto> obtenerPorVendedor (Usuario vendedor){
        return repositorio.findByVendedor(vendedor);
    }
    public List <Producto> obtenerPorPedido (Pedido pedido){
        return repositorio.findByPedido(pedido);
    }

    public void añadirACarrito(Producto producto, Pedido pedido, Usuario comprador){
        producto.setPedido(pedido); //impedimos meterlo en otro carrito
        producto.setVendido(true); //lo quitamos de la lista de productos disponibles
        pedido.setUdTotalPed(pedido.getUdTotalPed()+1);
        pedido.setPrecioTotalPed(pedido.getPrecioTotalPed()+producto.getPrecioProd());
        editar(producto);        
    }

    public void quitarDelCarrito(Producto producto, Pedido pedido){
        producto.setVendido(false); //lo devolvemos a la lista de productos disponibles 
        producto.setPedido(null); //y ahora otro puede meterlo en su carrito
        pedido.setUdTotalPed(pedido.getUdTotalPed()-1);
        pedido.setPrecioTotalPed(pedido.getPrecioTotalPed()-producto.getPrecioProd());
        editar(producto);
    }

    // si se quisiera poner un boton de vaciar carrito
    public void vaciarCarrito(Pedido pedido){
        List <Producto> productos = obtenerPorPedido(pedido);
        for (Producto p : productos) {
            quitarDelCarrito(p, pedido);
        }
    }

    public void comprar(Pedido pedido){
        List <Producto> productos = obtenerPorPedido(pedido);
        for (Producto p : productos) { 
            p.setVendido(true); //lo quitamos de la lista de productos e impedimos meterlo en otro carrito
            editar(p);
        }
    }


}
