CREATE TABLE movie_actor (
    id serial NOT NULL,
	movie_id int NOT NULL,
	actor_id int NOT NULL,
	CONSTRAINT pk_movie_actor PRIMARY KEY (id)
);

ALTER TABLE movie_actor ADD CONSTRAINT fk_movie FOREIGN KEY (movie_id) REFERENCES movie(id);
ALTER TABLE movie_actor ADD CONSTRAINT fk_actor FOREIGN KEY (actor_id) REFERENCES actor(id);