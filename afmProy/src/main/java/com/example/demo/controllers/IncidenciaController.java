package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.domain.Incidencia;
import com.example.demo.domain.Usuario;
import com.example.demo.services.IncidenciaService;
import com.example.demo.services.ResiduoService;
import com.example.demo.services.UsuarioService;

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
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("listaIncidencias", incidenciaService.obtenerTodas());
        model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
        return "incidencia/userListView";
    }
    
    // @GetMapping("/user/{id}") // lista de valoraciones realizadas por un usuario
    // public String showValoracionesByUsers(@PathVariable long id, Model model) {
    //     Usuario u = usuarioService.obtenerPorId(id);
    //     model.addAttribute("listaIncidencias", incidenciaService.obtenerPorUsuarioEmisor(u));
    //     model.addAttribute("usuario", usuarioService.obtenerPorId(id));
    //     return "incidencia/userListView";
    // }

    @GetMapping("/realizadas") // lista de valoraciones realizadas por el usuario conectado
    public String showValoracionesByUserLogin(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("listaIncidencias", incidenciaService.obtenerPorUsuarioEmisor(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
        return "incidencia/userListView";
    }

    // @GetMapping("/userTarget/{id}") // lista de valoraciones realizadas a un usuario
    // public String showValoracionesUserTarget(@PathVariable long id, Model model) {
    //     Usuario u = usuarioService.obtenerPorId(id);
    //     model.addAttribute("listaIncidencias", incidenciaService.obtenerPorUsuarioReceptor(u));
    //     model.addAttribute("usuario", usuarioService.obtenerPorId(id));
    //     return "incidencia/userListView";
    // }
    
    // @GetMapping("/recibidas") // lista de valoraciones realizadas al usuario conectado
    // public String showValoracionesUserTargetByUserLogin(Model model) {
    //     Usuario u = usuarioService.obtenerUsuarioConectado();
    //     model.addAttribute("listaIncidencias", incidenciaService.obtenerPorUsuarioReceptor(u));
    //     model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
    //     return "incidencia/userListView";
    // }

    @GetMapping("/listByUser") // lista valoraciones usuario conectado
    public String showValoracionesUserTargetByUserLogin(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("usuario", u);
        model.addAttribute("listaIncidencias", incidenciaService.obtenerLasDelUsuarioConectado());
        model.addAttribute("usuario", usuarioService.obtenerPorId(u.getIdUser()));
        return "incidencia/userListView";
    }
   
    @PostMapping("/responder/{id}")
    public String responderIncidencia(@PathVariable("id") Long idIncidencia, @RequestParam("comentario") String comentario, Model model) {
        Incidencia incidencia = incidenciaService.obtenerPorId(idIncidencia);
        incidenciaService.responder(incidencia, comentario);
        return "redirect:/incidencia/listByUser";
    }

    @PostMapping("/cerrar/{id}")
    public String showDeleteIncidencia(@PathVariable("id") Long idIncidencia, @RequestParam("valoracion") Integer valoracion, Model model) {
        incidenciaService.cerrarIncidencia(incidenciaService.obtenerPorId(idIncidencia),valoracion );
        return "redirect:/incidencia/listByUser";
    }

    // @GetMapping("/delete/{id}")
    // public String showDeleteVal(@PathVariable long id) {
    //     incidenciaService.borrar(incidenciaService.obtenerPorId(id));
    //     return "redirect:/valoraciones/userLogin";
    // }

    @GetMapping("/new")
    public String showNewIncidencia(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        Incidencia incidencia = new Incidencia();
        incidencia.setUsuarioEmisor(u);
        model.addAttribute("incidenciaForm", incidencia);
        model.addAttribute("listaUsuarios", usuarioService.obtenerDemas());
        return "incidencia/newIncidenciaView";
    }

    @PostMapping("/new/submit")
    public String showNewIncidenciaSubmit(
            @Valid Incidencia incidenciaForm,
            BindingResult bindingResult,
            Model model) {
                System.out.println(incidenciaForm);
        if (bindingResult.hasErrors()){
            return "redirect:/incidencia/new";}
        incidenciaService.añadir(incidenciaForm);
        return "redirect:/incidencia/listByUser";
    }

}
