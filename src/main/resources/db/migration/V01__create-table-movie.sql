CREATE TABLE movie (
	id serial NOT NULL,
	name varchar(100) NOT NULL,
	rate real NOT NULL,
	CONSTRAINT pk_movie PRIMARY KEY (id)
);