package com.linkadinho.api_linkadinho.repository;

import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
    Page<Usuario> findByEmpresaId(Pageable pageable, Long id);
    @Query("""
            SELECT u FROM Usuario u
            WHERE u.empresa.id = :empresaId
                AND u.id != :usuarioId
                AND NOT EXISTS (
                    SELECT a FROM Amizade a
                    WHERE ((a.usuarioRementente.id = :usuarioId AND a.usuarioDestinatario.id = u.id)
                        OR (a.usuarioRementente.id = u.id AND a.usuarioDestinatario.id = :usuarioId))
                        AND a.status IN ('PENDENTE','ACEITA')
            )
            """)
    Page<Usuario> buscarSugestoesAmizade(
            Pageable pageable, @Param("empresaId") Long empresaId, @Param("usuarioId") Long usuarioId);
}
