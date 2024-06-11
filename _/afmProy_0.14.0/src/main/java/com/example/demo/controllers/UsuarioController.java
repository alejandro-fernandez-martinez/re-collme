package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Usuario;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")
public class UsuarioController {
    @Autowired
    public UsuarioService usuarioService;
    
    @GetMapping({ "/list" })
    public String showList(Model model) {
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        return "usuarios/usuariosView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("usuarioForm", new Usuario());
        return "usuarios/newUsuariosView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid Usuario usuarioForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
                // System.out.println("ssssssssssssssssssss" + bindingResult.getFieldErrors());
            return "redirect:/user/new";}
         usuarioService.añadir(usuarioForm);
        return "redirect:/user/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Usuario usuario;
        usuario = usuarioService.obtenerPorId(id);
        if (usuario != null)
            model.addAttribute("usuarioForm", usuario);
        return "usuarios/editUsuariosView";
    }

    @GetMapping("/editUserLogin")
    public String showEdidLoginForm(Model model) {
        Usuario usuario = usuarioService.obtenerUsuarioConectado();
        if (usuario != null)
            model.addAttribute("usuarioForm", usuario);
        return "usuarios/editUsuariosView";
    }

    @PostMapping("/edit/submit")
    public String showEditSubmit(
            @Valid Usuario usuarioForm,
            BindingResult bindingResult) {
        usuarioService.editar(usuarioForm);
        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        usuarioService.borrarPorId(id);
        return "redirect:/user/list";
    }

}
