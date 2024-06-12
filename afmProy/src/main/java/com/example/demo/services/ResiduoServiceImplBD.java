package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Categoria;
import com.example.demo.domain.Residuo;
import com.example.demo.domain.Ruta;
import com.example.demo.domain.Usuario;
import com.example.demo.exceptions.ResiduoNotFoundException;
import com.example.demo.repositories.CategoriaRepository;
import com.example.demo.repositories.ResiduoRepository;

@Service
public class ResiduoServiceImplBD implements ResiduoService {
    @Autowired
    ResiduoRepository repositorio;

    @Autowired
    CategoriaRepository rC;

    @Autowired
    UsuarioService usuarioService;

    public Residuo añadirData(Residuo residuo){
        return repositorio.save(residuo);
    }

    public Residuo añadir(Residuo residuo){
        residuo.setFechaRegistroResiduo(LocalDateTime.now());
        residuo.setSolicitado(false);
        residuo.setReservado(false);
        Usuario gestor = usuarioService.obtenerPorNombre(residuo.getNombreGestor());
        if (gestor != null){
            solicitarReserva(residuo, gestor);
            aprobarReserva(residuo, gestor);
        }
        residuo.setRecogido(false);
        residuo.setBloqueado(false);
        residuo.setFechaRecogidaResiduo(null);
        if (residuo.getProductor() == null)
            residuo.setProductor(usuarioService.obtenerUsuarioConectado());
        return repositorio.save(residuo);
    }
    // public Residuo añadir(Residuo residuo, Usuario gestor){
    //     añadir(residuo);
    //     solicitarReserva(residuo, usuarioService.obtenerUsuarioConectado());
    //     aprobarReserva(residuo, gestor);
    //     return repositorio.save(residuo);
    // }
    
    public List<Residuo> obtenerTodos(){
        return repositorio.findAll();
    }
    public Residuo obtenerPorId(Long id)  throws ResiduoNotFoundException{
        Residuo residuo = repositorio.findById(id)
            .orElseThrow(() -> new ResiduoNotFoundException(id));
        return residuo;
    }
    public Residuo obtenerPorNomResiduo(String nomResiduo){
        return repositorio.findByNomResiduo(nomResiduo);
    }
    public List<Residuo> obtenerNoReservados (){
        return repositorio.findByReservado(false);
    }
    public List<Residuo> obtenerNoSolicitados (){
        return repositorio.findBySolicitado(false);
    }
    public List<Residuo> obtenerPorCategoria (Long idCat){
        Categoria cat = rC.findById(idCat).orElse(null);
        if (cat != null) return repositorio.findByCategoria(cat);
        return null;
    }
    public List<Residuo> obtenerPorCategoriaNoSoliciado(Long idCat){
        Categoria cat = rC.findById(idCat).orElse(null);
        if (cat != null) return repositorio.findByCategoriaAndSolicitado(cat, false);
        return null;
    }
    public List <Residuo> obtenerPorProductor (Usuario productor){
        return repositorio.findByProductor(productor);
    }
    public List <Residuo> obtenerPorNombreSolicitante (String nombreSolicitante){
        return repositorio.findByNombreSolicitante(nombreSolicitante);
    }
    public List <Residuo> obtenerPorProductorAndSolicitado(Usuario productor){
        return repositorio.findByProductorAndSolicitadoAndReservado(productor,true, false);
    }
    public List <Residuo> obtenerPorNombreGestorAndSolicitadoAndReservado(String nombreGestor){
        return repositorio.findBynombreGestorAndSolicitadoAndReservado(nombreGestor,true, true);
    }
    public List <Residuo> obtenerPorRuta (Ruta ruta){
        return repositorio.findByRuta(ruta);
    }

    public Residuo editar(Residuo residuo){
        Usuario gestor = usuarioService.obtenerPorNombre(residuo.getNombreGestor());
        if (gestor != null){
            solicitarReserva(residuo, gestor);
            aprobarReserva(residuo, gestor);
        }
        // todo el mundo puede editar :/
        // no puedo hacer esto porque cuando se añade a una ruta o se finaliza la creación de una ruta, se modifican atributos del residuo, sin tener pq ser su productor
        // if (residuo.getProductor() == usuarioService.obtenerUsuarioConectado()) //solo puede editar el residuo su productor
        //     return repositorio.save(residuo);
        // else return residuo;
        return repositorio.save(residuo);
    }
    public void solicitarReserva(Residuo residuo, Usuario gestor){
        residuo.setSolicitado(true);
        residuo.setNombreSolicitante(gestor.getNomUser());
        repositorio.save(residuo);
    }
    public void aprobarReserva(Residuo residuo, Usuario gestor){
        residuo.setReservado(true);
        residuo.setNombreGestor(gestor.getNomUser());
        repositorio.save(residuo);
    }
    public void rechazarSolicitudReserva(Residuo residuo){
        residuo.setSolicitado(false);
        residuo.setNombreSolicitante(null);
        repositorio.save(residuo);
    }
    public void rechazarAsignacion(Residuo residuo){
        residuo.setSolicitado(false);
        residuo.setNombreSolicitante(null);
        residuo.setReservado(false);
        residuo.setNombreGestor(null);
        repositorio.save(residuo);
    }
    public void añadirARuta(Residuo residuo, Ruta ruta){
        residuo.setRuta(ruta);
        ruta.setMasaTotal(ruta.getMasaTotal()+residuo.getMasaResiduoKg());
        ruta.setVolumenTotal(ruta.getVolumenTotal()+residuo.getVolumenResiduoM3());
        editar(residuo);        
    }
    public void quitarDeRuta(Residuo residuo, Ruta ruta){
        residuo.setRuta(null);
        residuo.setFechaRecogidaResiduo(LocalDateTime.now());
        ruta.setMasaTotal(ruta.getMasaTotal()-residuo.getMasaResiduoKg());
        ruta.setVolumenTotal(ruta.getVolumenTotal()-residuo.getVolumenResiduoM3());
        editar(residuo); 
    }
    // si se quisiera poner un boton de vaciar Ruta
    // public void vaciarRuta(Ruta ruta){
    //     List <Residuo> residuos = obtenerPorRuta(ruta);
    //     for (Residuo r : residuos) {
    //         quitarDeRuta(r, ruta);
    //     }
    // }
    public void confirmarResiduo(Residuo residuo){
        residuo.setRecogido(true);
        editar(residuo);
    }
    public void rechazarResiduo(Residuo residuo){
        //abrir incidencia
        residuo.setBloqueado(true);
        editar(residuo);
    }



    public void borrar(Residuo residuo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]") || //el admin puede borrar tambien :)
        residuo.getProductor() == usuarioService.obtenerUsuarioConectado()) //solo puede borrar el residuo su creador
            repositorio.delete(residuo);
    }
    public void borrarPorId(Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]") || //el admin puede borrar tambien :)
            obtenerPorId(id).getProductor() == usuarioService.obtenerUsuarioConectado()) //solo puede borrar el producto su creador
        repositorio.deleteById(id);
    }

    // public void comprar(Ruta pedido){
    //     List <Residuo> productos = obtenerPorPedido(pedido);
    //     for (Residuo p : productos) { 
    //         p.setVendido(true); //lo quitamos de la lista de productos e impedimos meterlo en otro carrito
    //         editar(p);
    //     }
    // }


}
