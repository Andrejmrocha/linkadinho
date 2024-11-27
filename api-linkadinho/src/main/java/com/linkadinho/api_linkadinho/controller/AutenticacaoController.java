package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.*;
import com.linkadinho.api_linkadinho.infra.exception.TokenInvalidoException;
import com.linkadinho.api_linkadinho.service.AutenticacaoService;
import com.linkadinho.api_linkadinho.service.RegistroService;
import com.linkadinho.api_linkadinho.service.TokenService;
import com.linkadinho.api_linkadinho.service.UsuarioService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("autenticacao")
public class AutenticacaoController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private RegistroService registroService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO dados) {
        RespostaLoginDTO resposta = autenticacaoService.login(dados);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/registrar")
    public ResponseEntity registrar(@RequestBody RegistrarUsuarioDTO dados, UriComponentsBuilder uriBuilder) {
        Usuario usuario = registroService.registrar(dados);
       var uri = uriBuilder.path("autenticacao/{id}").buildAndExpand(usuario.getId()).toUri();
       return ResponseEntity.created(uri).body(new DetalhesUsuarioDTO(usuario));
    }

    @PutMapping(consumes = "multipart/form-data")
    @Transactional
    public ResponseEntity atualizar(@RequestParam(value = "nome", required = false) String nome,
                                    @RequestParam(value = "sobrenome", required = false) String sobrenome,
                                    @RequestParam(value = "fotoPerfil", required = false) MultipartFile fotoPerfil,
                                    @RequestParam(value = "id") Long id){
        return ResponseEntity.ok(new DetalhesUsuarioDTO(registroService.atualizar(new AtualizarUsuarioDTO(id, nome, sobrenome, fotoPerfil))));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id) {
        var usuario = usuarioService.buscarUsuario(id);
        return ResponseEntity.ok(new DetalhesUsuarioDTO(usuario));
    }

    @PostMapping("/verificarToken")
    public ResponseEntity<Map<String, Object>> verificarToken(@RequestHeader("Authorization") String token) {
        try {
            TokenInfo tokenInfo = tokenService.validarToken(token.replace("Bearer ", ""));
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("valid", true);
            resposta.put("subject", tokenInfo.subject());
            resposta.put("role", tokenInfo.role());
            resposta.put("organization", tokenInfo.organization());
            resposta.put("id", tokenInfo.id());
            return ResponseEntity.ok(resposta);
        } catch (TokenInvalidoException e) {
            Map<String, Object> resposta = new HashMap<>();
            resposta.put("valid", false);
            return ResponseEntity.ok(resposta);

        }
    }

    @PostMapping("/atualizarToken")
    public ResponseEntity<Map<String, Object>> refreshToken(@RequestHeader("Authorization") String token) {
        Map<String, Object> resposta = new HashMap<>();
        try {
            // Remove o prefixo "Bearer " do token
            String tokenSemBearer = token.replace("Bearer ", "");

            // Valida o token e verifica se ele está próximo de expirar
            if (tokenService.isTokenExpiringSoon(tokenSemBearer)) {
                String novoToken = tokenService.gerarNovoToken(tokenSemBearer);
                resposta.put("token", novoToken);
            } else {
                resposta.put("token", tokenSemBearer); // Retorna o token atual se ainda for válido
            }

            resposta.put("valid", true);
            return ResponseEntity.ok(resposta);
        } catch (TokenInvalidoException e) {
            resposta.put("valid", false);
            return ResponseEntity.status(401).body(resposta);
        }
    }

    @GetMapping
    public ResponseEntity<Page<ListarUsuarioDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = usuarioService.listar(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listarSugestoes")
    public ResponseEntity<Page<ListarUsuarioDTO>> listarSugestoesAmizade(@PageableDefault(size = 4, sort = {"nome"}) Pageable paginacao) {
        var page = usuarioService.listarSugestoesAmizade(paginacao);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/listarColegas")
    public ResponseEntity<Page<ListarUsuarioDTO>> listarColegas(@PageableDefault(size=20, sort = {"nome"}) Pageable pageable) {
        var page = usuarioService.listarColegas(pageable);
        return ResponseEntity.ok(page);
    }
}
