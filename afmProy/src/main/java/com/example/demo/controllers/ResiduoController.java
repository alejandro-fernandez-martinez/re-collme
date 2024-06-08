package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.domain.Residuo;
import com.example.demo.domain.Usuario;
import com.example.demo.services.CategoryService;
import com.example.demo.services.ResiduoService;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/residuo")
public class ResiduoController {
    @Autowired
    public ResiduoService residuoService;
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public UsuarioService usuarioService;

    @GetMapping({ "/list" })
    public String showList(Model model) {
        model.addAttribute("listaResiduos", residuoService.obtenerNoSolicitados());
        model.addAttribute("listaCategorias", categoryService.obtenerTodas());
        model.addAttribute("categoriaSeleccionada", "Todas");
        return "residuo/residuoView";
    }

    @GetMapping({ "/listByUser" })
    public String showListByUser(Model model) {
        Usuario user = usuarioService.obtenerUsuarioConectado();
        System.out.println(residuoService.obtenerPorProductor(user));
        model.addAttribute("user", user);
        model.addAttribute("edicion", true);
        model.addAttribute("listaResiduos", residuoService.obtenerPorProductor(user));
        return "residuo/residuoView";
    }

    @GetMapping({ "/listByUserSolicitados" })
    public String showListByUserAndSolicitados(Model model) {
        Usuario user = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("user", user);
        model.addAttribute("listaResiduos", residuoService.obtenerPorProductorAndSolicitado(user));
        model.addAttribute("xestion", true);
        return "residuo/residuoView";
    }

    @GetMapping({ "/listByUserAndSolicitadosAndReservados" })
    public String showListByUserAndSolicitadosAndReservados(Model model) {
        Usuario user = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("user", user);
        model.addAttribute("listaResiduos", residuoService.obtenerPorNombreGestorAndSolicitadoAndReservado(user.getNomUser()));
        model.addAttribute("verReservas", true);
        return "residuo/residuoView";
    }


    @GetMapping({ "/listComplete" })
    public String showListComplete(Model model) {
        model.addAttribute("listaResiduos", residuoService.obtenerTodos());
        model.addAttribute("listaCategorias", categoryService.obtenerTodas());
        model.addAttribute("categoriaSeleccionada", "Todas");
        return "residuo/residuoView";
    }

    @GetMapping({ "/categoria/{idCat}"})
    public String showProductsByCategory (@PathVariable Long idCat, Model model){
        model.addAttribute("listaResiduos", residuoService.obtenerPorCategoriaNoSoliciado(idCat));
        model.addAttribute("listaCategorias", categoryService.obtenerTodas());
        System.out.println("idCat" + idCat);
        if  (idCat == 0)
            return "redirect:/residuo/list";
        model.addAttribute("categoriaSeleccionada", categoryService.obtenerPorId(idCat).getNomCat());
        return "residuo/residuoView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("residuoForm", new Residuo());
        model.addAttribute("listaGestores", usuarioService.obtenerGestores());
        model.addAttribute("listaCategorias", categoryService.obtenerTodas());
        return "residuo/newResiduoView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid Residuo residuoForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
                // System.out.println("ssssssssssssssssssss" + bindingResult.getFieldErrors());
            return "redirect:/residuo/new";}  
        residuoService.añadir(residuoForm);
        return "redirect:/residuo/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]")|| //el admin puede editar tambien :)
        residuoService.obtenerPorId(id).getProductor() == usuarioService.obtenerUsuarioConectado()){ //solo puede editar el producto su creador
            Residuo residuo = residuoService.obtenerPorId(id);
            if (residuo != null)
                model.addAttribute("residuoForm", residuo);
            model.addAttribute("listaGestores", usuarioService.obtenerGestores());
            model.addAttribute("listaCategorias", categoryService.obtenerTodas());
            return "residuo/editResiduoView";
        }
        else return "redirect:/residuo/list";
    }

    @PostMapping("/edit/submit")
    public String showEditSubmit(
            @Valid Residuo residuoForm,
            BindingResult bindingResult) {
        residuoService.editar(residuoForm);
        return "redirect:/residuo/list";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]")|| //el admin puede borrar tambien :)
            residuoService.obtenerPorId(id).getProductor() == usuarioService.obtenerUsuarioConectado()){ //solo puede borrar el producto su creador
            residuoService.borrarPorId(id);
            return "redirect:/residuo/list";
        }
        else return "redirect:";
    }

    @GetMapping("/solicitarReserva/{idResiduo}")
    public String showReservar(@PathVariable Long idResiduo){
        Usuario user = usuarioService.obtenerUsuarioConectado();
        residuoService.solicitarReserva(residuoService.obtenerPorId(idResiduo),user);
        return "redirect:/residuo/list";
    }

    
    @GetMapping("/aprobarReserva/{idResiduo}")
    public String showAprobarReserva(@PathVariable Long idResiduo){
        Residuo r = residuoService.obtenerPorId(idResiduo);
        Usuario solicitante = usuarioService.obtenerPorNombre(r.getNombreSolicitante());
        residuoService.aprobarReserva(r,solicitante);
        return "redirect:/residuo/listByUserSolicitados";
    }

    
    @GetMapping("/rechazarSolicitudReserva/{idResiduo}")
    public String showRechazarSolicitudReserva(@PathVariable Long idResiduo){
        Residuo r = residuoService.obtenerPorId(idResiduo);
        residuoService.rechazarSolicitudReserva(r);
        return "redirect:/residuo/listByUserSolicitados";
    }

    @GetMapping("/rechazarAsignacion/{idResiduo}")
    public String showRechazarAsignacion(@PathVariable Long idResiduo){
        Residuo r = residuoService.obtenerPorId(idResiduo);
        residuoService.rechazarAsignacion(r);
        return "redirect:/residuo/listByUserSolicitados";
    }

    // @GetMapping("/reservar/{id}")
    // public String showBuy(@PathVariable long id){
    //     Residuo residuo = residuoService.obtenerPorId(id);
    //     Usuario user = usuarioService.obtenerUsuarioConectado();
    //     Ruta rutaPdte = rutaService.verRutaPdte(user);
    //     residuoService.añadirARuta(residuo, rutaPdte);//añado el producto y el usuario al pedido
    //     return "redirect:/producto/list";
    // }

    // @GetMapping("/devolver/{id}")
    // public String showReturn(@PathVariable long id){
    //     Residuo residuo = residuoService.obtenerPorId(id);
    //     Ruta rutaPte = residuo.getRuta();
    //     residuoService.quitarDeRuta(residuo, rutaPte);
    //     rutaService.editar(rutaPte); 
    //     return "redirect:/residuo/list";
    // }

}
