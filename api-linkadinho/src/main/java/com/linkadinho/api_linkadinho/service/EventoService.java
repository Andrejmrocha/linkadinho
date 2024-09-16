package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import com.linkadinho.api_linkadinho.domain.evento.Evento;
import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.CadastrarEventoDTO;
import com.linkadinho.api_linkadinho.infra.exception.EventoNaoEncontradoException;
import com.linkadinho.api_linkadinho.repositories.EmpresaRepository;
import com.linkadinho.api_linkadinho.repositories.EventoRepository;
import com.linkadinho.api_linkadinho.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Evento cadastrarEvento(CadastrarEventoDTO dados, String emailUsuario) {
        Usuario usuario = (Usuario) usuarioRepository.findByEmail(emailUsuario);
        Empresa empresa = empresaRepository.findByNome(dados.empresa());
        Evento evento = new Evento(dados, empresa);

        return eventoRepository.save(evento);
    }

    public Evento buscarEvento(Long id) {
        return eventoRepository.findById(id).orElseThrow(() -> new EventoNaoEncontradoException("Evento não encontrado"));
    }
}
