package com.linkadinho.api_linkadinho.service;

import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import com.linkadinho.api_linkadinho.dto.ListarUsuarioDTO;
import com.linkadinho.api_linkadinho.infra.exception.UsuarioNaoEncontradoException;
import com.linkadinho.api_linkadinho.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username);
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException("Usuário não encontrado"));
    }

    public Page<ListarUsuarioDTO> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(ListarUsuarioDTO::new);
    }

    public Page<ListarUsuarioDTO> listarSugestoesAmizade(Pageable pageable) {
        Long idOrganizacao = this.getUsuarioAutenticado().getEmpresa().getId();
        Long idUsuario = this.getUsuarioAutenticado().getId();
        return usuarioRepository.buscarSugestoesAmizade(pageable, idOrganizacao, idUsuario).map(ListarUsuarioDTO::new);
    }

    public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication == null) || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não autenticado");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Usuario) {
            return (Usuario) principal;
        }
        throw new RuntimeException("Principal não é uma instância de UserDetails!");

    }

    public Page<ListarUsuarioDTO> listarColegas(Pageable pageable) {
        Long idOrganizacao = this.getUsuarioAutenticado().getEmpresa().getId();
        return usuarioRepository.findByEmpresaId(pageable, idOrganizacao).map(ListarUsuarioDTO::new);
    }
}
