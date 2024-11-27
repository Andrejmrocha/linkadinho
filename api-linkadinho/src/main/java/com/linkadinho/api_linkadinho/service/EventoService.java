package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.model.empresa.Empresa;
import com.linkadinho.api_linkadinho.model.evento.Evento;
import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.AtualizarEventoDTO;
import com.linkadinho.api_linkadinho.dto.CadastrarEventoDTO;
import com.linkadinho.api_linkadinho.infra.exception.EventoNaoEncontradoException;
import com.linkadinho.api_linkadinho.repository.EmpresaRepository;
import com.linkadinho.api_linkadinho.repository.EventoRepository;
import com.linkadinho.api_linkadinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Evento atualizarEvento(AtualizarEventoDTO dados) {
        Evento evento = eventoRepository.findById(dados.id()).orElseThrow(() -> new EventoNaoEncontradoException("Evento não encontrado"));
        evento.atualizarInformacoes(dados);
        return evento;
    }
}
