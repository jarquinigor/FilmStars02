package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Movie;

public interface IMovieService {
	public boolean save(Movie movie);
	public void delete(int idMovie);
	public List<Movie> findAllSortNameAsc();
	public List<Movie> findAllSortIdAsc();
	public Optional<Movie>findById(int idMovie);
	public List<Movie> findByName(String nameMovie);
}
