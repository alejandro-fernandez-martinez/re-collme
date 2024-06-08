package com.example.demo.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idRuta")

@Entity
public class Ruta {
    @Id
    @GeneratedValue
    private Long idRuta;
    private Double masaTotal;
    private Double volumenTotal;

    private String descripRuta;//guardar
    private Boolean rutaCreada;//->true
    private LocalDateTime fechaCreacionRuta;//asignar

    private Boolean rutaEnCurso;
    private LocalDateTime fechaInicioRuta;//
    private Boolean rutaTerminada;
    private LocalDateTime fechaFinRuta;//
    
    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario gestor;
}