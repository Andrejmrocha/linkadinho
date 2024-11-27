package com.linkadinho.api_linkadinho.repository;

import com.linkadinho.api_linkadinho.model.evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findById(Long id);

    Evento findByNome(String nome);
}
