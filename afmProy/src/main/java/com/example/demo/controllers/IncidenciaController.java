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
import com.example.demo.domain.Incidencia;
import com.example.demo.services.ResiduoService;
import com.example.demo.services.UsuarioService;
import com.example.demo.services.IncidenciaService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/incidencia")
public class IncidenciaController {
    @Autowired
    public IncidenciaService incidenciaService;

    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    public ResiduoService residuoService;
    
    @GetMapping("/list") // lista de todas las incidencias
    public String showAll(Model model) {
        model.addAttribute("listaIncidencias", incidenciaService.obtenerTodas());
        return "incidencia/listView";
    }
    
    @GetMapping("/user/{id}") // lista de valoraciones realizadas por un usuario
    public String showValoracionesByUsers(@PathVariable long id, Model model) {
        Usuario u = usuarioService.obtenerPorId(id);
        model.addAttribute("listaValoraciones", incidenciaService.obtenerPorUsuarioEmisor(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "valoraciones/userListView";
    }

    @GetMapping("/userLogin") // lista de valoraciones realizadas por el usuario conectado
    public String showValoracionesByUserLogin(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("listaValoraciones", incidenciaService.obtenerPorUsuarioEmisor(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
        return "valoraciones/userListView";
    }

    @GetMapping("/userTarget/{id}") // lista de valoraciones realizadas a un usuario
    public String showValoracionesUserTarget(@PathVariable long id, Model model) {
        Usuario u = usuarioService.obtenerPorId(id);
        model.addAttribute("listaValoraciones", incidenciaService.obtenerPorUsuarioReceptor(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "valoraciones/userListView";
    }
    
    @GetMapping("/userTargetByUserLogin") // lista de valoraciones realizadas al usuario conectado
    public String showValoracionesUserTargetByUserLogin(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("listaValoraciones", incidenciaService.obtenerPorUsuarioReceptor(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
        return "valoraciones/userListView";
    }
   

    @GetMapping("/delete/{id}")
    public String showDeleteVal(@PathVariable long id) {
        incidenciaService.borrar(incidenciaService.obtenerPorId(id));
        return "redirect:/valoraciones/userLogin";
    }

    @GetMapping("/new")
    public String showNewVal(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        Incidencia valoracion = new Incidencia();
        valoracion.setUsuarioEmisor(u);
        model.addAttribute("valoracionesForm", valoracion);
        model.addAttribute("listaUsuarios", usuarioService.obtenerDemas());
        return "valoraciones/valNewFormView";
    }

    @PostMapping("/new/submit")
    public String showNewValSubmit(
            @Valid Incidencia valoracionesForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "redirect:/valoraciones/new";}
        incidenciaService.añadir(valoracionesForm);
        System.out.println(valoracionesForm);
        return "redirect:/valoraciones/userLogin";
    }

}
