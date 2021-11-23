package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.NewsComment;

@Repository
public interface INewsCommentRepository extends JpaRepository<NewsComment, Integer> {
	@Query("from NewsComment nc where LOWER(nc.textNewsComment) like LOWER(concat('%',:textNewsComment,'%')) order by nc.idNewsComment ASC")
	List<NewsComment> findByText(@Param("textNewsComment") String textNewsComment);
	
	@Query("from NewsComment nc where nc.news.idNews = :idNews order by nc.idNewsComment DESC")
	List<NewsComment> findByNewsId(@Param("idNews") int idNews);
}
