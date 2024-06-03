package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Incidencia;
import com.example.demo.domain.Usuario;
import com.example.demo.repositories.IncidenciaRepository;
import com.example.demo.repositories.UsuarioRepository;

@Service
public class IncidenciaServiceImplBD implements IncidenciaService {
    @Autowired
    IncidenciaRepository repositorio;
    @Autowired
    UsuarioRepository rU;
    @Autowired
    UsuarioService usuarioService;

    public Incidencia añadir(Incidencia incidencia){
        incidencia.setIncidenciaCerrada(false);
        incidencia.setFechaCreacionIncidencia(LocalDateTime.now());
        responder(incidencia, incidencia.getComentarioIncidencia());
        return repositorio.save(incidencia);
    }

    public List<Incidencia> obtenerTodas(){
        return repositorio.findAll();
    }
    public Incidencia obtenerPorId(long id){
        return repositorio.findById(id).orElse(null); //pq devuelve un optional
    }
    public List<Incidencia> obtenerPorUsuarioReceptor (Usuario usuarioReceptor){
        return repositorio.findByUsuarioReceptor(usuarioReceptor);
    }
    public List<Incidencia> obtenerPorUsuarioEmisor (Usuario usuarioEmisor){
        return repositorio.findByUsuarioEmisor(usuarioEmisor);
    }

    public Incidencia editar(Incidencia incidencia){
        return repositorio.save(incidencia);
    }
    public Incidencia responder(Incidencia incidencia, String respuesta){
        String emailUser = usuarioService.obtenerUsuarioConectado().getEmailUser();
        String dateAnswer = LocalDateTime.now().toString();
        String comentario = dateAnswer + " - " + emailUser + ":\n " + respuesta;
        incidencia.getHiloIncidencia().add(comentario);
        editar(incidencia);
        return incidencia;
    }
    public void cerrarIncidencia(Incidencia incidencia){
        incidencia.setIncidenciaCerrada(true);
        modificarPuntuacionUsuarioReceptor(incidencia);
        editar(incidencia);
    }

    public void borrar(Incidencia incidencia){
        repositorio.delete(incidencia);
    }
    public void borrarPorId(Long id){
        repositorio.deleteById(id);
    }
    

    public void modificarPuntuacionUsuarioReceptor(Incidencia incidencia){
        Usuario usuarioReceptor = incidencia.getUsuarioReceptor();
        Double puntosHastaAhora = usuarioReceptor.getPuntuacion();
        Integer cantidadIncidencias = obtenerPorUsuarioReceptor(usuarioReceptor).size();
        Double puntosNuevos = (double)(puntosHastaAhora + incidencia.getValoracionIncidencia())/(cantidadIncidencias+1); 
        usuarioReceptor.setPuntuacion(puntosNuevos);
        rU.save(usuarioReceptor);
    }

}
