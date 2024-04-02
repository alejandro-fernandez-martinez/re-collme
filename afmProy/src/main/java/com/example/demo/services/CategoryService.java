package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Categoria;

public interface CategoryService {

    Categoria añadir(Categoria categoria);
    List<Categoria> obtenerTodos();
    Categoria obtenerPorId(long id);
    Categoria editar(Categoria categoria);
    void borrar(Categoria categoria);
    void borrarPorId(Long id);
    
    Categoria obtenerPorNombre(String nombre);
}
