package com.example.demo.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idIncidencia")

@Entity
public class Incidencia {
    @Id
    @GeneratedValue
    private Long idIncidencia;

    private LocalDateTime fechaCreacionIncidencia;
    private String comentarioIncidencia;
    private List <String> hiloIncidencia;
    private Boolean incidenciaCerrada;
    @Max(5)
    private Integer valoracionIncidencia;

    @NotNull
    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario usuarioReceptor;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario usuarioEmisor;
}
