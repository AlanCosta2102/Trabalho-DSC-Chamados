package com.trabalho.sistemachamadosifrn.repository;

import com.trabalho.sistemachamadosifrn.model.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ChamadoRepository
        extends JpaRepository<Chamado, Long>,
        JpaSpecificationExecutor<Chamado> {

    boolean existsByCategoriaId(Long categoriaId);

    boolean existsByCategoriaSetorId(Long setorId);

    List<Chamado> findBySolicitanteId(Long solicitanteId);

    List<Chamado> findByTecnicoId(Long tecnicoId);
}