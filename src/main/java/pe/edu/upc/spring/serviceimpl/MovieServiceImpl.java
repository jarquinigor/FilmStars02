package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Movie;
import pe.edu.upc.spring.repository.IMovieRepository;
import pe.edu.upc.spring.service.IMovieService;

@Service
public class MovieServiceImpl implements IMovieService {

	@Autowired
	private IMovieRepository dMovie;

	@Override
	@Transactional
	public boolean save(Movie movie) {
		Movie objMovie = dMovie.save(movie);
		if (objMovie == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idMovie) {
		dMovie.deleteById(idMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Movie> findAll() {
		return dMovie.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Movie> findAllSortAsc() {
		return dMovie.findAll(Sort.by(Sort.Direction.ASC,"idMovie"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Movie> findById(int idMovie) {
		return dMovie.findById(idMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Movie> findByName(String nameMovie) {
		return dMovie.findByName(nameMovie);
	}
}
