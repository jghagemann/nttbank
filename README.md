# Tecnologias e Ferramentas:
Linguagem, Framework e Banco de Dados:
- Java/Springboot
- PostgreSQL

Container
- Docker

Bibliotecas:
- JPA / Hibernate ORM/Validator
- SpringDoc OpenAPI para documentação
- Spring Security 
- Auth0 java-jwt para geração de token
- WebClient para chamadas à APIs

Geração de Documentos:
- Apache POI para Excel
- Apache PDFBox para PDF 
- JFreeChart para gráficos


# Estrutura do Desafio:

1. Containers Docker:
    1. Configurar o ambiente de desenvolvimento com Docker, incluindo um DB (e.g. PostgreSQL, MySQL) e a aplicação Spring Boot :white_check_mark:


2. CRUD de usuários e Transações:
    1. Implementar operações CRUD para gerenciar perfis de usuário. :white_check_mark:
    2. Implementar operações CRUD para gerenciar transações financeiras (depósitos, saques, transferências) :white_check_mark:


3. Cadastro em massa por Upload:
    1. Implementar funcionalidade para importar dados de usuários a partir de uma planilha Excel e salvar no BD :white_check_mark:


4. Análise de Despesas:
    1. Implementar uma funcionalidade que permite aos usuários visualizar um resumo e análise de suas despesas, categorizando as transações e exibindo gráficos. :white_check_mark:


5. Criar uma API Mock
    1. Criar uma API Mock no MockAPI para simular dados de conta bancária dos usuários :white_check_mark:


6. Consumir uma API Pública:
    1. Integrar uma API pública de taxas de câmbio (e.g. https://exchangeratesapi.io/) para converter valores de transações em diferentes moedas. :white_check_mark:


7. Consumir as APIs:
    1. Mostrar a taxa de câmbio atual em cada transação. :white_check_mark:
    2. Exibir o saldo da conta bancária dos usuários usando a API Mock :white_check_mark:


8. Validação dos Inputs:
    1. Implementar validações robustas para todas as entradas de dados, incluindo formulários de usuário e transações. :white_check_mark:


9. Spring Security:
    1. Implementar autenticação e autorização utilizando Spring Security, garantindo que somente usuários autorizados possam acessar certas funcionalidades :white_check_mark:


10. Testes Unitários:
    1. Escrever testes unitários abrangentes para todas as funcionalidades críticas da aplicação :white_check_mark:


11. Doc OpenAPI e Tech Write:
    1. Utilizar OpenAPI para documentar todos os endpoints da API. :white_check_mark:
    2. Criar uma documentação técnica detalhada descrevendo a arquitetura, principais funcionalidades e instruções para configurar e rodar o projeto


12. Rota para baixar relatório:
    1. Implementar uma rota que permite baixar um relatório em formato PDF ou Excel contendo um resumo das transações financeiras dos usuários :white_check_mark:

# Entregáveis:
- Código Fonte da Aplicação
- Arquivo docker compose para configurar o ambiente
- Planilha Excel de exemplo para importação de usuários
- Documentação técnica e OpenAPI
- Testes unitários com cobertura de código
- Demonstração funcional da API com integração das APIs Mock e pública