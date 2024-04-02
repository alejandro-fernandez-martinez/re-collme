package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.domain.Pedido;
import com.example.demo.domain.Producto;
import com.example.demo.domain.Usuario;
import com.example.demo.services.PedidoService;
import com.example.demo.services.ProductoService;
import com.example.demo.services.UsuarioService;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    public PedidoService pedidoService;

    @Autowired
    public UsuarioService usuarioService;

    @Autowired
    public ProductoService productoService;
    
    
    @GetMapping("/user/{id}") // lista de pedidos de un usuario
    public String showPedidosByUsers(@PathVariable long id, Model model) {
        Usuario u = usuarioService.obtenerPorId(id);
        model.addAttribute("listaPedidos", pedidoService.obtenerPorComprador(u));
        model.addAttribute("usuario", usuarioService.obtenerPorId(id));
        return "pedido/userListView";
    }

    @GetMapping("/userLogin") // lista de pedidos del usuario conectado
    public String showPedidosUserLogin(Model model) {
        Usuario u = usuarioService.obtenerUsuarioConectado();
        model.addAttribute("listaPedidos", pedidoService.obtenerPorComprador(u));
        model.addAttribute("usuario", u);
        return "pedido/userListView";
    }

    @GetMapping("/list") // lista de TODOS los pedidos
    public String showAllOrders(Model model) {
        model.addAttribute("listaPedidos", pedidoService.obtenerTodos());
        return "pedido/pedListView";
    }

    @GetMapping("/delete/{id}")
    public String showDeletePed(@PathVariable long id) {
        pedidoService.borrar(pedidoService.obtenerPorId(id));
        return "redirect:/public";
    }

    @GetMapping("/new")
    public String showNewPed(Model model) {
        model.addAttribute("pedidoForm", new Pedido());
        model.addAttribute("listaUsuarios", usuarioService.obtenerTodos());
        model.addAttribute("listaProductos", productoService.obtenerTodos());
        return "pedido/pedNewFormView";
    }

    @PostMapping("/new/submit")
    public String showNewValSubmit(@Valid Pedido pedidoForm,
            BindingResult bindingResult) {
        if (!bindingResult.hasErrors())
            pedidoService.añadir(pedidoForm);
            System.out.println(pedidoForm);
        return "redirect:/public";
    }

    @GetMapping("/pedidoPdte")
    public String showCurrentOrder(Model model){
        Usuario user = usuarioService.obtenerUsuarioConectado();
        Pedido carrito = pedidoService.verPedidoPdte(user);
        List <Producto> productosCarrito = productoService.obtenerPorPedido(carrito);
        model.addAttribute("pedido", carrito);
        model.addAttribute("listaProductos", productosCarrito);
        return "pedido/pedPdteView";
    }

    @PostMapping("/pedidoPdte/submit")
    public String showCurrentOrderSubmit(@Valid Pedido pedidoForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()){
            pedidoService.finalizarPedido(pedidoForm);
            return "redirect:/pedido/user/"+usuarioService.obtenerUsuarioConectado().getIdUser();
        }
        return "redirect:/pedidoPdte";
    }
    
    @GetMapping("/detalle/{id}")
    public String showOrderDetails(@PathVariable long id, Model model){
        Pedido pedido = pedidoService.obtenerPorId(id);
        List <Producto> productos = productoService.obtenerPorPedido(pedido);
        model.addAttribute("pedido", pedido);
        model.addAttribute("listaProductos", productos);
        return "pedido/pedDetView";
    }

}
