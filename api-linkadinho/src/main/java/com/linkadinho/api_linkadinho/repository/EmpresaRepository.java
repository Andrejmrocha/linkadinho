package com.linkadinho.api_linkadinho.repository;

import com.linkadinho.api_linkadinho.model.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsByCodigo(String codigo);

    Empresa findByNome(String empresa);
}
