package pe.edu.upc.spring.service;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.spring.model.CriticReview;

public interface ICriticReviewService {
	public boolean save(CriticReview criticReview);
	public void delete(int idCriticReview);
	public List<CriticReview> findAll();
	public List<CriticReview> findAllSortAsc();
	public Optional<CriticReview>findById(int idCriticReview);
	public List<CriticReview> findByCriticName(String nameCritic);
	public List<CriticReview> findByMovieName(String nameMovie);
}
