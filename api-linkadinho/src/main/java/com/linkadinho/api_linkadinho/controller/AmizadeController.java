package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.model.amizade.Amizade;
import com.linkadinho.api_linkadinho.dto.DetalhesAmizadeDTO;
import com.linkadinho.api_linkadinho.dto.DetalhesUsuarioDTO;
import com.linkadinho.api_linkadinho.dto.SolicitacaoAmizadeDTO;
import com.linkadinho.api_linkadinho.service.AmizadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/amizades")
public class AmizadeController {

    @Autowired
    private AmizadeService amizadeService;

    @PostMapping("/enviar")
    public ResponseEntity<DetalhesAmizadeDTO> enviarSolicitacao(@RequestBody SolicitacaoAmizadeDTO dadosSolicitacao) {
        Amizade amizade = amizadeService.enviarSolicitacao(dadosSolicitacao.remetenteId(), dadosSolicitacao.destinatarioId());
        return ResponseEntity.ok(new DetalhesAmizadeDTO(amizade));
    }

    @PostMapping("/aceitar/{idAmizade}")
    public ResponseEntity<DetalhesAmizadeDTO> aceitarSolicitacao(@PathVariable Long idAmizade) {
        Amizade amizade = amizadeService.aceitarSolicitacao(idAmizade);
        return ResponseEntity.ok(new DetalhesAmizadeDTO(amizade));
    }

    @PostMapping("/recusar/{idAmizade}")
    public ResponseEntity<DetalhesAmizadeDTO> recusarSolicitacao(@PathVariable Long idAmizade) {
        Amizade amizade = amizadeService.recusarSolicitacao(idAmizade);
        return ResponseEntity.ok(new DetalhesAmizadeDTO(amizade));
    }

    @GetMapping("/solicitacoes/recebidas/{destinatarioId}")
    public ResponseEntity<Page<DetalhesAmizadeDTO>> listarSolicitacoesRecebidas(@PathVariable Long destinatarioId, Pageable pageable) {
        var page = amizadeService.listarSolicitacoesRecebidas(pageable, destinatarioId);
        return ResponseEntity.ok(page);
    }

    @GetMapping
    public ResponseEntity<Page<DetalhesUsuarioDTO>> listarAmizades(Pageable pageable){
        var page = amizadeService.listarAmigos(pageable);
        return ResponseEntity.ok(page);
    }
}
