package com.trabalho.sistemachamadosifrn.repository;

import com.trabalho.sistemachamadosifrn.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository
        extends JpaRepository<Categoria, Long> {

    Page<Categoria> findBySetorId(Long setorId, Pageable pageable);

    boolean existsByNomeAndSetorId(String nome, Long setorId);

    boolean existsBySetorId(Long setorId);
}