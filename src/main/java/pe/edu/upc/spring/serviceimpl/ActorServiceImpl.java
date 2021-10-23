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
	public boolean save(Actor actor) {
		Actor objActor = dActor.save(actor);
		if (objActor == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idActor) {
		dActor.deleteById(idActor);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findAll() {
		return dActor.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Actor> findAllSortAsc() {
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
