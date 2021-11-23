package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.News;

@Repository
public interface INewsRepository extends JpaRepository<News, Integer> {
	@Query("from News n where LOWER(n.titleNews) like LOWER(concat('%',:titleNews,'%')) order by n.idNews ASC")
	List<News> findByName(@Param("titleNews") String titleNews);
}
