package com.trabalho.sistemachamadosifrn.service;

import com.trabalho.sistemachamadosifrn.dto.request.AssumirChamadoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.CancelarChamadoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.CriarChamadoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.InteracaoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.ResolverChamadoRequest;
import com.trabalho.sistemachamadosifrn.exception.RecursoNaoEncontradoException;
import com.trabalho.sistemachamadosifrn.exception.RegraNegocioException;
import com.trabalho.sistemachamadosifrn.model.Categoria;
import com.trabalho.sistemachamadosifrn.model.Chamado;
import com.trabalho.sistemachamadosifrn.model.Interacao;
import com.trabalho.sistemachamadosifrn.model.Usuario;
import com.trabalho.sistemachamadosifrn.model.enums.Prioridade;
import com.trabalho.sistemachamadosifrn.model.enums.StatusChamado;
import com.trabalho.sistemachamadosifrn.model.enums.TipoUsuario;
import com.trabalho.sistemachamadosifrn.repository.CategoriaRepository;
import com.trabalho.sistemachamadosifrn.repository.ChamadoRepository;
import com.trabalho.sistemachamadosifrn.repository.ChamadoSpecifications;
import com.trabalho.sistemachamadosifrn.repository.InteracaoRepository;
import com.trabalho.sistemachamadosifrn.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final InteracaoRepository interacaoRepository;

    public ChamadoService(
            ChamadoRepository chamadoRepository,
            UsuarioRepository usuarioRepository,
            CategoriaRepository categoriaRepository,
            InteracaoRepository interacaoRepository) {

        this.chamadoRepository = chamadoRepository;
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.interacaoRepository = interacaoRepository;
    }

    public Page<Chamado> listar(Pageable pageable) {
        return chamadoRepository.findAll(pageable);
    }

    public Page<Chamado> listarFiltrados(
            StatusChamado status,
            Prioridade prioridade,
            Long setorId,
            Long categoriaId,
            Long solicitanteId,
            Long tecnicoId,
            LocalDateTime dataInicio,
            LocalDateTime dataFim,
            Pageable pageable) {

        Specification<Chamado> filtros =
                ChamadoSpecifications.porStatus(status)
                        .and(ChamadoSpecifications.porPrioridade(prioridade))
                        .and(ChamadoSpecifications.porSetor(setorId))
                        .and(ChamadoSpecifications.porCategoria(categoriaId))
                        .and(ChamadoSpecifications.porSolicitante(solicitanteId))
                        .and(ChamadoSpecifications.porTecnico(tecnicoId))
                        .and(ChamadoSpecifications.abertoApos(dataInicio))
                        .and(ChamadoSpecifications.abertoAntes(dataFim));

        return chamadoRepository.findAll(filtros, pageable);
    }

    public Chamado buscarPorId(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Chamado não encontrado: " + id));
    }

    public Page<Chamado> listarPorSolicitante(
            Long solicitanteId,
            Pageable pageable) {

        if (!usuarioRepository.existsById(solicitanteId)) {
            throw new RecursoNaoEncontradoException(
                    "Usuário não encontrado: " + solicitanteId);
        }

        return chamadoRepository.findAll(
                (root, query, cb) ->
                        cb.equal(
                                root.get("solicitante").get("id"),
                                solicitanteId),
                pageable);
    }

    @Transactional
    public Chamado criar(CriarChamadoRequest request) {

        Usuario solicitante = usuarioRepository
                .findById(request.getSolicitanteId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Solicitante não encontrado."));

        if (solicitante.getTipo() != TipoUsuario.SOLICITANTE) {
            throw new RegraNegocioException(
                    "Somente usuários SOLICITANTE podem abrir chamados.");
        }

        Categoria categoria = categoriaRepository
                .findById(request.getCategoriaId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Categoria não encontrada."));

        Chamado chamado = new Chamado();

        chamado.setTitulo(request.getTitulo());
        chamado.setDescricao(request.getDescricao());
        chamado.setLocal(request.getLocal());
        chamado.setSolicitante(solicitante);
        chamado.setCategoria(categoria);
        chamado.setStatus(StatusChamado.ABERTO);
        chamado.setPrioridade(
                request.getPrioridade() == null
                        ? Prioridade.MEDIA
                        : request.getPrioridade());
        chamado.setDataAbertura(LocalDateTime.now());

        return chamadoRepository.save(chamado);
    }

    @Transactional
    public Chamado assumir(
            Long chamadoId,
            AssumirChamadoRequest request) {

        Chamado chamado = buscarPorId(chamadoId);

        Usuario tecnico = usuarioRepository
                .findById(request.getTecnicoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Técnico não encontrado."));

        if (tecnico.getTipo() != TipoUsuario.TECNICO) {
            throw new RegraNegocioException(
                    "Somente usuários TECNICO podem assumir chamados.");
        }

        if (chamado.getStatus() != StatusChamado.ABERTO) {
            throw new RegraNegocioException(
                    "Somente chamados ABERTOS podem ser assumidos.");
        }

        if (tecnico.getSetor() == null
                || chamado.getCategoria().getSetor() == null
                || !tecnico.getSetor().getId().equals(
                chamado.getCategoria().getSetor().getId())) {

            throw new RegraNegocioException(
                    "O técnico só pode assumir chamados do próprio setor.");
        }

        chamado.setTecnico(tecnico);
        chamado.setStatus(StatusChamado.EM_ATENDIMENTO);

        return chamadoRepository.save(chamado);
    }

    @Transactional
    public Chamado resolver(
            Long chamadoId,
            ResolverChamadoRequest request) {

        Chamado chamado = buscarPorId(chamadoId);

        Usuario tecnico = usuarioRepository
                .findById(request.getTecnicoId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Técnico não encontrado."));

        if (tecnico.getTipo() != TipoUsuario.TECNICO) {
            throw new RegraNegocioException(
                    "Somente um técnico pode resolver o chamado.");
        }

        if (chamado.getStatus() != StatusChamado.EM_ATENDIMENTO) {
            throw new RegraNegocioException(
                    "Somente chamados EM_ATENDIMENTO podem ser resolvidos.");
        }

        if (chamado.getTecnico() == null
                || !chamado.getTecnico().getId()
                .equals(tecnico.getId())) {

            throw new RegraNegocioException(
                    "Somente o técnico atribuído pode resolver o chamado.");
        }

        if (request.getSolucao() == null
                || request.getSolucao().isBlank()) {

            throw new RegraNegocioException(
                    "A solução do chamado é obrigatória.");
        }

        chamado.setSolucao(request.getSolucao());
        chamado.setStatus(StatusChamado.RESOLVIDO);
        chamado.setDataFechamento(LocalDateTime.now());

        return chamadoRepository.save(chamado);
    }

    @Transactional
    public Chamado cancelar(
            Long chamadoId,
            CancelarChamadoRequest request) {

        Chamado chamado = buscarPorId(chamadoId);

        Usuario solicitante = usuarioRepository
                .findById(request.getSolicitanteId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Solicitante não encontrado."));

        if (solicitante.getTipo() != TipoUsuario.SOLICITANTE
                || !chamado.getSolicitante().getId()
                .equals(solicitante.getId())) {

            throw new RegraNegocioException(
                    "Somente o solicitante original pode cancelar o chamado.");
        }

        if (chamado.getStatus() != StatusChamado.ABERTO) {
            throw new RegraNegocioException(
                    "Somente chamados ABERTOS podem ser cancelados.");
        }

        chamado.setStatus(StatusChamado.CANCELADO);
        chamado.setDataFechamento(LocalDateTime.now());

        return chamadoRepository.save(chamado);
    }

    @Transactional
    public Interacao interagir(
            Long chamadoId,
            InteracaoRequest request) {

        Chamado chamado = buscarPorId(chamadoId);

        if (chamado.getStatus() == StatusChamado.RESOLVIDO
                || chamado.getStatus() == StatusChamado.CANCELADO) {

            throw new RegraNegocioException(
                    "Chamados encerrados não aceitam novas interações.");
        }

        Usuario autor = usuarioRepository
                .findById(request.getAutorId())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Autor da interação não encontrado."));

        boolean ehSolicitante =
                chamado.getSolicitante().getId()
                        .equals(autor.getId());

        boolean ehTecnicoAtribuido =
                chamado.getTecnico() != null
                        && chamado.getTecnico().getId()
                        .equals(autor.getId());

        if (!ehSolicitante && !ehTecnicoAtribuido) {
            throw new RegraNegocioException(
                    "Somente o solicitante ou o técnico atribuído "
                            + "podem interagir.");
        }

        if (request.getTexto() == null
                || request.getTexto().isBlank()) {

            throw new RegraNegocioException(
                    "O texto da interação é obrigatório.");
        }

        Interacao interacao = new Interacao();
        interacao.setTexto(request.getTexto());
        interacao.setDataHora(LocalDateTime.now());
        interacao.setChamado(chamado);
        interacao.setAutor(autor);

        return interacaoRepository.save(interacao);
    }
}