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
import com.example.demo.domain.Valoraciones;
import com.example.demo.services.ProductoService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.ValoracionesService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/valoraciones")
public class ValoracionesController {
    @Autowired
    public ValoracionesService valoracionesService;

    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    public ProductoService productoService;
    
    @GetMapping("/list") // lista de todas las valoraciones
    public String showAll(Model model) {
        model.addAttribute("listaValoraciones", valoracionesService.obtenerTodas());
        return "valoraciones/listView";
    }
    
    @GetMapping("/user/{id}") // lista de valoraciones realizadas por un usuario
    public String showValoracionesByUsers(@PathVariable long id, Model model) {
        Usuario u = usuarioService.obtenerPorId(id);
        model.addAttribute("listaValoraciones", valoracionesService.obtenerPorUsuarioQueValora(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "valoraciones/userListView";
    }

    @GetMapping("/userLogin") // lista de valoraciones realizadas por el usuario conectado
    public String showValoracionesByUserLogin(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("listaValoraciones", valoracionesService.obtenerPorUsuarioQueValora(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
        return "valoraciones/userListView";
    }

    @GetMapping("/userTarget/{id}") // lista de valoraciones realizadas a un usuario
    public String showValoracionesUserTarget(@PathVariable long id, Model model) {
        Usuario u = usuarioService.obtenerPorId(id);
        model.addAttribute("listaValoraciones", valoracionesService.obtenerPorUsuarioAValorar(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "valoraciones/userListView";
    }
    
    @GetMapping("/userTargetByUserLogin") // lista de valoraciones realizadas al usuario conectado
    public String showValoracionesUserTargetByUserLogin(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("listaValoraciones", valoracionesService.obtenerPorUsuarioAValorar(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
        return "valoraciones/userListView";
    }
   

    @GetMapping("/delete/{id}")
    public String showDeleteVal(@PathVariable long id) {
        valoracionesService.borrar(valoracionesService.obtenerPorId(id));
        return "redirect:/valoraciones/userLogin";
    }

    @GetMapping("/new")
    public String showNewVal(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        Valoraciones valoracion = new Valoraciones();
        valoracion.setUsuarioQueValora(u);
        model.addAttribute("valoracionesForm", valoracion);
        model.addAttribute("listaUsuarios", usuarioService.obtenerDemas());
        return "valoraciones/valNewFormView";
    }

    @PostMapping("/new/submit")
    public String showNewValSubmit(
            @Valid Valoraciones valoracionesForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "redirect:/valoraciones/new";}
        valoracionesService.añadir(valoracionesForm);
        System.out.println(valoracionesForm);
        return "redirect:/valoraciones/userLogin";
    }

}
