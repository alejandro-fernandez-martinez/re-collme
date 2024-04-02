package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoraciones;

public interface ValoracionesService {
    
    Valoraciones añadir(Valoraciones valoraciones);
    List<Valoraciones> obtenerTodas();
    Valoraciones obtenerPorId(long id);
    Valoraciones editar(Valoraciones valoraciones);
    void borrar(Valoraciones valoraciones);
    void borrarPorId(Long id);

    List<Valoraciones> obtenerPorUsuarioAValorar (Usuario usuarioAValorar);
    List<Valoraciones> obtenerPorUsuarioQueValora (Usuario usuarioQueValora);
}
