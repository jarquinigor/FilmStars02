package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.MovieGenre;

public interface IMovieGenreService {
	public boolean save(MovieGenre movieGenre);
	public void delete(int idMovieGenre);
	public List<MovieGenre> findAll();
	public List<MovieGenre> findAllSortAsc();
	public Optional<MovieGenre>findById(int idMovieGenre);
	public List<MovieGenre> findByMovieName(String nameMovie);
	public List<MovieGenre> findByGenreName(String nameGenre);
}
