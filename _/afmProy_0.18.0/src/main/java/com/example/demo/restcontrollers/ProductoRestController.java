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

    // // POST
    // @PostMapping("/residuo")
    // public ResponseEntity<?> newElement(@Valid @RequestBody Residuo nuevoProducto) {
    //     Residuo producto = residuoService.añadir(nuevoProducto);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(producto); // cod 201
    // }

    // // PUT
    // @PutMapping("/residuo/{id}")
    // public Residuo editElement(@Valid @RequestBody Residuo editProducto,
    //         @PathVariable Long id) {
    //             residuoService.obtenerPorId(id); // esto es para ver si se produce excepción
    //     return residuoService.editar(editProducto);
    // }

    // // DELETE
    // @DeleteMapping("/residuo/{id}")
    // public void deleteElement(@PathVariable Long id) {
    //     residuoService.borrarPorId(id);
    // }
}
