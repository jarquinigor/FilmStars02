package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Genre;
import pe.edu.upc.spring.repository.IGenreRepository;
import pe.edu.upc.spring.service.IGenreService;

@Service
public class GenreServiceImpl implements IGenreService {

	@Autowired
	private IGenreRepository dGenre;

	@Override
	@Transactional
	public int save(Genre genre) {
		int rpta = dGenre.findGenre(genre.getNameGenre());
		if (rpta == 0){
			dGenre.save(genre);
		}
		return rpta;
	}

	@Override
	@Transactional
	public void delete(int idGenre) {
		dGenre.deleteById(idGenre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Genre> findAllSortNameAsc() {
		return dGenre.findAll(Sort.by(Sort.Direction.ASC,"nameGenre"));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Genre> findAllSortIdAsc() {
		return dGenre.findAll(Sort.by(Sort.Direction.ASC,"idGenre"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Genre> findById(int idGenre) {
		return dGenre.findById(idGenre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Genre> findByName(String nameGenre) {
		return dGenre.findByName(nameGenre);
	}
}
