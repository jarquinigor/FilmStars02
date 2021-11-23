package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.NewsComment;
import pe.edu.upc.spring.repository.INewsCommentRepository;
import pe.edu.upc.spring.service.INewsCommentService;

@Service
public class NewsCommentServiceImpl implements INewsCommentService {

	@Autowired
	private INewsCommentRepository dNewsComment;

	@Override
	@Transactional
	public boolean save(NewsComment newsComment) {
		NewsComment objNewsComment = dNewsComment.save(newsComment);
		if (objNewsComment == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idNewsComment) {
		dNewsComment.deleteById(idNewsComment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NewsComment> findAllSortIdDesc() {
		return dNewsComment.findAll(Sort.by(Sort.Direction.DESC,"idNewsComment"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NewsComment> findAllSortIdAsc() {
		return dNewsComment.findAll(Sort.by(Sort.Direction.ASC,"idNewsComment"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<NewsComment> findById(int idNewsComment) {
		return dNewsComment.findById(idNewsComment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NewsComment> findByText(String textNewsComment) {
		return dNewsComment.findByText(textNewsComment);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<NewsComment> findByNewsId(int idNews){
		return dNewsComment.findByNewsId(idNews);
	}
}
