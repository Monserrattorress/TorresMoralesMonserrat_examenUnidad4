package com.ruleta.examenmoooonn.service;

import org.springframework.stereotype.Service;

import com.ruleta.examenmoooonn.entity.Jugador;
import com.ruleta.examenmoooonn.entity.Partida;
import com.ruleta.examenmoooonn.entity.Tiro;
import com.ruleta.examenmoooonn.entity.enums.EstadoPartida;
import com.ruleta.examenmoooonn.repository.JugadorRepository;
import com.ruleta.examenmoooonn.repository.PartidaRepository;
import com.ruleta.examenmoooonn.repository.TiroRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class JuegoService {

    private final JugadorRepository jugadorRepository;
    private final PartidaRepository partidaRepository;
    private final TiroRepository tiroRepository;
    private final Random random = new Random();

    public JuegoService(JugadorRepository jugadorRepository,
                        PartidaRepository partidaRepository,
                        TiroRepository tiroRepository) {
        this.jugadorRepository = jugadorRepository;
        this.partidaRepository = partidaRepository;
        this.tiroRepository = tiroRepository;
    }

    public Partida iniciarPartida(Long jugadorId, double apuesta) {
        Jugador jugador = jugadorRepository.findById(jugadorId)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado con id: " + jugadorId));

        if (!jugador.isActivo()) {
            throw new RuntimeException("El jugador se encuentra inactivo");
        }

        if (jugador.getSaldo() < apuesta) {
            throw new RuntimeException("El jugador no cuenta con saldo suficiente");
        }

        jugador.setSaldo(jugador.getSaldo() - apuesta);
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setFecha(LocalDateTime.now());
        partida.setApuesta(apuesta);
        partida.setEstado(EstadoPartida.EN_JUEGO);

        return partidaRepository.save(partida);
    }

    public Tiro realizarTiro(Long partidaId) {
        Partida partida = partidaRepository.findById(partidaId)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada con id: " + partidaId));

        if (partida.getEstado() == EstadoPartida.FINALIZADA) {
            throw new RuntimeException("No se pueden realizar tiros en una partida finalizada");
        }

        int dado1 = lanzarDado();
        int dado2 = lanzarDado();
        int suma = dado1 + dado2;

        boolean esGanador = false;

        if (suma == 7 || suma == 11) {
            esGanador = true;
            partida.setEstado(EstadoPartida.FINALIZADA);
            Jugador jugador = partida.getJugador();
            jugador.setSaldo(jugador.getSaldo() + partida.getApuesta() * 2);
            jugadorRepository.save(jugador);
        } else if (suma == 2 || suma == 3 || suma == 12) {
            partida.setEstado(EstadoPartida.FINALIZADA);
        }

        partidaRepository.save(partida);

        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(esGanador);

        return tiroRepository.save(tiro);
    }

}
