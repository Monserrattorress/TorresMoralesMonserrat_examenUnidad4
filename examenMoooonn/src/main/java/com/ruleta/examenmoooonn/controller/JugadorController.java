package com.ruleta.examenmoooonn.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ruleta.examenmoooonn.dto.RecargaRequest;
import com.ruleta.examenmoooonn.entity.Jugador;
import com.ruleta.examenmoooonn.entity.Partida;
import com.ruleta.examenmoooonn.service.JuegoService;

import java.util.List;

@RestController
@RequestMapping("/api/jugadores")
public class JugadorController {

    private final JuegoService juegoService;

    public JugadorController(JuegoService juegoService) {
        this.juegoService = juegoService;
    }

    @PatchMapping("/{jugadorId}/recargar")
    public ResponseEntity<Jugador> recargarSaldo(@PathVariable Long jugadorId,
                                                  @RequestBody RecargaRequest request) {
        Jugador jugador = juegoService.recargarSaldo(jugadorId, request.getMonto());
        return ResponseEntity.ok(jugador);
    }

    @GetMapping("/{jugadorId}/partidas")
    public ResponseEntity<List<Partida>> obtenerHistorial(@PathVariable Long jugadorId) {
        List<Partida> historial = juegoService.obtenerHistorialPartidas(jugadorId);
        return ResponseEntity.ok(historial);
    }
}
