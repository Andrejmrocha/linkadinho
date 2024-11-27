package com.linkadinho.api_linkadinho.repository;

import com.linkadinho.api_linkadinho.model.feedback.Feedback;
import com.linkadinho.api_linkadinho.model.feedback.FeedbackUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT f FROM Feedback f WHERE f.usuarioRemetente.id = :idUsuario OR f.usuarioDestinatario.id = :idUsuario")
    Page<FeedbackUsuario> findAllByUsuarioId(@Param("idUsuario") Long idUsuario, Pageable paginacao);
}
