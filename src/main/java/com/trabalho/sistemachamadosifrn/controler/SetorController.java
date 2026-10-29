package com.trabalho.sistemachamadosifrn.controler;

import com.trabalho.sistemachamadosifrn.dto.request.CriarSetorRequest;
import com.trabalho.sistemachamadosifrn.model.Setor;
import com.trabalho.sistemachamadosifrn.service.SetorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/setores")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Setor criar(@Valid @RequestBody CriarSetorRequest request) {
        return setorService.criar(request);
    }

    @GetMapping
    public Page<Setor> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        if (page < 0 || size < 1 || size > 50) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "page deve ser >= 0 e size deve estar entre 1 e 50.");
        }

        String campo = sort.length > 0 ? sort[0] : "id";
        String ordem = sort.length > 1 ? sort[1] : "asc";

        Sort.Direction direction;

        try {
            direction = Sort.Direction.fromString(ordem);
        } catch (IllegalArgumentException ex) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ordem de sort inválida.");
        }

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, campo)
        );

        return setorService.listar(pageable);
    }

    @GetMapping("/{id}")
    public Setor buscar(@PathVariable Long id) {
        return setorService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Setor atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CriarSetorRequest request) {

        return setorService.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        setorService.excluir(id);
    }
}