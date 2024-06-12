package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Categoria;

public interface CategoryService {

    Categoria añadir(Categoria categoria);

    List<Categoria> obtenerTodas();
    Categoria obtenerPorId(long id);
    Categoria obtenerPorNomCat(String nomCat);

    Categoria editar(Categoria categoria);
    
    void borrar(Categoria categoria);
    void borrarPorId(Long id);
    
    
}
