package com.example.demo.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.EditarPassDto;
import com.example.demo.repositories.UsuarioRepository;

@Service
public class UsuarioServiceImplBD implements UsuarioService {
    @Autowired
    UsuarioRepository repositorio;
    @Autowired
    PasswordEncoder passwordEncoder;

    public Usuario añadir(Usuario usuario) {
        usuario.setUserActivo(true);
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setPuntuacion(0);
        String passCrypted = passwordEncoder.encode(usuario.getPassUser());
        usuario.setPassUser(passCrypted);
        try {
            return repositorio.save(usuario);

        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Usuario> obtenerTodos() {
        return repositorio.findAll();
    }
    public Usuario obtenerPorId(Long id) {
        return repositorio.findById(id).orElse(null); // pq devuelve un optional
    }
    public Usuario obtenerPorNombre(String nombre) {
        return repositorio.findByNomUser(nombre);
    }
    public Usuario obtenerUsuarioConectado (){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Usuario user = obtenerPorNombre(authentication.getName());
            return user;
        }
        return null;
    }
    public List<Usuario> obtenerDemas() {
        List<Usuario> todosLosUsuarios = obtenerTodos();
        Usuario usuarioConectado = obtenerUsuarioConectado();
        todosLosUsuarios.remove(usuarioConectado);
        return todosLosUsuarios;
    }
    public List<Usuario> obtenerGestores(){
        List<Usuario> listaGestores = repositorio.findByGestorTrue();
        return listaGestores;
    }

    public Usuario editar(Usuario usuario) {
        String passCrypted = passwordEncoder.encode(usuario.getPassUser());
        usuario.setPassUser(passCrypted);
        try {
            return repositorio.save(usuario);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Usuario editarPass(Usuario usuario) {
        String passCrypted = passwordEncoder.encode(usuario.getPassUser());
        usuario.setPassUser(passCrypted);
        try {
            return repositorio.save(usuario);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void borrar(Usuario usuario) {
        usuario.setUserActivo(false);
        editar(usuario);
    }
    public void borrarPorId(Long id) {
        Usuario usuario = obtenerPorId(id);
        if (usuario != null) {
            usuario.setUserActivo(false);
            editar(usuario);
        }
    }

    public Usuario convertDtoToUsuario(EditarPassDto userDto){
        Usuario userLogueado = obtenerUsuarioConectado();
        if (userLogueado != null){
            userLogueado.setPassUser(userDto.getPassword());
            editar(userLogueado);}
        return userLogueado;
    }
    
    
}
