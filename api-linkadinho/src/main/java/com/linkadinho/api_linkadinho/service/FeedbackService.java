package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.model.evento.Evento;
import com.linkadinho.api_linkadinho.model.feedback.FeedbackEvento;
import com.linkadinho.api_linkadinho.model.feedback.FeedbackUsuario;
import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.CadastrarFeedbackEventoDTO;
import com.linkadinho.api_linkadinho.dto.CadastrarFeedbackUsuarioDTO;
import com.linkadinho.api_linkadinho.dto.ListarFeedbackDTO;
import com.linkadinho.api_linkadinho.infra.exception.FeedbackNaoEncontradoException;
import com.linkadinho.api_linkadinho.repository.EventoRepository;
import com.linkadinho.api_linkadinho.repository.FeedbackRepository;
import com.linkadinho.api_linkadinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Optional<Usuario> usuarioDestinatario = usuarioRepository.findById(dados.idUsuarioDestinatario());
        if (usuarioDestinatario.isPresent()) {
            FeedbackUsuario feedback = new FeedbackUsuario(dados, usuarioRementente, usuarioDestinatario.get());
            return feedbackRepository.save(feedback);
        }
        return null;
    }

    public FeedbackUsuario buscarFeedbackUsuario(Long id) {
        return (FeedbackUsuario) feedbackRepository.findById(id).orElseThrow(() -> new FeedbackNaoEncontradoException("Feedback não encontrado"));
    }

    public Page<ListarFeedbackDTO> listarFeedbackPorUsuario(Pageable paginacao, Long idUsuario) {
        return feedbackRepository.findAllByUsuarioId(idUsuario, paginacao).map(ListarFeedbackDTO::new);
    }

}
