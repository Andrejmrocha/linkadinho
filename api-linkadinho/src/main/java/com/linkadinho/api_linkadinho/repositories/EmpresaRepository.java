package com.linkadinho.api_linkadinho.repositories;

import com.linkadinho.api_linkadinho.domain.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByCodigo(String codigo);
}
