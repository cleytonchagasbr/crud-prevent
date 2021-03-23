<h1>Passo a passo para rodar o projeto</h1>

<h2>Swagger Documentação - http://localhost:8080/swagger-ui.html#/</h2>

### Pré-requisitos
1 - Crie a tabela de logs com o comando abaixo no banco de dados postgres default. Se preferir use o arquivo "querys.sql" dentro dele encontra-se todas as chamadas utilizadas. </br>

```
create table log(
	id_log serial NOT null  constraint pk_id primary key,
	data_log TIMESTAMP,
	ip varchar(40),
	request varchar(40),
	status varchar(100),
	user_agent varchar(500)
);
```

2 - Execute o endoint "/insertFileLog" informando o path/caminho que encontra-se o arquivo.log para carregamento dos dados através da api rest.

</hr>

### Postgres - Banco de dados
<strong>Foi utilizado o banco de dados default do Postgres</strong> </br>
</br>
Informações application.properties</b></br>

```
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=root
spring.datasource.platform=postgresq
```

</hr>

### Testes executados - Junit
<p> Para os testes tentei cubrir todos os cenários de <b>sucesso</b> e o que era esperado de cada método.</p>

```
testaConexaoComBancoDeDadosSucess
testaFindLogByDateComSucesso
testaInsertFileLogComSucesso
testaInsertUmLogComSuccesso
testaInsertLogEmLoteComSucesso
testaGetAllLogsByIpComSucesso
testaBuildLogModel
testaDeleteLogComSucesso
buildError
```
</hr>

### Dependência e ferramentas utilizadas
Já que não pude utilizar o JPA não vi sentido em usar o Hibernate, então utilizei o <b>DataSource</b> e <b>jdbcTemplate</b> como alternativa para acessar o banco de dados e realizar as chamadas.
</br>

<h3>Dependências</h3>

```
spring-boot-starter-jdbc
spring-boot-starter-web
lombok
postgresql
springfox-swagger-ui
spring-boot-starter-test
```

<h3 align="center">contato - cleyton-chagas@hotmail.com</h3>



