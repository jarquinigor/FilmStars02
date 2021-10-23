package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Critic;

public interface ICriticService {
	public boolean save(Critic critic);
	public void delete(int idCritic);
	public List<Critic> findAll();
	public List<Critic> findAllSortAsc();
	public Optional<Critic>findById(int idCritic);
	public List<Critic> findByName(String nameCritic);
}
