package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Usuario;

public interface UsuarioService {
    
    Usuario añadir(Usuario usuario);
    List<Usuario> obtenerTodos();
    Usuario obtenerPorId(long id);
    Usuario editar(Usuario usuario);
    void borrar(Usuario usuario);
    void borrarPorId(Long id);

    Usuario obtenerPorNombre (String nombre);
    Usuario obtenerUsuarioConectado ();
    List<Usuario> obtenerDemas();
}
