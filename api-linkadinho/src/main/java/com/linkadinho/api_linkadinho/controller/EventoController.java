package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.domain.evento.Evento;
import com.linkadinho.api_linkadinho.dto.AtualizarEventoDTO;
import com.linkadinho.api_linkadinho.dto.CadastrarEventoDTO;
import com.linkadinho.api_linkadinho.dto.DetalhesEventoDTO;
import com.linkadinho.api_linkadinho.service.EventoService;
import com.linkadinho.api_linkadinho.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody CadastrarEventoDTO dados, UriComponentsBuilder uriBuilder){
        Evento evento = eventoService.cadastrarEvento(dados,
                SecurityContextHolder.getContext().getAuthentication().getName());
        var uri = uriBuilder.path("evento/{id}").buildAndExpand(evento.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesEventoDTO(evento));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody AtualizarEventoDTO dados) {
        var evento = eventoService.atualizarEvento(dados);
        return ResponseEntity.ok(new DetalhesEventoDTO(evento));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var evento = eventoService.buscarEvento(id);
        return ResponseEntity.ok(new DetalhesEventoDTO(evento));
    }
}
