package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.MovieActor;

@Repository
public interface IMovieActorRepository extends JpaRepository<MovieActor, Integer> {
	@Query("from MovieActor ma where ma.movie.nameMovie like %:nameMovie% order by ma.idMovieActor ASC")
	List<MovieActor> findByMovieName(@Param("nameMovie") String nameMovie);
	
	@Query("from MovieActor ma where ma.actor.nameActor like %:nameActor% order by ma.idMovieActor ASC")
	List<MovieActor> findByActorName(@Param("nameActor") String nameActor);
}
