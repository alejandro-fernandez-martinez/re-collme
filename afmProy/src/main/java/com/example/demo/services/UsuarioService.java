package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Usuario;
import com.example.demo.dto.EditarPassDto;

public interface UsuarioService {
    
    Usuario añadir(Usuario usuario);
    List<Usuario> obtenerTodos();
    Usuario obtenerPorId(Long id);
    Usuario editar(Usuario usuario);
    Usuario editarPass(Usuario usuario);
    void borrar(Usuario usuario);
    void borrarPorId(Long id);

    Usuario obtenerPorNombre (String nombre);
    Usuario obtenerUsuarioConectado ();
    List<Usuario> obtenerDemas();
    List<Usuario> obtenerGestores();
    Usuario convertDtoToUsuario(EditarPassDto userDto);
}
