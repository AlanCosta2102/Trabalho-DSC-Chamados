package com.trabalho.sistemachamadosifrn.controler;

import com.trabalho.sistemachamadosifrn.dto.request.AssumirChamadoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.CancelarChamadoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.CriarChamadoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.InteracaoRequest;
import com.trabalho.sistemachamadosifrn.dto.request.ResolverChamadoRequest;
import com.trabalho.sistemachamadosifrn.model.Chamado;
import com.trabalho.sistemachamadosifrn.model.Interacao;
import com.trabalho.sistemachamadosifrn.service.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;

    public ChamadoController(ChamadoService chamadoService) {
        this.chamadoService = chamadoService;
    }

    @PostMapping
    public ResponseEntity<Chamado> criar(
            @Valid @RequestBody CriarChamadoRequest request) {

        Chamado chamado = chamadoService.criar(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(chamado);
    }

    @GetMapping
    public ResponseEntity<Page<Chamado>> listar(
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(
                chamadoService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chamado> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                chamadoService.buscarPorId(id));
    }

    @PatchMapping("/{id}/assumir")
    public ResponseEntity<Chamado> assumir(
            @PathVariable Long id,
            @Valid @RequestBody AssumirChamadoRequest request) {

        return ResponseEntity.ok(
                chamadoService.assumir(id, request));
    }

    @PatchMapping("/{id}/resolver")
    public ResponseEntity<Chamado> resolver(
            @PathVariable Long id,
            @Valid @RequestBody ResolverChamadoRequest request) {

        return ResponseEntity.ok(
                chamadoService.resolver(id, request));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Chamado> cancelar(
            @PathVariable Long id,
            @Valid @RequestBody CancelarChamadoRequest request) {

        return ResponseEntity.ok(
                chamadoService.cancelar(id, request));
    }

    @PostMapping("/{id}/interacoes")
    public ResponseEntity<Interacao> interagir(
            @PathVariable Long id,
            @Valid @RequestBody InteracaoRequest request) {

        Interacao interacao =
                chamadoService.interagir(id, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(interacao);
    }
}