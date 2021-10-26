package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Movie;

@Repository
public interface IMovieRepository extends JpaRepository<Movie, Integer> {
	@Query("from Movie m where m.nameMovie like %:nameMovie% order by m.idMovie ASC")
	List<Movie> findByName(@Param("nameMovie") String nameMovie);
}
