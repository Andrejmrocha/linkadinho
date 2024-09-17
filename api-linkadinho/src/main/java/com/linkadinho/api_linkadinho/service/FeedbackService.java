package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.domain.evento.Evento;
import com.linkadinho.api_linkadinho.domain.feedback.Feedback;
import com.linkadinho.api_linkadinho.domain.feedback.FeedbackEvento;
import com.linkadinho.api_linkadinho.domain.feedback.FeedbackUsuario;
import com.linkadinho.api_linkadinho.domain.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.CadastrarFeedbackEventoDTO;
import com.linkadinho.api_linkadinho.dto.CadastrarFeedbackUsuarioDTO;
import com.linkadinho.api_linkadinho.infra.exception.FeedbackNaoEncontradoException;
import com.linkadinho.api_linkadinho.repositories.EventoRepository;
import com.linkadinho.api_linkadinho.repositories.FeedbackRepository;
import com.linkadinho.api_linkadinho.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventoRepository eventoRepository;

    public FeedbackEvento cadastrarFeedbackEvento(CadastrarFeedbackEventoDTO dados, String emailUsuario){
        Usuario usuarioRementente = (Usuario) usuarioRepository.findByEmail(emailUsuario);
        Evento evento = eventoRepository.findByNome(dados.nomeEvento());

        FeedbackEvento feedback = new FeedbackEvento(dados, usuarioRementente, evento);
        return feedbackRepository.save(feedback);
    }

    public FeedbackEvento buscarFeedbackEvento(Long id) {
        return (FeedbackEvento) feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNaoEncontradoException("Feedback não encontrado"));
    }

    public FeedbackUsuario cadastrarFeedbackUsuario(CadastrarFeedbackUsuarioDTO dados, String emailUsuario){
        Usuario usuarioRementente = (Usuario) usuarioRepository.findByEmail(emailUsuario);
        Usuario usuarioDestinatario = (Usuario) usuarioRepository.findByEmail(dados.emailUsuarioDestinatario());
        FeedbackUsuario feedback = new FeedbackUsuario(dados, usuarioRementente, usuarioDestinatario);
        return feedbackRepository.save(feedback);
    }

    public FeedbackUsuario buscarFeedbackUsuario(Long id) {
        return (FeedbackUsuario) feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNaoEncontradoException("Feedback não encontrado"));
    }
}
