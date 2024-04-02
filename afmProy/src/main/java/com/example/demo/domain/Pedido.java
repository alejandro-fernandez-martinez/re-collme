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
@EqualsAndHashCode(of = "idPed")

@Entity
public class Pedido {
    @Id
    @GeneratedValue
    private Long idPed;
    private Double udTotalPed;
    private Double precioTotalPed;
    private String descripPed;
    private Boolean pedRealizado;
    private LocalDateTime fechaPedRealizado;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario comprador;
}