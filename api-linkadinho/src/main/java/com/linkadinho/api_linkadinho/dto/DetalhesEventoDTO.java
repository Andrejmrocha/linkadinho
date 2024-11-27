package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.evento.Evento;

import java.time.LocalDate;

public record DetalhesEventoDTO(Long id, String nome, LocalDate dataCadastro) {

    public DetalhesEventoDTO(Evento evento) {
        this(evento.getId(), evento.getNome(), evento.getData());
    }
}
