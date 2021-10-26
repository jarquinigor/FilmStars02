package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Movie;

public interface IMovieService {
	public boolean save(Movie movie);
	public void delete(int idMovie);
	public List<Movie> findAll();
	public List<Movie> findAllSortAsc();
	public Optional<Movie>findById(int idMovie);
	public List<Movie> findByName(String nameMovie);
}
