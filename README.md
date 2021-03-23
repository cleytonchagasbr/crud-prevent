<h1>Passo a passo para rodar o projeto</h1>

<h2> Primeiro - Criação de tabela de log e inserção de logs por api rest.</h2>
1 - Crie a tabela de logs no banco de dados postgres default. Use o arquivo "querys.sql" dentro dele encontra-se todas as chamadas utilizadas. </br>
2 - Execute o endoint "/insertFileLog" informando o path/caminho que encontra-se o arquivo.log para carregamento dos dados através da api rest.

</hr>
<h2>Postgres - Banco de dados</h2>
<strong>Foi utilizado o banco de dados default do Postgres</strong> </br>
</br>
Informações application.properties</b></br>

<b>spring.datasource.url=jdbc:postgresql://localhost:5432/postgres </b></br>
<b>spring.datasource.username=postgres </b></br>
<b>spring.datasource.password=root </b></br>
<b>spring.datasource.platform=postgresql</b></br>

</hr>
<h1>Passo a passo para executar os testes - Junit</h1>


</hr>
<h2>Dependência e ferramentas utilizadas</h2>
Já que não pude utilizar o JPA não vi sentido em usar o Hibernate, então utilizei o <b>DataSource</b> e <b>jdbcTemplate</b> como alternativa para acessar o banco de dados e realizar as chamadas.
</br>

<h3>Dependências</h3>
spring-boot-starter-jdbc </br>
spring-boot-starter-web </br>
lombok </br>
postgresql </br>
springfox-swagger-ui </br>
spring-boot-starter-test



