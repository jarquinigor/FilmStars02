package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.News;

public interface INewsService {
	public boolean save(News news);
	public void delete(int idNews);
	public List<News> findAllSortNameAsc();
	public List<News> findAllSortIdDesc();
	public List<News> findAllSortIdAsc();
	public Optional<News>findById(int idNews);
	public List<News> findByName(String nameNews);
}
