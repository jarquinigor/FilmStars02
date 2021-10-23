package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Reaction;

public interface IReactionService {
	public boolean save(Reaction reaction);
	public void delete(int idReaction);
	public List<Reaction> findAll();
	public List<Reaction> findAllSortAsc();
	public Optional<Reaction>findById(int idReaction);
	public List<Reaction> findByName(String nameReaction);
}
