package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.CriticReview;

@Repository
public interface ICriticReviewRepository extends JpaRepository<CriticReview, Integer> {
	@Query("from CriticReview cr where cr.critic.nameCritic like %:nameCritic% order by cr.idCriticReview ASC")
	List<CriticReview> findByCriticName(@Param("nameCritic") String nameCritic);
	
	@Query("from CriticReview cr where cr.movie.nameMovie like %:nameMovie% order by cr.idCriticReview ASC")
	List<CriticReview> findByMovieName(@Param("nameMovie") String nameMovie);
}
