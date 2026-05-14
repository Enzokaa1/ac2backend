# Documento de Requisitos — AC2 Backend Spring Boot

## 1. Contexto

Este projeto deve ser desenvolvido para uma avaliação da disciplina de Desenvolvimento Web Back-End do curso de Análise e Desenvolvimento de Sistemas.

A avaliação exige a construção e apresentação de dois sistemas backend integrados/conceitualmente relacionados:

1. Sistema de Controle de Projetos.
2. Sistema PetCare.

O projeto deve ser apresentado funcionando ao vivo, com backend rodando, banco funcionando, endpoints funcionais, testes via Postman/Insomnia ou frontend simples, além de explicação do código.

---

## 2. Objetivo Geral

Desenvolver uma aplicação backend em Java com Spring Boot, aplicando:

- Arquitetura em camadas.
- API REST.
- Spring Data JPA.
- Banco de dados H2.
- Lombok.
- Relacionamentos reais entre entidades.
- Regras de negócio implementadas na camada Service.
- Validações.
- Tratamento de erros.
- Controllers REST.
- Frontend simples e funcional para pelo menos listar e inserir projetos.

---

## 3. Stack Técnica Obrigatória

Usar as seguintes tecnologias:

- Java 17 ou superior.
- Spring Boot.
- Spring Web.
- Spring Data JPA.
- H2 Database.
- Lombok.
- Maven.
- HTML, CSS e JavaScript simples para o frontend estático.

---

## 4. Estrutura Recomendada do Projeto

Criar o projeto seguindo arquitetura em camadas:

```text
src/main/java/com/example/ac2
 ├── controller
 ├── service
 ├── repository
 ├── model
 ├── dto
 ├── exception
 └── Ac2Application.java
```

Também criar frontend estático em:

```text
src/main/resources/static/index.html
```

E configurações em:

```text
src/main/resources/application.properties
```

---

# PARTE 1 — Sistema de Controle de Projetos

## 5. Objetivo da Parte 1

Construir uma API REST para gerenciamento de:

- Projetos.
- Funcionários.
- Setores.
- Vínculos entre funcionários e projetos.

---

## 6. Entidades da Parte 1

### 6.1. Entidade Setor

Representa um setor da empresa.

Campos obrigatórios:

```text
id: Long
nome: String
```

Relacionamento:

- Um setor pode possuir vários funcionários.
- Um funcionário pertence a apenas um setor.

Mapeamento esperado:

```text
Setor 1 ---- N Funcionario
```

---

### 6.2. Entidade Funcionario

Representa um funcionário da empresa.

Campos obrigatórios:

```text
id: Long
nome: String
setor: Setor
projetos: List<Projeto>
```

Relacionamentos:

- Muitos funcionários podem pertencer a um setor.
- Um funcionário pode participar de vários projetos.

---

### 6.3. Entidade Projeto

Representa um projeto da empresa.

Campos obrigatórios:

```text
id: Long
descricao: String
dataInicio: LocalDate
dataFim: LocalDate
funcionarios: List<Funcionario>
```

Relacionamento:

- Um projeto pode ter vários funcionários.
- Um funcionário pode estar vinculado a vários projetos.

Mapeamento esperado:

```text
Projeto N ---- N Funcionario
```

Usar tabela intermediária:

```text
projeto_funcionario
```

Com colunas:

```text
projeto_id
funcionario_id
```

---

## 7. Relacionamentos JPA Esperados

### Setor e Funcionario

Em `Setor`:

```java
@OneToMany(mappedBy = "setor")
private List<Funcionario> funcionarios;
```

Em `Funcionario`:

```java
@ManyToOne
@JoinColumn(name = "setor_id")
private Setor setor;
```

### Projeto e Funcionario

Em `Projeto`:

```java
@ManyToMany
@JoinTable(
    name = "projeto_funcionario",
    joinColumns = @JoinColumn(name = "projeto_id"),
    inverseJoinColumns = @JoinColumn(name = "funcionario_id")
)
private List<Funcionario> funcionarios;
```

Em `Funcionario`:

```java
@ManyToMany(mappedBy = "funcionarios")
private List<Projeto> projetos;
```

Evitar loop infinito no JSON usando DTOs ou anotações como `@JsonIgnore`, `@JsonManagedReference`/`@JsonBackReference`, ou preferencialmente DTOs de resposta.

---

## 8. Repositórios da Parte 1

Criar:

```text
ProjetoRepository
FuncionarioRepository
SetorRepository
```

### Queries obrigatórias

Implementar queries para:

1. Buscar projeto com funcionários.
2. Buscar projetos por período.
3. Buscar projetos por funcionário.
4. Listar setores com funcionários.

Sugestão de métodos:

```java
@Query("SELECT p FROM Projeto p LEFT JOIN FETCH p.funcionarios WHERE p.id = :id")
Optional<Projeto> buscarProjetoComFuncionarios(Long id);
```

```java
List<Projeto> findByDataInicioGreaterThanEqualAndDataFimLessThanEqual(LocalDate inicio, LocalDate fim);
```

```java
@Query("SELECT p FROM Projeto p JOIN p.funcionarios f WHERE f.id = :funcionarioId")
List<Projeto> buscarProjetosPorFuncionario(Long funcionarioId);
```

```java
@Query("SELECT DISTINCT s FROM Setor s LEFT JOIN FETCH s.funcionarios")
List<Setor> listarSetoresComFuncionarios();
```

---

## 9. Services da Parte 1

Criar services para centralizar regras de negócio:

```text
ProjetoService
FuncionarioService
SetorService
```

### Regras de negócio obrigatórias/recomendadas

#### Projeto

- Não permitir cadastro de projeto sem descrição.
- Não permitir cadastro de projeto sem data de início.
- Não permitir cadastro de projeto sem data de fim.
- Não permitir data final anterior à data inicial.
- Não permitir vincular funcionário inexistente a projeto.
- Não permitir vincular projeto inexistente a funcionário.
- Não permitir vincular o mesmo funcionário duas vezes ao mesmo projeto.

#### Funcionário

- Não permitir cadastro de funcionário sem nome.
- Não permitir cadastro de funcionário sem setor.
- Não permitir associar funcionário a setor inexistente.

#### Setor

- Não permitir cadastro de setor sem nome.
- Não permitir nomes vazios ou apenas espaços.

---

## 10. Controllers da Parte 1

Criar controllers REST:

```text
ProjetoController
FuncionarioController
SetorController
```

### Endpoints obrigatórios

#### Projetos

```http
POST /api/projetos
```

Cadastrar projeto.

Exemplo de request:

```json
{
  "descricao": "Sistema de Controle de Projetos",
  "dataInicio": "2026-05-01",
  "dataFim": "2026-05-30"
}
```

---

```http
GET /api/projetos/{id}
```

Buscar projeto por ID com funcionários vinculados.

---

```http
GET /api/projetos/periodo?inicio=2026-05-01&fim=2026-05-30
```

Buscar projetos por período.

---

```http
POST /api/projetos/{projetoId}/funcionarios/{funcionarioId}
```

Vincular funcionário a projeto.

---

#### Funcionários

```http
POST /api/funcionarios
```

Cadastrar funcionário.

Exemplo de request:

```json
{
  "nome": "João Silva",
  "setorId": 1
}
```

---

```http
GET /api/funcionarios
```

Listar funcionários.

---

```http
GET /api/funcionarios/{id}/projetos
```

Consultar projetos de um funcionário.

---

#### Setores

```http
POST /api/setores
```

Cadastrar setor.

Exemplo de request:

```json
{
  "nome": "Tecnologia"
}
```

---

```http
GET /api/setores
```

Listar setores com funcionários.

---

```http
GET /api/setores/{id}
```

Consultar setor por ID.

---

# PARTE 2 — Sistema PetCare

## 11. Objetivo da Parte 2

Criar uma expansão mais complexa do projeto, com foco em regras reais de negócio, modelagem correta e uso adequado da camada Service.

O sistema PetCare deve permitir:

- Cadastro de tutores.
- Cadastro de animais.
- Cadastro de veterinários.
- Cadastro de especialidades.
- Agendamento de consultas.
- Registro de prontuário.
- Registro de vacinação.

---

## 12. Entidades da Parte 2

### 12.1. Tutor

Campos:

```text
id: Long
nome: String
telefone: String
animais: List<Animal>
```

Relacionamento:

```text
Tutor 1 ---- N Animal
```

---

### 12.2. Animal

Campos:

```text
id: Long
nome: String
especie: String
raca: String
idade: Integer
tutor: Tutor
consultas: List<Consulta>
vacinas: List<Vacinacao>
```

Relacionamento:

- Um animal pertence a um tutor.
- Um animal pode ter várias consultas.
- Um animal pode ter várias vacinas registradas.

---

### 12.3. Especialidade

Campos:

```text
id: Long
nome: String
```

Exemplos:

```text
Clínico Geral
Cardiologia
Dermatologia
Ortopedia
Odontologia
```

---

### 12.4. Veterinario

Campos:

```text
id: Long
nome: String
crmv: String
especialidade: Especialidade
consultas: List<Consulta>
```

Relacionamento:

- Um veterinário possui uma especialidade.
- Um veterinário pode atender várias consultas.

---

### 12.5. Consulta

Campos:

```text
id: Long
animal: Animal
veterinario: Veterinario
especialidade: Especialidade
dataHora: LocalDateTime
motivo: String
status: String
```

Status sugeridos:

```text
AGENDADA
REALIZADA
CANCELADA
```

Regras importantes:

- Não permitir conflito de agenda para o mesmo veterinário na mesma data/hora.
- Veterinário só pode atender consulta da própria especialidade.
- Animal deve existir.
- Veterinário deve existir.
- Especialidade deve existir.

---

### 12.6. Prontuario

Campos:

```text
id: Long
consulta: Consulta
descricao: String
diagnostico: String
tratamento: String
dataRegistro: LocalDateTime
```

Regras:

- Prontuário deve estar associado a uma consulta existente.
- Deve registrar histórico completo do atendimento.
- Ao criar prontuário, a consulta pode ser marcada como REALIZADA.

---

### 12.7. Vacinacao

Campos:

```text
id: Long
animal: Animal
nomeVacina: String
dataAplicacao: LocalDate
proximaDose: LocalDate
observacoes: String
```

Regras:

- Vacinação deve estar associada a um animal existente.
- Deve compor o histórico do animal.

---

## 13. Regras Obrigatórias do PetCare

### 13.1. Não permitir conflito de agenda

Antes de cadastrar uma consulta, verificar se já existe uma consulta para o mesmo veterinário na mesma data e hora com status AGENDADA.

Caso exista, retornar erro de regra de negócio.

Mensagem sugerida:

```text
Veterinário já possui consulta agendada para este horário.
```

---

### 13.2. Veterinário atende apenas sua especialidade

Ao agendar consulta, verificar se a especialidade informada na consulta é a mesma especialidade do veterinário.

Caso não seja, retornar erro.

Mensagem sugerida:

```text
Veterinário não atende a especialidade informada.
```

---

### 13.3. Registro completo de histórico

O sistema deve permitir consultar o histórico do animal, incluindo:

- Dados do animal.
- Dados do tutor.
- Consultas realizadas/agendadas.
- Prontuários.
- Vacinas aplicadas.

Endpoint sugerido:

```http
GET /api/animais/{id}/historico
```

---

### 13.4. Associação tutor ↔ animal

Todo animal deve obrigatoriamente estar associado a um tutor.

Não permitir cadastrar animal sem tutor válido.

---

## 14. Controllers do PetCare

Criar controllers:

```text
TutorController
AnimalController
EspecialidadeController
VeterinarioController
ConsultaController
ProntuarioController
VacinacaoController
```

### Endpoints sugeridos

#### Tutores

```http
POST /api/tutores
GET  /api/tutores
GET  /api/tutores/{id}
```

#### Animais

```http
POST /api/animais
GET  /api/animais
GET  /api/animais/{id}
GET  /api/animais/{id}/historico
```

#### Especialidades

```http
POST /api/especialidades
GET  /api/especialidades
```

#### Veterinários

```http
POST /api/veterinarios
GET  /api/veterinarios
```

#### Consultas

```http
POST /api/consultas
GET  /api/consultas
GET  /api/consultas/{id}
```

#### Prontuários

```http
POST /api/prontuarios
GET  /api/prontuarios
```

#### Vacinação

```http
POST /api/vacinacoes
GET  /api/vacinacoes
```

---

# 15. Tratamento de Erros

Criar tratamento global de exceções com `@RestControllerAdvice`.

Criar exceções customizadas:

```text
RegraNegocioException
RecursoNaoEncontradoException
```

Retornar respostas padronizadas em caso de erro.

Exemplo:

```json
{
  "status": 400,
  "erro": "Regra de negócio",
  "mensagem": "Data final não pode ser anterior à data inicial"
}
```

Para recurso não encontrado:

```json
{
  "status": 404,
  "erro": "Recurso não encontrado",
  "mensagem": "Projeto não encontrado"
}
```

---

# 16. DTOs

Usar DTOs para entrada de dados, evitando expor entidades diretamente nas requisições.

DTOs mínimos sugeridos:

```text
ProjetoRequest
FuncionarioRequest
SetorRequest
TutorRequest
AnimalRequest
EspecialidadeRequest
VeterinarioRequest
ConsultaRequest
ProntuarioRequest
VacinacaoRequest
```

DTOs de resposta podem ser simples ou as próprias entidades podem ser retornadas, desde que não ocorra loop infinito no JSON.

Preferência: usar DTOs de response para melhorar a clareza.

---

# 17. Banco H2

Configurar banco H2 em memória.

Exemplo de `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:ac2db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

---

# 18. Frontend Simples

Criar um frontend estático simples em `src/main/resources/static/index.html`.

Funcionalidades mínimas:

- Listar projetos.
- Inserir projeto.

Funcionalidades extras desejáveis:

- Listar setores.
- Cadastrar setor.
- Listar funcionários.
- Cadastrar funcionário.
- Vincular funcionário a projeto.

O frontend pode usar HTML, CSS e JavaScript puro com `fetch` para chamar a API.

---

# 19. Dados de Exemplo

Criar opcionalmente uma classe `DataLoader` usando `CommandLineRunner` para popular dados iniciais.

Exemplos:

## Setores

```text
Tecnologia
Recursos Humanos
Financeiro
```

## Funcionários

```text
João Silva
Maria Oliveira
Carlos Souza
```

## Projetos

```text
Sistema de Controle de Projetos
API PetCare
Portal Administrativo
```

## Especialidades

```text
Clínico Geral
Dermatologia
Ortopedia
Cardiologia
```

## Tutores e Animais

```text
Tutor: Ana Paula
Animal: Rex, cachorro

Tutor: Bruno Lima
Animal: Mimi, gato
```

---

# 20. Collection Postman

Criar uma collection do Postman ou documentar os requests no README.

A apresentação precisa conseguir demonstrar:

## Parte 1

1. Criar setor.
2. Criar funcionário associado ao setor.
3. Criar projeto.
4. Vincular funcionário ao projeto.
5. Buscar projeto com funcionários.
6. Buscar projetos por funcionário.
7. Buscar projetos por período.
8. Listar setores com funcionários.

## Parte 2

1. Criar tutor.
2. Criar animal vinculado ao tutor.
3. Criar especialidade.
4. Criar veterinário com especialidade.
5. Agendar consulta.
6. Tentar agendar consulta em horário conflitante e demonstrar erro.
7. Tentar agendar consulta com especialidade diferente da do veterinário e demonstrar erro.
8. Registrar prontuário.
9. Registrar vacinação.
10. Consultar histórico do animal.

---

# 21. README Obrigatório

Criar um `README.md` explicando:

- Objetivo do projeto.
- Tecnologias usadas.
- Como rodar.
- Como acessar o H2.
- Endpoints principais.
- Exemplos de JSON.
- Regras de negócio implementadas.
- Roteiro de apresentação.

---

# 22. Critérios de Aceite

O projeto será considerado pronto quando:

- Compilar sem erros.
- Subir com `mvn spring-boot:run`.
- Acessar o H2 Console corretamente.
- Permitir cadastrar projetos.
- Permitir cadastrar funcionários.
- Permitir cadastrar setores.
- Permitir vincular funcionários a projetos.
- Buscar projeto com funcionários.
- Buscar projetos por período.
- Buscar projetos por funcionário.
- Listar setores com funcionários.
- Permitir cadastrar tutores.
- Permitir cadastrar animais associados a tutores.
- Permitir cadastrar especialidades.
- Permitir cadastrar veterinários.
- Permitir agendar consultas.
- Bloquear conflito de agenda.
- Bloquear veterinário atendendo especialidade diferente da sua.
- Permitir registrar prontuário.
- Permitir registrar vacinação.
- Permitir consultar histórico do animal.
- Ter tratamento de erros claro.
- Ter frontend simples para listar e inserir projetos.

---

# 23. Instruções Específicas para o Codex

Implemente o projeto completo em Java com Spring Boot seguindo este documento.

Prioridades:

1. Código simples, didático e fácil de explicar em sala.
2. Evitar complexidade desnecessária.
3. Usar arquitetura em camadas tradicional: Controller, Service, Repository, Model, DTO e Exception.
4. Implementar regras de negócio dentro dos Services.
5. Não colocar regra de negócio nos Controllers.
6. Usar H2 para facilitar apresentação.
7. Usar Lombok para reduzir boilerplate.
8. Garantir que os endpoints funcionem no Postman/Insomnia.
9. Criar frontend simples em HTML/JS para listar e inserir projetos.
10. Criar README completo.

Evite:

- Segurança com login/autenticação.
- JWT.
- Docker.
- Banco externo.
- Arquitetura hexagonal.
- Microsserviços.
- Complexidade excessiva.

O foco é uma aplicação monolítica Spring Boot simples, funcional e bem organizada para apresentação acadêmica.

---

# 24. Explicação Esperada em Sala

O aluno deve conseguir explicar:

## Repository

Camada responsável pelo acesso ao banco de dados.

Exemplo:

```text
ProjetoRepository busca, salva e consulta projetos no banco usando JPA.
```

## Service

Camada responsável pelas regras de negócio.

Exemplo:

```text
ProjetoService valida se a data final é maior que a inicial e impede funcionário duplicado no projeto.
```

## Controller

Camada responsável por receber requisições HTTP e devolver respostas REST.

Exemplo:

```text
ProjetoController recebe um POST /api/projetos e chama o ProjetoService para cadastrar.
```

## Relacionamento Setor x Funcionário

```text
Um setor pode ter vários funcionários, mas cada funcionário pertence a um setor.
```

## Relacionamento Projeto x Funcionário

```text
Um projeto pode ter vários funcionários e um funcionário pode participar de vários projetos.
Por isso o relacionamento é ManyToMany.
```

## Regra de conflito de agenda no PetCare

```text
Antes de salvar uma consulta, o ConsultaService verifica se já existe consulta agendada para o mesmo veterinário no mesmo horário.
Se existir, o sistema lança uma exceção de regra de negócio.
```

## Regra da especialidade do veterinário

```text
Ao agendar consulta, o sistema compara a especialidade da consulta com a especialidade do veterinário.
Se forem diferentes, a consulta não é permitida.
```

---

# 25. Resultado Esperado

Ao final, o projeto deve conter:

```text
Backend Spring Boot completo
API REST funcional
Banco H2 funcionando
Relacionamentos JPA corretos
Queries customizadas
Services com regras de negócio
Tratamento global de erros
Frontend simples
README explicativo
Dados ou exemplos para apresentação
```

