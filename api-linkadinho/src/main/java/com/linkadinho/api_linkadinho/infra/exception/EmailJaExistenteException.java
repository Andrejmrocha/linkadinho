package com.linkadinho.api_linkadinho.infra.exception;

public class EmailJaExistenteException extends RuntimeException{
    public EmailJaExistenteException(String mensagem) {
        super(mensagem);
    }
}
