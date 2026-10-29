package com.trabalho.sistemachamadosifrn.repository;

import com.trabalho.sistemachamadosifrn.model.Interacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InteracaoRepository
        extends JpaRepository<Interacao, Long> {

    List<Interacao> findByChamadoIdOrderByDataHoraAsc(
            Long chamadoId);
}