package com.example.demo.domain;

import java.time.LocalDateTime;

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
@EqualsAndHashCode(of = "idVal")

@Entity
public class Valoraciones {
    @Id
    @GeneratedValue
    private Long idVal;

    @NotNull
    @Max(5)
    private Integer puntuacionVal;
    private String comentarioVal;
    private LocalDateTime fechaVal;

    @NotNull
    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario usuarioAValorar;

    @ManyToOne
    @OnDelete (action = OnDeleteAction.CASCADE)
    private Usuario usuarioQueValora;
}