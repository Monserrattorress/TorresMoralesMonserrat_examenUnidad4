package com.ruleta.examenmoooonn.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tiro")
public class Tiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partida_id", nullable = false)
    private Partida partida;

    @Column(nullable = false)
    private int valorDado1;

    @Column(nullable = false)
    private int valorDado2;

    @Column(nullable = false)
    private int suma;

    @Column(nullable = false)
    private boolean esGanador;
}
