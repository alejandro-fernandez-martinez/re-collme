package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Ruta;
import com.example.demo.domain.Usuario;

public interface RutaService {
    
    Ruta añadir(Ruta ruta);

    List<Ruta> obtenerTodas();
    Ruta obtenerPorId(long id);
    List<Ruta> obtenerPorGestor (Usuario gestor);
    Ruta obtenerRutaPdte();
    Ruta obtenerRutaPorGestorYRutaCreada(Usuario gestor);
    Ruta verRutaPdte(Usuario gestor);

    Ruta editar(Ruta ruta);
    void finalizarCreacionRuta(Ruta ruta);

    // void borrar(Ruta ruta);
    void borrarPorId(Long id);

    

}
