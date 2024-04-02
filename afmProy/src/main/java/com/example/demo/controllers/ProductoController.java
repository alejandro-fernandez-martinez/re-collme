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

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.services.CategoryService;
import com.example.demo.services.PedidoService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/producto")
public class ProductoController {
    @Autowired
    public ProductoService productoService;
    @Autowired
    public CategoryService categoryService;
    @Autowired
    public PedidoService pedidoService;
    @Autowired
    public UsuarioService usuarioService;
    
    @GetMapping({ "/list" })
    public String showList(Model model) {
        model.addAttribute("listaProductos", productoService.obtenerNoVendidos());
        model.addAttribute("listaCategorias", categoryService.obtenerTodos());
        model.addAttribute("categoriaSeleccionada", "Todas");
        return "producto/productsView";
    }

    @GetMapping({ "/listUser" })
    public String showListUser(Model model) {
        Usuario user = usuarioService.obtenerUsuarioConectado();
        System.out.println(productoService.obtenerPorVendedor(user));
        model.addAttribute("listaProductos", productoService.obtenerPorVendedor(user));
        model.addAttribute("listaCategorias", categoryService.obtenerTodos());
        model.addAttribute("categoriaSeleccionada", "Todas");
        return "producto/productsView";
    }

    @GetMapping({ "/listComplete" })
    public String showListComplete(Model model) {
        model.addAttribute("listaProductos", productoService.obtenerTodos());
        model.addAttribute("listaCategorias", categoryService.obtenerTodos());
        model.addAttribute("categoriaSeleccionada", "Todas");
        return "producto/adminProductsView";
    }

    @GetMapping({ "/categoria/{idCat}"})
    public String showProductsByCategory (@PathVariable Long idCat, Model model){
        model.addAttribute("listaProductos", productoService.obtenerPorCategoria(idCat));
        model.addAttribute("listaCategorias", categoryService.obtenerTodos());
        System.out.println("idCat" + idCat);
        if  (idCat == 0)
            return "redirect:/producto/list";
        model.addAttribute("categoriaSeleccionada", categoryService.obtenerPorId(idCat).getNomCat());
        return "producto/productsView";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("productoForm", new Producto());
        model.addAttribute("listaCategorias", categoryService.obtenerTodos());
        return "producto/newProductView";
    }

    @PostMapping("/new/submit")
    public String showNewSubmit(
            @Valid Producto productoForm,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
                // System.out.println("ssssssssssssssssssss" + bindingResult.getFieldErrors());
            return "redirect:/producto/new";}
        productoForm.setVendedor(usuarioService.obtenerUsuarioConectado());    
        productoService.añadir(productoForm);
        return "redirect:/producto/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]")|| //el admin puede editar tambien :)
        productoService.obtenerPorId(id).getVendedor() == usuarioService.obtenerUsuarioConectado()){ //solo puede editar el producto su creador
            Producto producto = productoService.obtenerPorId(id);
            if (producto != null)
                model.addAttribute("productoForm", producto);
            model.addAttribute("listaCategorias", categoryService.obtenerTodos());
            return "producto/editProductView";
        }
        else return "redirect:/producto/list";
    }

    @PostMapping("/edit/submit")
    public String showEditSubmit(
            @Valid Producto productoForm,
            BindingResult bindingResult) {
        productoService.editar(productoForm);
        return "redirect:/producto/list";
    }

    @GetMapping("/delete/{id}")
    public String showDelete(@PathVariable long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserRol = authentication.getAuthorities().toString();
        if (currentUserRol.equals("[ROLE_ADMIN]")|| //el admin puede borrar tambien :)
            productoService.obtenerPorId(id).getVendedor() == usuarioService.obtenerUsuarioConectado()){ //solo puede borrar el producto su creador
            productoService.borrarPorId(id);
            return "redirect:/producto/list";
        }
        else return "redirect:";
    }

    @GetMapping("/comprar/{id}")
    public String showBuy(@PathVariable long id){
        Producto producto = productoService.obtenerPorId(id);
        Usuario user = usuarioService.obtenerUsuarioConectado();
        Pedido pedPdte = pedidoService.verPedidoPdte(user);
        productoService.añadirACarrito(producto, pedPdte, user);//añado el producto y el usuario al pedido
        return "redirect:/producto/list";
    }

    @GetMapping("/devolver/{id}")
    public String showReturn(@PathVariable long id){
        Producto producto = productoService.obtenerPorId(id);
        Pedido pedPte = producto.getPedido();
        productoService.quitarDelCarrito(producto, pedPte);
        pedidoService.editar(pedPte); 
        return "redirect:/producto/list";
    }

}
