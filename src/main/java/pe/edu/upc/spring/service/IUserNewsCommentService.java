package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.UserNewsComment;

public interface IUserNewsCommentService {
	public boolean save(UserNewsComment UserNewsComment);
	public void delete(int idUserNewsComment);
	public void deleteBatch(int idNewsComment);
	public List<UserNewsComment> findAllSortIdDesc();
	public List<UserNewsComment> findAllSortIdAsc();
	public Optional<UserNewsComment>findById(int idUserNewsComment);
	public int identifyCommentAuthor(int idUser, int idUserNews);
	public int identifyCommentNonAuthor(int idUser, int idUserNews);
	public List<UserNewsComment>findAllByUserAndNewsId(int idUser, int idNews);
	public List<UserNewsComment>findRow(int idUser, int idNewsComment);
	public UserNewsComment findByUNCId(int idUserNewsComment);
}
