package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Incidencia;

public interface IncidenciaService {
    
    void añadirData(Incidencia incidencia);
    Incidencia añadir(Incidencia incidencia);

    List<Incidencia> obtenerTodas();
    Incidencia obtenerPorId(Long idIncidencia);
    List<Incidencia> obtenerPorUsuarioReceptor (Usuario usuarioReceptor);
    List<Incidencia> obtenerPorUsuarioEmisor (Usuario usuarioEmisor);
    List<Incidencia> obtenerLasDelUsuarioConectado ();

    Incidencia editar(Incidencia incidencia);
    void responder(Incidencia incidencia, String respuesta);

    void cerrarIncidencia(Incidencia incidencia, Integer valoracionIncidencia);
    void borrar(Incidencia incidencia);
    void borrarPorId(Long idIncidencia);
    void modificarPuntuacionUsuarioReceptor(Incidencia incidencia, Integer valoracionIncidencia);
}
    
