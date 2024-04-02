package com.example.demo.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idUser")

@Entity
public class Usuario {
    @Id
    @GeneratedValue
    private Long idUser;
    @Column(unique = true) //evita duplicados a nivel base de datos
    private String nomUser;
    private String passUser;
    private String dniNif;
    private String emailUser;
    private Long tlfUser;
    private String dirUser;
    private Boolean negociante;
    private Boolean userActivo;
    private LocalDateTime fechaRegistro;
    private Rol rol;
    private Double puntuacion;
}