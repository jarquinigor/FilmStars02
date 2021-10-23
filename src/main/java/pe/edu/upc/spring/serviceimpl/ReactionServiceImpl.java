package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Reaction;
import pe.edu.upc.spring.repository.IReactionRepository;
import pe.edu.upc.spring.service.IReactionService;

@Service
public class ReactionServiceImpl implements IReactionService {

	@Autowired
	private IReactionRepository dReaction;

	@Override
	@Transactional
	public boolean save(Reaction reaction) {
		Reaction objReaction = dReaction.save(reaction);
		if (objReaction == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idReaction) {
		dReaction.deleteById(idReaction);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Reaction> findAll() {
		return dReaction.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Reaction> findAllSortAsc() {
		return dReaction.findAll(Sort.by(Sort.Direction.ASC,"idReaction"));
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Reaction> findById(int idReaction) {
		return dReaction.findById(idReaction);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Reaction> findByName(String nameReaction) {
		return dReaction.findByName(nameReaction);
	}
}
