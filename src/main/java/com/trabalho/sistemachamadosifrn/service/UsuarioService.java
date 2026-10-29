package com.trabalho.sistemachamadosifrn.service;

import com.trabalho.sistemachamadosifrn.dto.request.CriarUsuarioRequest;
import com.trabalho.sistemachamadosifrn.exception.RecursoNaoEncontradoException;
import com.trabalho.sistemachamadosifrn.exception.RegraNegocioException;
import com.trabalho.sistemachamadosifrn.model.Setor;
import com.trabalho.sistemachamadosifrn.model.Usuario;
import com.trabalho.sistemachamadosifrn.model.enums.TipoUsuario;
import com.trabalho.sistemachamadosifrn.repository.SetorRepository;
import com.trabalho.sistemachamadosifrn.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final SetorRepository setorRepository;

    public UsuarioService(
            UsuarioRepository usuarioRepository,
            SetorRepository setorRepository) {
        this.usuarioRepository = usuarioRepository;
        this.setorRepository = setorRepository;
    }

    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Usuário não encontrado: " + id));
    }

    @Transactional
    public Usuario criar(CriarUsuarioRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RegraNegocioException(
                    "Já existe um usuário cadastrado com esse e-mail.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setMatricula(request.getMatricula());
        usuario.setTipo(request.getTipo());

        configurarSetor(usuario, request);

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(
            Long id,
            CriarUsuarioRequest request) {

        Usuario usuario = buscarPorId(id);

        if (usuarioRepository.existsByEmailAndIdNot(
                request.getEmail(), id)) {
            throw new RegraNegocioException(
                    "O e-mail informado já pertence a outro usuário.");
        }

        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuario.setMatricula(request.getMatricula());
        usuario.setTipo(request.getTipo());

        configurarSetor(usuario, request);

        return usuarioRepository.save(usuario);
    }

    private void configurarSetor(
            Usuario usuario,
            CriarUsuarioRequest request) {

        if (request.getTipo() == TipoUsuario.TECNICO) {
            if (request.getSetorId() == null) {
                throw new RegraNegocioException(
                        "Um técnico precisa estar associado a um setor.");
            }

            Setor setor = setorRepository.findById(
                            request.getSetorId())
                    .orElseThrow(() ->
                            new RecursoNaoEncontradoException(
                                    "Setor não encontrado: "
                                            + request.getSetorId()));

            usuario.setSetor(setor);
        } else {
            usuario.setSetor(null);
        }
    }
}