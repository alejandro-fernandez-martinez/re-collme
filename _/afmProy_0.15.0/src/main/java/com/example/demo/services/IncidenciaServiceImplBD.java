package com.example.demo.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
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

    public void añadirData(Incidencia incidencia){
        repositorio.save(incidencia);
    }

    public Incidencia añadir(Incidencia incidencia){
        incidencia.setFechaCreacionIncidencia(LocalDateTime.now());
        incidencia.setHiloIncidencia(Arrays.asList(incidencia.getComentarioIncidencia()));
        incidencia.setIncidenciaCerrada(false);
        incidencia.setUsuarioEmisor(usuarioService.obtenerUsuarioConectado());
        System.out.println(incidencia.getUsuarioReceptor());
        incidencia.setUsuarioReceptor(incidencia.getUsuarioReceptor());
        
        return repositorio.save(incidencia);
    }

    public List<Incidencia> obtenerTodas(){
        return repositorio.findAll();
    }
    public Incidencia obtenerPorId(Long id){
        return repositorio.findById(id).orElse(null); //pq devuelve un optional
    }
    public List<Incidencia> obtenerPorUsuarioReceptor (Usuario usuarioReceptor){
        return repositorio.findByUsuarioReceptor(usuarioReceptor);
    }
    public List<Incidencia> obtenerPorUsuarioEmisor (Usuario usuarioEmisor){
        return repositorio.findByUsuarioEmisor(usuarioEmisor);
    }
    public List<Incidencia> obtenerLasDelUsuarioConectado (){
        Usuario user = usuarioService.obtenerUsuarioConectado();
        return repositorio.findByUsuarioReceptorOrUsuarioEmisor(user, user);
    }
    public Incidencia editar(Incidencia incidencia){
        return repositorio.save(incidencia);
    }
    public void responder(Incidencia incidencia, String respuesta){
        String emailUser = usuarioService.obtenerUsuarioConectado().getEmailUser();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        String dateAnswer = now.format(formatter);
        String comentario = dateAnswer + " - " + emailUser + ": " + respuesta;
        incidencia.getHiloIncidencia().add(comentario);
        editar(incidencia);
    }
    public void cerrarIncidencia(Incidencia incidencia, Integer valoracionIncidencia){
        incidencia.setIncidenciaCerrada(true);
        incidencia.setValoracionIncidencia(valoracionIncidencia);
        modificarPuntuacionUsuarioReceptor(incidencia, valoracionIncidencia);
        editar(incidencia);
    }

    public void borrar(Incidencia incidencia){
        repositorio.delete(incidencia);
    }
    public void borrarPorId(Long id){
        repositorio.deleteById(id);
    }
    

    public void modificarPuntuacionUsuarioReceptor(Incidencia incidencia, Integer valoracionIncidencia){
        Usuario usuarioReceptor = incidencia.getUsuarioReceptor();
        Integer puntosHastaAhora = usuarioReceptor.getPuntuacion();
        Integer puntosNuevos = (puntosHastaAhora + valoracionIncidencia); 
        usuarioReceptor.setPuntuacion(puntosNuevos);
        rU.save(usuarioReceptor);
    }

}
