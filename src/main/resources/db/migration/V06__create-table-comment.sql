CREATE TABLE comment (
    id serial NOT NULL,
	movie_id int NOT NULL,
	rate real NOT NULL,
	title varchar(200) NOT NULL,
	description text NOT NULL,
	CONSTRAINT pk_comment PRIMARY KEY (id)
);

ALTER TABLE comment ADD CONSTRAINT fk_movie_comment FOREIGN KEY (movie_id) REFERENCES movie(id);