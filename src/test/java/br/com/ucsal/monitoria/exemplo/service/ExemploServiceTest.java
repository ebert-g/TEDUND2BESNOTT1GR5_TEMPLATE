package br.com.ucsal.monitoria.exemplo.service;

import br.com.ucsal.monitoria.exemplo.dto.ExemploRequest;
import br.com.ucsal.monitoria.exemplo.dto.ExemploResponse;
import br.com.ucsal.monitoria.exemplo.exception.ConflitoException;
import br.com.ucsal.monitoria.exemplo.exception.RecursoNaoEncontradoException;
import br.com.ucsal.monitoria.exemplo.exception.RegraNegocioException;
import br.com.ucsal.monitoria.exemplo.model.Exemplo;
import br.com.ucsal.monitoria.exemplo.repository.ExemploRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExemploServiceTest {

    @Mock
    private ExemploRepository repository;

    @InjectMocks
    private ExemploService service;

    @Test
    void criaComSiglaNormalizada() {
        when(repository.existsBySiglaIgnoreCase("ab1")).thenReturn(false);
        when(repository.save(any(Exemplo.class))).thenAnswer(chamada -> chamada.getArgument(0));

        ExemploResponse criado = service.criar(new ExemploRequest("ab1", " Descrição ", 3L));

        assertThat(criado.sigla()).isEqualTo("AB1");
        assertThat(criado.descricao()).isEqualTo("Descrição");
        assertThat(criado.ativo()).isTrue();
    }

    @Test
    void recusaSiglaDuplicada() {
        when(repository.existsBySiglaIgnoreCase("AB1")).thenReturn(true);

        assertThatThrownBy(() -> service.criar(new ExemploRequest("AB1", "Descrição", 3L)))
                .isInstanceOf(ConflitoException.class);
        verify(repository, never()).save(any());
    }

    @Test
    void buscaInexistenteLanca404() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscar(99L))
                .isInstanceOf(RecursoNaoEncontradoException.class)
                .hasMessageContaining("99");
    }

    @Test
    void naoInativaDuasVezes() {
        Exemplo inativo = new Exemplo();
        inativo.setAtivo(false);
        when(repository.findById(1L)).thenReturn(Optional.of(inativo));

        assertThatThrownBy(() -> service.inativar(1L)).isInstanceOf(RegraNegocioException.class);
    }
}
