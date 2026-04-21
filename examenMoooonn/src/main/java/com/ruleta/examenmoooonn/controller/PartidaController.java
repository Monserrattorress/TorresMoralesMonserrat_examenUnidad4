package com.ruleta.examenmoooonn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruleta.examenmoooonn.dto.NuevaPartidaRequest;
import com.ruleta.examenmoooonn.entity.Partida;
import com.ruleta.examenmoooonn.entity.Tiro;
import com.ruleta.examenmoooonn.service.JuegoService;

@RestController
@RequestMapping("/api/partidas")
public class PartidaController {

    private final JuegoService juegoService;

    public PartidaController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PostMapping("/nueva")
    public ResponseEntity<Partida> iniciarPartida(@RequestBody NuevaPartidaRequest request) {
        Partida partida = juegoService.iniciarPartida(request.getJugadorId(), request.getApuesta());
        return ResponseEntity.ok(partida);
    }

    @PostMapping("/{partidaId}/lanzar")
    public ResponseEntity<Tiro> realizarTiro(@PathVariable Long partidaId) {
        Tiro tiro = juegoService.realizarTiro(partidaId);
        return ResponseEntity.ok(tiro);
    }

    @PatchMapping("/{partidaId}/cerrar")
    public ResponseEntity<Partida> finalizarPartidaManual(@PathVariable Long partidaId) {
        Partida partida = juegoService.finalizarPartidaManual(partidaId);
        return ResponseEntity.ok(partida);
    }
}
