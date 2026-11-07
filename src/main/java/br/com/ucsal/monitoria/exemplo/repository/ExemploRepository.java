package br.com.ucsal.monitoria.exemplo.repository;

import br.com.ucsal.monitoria.exemplo.model.Exemplo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExemploRepository extends JpaRepository<Exemplo, Long> {

    boolean existsBySiglaIgnoreCase(String sigla);

    boolean existsBySiglaIgnoreCaseAndIdNot(String sigla, Long id);

    List<Exemplo> findByCursoId(Long cursoId);

    boolean existsByCursoId(Long cursoId);
}
