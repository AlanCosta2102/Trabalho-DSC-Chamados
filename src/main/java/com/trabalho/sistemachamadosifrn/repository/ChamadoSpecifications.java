package com.trabalho.sistemachamadosifrn.repository;

import com.trabalho.sistemachamadosifrn.model.Chamado;
import com.trabalho.sistemachamadosifrn.model.enums.Prioridade;
import com.trabalho.sistemachamadosifrn.model.enums.StatusChamado;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public final class ChamadoSpecifications {

    private ChamadoSpecifications() {
    }

    public static Specification<Chamado> porStatus(
            StatusChamado status) {

        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.equal(root.get("status"), status);
    }

    public static Specification<Chamado> porPrioridade(
            Prioridade prioridade) {

        return (root, query, cb) ->
                prioridade == null
                        ? cb.conjunction()
                        : cb.equal(
                        root.get("prioridade"),
                        prioridade);
    }

    public static Specification<Chamado> porCategoria(
            Long categoriaId) {

        return (root, query, cb) ->
                categoriaId == null
                        ? cb.conjunction()
                        : cb.equal(
                        root.get("categoria").get("id"),
                        categoriaId);
    }

    public static Specification<Chamado> porSolicitante(
            Long solicitanteId) {

        return (root, query, cb) ->
                solicitanteId == null
                        ? cb.conjunction()
                        : cb.equal(
                        root.get("solicitante").get("id"),
                        solicitanteId);
    }

    public static Specification<Chamado> porTecnico(
            Long tecnicoId) {

        return (root, query, cb) ->
                tecnicoId == null
                        ? cb.conjunction()
                        : cb.equal(
                        root.get("tecnico").get("id"),
                        tecnicoId);
    }

    public static Specification<Chamado> porSetor(
            Long setorId) {

        return (root, query, cb) ->
                setorId == null
                        ? cb.conjunction()
                        : cb.equal(
                        root.get("categoria")
                                .get("setor")
                                .get("id"),
                        setorId);
    }

    public static Specification<Chamado> abertoApos(
            LocalDateTime dataInicio) {

        return (root, query, cb) ->
                dataInicio == null
                        ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(
                        root.get("dataAbertura"),
                        dataInicio);
    }

    public static Specification<Chamado> abertoAntes(
            LocalDateTime dataFim) {

        return (root, query, cb) ->
                dataFim == null
                        ? cb.conjunction()
                        : cb.lessThanOrEqualTo(
                        root.get("dataAbertura"),
                        dataFim);
    }
}