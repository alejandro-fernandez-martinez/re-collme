package com.example.demo.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "idResiduo")

@Entity
public class Residuo {
    @Id
    @GeneratedValue
    private Long idResiduo;
    private String nomResiduo;
    private String descripResiduo;
    private String codLer;
    private LocalDateTime fechaRegistroResiduo;

    private Long masaResiduoKg;
    private Long volumenResiduoM3;
    
    private String dirCalleResiduo;
    private Long dirNumResiduo;
    private Long dirCodPostalResiduo;
    private String dirLocalidadResiduo;
    private String dirProvinciaResiduo;

    private Boolean solicitado;
    @OneToOne
    private Usuario solicitante;

    private Boolean reservado;
    @OneToOne
    private Usuario gestor;
    
    private Boolean recogido;
    private Boolean bloqueado;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Categoria categoria;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario productor;

    // @ManyToOne
    // @OnDelete (action = OnDeleteAction.CASCADE)
    // private Ruta ruta;
}