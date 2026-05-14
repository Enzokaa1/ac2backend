# AC2 - Projetos + PetCare (Spring Boot)

Backend monolítico para avaliação de Desenvolvimento Web Back-End com:
1. Sistema de Controle de Projetos
2. Sistema PetCare

## Tecnologias
- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Lombok
- Gradle

## Como rodar
```bash
./gradlew bootRun
```
No Windows:
```bash
gradlew.bat bootRun
```

## H2 Console
- URL app: `http://localhost:8080`
- H2: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:ac2db`
- Usuário: `sa`
- Senha: (vazia)

## Endpoints principais
### Parte 1 - Projetos
- `POST /api/setores`
- `GET /api/setores`
- `GET /api/setores/{id}`
- `POST /api/funcionarios`
- `GET /api/funcionarios`
- `GET /api/funcionarios/{id}/projetos`
- `POST /api/projetos`
- `GET /api/projetos`
- `GET /api/projetos/{id}`
- `GET /api/projetos/periodo?inicio=2026-05-01&fim=2026-05-30`
- `POST /api/projetos/{projetoId}/funcionarios/{funcionarioId}`

### Parte 2 - PetCare
- `POST /api/tutores` | `GET /api/tutores` | `GET /api/tutores/{id}`
- `POST /api/animais` | `GET /api/animais` | `GET /api/animais/{id}` | `GET /api/animais/{id}/historico`
- `POST /api/especialidades` | `GET /api/especialidades`
- `POST /api/veterinarios` | `GET /api/veterinarios`
- `POST /api/consultas` | `GET /api/consultas` | `GET /api/consultas/{id}`
- `POST /api/prontuarios` | `GET /api/prontuarios`
- `POST /api/vacinacoes` | `GET /api/vacinacoes`

## JSON de exemplo
```json
{
  "descricao": "Sistema de Controle de Projetos",
  "dataInicio": "2026-05-01",
  "dataFim": "2026-05-30"
}
```

```json
{
  "nome": "João Silva",
  "setorId": 1
}
```

```json
{
  "animalId": 1,
  "veterinarioId": 1,
  "especialidadeId": 1,
  "dataHora": "2026-05-20T10:00:00",
  "motivo": "Consulta de rotina"
}
```

## Regras de negócio implementadas
- Projeto exige descrição, data início e data fim.
- Data fim do projeto não pode ser anterior à data início.
- Não permite vínculo duplicado funcionário/projeto.
- Funcionário exige setor válido.
- Setor exige nome não vazio.
- Animal exige tutor válido.
- Consulta bloqueia conflito de agenda para mesmo veterinário/dataHora com status `AGENDADA`.
- Consulta exige especialidade compatível com o veterinário.
- Ao criar prontuário, consulta é marcada como `REALIZADA`.

## Frontend simples
Arquivo `src/main/resources/static/index.html`:
- lista projetos
- cadastra projeto

## Roteiro de apresentação
1. Criar setor
2. Criar funcionário
3. Criar projeto
4. Vincular funcionário no projeto
5. Buscar projeto com funcionários
6. Buscar projetos por período e por funcionário
7. Criar tutor, animal, especialidade e veterinário
8. Agendar consulta
9. Demonstrar erro de conflito de agenda
10. Demonstrar erro de especialidade incompatível
11. Registrar prontuário e vacinação
12. Consultar histórico do animal
