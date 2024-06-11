package com.example.demo.restcontrollers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Residuo;
import com.example.demo.services.ResiduoService;

@RestController
@RequestMapping ("/api/")
public class ProductoRestController {
    @Autowired
    public ResiduoService residuoService;
    
    // GET todos
    @GetMapping({ "/residuos" })
    public List<Residuo> showList(Model model) {
        List<Residuo> listaProductos = residuoService.obtenerTodos();
        return listaProductos;
    }


}
