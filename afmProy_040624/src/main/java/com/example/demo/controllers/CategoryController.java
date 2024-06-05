package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Categoria;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ResiduoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/categoria")
public class CategoryController {
    @Autowired
    public CategoryService categoriaService;
    @Autowired
    public ResiduoService productoService;
    
    @GetMapping({ "/list" })
    public String showList(Model model) {
        model.addAttribute("listaCategorias", categoriaService.obtenerTodas());
        return "categorias/categoriesView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("categoryForm", new Categoria());
        return "categorias/newCategoryView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid Categoria categoryForm,
            BindingResult bindingResult) {
        categoriaService.añadir(categoryForm);
        return "redirect:/categoria/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Categoria categoria;
        categoria = categoriaService.obtenerPorId(id);
        if (categoria != null)
            model.addAttribute("categoryForm", categoria);
        return "categorias/editCategoryView";
    }

    @PostMapping("/edit/submit")
    public String showEditSubmit(
            @Valid Categoria categoryForm,
            BindingResult bindingResult) {
        categoriaService.editar(categoryForm);
        return "redirect:/categoria/list";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        if (productoService.obtenerPorCategoria(id).size() == 0)
            categoriaService.borrarPorId(id);
        return "redirect:/categoria/list";
    }
}
