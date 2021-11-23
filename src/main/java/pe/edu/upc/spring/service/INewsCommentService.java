package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.NewsComment;

public interface INewsCommentService {
	public boolean save(NewsComment newsComment);
	public void delete(int idNewsComment);
	public List<NewsComment> findAllSortIdDesc();
	public List<NewsComment> findAllSortIdAsc();
	public Optional<NewsComment>findById(int idNewsComment);
	public List<NewsComment> findByText(String textNewsComment);
	public List<NewsComment> findByNewsId(int idNews);
}
