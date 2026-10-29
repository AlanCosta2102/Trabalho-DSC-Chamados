package com.trabalho.sistemachamadosifrn.service;

import com.trabalho.sistemachamadosifrn.dto.request.CriarCategoriaRequest;
import com.trabalho.sistemachamadosifrn.dto.response.CategoriaResponse;
import com.trabalho.sistemachamadosifrn.exception.RecursoNaoEncontradoException;
import com.trabalho.sistemachamadosifrn.exception.RegraNegocioException;
import com.trabalho.sistemachamadosifrn.model.Categoria;
import com.trabalho.sistemachamadosifrn.model.Setor;
import com.trabalho.sistemachamadosifrn.repository.CategoriaRepository;
import com.trabalho.sistemachamadosifrn.repository.ChamadoRepository;
import com.trabalho.sistemachamadosifrn.repository.SetorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final SetorRepository setorRepository;
    private final ChamadoRepository chamadoRepository;

    public CategoriaService(
            CategoriaRepository categoriaRepository,
            SetorRepository setorRepository,
            ChamadoRepository chamadoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.setorRepository = setorRepository;
        this.chamadoRepository = chamadoRepository;
    }

    @Transactional(readOnly = true)
    public Page<CategoriaResponse> listar(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(CategoriaResponse::new);
    }

    @Transactional(readOnly = true)
    public CategoriaResponse buscarPorId(Long id) {
        Categoria categoria = obterCategoria(id);
        return new CategoriaResponse(categoria);
    }

    @Transactional(readOnly = true)
    public Page<CategoriaResponse> listarPorSetor(
            Long setorId,
            Pageable pageable) {

        if (!setorRepository.existsById(setorId)) {
            throw new RecursoNaoEncontradoException(
                    "Setor não encontrado: " + setorId);
        }

        return categoriaRepository.findBySetorId(setorId, pageable)
                .map(CategoriaResponse::new);
    }

    @Transactional
    public CategoriaResponse criar(CriarCategoriaRequest request) {
        Setor setor = setorRepository.findById(request.getSetorId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Setor não encontrado: "
                                        + request.getSetorId()));

        Categoria categoria = new Categoria();
        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        categoria.setSetor(setor);

        Categoria salva = categoriaRepository.save(categoria);

        return new CategoriaResponse(salva);
    }

    @Transactional
    public CategoriaResponse atualizar(
            Long id,
            CriarCategoriaRequest request) {

        Categoria categoria = obterCategoria(id);

        Setor setor = setorRepository.findById(request.getSetorId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Setor não encontrado: "
                                        + request.getSetorId()));

        categoria.setNome(request.getNome());
        categoria.setDescricao(request.getDescricao());
        categoria.setSetor(setor);

        Categoria atualizada = categoriaRepository.save(categoria);

        return new CategoriaResponse(atualizada);
    }

    @Transactional
    public void excluir(Long id) {
        Categoria categoria = obterCategoria(id);

        if (chamadoRepository.existsByCategoriaId(id)) {
            throw new RegraNegocioException(
                    "Não é possível excluir uma categoria "
                            + "que possui chamados vinculados.");
        }

        categoriaRepository.delete(categoria);
    }

    private Categoria obterCategoria(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Categoria não encontrada: " + id));
    }
}