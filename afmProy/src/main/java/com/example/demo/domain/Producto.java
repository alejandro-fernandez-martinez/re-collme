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
@EqualsAndHashCode(of = "idProd")

@Entity
public class Producto {
    @Id
    @GeneratedValue
    private Long idProd;
    private String nomProd;
    private String codLer;
    private Double cantProd;
    private String udMedida;
    private Double precioProd;
    private String descripProd;
    private String dirProd;
    private LocalDateTime fechaProd;
    private Boolean reservado;
    private Boolean vendido;
    private Boolean prodRecurrente;
    private Frecuencia frecProd;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Categoria categoria;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario vendedor;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Pedido pedido;
}