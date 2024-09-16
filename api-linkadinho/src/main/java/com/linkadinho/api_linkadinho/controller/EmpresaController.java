package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.domain.empresa.DadosAtualizarEmpresa;
import com.linkadinho.api_linkadinho.domain.empresa.DadosCadastroEmpresa;
import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import com.linkadinho.api_linkadinho.dto.DetalhesEmpresaDTO;
import com.linkadinho.api_linkadinho.service.EmpresaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService service;

    @PostMapping(consumes = "multipart/form-data")
    @Transactional
    public ResponseEntity cadastrar(@RequestParam("nome") String nome,
                                             @RequestParam(value= "imagem", required = false)MultipartFile imagem,
                                             UriComponentsBuilder uriBuilder){
        DadosCadastroEmpresa dtoCadastroEmpresa = new DadosCadastroEmpresa(nome, imagem);
        Empresa empresa = service.cadastrarEmpresa(dtoCadastroEmpresa,
                SecurityContextHolder.getContext().getAuthentication().getName());
        var uri = uriBuilder.path("empresa/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesEmpresaDTO(empresa));

    }

    @PutMapping(consumes = "multipart/form-data")
    @Transactional
    public ResponseEntity<Empresa> atualizar(@RequestParam(value = "nome", required = false) String nome,
                                             @RequestParam(value = "imagem", required = false) MultipartFile imagem,
                                             @RequestParam("id") Long id){
        return ResponseEntity.ok(service.atualizarEmpresa(new DadosAtualizarEmpresa(id, nome, imagem)));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var empresa = service.buscarEmpresa(id);
        return ResponseEntity.ok(new DetalhesEmpresaDTO(empresa));
    }
}
