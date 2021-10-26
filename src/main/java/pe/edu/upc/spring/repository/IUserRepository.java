package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
	@Query("from User u where u.nameUser like %:nameUser% order by u.idUser ASC")
	List<User> findByName(@Param("nameUser") String nameUser);
	
	@Query("from User u where u.emailUser = :emailUser")
	List<User> findByEmail(@Param("emailUser") String emailUser); //Devuelve un usuario (OJO)
}
