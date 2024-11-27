package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.model.amizade.Amizade;
import com.linkadinho.api_linkadinho.model.amizade.StatusAmizade;
import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.DetalhesAmizadeDTO;
import com.linkadinho.api_linkadinho.dto.DetalhesUsuarioDTO;
import com.linkadinho.api_linkadinho.repository.AmizadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AmizadeService {

    @Autowired
    private AmizadeRepository amizadeRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Amizade enviarSolicitacao(Long remetenteId, Long destinatarioId) {
        var remetente = usuarioService.buscarUsuario(remetenteId);
        var destinatario = usuarioService.buscarUsuario(destinatarioId);
        Amizade amizade = new Amizade(remetente, destinatario, StatusAmizade.PENDENTE, LocalDate.now(), LocalDate.now());
        return amizadeRepository.save(amizade);
    }

    public Amizade aceitarSolicitacao(Long idAmizade) {
        Amizade amizade = amizadeRepository.getReferenceById(idAmizade);
        amizade.setStatus(StatusAmizade.ACEITA);
        amizade.setDataAtualizacao(LocalDate.now());
        return amizadeRepository.save(amizade);
    }

    public Amizade recusarSolicitacao(Long idAmizade) {
        Amizade amizade = amizadeRepository.getReferenceById(idAmizade);
        amizade.setStatus(StatusAmizade.RECUSADA);
        amizade.setDataAtualizacao(LocalDate.now());
        return amizadeRepository.save(amizade);
    }

    public Page<DetalhesAmizadeDTO> listarSolicitacoesRecebidas(Pageable pageable, Long destinatarioId) {
        return amizadeRepository.findByUsuarioDestinatarioIdAndStatus(pageable, destinatarioId, StatusAmizade.PENDENTE).map(DetalhesAmizadeDTO::new);
    }

    public Page<DetalhesAmizadeDTO> listarAmizades(Pageable pageable) {
        Long usuarioId = usuarioService.getUsuarioAutenticado().getId();
        return amizadeRepository.findByUsuarioDestinatarioIdOrUsuarioRemententeIdAndStatus(
                pageable, usuarioId, usuarioId, StatusAmizade.ACEITA).map(DetalhesAmizadeDTO::new);
    }

    public Page<DetalhesUsuarioDTO> listarAmigos(Pageable pageable) {
        Long usuarioId = usuarioService.getUsuarioAutenticado().getId();
        Page<Usuario> usuariosAmigos = amizadeRepository.buscarUsuariosAmigos(pageable, usuarioId);
        return usuariosAmigos.map(DetalhesUsuarioDTO::new); // DetalhesUsuarioDTO seria um DTO com as informações do usuário amigo
    }

}
