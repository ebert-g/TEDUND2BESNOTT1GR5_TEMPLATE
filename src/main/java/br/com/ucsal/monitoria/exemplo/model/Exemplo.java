package br.com.ucsal.monitoria.exemplo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exemplo")
public class Exemplo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String sigla;

    @Column(nullable = false)
    private String descricao;

    // Referência a outro microserviço: só o id, nunca @ManyToOne
    @Column(nullable = false)
    private Long cursoId;

    private LocalDate dataCadastramento = LocalDate.now();
    private boolean ativo = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Long getCursoId() { return cursoId; }
    public void setCursoId(Long cursoId) { this.cursoId = cursoId; }

    public LocalDate getDataCadastramento() { return dataCadastramento; }
    public void setDataCadastramento(LocalDate dataCadastramento) { this.dataCadastramento = dataCadastramento; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
