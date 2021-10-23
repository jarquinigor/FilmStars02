package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.Actor;

public interface IActorService {
	public boolean save(Actor race);
	public void delete(int idRace);
	public List<Actor> findAll();
	public List<Actor> findAllSortAsc();
	public Optional<Actor>findById(int idActor);
	public List<Actor> findByName(String nameRace);
}
