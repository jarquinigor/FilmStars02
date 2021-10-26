package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.MovieGenre;
import pe.edu.upc.spring.repository.IMovieGenreRepository;
import pe.edu.upc.spring.service.IMovieGenreService;

@Service
public class MovieGenreServiceImpl implements IMovieGenreService {

	@Autowired
	private IMovieGenreRepository dMovieGenre;

	@Override
	@Transactional
	public boolean save(MovieGenre movieGenre) {
		MovieGenre objMovieGenre = dMovieGenre.save(movieGenre);
		if (objMovieGenre == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idMovie) {
		dMovieGenre.deleteById(idMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieGenre> findAll() {
		return dMovieGenre.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieGenre> findAllSortAsc() {
		return dMovieGenre.findAll(Sort.by(Sort.Direction.ASC,"idMovieGenre"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<MovieGenre> findById(int idMovieGenre) {
		return dMovieGenre.findById(idMovieGenre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieGenre> findByMovieName(String nameMovie) {
		return dMovieGenre.findByMovieName(nameMovie);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<MovieGenre> findByGenreName(String nameGenre) {
		return dMovieGenre.findByGenreName(nameGenre);
	}
}
