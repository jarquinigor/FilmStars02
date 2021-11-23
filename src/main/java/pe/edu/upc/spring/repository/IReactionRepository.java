package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.Reaction;

@Repository
public interface IReactionRepository extends JpaRepository<Reaction, Integer> {
	@Query("from Reaction r where r.nameReaction like %:nameReaction% order by r.idReaction ASC")
	List<Reaction> findByName(@Param("nameReaction") String nameReaction);
}
