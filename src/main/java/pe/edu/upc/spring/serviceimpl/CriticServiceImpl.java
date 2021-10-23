package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.Critic;
import pe.edu.upc.spring.repository.ICriticRepository;
import pe.edu.upc.spring.service.ICriticService;

@Service
public class CriticServiceImpl implements ICriticService {

	@Autowired
	private ICriticRepository dCritic;

	@Override
	@Transactional
	public boolean save(Critic critic) {
		Critic objCritic = dCritic.save(critic);
		if (objCritic == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idCritic) {
		dCritic.deleteById(idCritic);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Critic> findAll() {
		return dCritic.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Critic> findAllSortAsc() {
		return dCritic.findAll(Sort.by(Sort.Direction.ASC,"idCritic"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<Critic> findById(int idCritic) {
		return dCritic.findById(idCritic);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Critic> findByName(String nameCritic) {
		return dCritic.findByName(nameCritic);
	}
}
