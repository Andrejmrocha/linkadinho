package com.linkadinho.api_linkadinho.controller;

import com.linkadinho.api_linkadinho.dto.AtualizarEmpresaDTO;
import com.linkadinho.api_linkadinho.dto.CadastrarEmpresaDTO;
import com.linkadinho.api_linkadinho.model.empresa.Empresa;
import com.linkadinho.api_linkadinho.dto.DetalhesEmpresaDTO;
import com.linkadinho.api_linkadinho.dto.ListarEmpresaDTO;
import com.linkadinho.api_linkadinho.service.EmpresaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @PostMapping(consumes = "multipart/form-data") @Transactional
    public ResponseEntity cadastrar(@RequestParam("nome") String nome,
                                             @RequestParam(value= "imagem", required = false)MultipartFile imagem,
                                             UriComponentsBuilder uriBuilder){
        CadastrarEmpresaDTO dtoCadastroEmpresa = new CadastrarEmpresaDTO(nome, imagem);
        Empresa empresa = service.cadastrarEmpresa(dtoCadastroEmpresa,
                SecurityContextHolder.getContext().getAuthentication().getName());
        var uri = uriBuilder.path("empresa/{id}").buildAndExpand(empresa.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalhesEmpresaDTO(empresa));

    }

    @PutMapping(consumes = "multipart/form-data") @Transactional
    public ResponseEntity<DetalhesEmpresaDTO> atualizar(@RequestParam(value = "nome", required = false) String nome,
                                             @RequestParam(value = "imagem", required = false)MultipartFile imagem,
                                             @RequestParam("id") Long id){
        return ResponseEntity.ok(service.atualizarEmpresa(new AtualizarEmpresaDTO(id, nome, imagem)));
    }

    @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var empresa = service.buscarEmpresa(id);
        return ResponseEntity.ok(new DetalhesEmpresaDTO(empresa));
    }

    @GetMapping
    public ResponseEntity<Page<ListarEmpresaDTO>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = service.listar(paginacao);
        return ResponseEntity.ok(page);
    }

}
