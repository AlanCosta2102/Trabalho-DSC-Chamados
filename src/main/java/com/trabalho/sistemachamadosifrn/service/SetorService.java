package com.trabalho.sistemachamadosifrn.service;

import com.trabalho.sistemachamadosifrn.dto.request.CriarSetorRequest;
import com.trabalho.sistemachamadosifrn.exception.RecursoNaoEncontradoException;
import com.trabalho.sistemachamadosifrn.exception.RegraNegocioException;
import com.trabalho.sistemachamadosifrn.model.Setor;
import com.trabalho.sistemachamadosifrn.repository.CategoriaRepository;
import com.trabalho.sistemachamadosifrn.repository.SetorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;
    private final CategoriaRepository categoriaRepository;

    public SetorService(
            SetorRepository setorRepository,
            CategoriaRepository categoriaRepository) {
        this.setorRepository = setorRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<Setor> listar(Pageable pageable) {
        return setorRepository.findAll(pageable);
    }

    public Setor buscarPorId(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Setor não encontrado: " + id));
    }

    @Transactional
    public Setor criar(CriarSetorRequest request) {
        if (setorRepository.existsBySigla(request.getSigla())) {
            throw new RegraNegocioException(
                    "Já existe um setor cadastrado com essa sigla.");
        }

        Setor setor = new Setor();
        setor.setNome(request.getNome());
        setor.setSigla(request.getSigla());

        return setorRepository.save(setor);
    }

    @Transactional
    public Setor atualizar(
            Long id,
            CriarSetorRequest request) {

        Setor setor = buscarPorId(id);

        if (setorRepository.existsBySiglaAndIdNot(
                request.getSigla(), id)) {
            throw new RegraNegocioException(
                    "A sigla informada já pertence a outro setor.");
        }

        setor.setNome(request.getNome());
        setor.setSigla(request.getSigla());

        return setorRepository.save(setor);
    }

    @Transactional
    public void excluir(Long id) {
        Setor setor = buscarPorId(id);

        if (categoriaRepository.existsBySetorId(id)) {
            throw new RegraNegocioException(
                    "Não é possível excluir um setor que possui categorias vinculadas.");
        }

        setorRepository.delete(setor);
    }
}