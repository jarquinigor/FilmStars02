package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.MovieActor;

public interface IMovieActorService {
	public boolean save(MovieActor movieActor);
	public void delete(int idMovieActor);
	public List<MovieActor> findAll();
	public List<MovieActor> findAllSortAsc();
	public Optional<MovieActor>findById(int idMovieActor);
	public List<MovieActor> findByMovieName(String nameMovie);
	public List<MovieActor> findByActorName(String nameActor);
}
