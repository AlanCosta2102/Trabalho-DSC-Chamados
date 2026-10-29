package com.trabalho.sistemachamadosifrn.controler;

import com.trabalho.sistemachamadosifrn.dto.request.CriarUsuarioRequest;
import com.trabalho.sistemachamadosifrn.model.Usuario;
import com.trabalho.sistemachamadosifrn.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> criar(
            @Valid @RequestBody CriarUsuarioRequest request) {

        Usuario usuario = usuarioService.criar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @GetMapping
    public ResponseEntity<Page<Usuario>> listar(
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(usuarioService.listar(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody CriarUsuarioRequest request) {

        return ResponseEntity.ok(
                usuarioService.atualizar(id, request));
    }
}