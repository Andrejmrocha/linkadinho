package com.linkadinho.api_linkadinho.dto;

import com.linkadinho.api_linkadinho.model.feedback.FeedbackUsuario;

import java.time.LocalDate;

public record ListarFeedbackDTO(String comentario, String assunto ,LocalDate data, UsuarioFeedbackDTO remetente, UsuarioFeedbackDTO destinatario) {
    public ListarFeedbackDTO(FeedbackUsuario feedback) {
        this(
                feedback.getComentario(),
                feedback.getAssunto(),
                feedback.getData(),
                new UsuarioFeedbackDTO(feedback.getUsuarioRemetente()),
                new UsuarioFeedbackDTO(feedback.getUsuarioDestinatario())
                );
    }
}
