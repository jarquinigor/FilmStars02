package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Director;
import pe.edu.upc.spring.repository.IDirectorRepository;
import pe.edu.upc.spring.service.IDirectorService;

@Service
public class DirectorServiceImpl implements IDirectorService {

	@Autowired
	private IDirectorRepository dDirector;

	@Override
	@Transactional
	public boolean save(Director director) {
		Director objDirector = dDirector.save(director);
		if (objDirector == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idDirector) {
		dDirector.deleteById(idDirector);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Director> findAll() {
		return dDirector.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Director> findAllSortAsc() {
		return dDirector.findAll(Sort.by(Sort.Direction.ASC,"idDirector"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Director> findById(int idDirector) {
		return dDirector.findById(idDirector);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Director> findByName(String nameDirector) {
		return dDirector.findByName(nameDirector);
	}
}
