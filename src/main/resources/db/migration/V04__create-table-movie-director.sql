CREATE TABLE movie_director (
    id serial NOT NULL,
	movie_id int NOT NULL,
	director_id int NOT NULL,
	CONSTRAINT pk_movie_director PRIMARY KEY (id)
);

ALTER TABLE movie_director ADD CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movie(id);
ALTER TABLE movie_director ADD CONSTRAINT fk_director FOREIGN KEY (director_id) REFERENCES director(id);