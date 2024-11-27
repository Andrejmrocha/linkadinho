package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.feedback.FeedbackEvento;

import java.time.LocalDate;

public record DetalhesFeedbackEventoDTO(String comentario, LocalDate data, String remetente, String evento) {
    public DetalhesFeedbackEventoDTO(FeedbackEvento feedbackEvento) {
        this(feedbackEvento.getComentario(), feedbackEvento.getData(), feedbackEvento.getUsuarioRemetente().getNome(), feedbackEvento.getEvento().getNome());
    }
}
