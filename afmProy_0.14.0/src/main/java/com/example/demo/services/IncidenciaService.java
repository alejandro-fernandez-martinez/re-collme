package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Incidencia;

public interface IncidenciaService {
    
    Incidencia añadir(Incidencia incidencia);

    List<Incidencia> obtenerTodas();
    Incidencia obtenerPorId(long idIncidencia);
    List<Incidencia> obtenerPorUsuarioReceptor (Usuario usuarioReceptor);
    List<Incidencia> obtenerPorUsuarioEmisor (Usuario usuarioEmisor);

    Incidencia editar(Incidencia incidencia);
    Incidencia responder(Incidencia incidencia, String respuesta);
    void cerrarIncidencia(Incidencia incidencia);

    void borrar(Incidencia incidencia);
    void borrarPorId(Long idIncidencia);
}
    
