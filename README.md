# Teste n53 
Este é um projeto de seleção número 53

## Tecnologias Utilizadas
- Java 8
- Spring Boot 2.7.11
- Swagger

## Pré-requisitos
- Java 8
- Maven 3.8.3 ou superior

## Instalação e Execução
- Faça a instalão do MYSQL Server em sua maquina.
- Criei um banco de dados chamado teste53.
- Senha e usuário esta usando root.
- Criação de tabelas é direto pelo flyway.

## Acessos
- http://localhost:8080/swagger-ui/index.html#/

## Como fazer pesquisa 
- No sawgger vá onde tem /pedidos/v2/filter/page
- Preencha desta forma para fazer alguma pesquisa exemplo:
- {
- "filters": {
- "codigoCliente": 1
- }
- }

## Caso deseja fazer a pesquisa por outros parametros ou mais
## é so ir na classe PedidosDTO por exemplo 'nomeProduto'
- {
- "filters": {
- "codigoCliente": 1,
- "nomeProduto": "teste"
- }
- }

## Para pesquisar todo só remover os parametros de pesquisa