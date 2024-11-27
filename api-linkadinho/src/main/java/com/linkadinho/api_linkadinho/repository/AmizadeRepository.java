package com.linkadinho.api_linkadinho.repository;

import com.linkadinho.api_linkadinho.model.amizade.Amizade;
import com.linkadinho.api_linkadinho.model.amizade.StatusAmizade;
import com.linkadinho.api_linkadinho.model.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AmizadeRepository extends JpaRepository<Amizade, Long> {
    Page<Amizade> findByUsuarioDestinatarioIdAndStatus(Pageable pageable, Long id, StatusAmizade status);
    Page<Amizade> findByUsuarioDestinatarioIdOrUsuarioRemententeIdAndStatus(Pageable pageable, Long destinatarioId, Long remetenteId, StatusAmizade status);
    @Query("""
    SELECT u FROM Usuario u
    WHERE u.id IN (
        SELECT a.usuarioRementente.id FROM Amizade a WHERE a.usuarioDestinatario.id = :usuarioId AND a.status = 'ACEITA'
        UNION
        SELECT a.usuarioDestinatario.id FROM Amizade a WHERE a.usuarioRementente.id = :usuarioId AND a.status = 'ACEITA'
    )
""")
    Page<Usuario> buscarUsuariosAmigos(Pageable pageable, @Param("usuarioId") Long usuarioId);
//    List<Amizade> findByUsuarioRemetenteIdAndStatus(Long id, StatusAmizade status);
//    Optional<Amizade> findByUsuarioRemetenteIdAndUsuarioDestinatarioId(Long remetenteId, Long destinatarioId);
}
