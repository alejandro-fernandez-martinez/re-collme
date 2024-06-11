package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Ruta;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.RutaRepository;

@Service
public class RutaServiceImplBD implements RutaService {
    @Autowired
    RutaRepository repositorio;

    @Autowired
    ResiduoService productoService;

    public Ruta añadir(Ruta ruta){
        return repositorio.save(ruta);
    }

    public List<Ruta> obtenerTodas(){
        return repositorio.findAll();
    }
    public Ruta obtenerPorId(long id){
        return repositorio.findById(id).orElse(null); //pq devuelve un optional
    }
    public List<Ruta> obtenerPorGestor(Usuario gestor){
        return repositorio.findByGestor(gestor);
    }
    public Ruta obtenerRutaPdte(){
        return repositorio.findByRutaCreada(false);
    }
    public Ruta obtenerRutaPorGestorYRutaCreada(Usuario gestor) {
        return repositorio.findByGestorAndRutaCreada(gestor);
    }
    public Ruta verRutaPdte(Usuario gestor){
        Ruta rutaPdte = obtenerRutaPorGestorYRutaCreada(gestor);
        if (rutaPdte == null){ //si no hay pedido abierto creo uno
            rutaPdte = new Ruta();
            rutaPdte.setMasaTotal(0d);
            rutaPdte.setVolumenTotal(0d);
            rutaPdte.setRutaCreada(false);
            rutaPdte.setGestor(gestor);
            rutaPdte.setRutaEnCurso(false);
            rutaPdte.setRutaTerminada(false);
            añadir(rutaPdte); //añadir si no existe y editar si existe, son los dos save :)
        }
        return rutaPdte;
    }

    public Ruta editar(Ruta ruta){
        return repositorio.save(ruta);
    }
    public void finalizarCreacionRuta(Ruta ruta){    
        ruta.setFechaCreacionRuta(LocalDateTime.now());
        ruta.setRutaCreada(true);
        editar(ruta);
    }

    // public void borrar(Ruta ruta){
    //     productoService.vaciarRuta(ruta);
    //     repositorio.delete(ruta);
    // }
    public void borrarPorId(Long id){
        repositorio.deleteById(id);
    }






    

    

    
}
