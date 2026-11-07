package br.com.ucsal.monitoria.exemplo.service;

import br.com.ucsal.monitoria.exemplo.dto.ExemploRequest;
import br.com.ucsal.monitoria.exemplo.dto.ExemploResponse;
import br.com.ucsal.monitoria.exemplo.exception.ConflitoException;
import br.com.ucsal.monitoria.exemplo.exception.RecursoNaoEncontradoException;
import br.com.ucsal.monitoria.exemplo.exception.RegraNegocioException;
import br.com.ucsal.monitoria.exemplo.model.Exemplo;
import br.com.ucsal.monitoria.exemplo.repository.ExemploRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ExemploService {

    private final ExemploRepository repository;

    public ExemploService(ExemploRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ExemploResponse> listar(Long cursoId) {
        List<Exemplo> exemplos = cursoId == null ? repository.findAll() : repository.findByCursoId(cursoId);
        return exemplos.stream().map(ExemploResponse::de).toList();
    }

    @Transactional(readOnly = true)
    public ExemploResponse buscar(Long id) {
        return ExemploResponse.de(encontrar(id));
    }

    // Usado pelo orquestrador antes de excluir/inativar o "pai" (aqui, o curso)
    @Transactional(readOnly = true)
    public boolean possuiVinculoComCurso(Long cursoId) {
        return repository.existsByCursoId(cursoId);
    }

    @Transactional
    public ExemploResponse criar(ExemploRequest request) {
        if (repository.existsBySiglaIgnoreCase(request.sigla())) {
            throw new ConflitoException("Já existe um exemplo com a sigla " + request.sigla());
        }
        Exemplo exemplo = new Exemplo();
        preencher(exemplo, request);
        return ExemploResponse.de(repository.save(exemplo));
    }

    @Transactional
    public ExemploResponse atualizar(Long id, ExemploRequest request) {
        Exemplo exemplo = encontrar(id);
        if (repository.existsBySiglaIgnoreCaseAndIdNot(request.sigla(), id)) {
            throw new ConflitoException("Já existe um exemplo com a sigla " + request.sigla());
        }
        preencher(exemplo, request);
        return ExemploResponse.de(exemplo);
    }

    @Transactional
    public ExemploResponse inativar(Long id) {
        Exemplo exemplo = encontrar(id);
        if (!exemplo.isAtivo()) {
            throw new RegraNegocioException("O exemplo " + id + " já está inativo");
        }
        exemplo.setAtivo(false);
        return ExemploResponse.de(exemplo);
    }

    @Transactional
    public ExemploResponse reativar(Long id) {
        Exemplo exemplo = encontrar(id);
        if (exemplo.isAtivo()) {
            throw new RegraNegocioException("O exemplo " + id + " já está ativo");
        }
        exemplo.setAtivo(true);
        return ExemploResponse.de(exemplo);
    }

    private Exemplo encontrar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Exemplo " + id + " não encontrado"));
    }

    private void preencher(Exemplo exemplo, ExemploRequest request) {
        exemplo.setSigla(request.sigla().trim().toUpperCase());
        exemplo.setDescricao(request.descricao().trim());
        exemplo.setCursoId(request.cursoId());
    }
}
