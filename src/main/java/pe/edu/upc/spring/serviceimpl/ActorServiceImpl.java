package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Actor;
import pe.edu.upc.spring.repository.IActorRepository;
import pe.edu.upc.spring.service.IActorService;

@Service
public class ActorServiceImpl implements IActorService {

	@Autowired
	private IActorRepository dActor;

	@Override
	@Transactional
	public int save(Actor actor) {
		int rpta = dActor.findActor(actor.getNameActor());
		if (rpta == 0) {
			dActor.save(actor);
		}
		return rpta;
	}
	
	@Override
	@Transactional
	public void update(Actor race) {
		dActor.save(race);
	}
	
	@Override
	@Transactional
	public void delete(int idActor) {
		dActor.deleteById(idActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findAllSortNameAsc() {
		return dActor.findAll(Sort.by(Sort.Direction.ASC,"nameActor"));//AGREGADO
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findAllSortIdAsc() {
		return dActor.findAll(Sort.by(Sort.Direction.ASC,"idActor"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Actor> findById(int idActor) {
		return dActor.findById(idActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findByName(String nameActor) {
		return dActor.findByName(nameActor);
	}
}
