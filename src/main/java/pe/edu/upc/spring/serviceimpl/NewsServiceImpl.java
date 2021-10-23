package pe.edu.upc.spring.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.upc.spring.model.News;
import pe.edu.upc.spring.repository.INewsRepository;
import pe.edu.upc.spring.service.INewsService;

@Service
public class NewsServiceImpl implements INewsService {

	@Autowired
	private INewsRepository dNews;

	@Override
	@Transactional
	public boolean save(News news) {
		News objNews = dNews.save(news);
		if (objNews == null)
			return false;
		else
			return true;
	}

	@Override
	@Transactional
	public void delete(int idNews) {
		dNews.deleteById(idNews);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<News> findAll() {
		return dNews.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<News> findAllSortAsc() {
		return dNews.findAll(Sort.by(Sort.Direction.ASC,"idNews"));
	}
	
	@Override
	@Transactional(readOnly = true)
	public Optional<News> findById(int idNews) {
		return dNews.findById(idNews);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<News> findByName(String nameNews) {
		return dNews.findByName(nameNews);
	}
}
