package com.linkadinho.api_linkadinho.infra.exception;

public class EmpresaNaoEncontradaException extends RuntimeException{
    public EmpresaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
