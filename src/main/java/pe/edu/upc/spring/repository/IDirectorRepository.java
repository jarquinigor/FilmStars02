package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Director;

@Repository
public interface IDirectorRepository extends JpaRepository<Director, Integer> {
	@Query("from Director d where d.nameDirector like %:nameDirector%")
	List<Director> findByName(@Param("nameDirector") String nameDirector);
}
