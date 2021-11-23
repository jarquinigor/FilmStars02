package pe.edu.upc.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.spring.model.UserNewsComment;

@Repository
public interface IUserNewsCommentRepository extends JpaRepository<UserNewsComment, Integer> {
	@Query("select COUNT(unc) from UserNewsComment unc where unc.newsComment.idNewsComment = :idNewsComment and unc.reaction.idReaction = 1")
	int countLikes(@Param("idNewsComment") int idNewsComment);
	
	@Query("select COUNT(unc) from UserNewsComment unc where unc.newsComment.idNewsComment = :idNewsComment and unc.reaction.idReaction = 2")
	int countDislikes(@Param("idNewsComment") int idNewsComment);
	
	@Query("select COUNT(unc) from UserNewsComment unc where unc.user.idUser = :idUser and unc.newsComment.idNewsComment = :idNewsComment and unc.newsComment.user.idUser=:idUser")
	int identifyCommentAuthor(@Param("idUser") int idUser,@Param("idNewsComment")  int idNewsComment);
	
	@Query("select COUNT(unc) from UserNewsComment unc where unc.user.idUser = :idUser and unc.newsComment.idNewsComment = :idNewsComment")
	int identifyCommentNonAuthor(@Param("idUser") int idUser,@Param("idNewsComment")  int idNewsComment);
	
	@Query("from UserNewsComment unc where unc.user.idUser = :idUser and unc.newsComment.news.idNews = :idNews order by unc.newsComment.idNewsComment DESC")
	List<UserNewsComment>findAllByUserAndNewsId(@Param("idUser") int idUser, @Param("idNews") int idNews);
	
	
	@Query("from UserNewsComment unc where unc.newsComment.idNewsComment = :idNewsComment")
	List<UserNewsComment>findAllByNewsCommentId(@Param("idNewsComment") int idNewsComment);
	
	
	@Query("from UserNewsComment unc where unc.user.idUser = :idUser and unc.newsComment.idNewsComment = :idNewsComment")
	List<UserNewsComment>findRow(@Param("idUser") int idUser, @Param("idNewsComment") int idNewsComment);
	
	@Query("from UserNewsComment unc where unc.idUserNewsComment = :idUserNewsComment")
	UserNewsComment findByUNCId(@Param("idUserNewsComment") int idUserNewsComment);
}
