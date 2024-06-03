package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Categoria;
import com.example.demo.repositories.CategoriaRepository;

@Service
public class CategoryServiceImplBD implements CategoryService {
    @Autowired
    CategoriaRepository repositorio;

    public Categoria añadir(Categoria categoria){
        return repositorio.save(categoria);
    }

    public List<Categoria> obtenerTodas(){
        return repositorio.findAll();
    }
    public Categoria obtenerPorId(long id){
        return repositorio.findById(id).orElse(null); //pq devuelve un optional
    }

    public Categoria obtenerPorNombre(String nombre) {
        return repositorio.findByNomCat(nombre);
    }


    public Categoria editar(Categoria categoria){
        return repositorio.save(categoria);
    }
    
    public void borrar(Categoria categoria){
        repositorio.delete(categoria);
    }
    public void borrarPorId(Long id){
        repositorio.deleteById(id);
    }



}
