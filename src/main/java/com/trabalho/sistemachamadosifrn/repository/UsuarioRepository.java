package com.trabalho.sistemachamadosifrn.repository;

import com.trabalho.sistemachamadosifrn.model.Usuario;
import com.trabalho.sistemachamadosifrn.model.enums.TipoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

    Page<Usuario> findByNomeContainingIgnoreCase(
            String nome,
            Pageable pageable);

    Page<Usuario> findByTipo(
            TipoUsuario tipo,
            Pageable pageable);

    Page<Usuario> findBySetorId(
            Long setorId,
            Pageable pageable);
}