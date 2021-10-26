package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.MovieGenre;

@Repository
public interface IMovieGenreRepository extends JpaRepository<MovieGenre, Integer> {
	@Query("from MovieGenre mg where mg.movie.nameMovie like %:nameMovie% order by mg.idMovieGenre ASC")
	List<MovieGenre> findByMovieName(@Param("nameMovie") String nameMovie);
	
	@Query("from MovieGenre mg where mg.genre.nameGenre like %:nameGenre% order by mg.idMovieGenre ASC")
	List<MovieGenre> findByGenreName(@Param("nameGenre") String nameGenre);
}
