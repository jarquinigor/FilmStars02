package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Director;

public interface IDirectorService {
	public boolean save(Director director);
	public void delete(int idDirector);
	public List<Director> findAll();
	public List<Director> findAllSortAsc();
	public Optional<Director>findById(int idDirector);
	public List<Director> findByName(String nameDirector);
}
