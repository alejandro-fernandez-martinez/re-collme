package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Usuario;
import com.example.demo.domain.Valoraciones;
import com.example.demo.repositories.UsuarioRepository;
import com.example.demo.repositories.ValoracionesRepository;

@Service
public class ValoracionesServiceImplBD implements ValoracionesService {
    @Autowired
    ValoracionesRepository repositorio;
    @Autowired
    UsuarioRepository rU;

    public Valoraciones añadir(Valoraciones valoraciones){
        valoraciones.setFechaVal(LocalDateTime.now());
        modificarPuntuacionUsuarioAValorar(valoraciones);
        return repositorio.save(valoraciones);
    }
    public List<Valoraciones> obtenerTodas(){
        return repositorio.findAll();
    }
    public Valoraciones obtenerPorId(long id){
        return repositorio.findById(id).orElse(null); //pq devuelve un optional
    }
    public Valoraciones editar(Valoraciones valoraciones){
        return repositorio.save(valoraciones);
    }
    public void borrar(Valoraciones valoraciones){
        repositorio.delete(valoraciones);
    }
    public void borrarPorId(Long id){
        repositorio.deleteById(id);
    }

    public List<Valoraciones> obtenerPorUsuarioAValorar (Usuario usuarioAValorar){
        return repositorio.findByUsuarioAValorar(usuarioAValorar);
    }

    public List<Valoraciones> obtenerPorUsuarioQueValora (Usuario usuarioQueValora){
        return repositorio.findByUsuarioQueValora(usuarioQueValora);
    }

    public void modificarPuntuacionUsuarioAValorar(Valoraciones valoraciones){
        Usuario usuarioAValorar = valoraciones.getUsuarioAValorar();
        Double puntosHastaAhora = valoraciones.getUsuarioAValorar().getPuntuacion();
        Integer cantVal = obtenerPorUsuarioAValorar(usuarioAValorar).size();
        Double puntosNuevos = (double)(puntosHastaAhora + valoraciones.getPuntuacionVal())/(cantVal+1); 
        usuarioAValorar.setPuntuacion(puntosNuevos);
        rU.save(usuarioAValorar);
    }

}
