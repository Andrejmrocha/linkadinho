package com.linkadinho.api_linkadinho.infra.exception;

public class EventoNaoEncontradoException extends RuntimeException {
    public EventoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
