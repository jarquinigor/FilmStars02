package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.UserNewsComment;
import pe.edu.upc.spring.repository.IUserNewsCommentRepository;
import pe.edu.upc.spring.service.IUserNewsCommentService;

@Service
public class UserNewsCommentServiceImpl implements IUserNewsCommentService {

	@Autowired
	private IUserNewsCommentRepository dUserNewsComment;

	@Override
	@Transactional
	public boolean save(UserNewsComment userNewsComment) {
		UserNewsComment objUserNewsComment = dUserNewsComment.save(userNewsComment);
		if (objUserNewsComment == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idUserNewsComment) {
		dUserNewsComment.deleteById(idUserNewsComment);
		
	}
	
	@Override
	@Transactional
	public void deleteBatch(int idNewsComment) {
		dUserNewsComment.deleteInBatch(dUserNewsComment.findAllByNewsCommentId(idNewsComment));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserNewsComment> findAllSortIdDesc() {
		return dUserNewsComment.findAll(Sort.by(Sort.Direction.DESC,"idUserNewsComment"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserNewsComment> findAllSortIdAsc() {
		return dUserNewsComment.findAll(Sort.by(Sort.Direction.ASC,"idUserNewsComment"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<UserNewsComment> findById(int idUserNewsComment) {
		return dUserNewsComment.findById(idUserNewsComment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int identifyCommentAuthor(int idUser, int idNewsComment) {
		return dUserNewsComment.identifyCommentAuthor(idUser, idNewsComment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public int identifyCommentNonAuthor(int idUser, int idNewsComment) {
		return dUserNewsComment.identifyCommentNonAuthor(idUser, idNewsComment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserNewsComment>findAllByUserAndNewsId(int idUser, int idNews){
		return dUserNewsComment.findAllByUserAndNewsId(idUser, idNews);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<UserNewsComment>findRow(int idUser, int idNewsComment){
		return dUserNewsComment.findRow(idUser, idNewsComment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserNewsComment findByUNCId(int idUserNewsComment) {
		return dUserNewsComment.findByUNCId(idUserNewsComment);
	}
}
