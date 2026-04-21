package com.ruleta.examenmoooonn.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ruleta.examenmoooonn.entity.Partida;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByJugador_IdOrderByFechaDesc(Long jugadorId);
}
