# ms-exemplo (template dos microserviços do G5)

TED 2ª Unidade · Sistema de Controle de Monitoria de Alunos · Grupo 5

Este repositório é a **base** para todos os microserviços do grupo. Ele já traz a estrutura de pacotes, o erro em JSON padrão,
o registro no Eureka, o Swagger e um recurso `Exemplo` completo passando por todas as camadas.

Stack: Java 25 · Spring Boot 4.0.9 · Spring Cloud 2025.1.4 · PostgreSQL · springdoc-openapi 3.0.3

---

## Como usar este template

1. Clique em **Use this template → Create a new repository**, na **sua** conta do GitHub.
   - Nome: `TEDUND2BESNOTT1GR5_<COMPONENTE>` (ex.: `TEDUND2BESNOTT1GR5_MS_PESSOAS`)
   - Visibilidade: **Private**
2. Clone e troque `exemplo` pelo seu domínio:

   | Onde | De | Para (ex.: ms-pessoas) |
   |---|---|---|
   | `pom.xml` → `artifactId` e `name` | `ms-exemplo` | `ms-pessoas` |
   | Pasta do pacote (main e test) | `.../monitoria/exemplo` | `.../monitoria/pessoas` |
   | `package` / `import` das classes | `br.com.ucsal.monitoria.exemplo` | `br.com.ucsal.monitoria.pessoas` |
   | Classe main | `MsExemploApplication` | `PessoasApplication` |
   | `application.yml` → `spring.application.name` | `ms-exemplo` | `ms-pessoas` |
   | `application.yml` → porta e banco | `8099` / `exemplo_db` | sua porta / `pessoas_db` (tabela do plano) |
   | `OpenApiConfig` → título | `ms-exemplo` | `ms-pessoas` |

   No IntelliJ: botão direito no pacote → **Refactor → Rename** troca pasta, `package` e `import` de uma vez.
3. Use o `Exemplo` como modelo para as suas entidades e **apague-o** quando não precisar mais.
4. Substitua este README pelo modelo da seção [Modelo de README](#modelo-de-readme).
5. Settings → Collaborators: adicione **`fcatv`** e **`ebert-g`**. Settings → General: marque **Allow forking**.

---

## Padrão das camadas

| Pacote | O que vai nele | Regra |
|---|---|---|
| `controller` | `@RestController` | Só HTTP. Nenhuma regra de negócio |
| `dto` | `record XxxRequest` / `XxxResponse` | A entidade nunca sai pela API |
| `model` | `@Entity` e enums | Referência a outro microserviço **só por id** (`Long cursoId`), nunca `@ManyToOne` |
| `repository` | Spring Data JPA | Só acesso a dados |
| `service` | Regras de negócio | Lança as exceções do pacote `exception` |
| `exception` | Exceções + `GlobalExceptionHandler` | Garante o erro em JSON padrão |
| `config` | Swagger, carga inicial | Configuração técnica |

## Contrato com o orquestrador

- **Erro sempre em JSON:**
  ```json
  { "status": 404, "erro": "Not Found", "mensagem": "Exemplo 9 não encontrado", "caminho": "/api/exemplos/9", "timestamp": "..." }
  ```
- **Status:** 400 validação · 404 `RecursoNaoEncontradoException` · 409 `ConflitoException` · 422 `RegraNegocioException`.
- **Rotas iguais às do monolito:** `/api/<recurso>`.
- Exponha `GET /api/<recurso>/{id}` e, se o seu recurso for "filho" de outro serviço, uma rota de vínculo
  (ex.: `GET /api/exemplos/vinculos/curso/{cursoId}`), que o orquestrador usa antes de excluir o "pai".
- Quem executou a ação chega nos cabeçalhos `X-Usuario-Id` e `X-Pessoa-Id`. Não use Spring Security
  (exceto no ms-autenticacao).
- Nenhuma senha no código: tudo por variável de ambiente (veja `.env.example`).

## Como rodar

```bash
docker run -d --name ted2-postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=exemplo_db -p 5432:5432 postgres:17
./mvnw spring-boot:run
```

- Sem o Eureka no ar: `EUREKA_CLIENT_ENABLED=false ./mvnw spring-boot:run`
- Swagger: <http://localhost:8099/swagger-ui.html>
- Testes (sem banco): `./mvnw test`

## Checklist de entrega para o Ebert

- [ ] Repositório com o nome padrão, privado, com `fcatv` e `ebert-g` como colaboradores
- [ ] Fork liberado
- [ ] `./mvnw package` passando na `main`
- [ ] Sobe só com variáveis de ambiente e aparece no painel do Eureka
- [ ] Swagger abrindo e README preenchido
- [ ] Link do repositório + matrícula enviados no grupo

---

## Modelo de README

```markdown
# ms-<dominio>

Autor: <nome> – matrícula <nº>

## Objetivo

## Funcionalidades

## Endpoints

| Método | Rota | Descrição | Perfil |
|---|---|---|---|

## Variáveis de ambiente

| Variável | Padrão |
|---|---|

## Como rodar

Eureka no ar → `./mvnw spring-boot:run` → http://localhost:<porta>/swagger-ui.html
```
