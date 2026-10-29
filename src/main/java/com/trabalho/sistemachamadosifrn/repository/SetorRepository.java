package com.trabalho.sistemachamadosifrn.repository;

import com.trabalho.sistemachamadosifrn.model.Setor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetorRepository extends JpaRepository<Setor, Long> {

    boolean existsBySigla(String sigla);

    boolean existsBySiglaAndIdNot(String sigla, Long id);
}