package com.example.demo.services;

import java.util.List;

import com.example.demo.domain.Residuo;
import com.example.demo.domain.Usuario;

public interface ResiduoService {
    
    Residuo añadirData (Residuo residuo);
    Residuo añadir(Residuo residuo);

    List <Residuo> obtenerTodos();
    Residuo obtenerPorId(Long id);
    Residuo obtenerPorNomResiduo(String nomResiduo);
    List <Residuo> obtenerNoReservados();
    List <Residuo> obtenerNoSolicitados();
    List <Residuo> obtenerPorCategoria (Long idCat);
    List<Residuo> obtenerPorCategoriaNoSoliciado(Long idCat);
    List <Residuo> obtenerPorProductor (Usuario productor);
    List <Residuo> obtenerPorProductorAndSolicitado(Usuario productor);
    List <Residuo> obtenerPorNombreGestorAndSolicitadoAndReservado(String nombreGestor);
    // List <Residuo> obtenerPorRuta (Ruta ruta);

    Residuo editar(Residuo residuo);
    void solicitarReserva(Residuo residuo, Usuario gestor);
    void aprobarReserva(Residuo residuo, Usuario gestor);
    void rechazarSolicitudReserva(Residuo residuo);
    void rechazarAsignacion(Residuo residuo);
    // void añadirARuta (Residuo residuo, Ruta ruta);
    // void quitarDeRuta (Residuo residuo, Ruta ruta);
    // void vaciarRuta(Ruta ruta);
    void confirmarResiduo(Residuo residuo);
    void rechazarResiduo(Residuo residuo);

    void borrar(Residuo residuo);
    void borrarPorId(Long id);    
    
    // void reservar (Ruta ruta);
}
