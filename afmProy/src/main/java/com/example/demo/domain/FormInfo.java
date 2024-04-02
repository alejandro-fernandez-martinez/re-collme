package com.example.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormInfo {
    private String nombre;
    private String email;
    private Integer motivo;
    private String comentarios;
    private Boolean aceptarCondiciones;

}
