package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import com.linkadinho.api_linkadinho.domain.evento.Evento;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public record DetalhesEventoDTO(Long id, String nome, LocalDate dataCadastro) {

    public DetalhesEventoDTO(Evento evento) {
        this(evento.getId(), evento.getNome(), evento.getData());
    }
}
