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
	public boolean save(Genre genre) {
		Genre objGenre = dGenre.save(genre);
		if (objGenre == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idGenre) {
		dGenre.deleteById(idGenre);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Genre> findAll() {
		return dGenre.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Genre> findAllSortAsc() {
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
