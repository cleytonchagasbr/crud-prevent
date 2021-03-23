DROP TABLE IF EXISTS log;

create table log(
	id_log serial NOT null  constraint pk_id primary key,
	data_log TIMESTAMP,
	ip varchar(40),
	request varchar(40),
	status varchar(100),
	user_agent varchar(500)
);