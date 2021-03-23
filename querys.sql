
SHOW TIMEZONE;
SELECT CURRENT_TIMESTAMP;

-- 1. Crie a tabela log.
create table log(
	id_log serial NOT null  constraint pk_id primary key,
	data_log TIMESTAMP,
	ip varchar(40),
	request varchar(40),
	status varchar(100),
	user_agent varchar(500)
);


select * from log;
select * from log where data_log = to_timestamp('2020-01-01 00:01:07.036', 'YYYY-MM-DD HH24:MI:SS:MS');
select * from log where data_log = to_timestamp('2020-01-01 00:00:11.763', 'YYYY/MM/dd HS:MI:SS:MS');

-- filtro
select * from log where data_log between to_timestamp('2020-01-01 00:00:11', 'YYYY/MM/dd HS:MI:SS:MS') and to_timestamp('2020/01/01 00:00:52', 'YYYY/MM/dd HS:MI:SS:MS');
select data_log from log;
INSERT INTO log (data_log, ip, request, status, user_agent) VALUES ('2020-01-01 00:00:11.763', 'teste', 'teste', 'teste', 'teste');

drop table log;