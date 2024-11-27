package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.model.feedback.FeedbackEvento;
import com.linkadinho.api_linkadinho.model.feedback.FeedbackUsuario;
import com.linkadinho.api_linkadinho.dto.*;
import com.linkadinho.api_linkadinho.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/evento")
    public ResponseEntity cadastrar(@RequestBody CadastrarFeedbackEventoDTO dados, UriComponentsBuilder uriBuilder) {
        FeedbackEvento feedbackEvento = feedbackService.cadastrarFeedbackEvento(dados,
                SecurityContextHolder.getContext().getAuthentication().getName());
        var uri = uriBuilder.path("feedback/evento/{id}").buildAndExpand(feedbackEvento.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesFeedbackEventoDTO(feedbackEvento));
    }

    @PostMapping("/usuario")
    public ResponseEntity cadastrar(@RequestBody CadastrarFeedbackUsuarioDTO dados, UriComponentsBuilder uriBuilder) {
        FeedbackUsuario feedbackUsuario = feedbackService.cadastrarFeedbackUsuario(dados,
                SecurityContextHolder.getContext().getAuthentication().getName());
        var uri = uriBuilder.path("feedback/usuario/{id}").buildAndExpand(feedbackUsuario.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesFeedbackUsuarioDTO(feedbackUsuario));
    }

    @GetMapping("/evento/{id}")
    public ResponseEntity detalharFeedbackEvento(@PathVariable Long id) {
        FeedbackEvento feedback = feedbackService.buscarFeedbackEvento(id);
        return ResponseEntity.ok(new DetalhesFeedbackEventoDTO(feedback));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity detalharFeedbackUsuario(@PathVariable Long id) {
        FeedbackUsuario feedback = feedbackService.buscarFeedbackUsuario(id);
        return ResponseEntity.ok(new DetalhesFeedbackUsuarioDTO(feedback));
    }

    @GetMapping("/usuario/lista/{idUsuario}")
    public ResponseEntity<Page<ListarFeedbackDTO>> listarFeedbacksPorUsuario(@PageableDefault(size = 10, sort = {"data"}, direction = Sort.Direction.DESC) Pageable paginacao, @PathVariable Long idUsuario) {
        var page = feedbackService.listarFeedbackPorUsuario(paginacao, idUsuario);
        return ResponseEntity.ok(page);
    }
}
