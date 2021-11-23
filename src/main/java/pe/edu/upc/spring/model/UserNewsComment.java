package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserNewsComment")
public class UserNewsComment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idUserNewsComment;
	
	@ManyToOne
	@JoinColumn(name = "idUser", nullable = false)
	private Users user;
	
	@ManyToOne
	@JoinColumn(name = "idNewsComment", nullable = false)
	private NewsComment newsComment;
	
	@ManyToOne
	@JoinColumn(name = "idReaction", nullable = false)
	private Reaction reaction;
	
	public UserNewsComment() {
		super();
	}

	public UserNewsComment(int idUserNewsComment, Users user, NewsComment newsComment, Reaction reaction) {
		super();
		this.idUserNewsComment = idUserNewsComment;
		this.user = user;
		this.newsComment = newsComment;
		this.reaction = reaction;
	}

	public int getIdUserNewsComment() {
		return idUserNewsComment;
	}

	public void setIdUserNewsComment(int idUserNewsComment) {
		this.idUserNewsComment = idUserNewsComment;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public NewsComment getNewsComment() {
		return newsComment;
	}

	public void setNewsComment(NewsComment newsComment) {
		this.newsComment = newsComment;
	}

	public Reaction getReaction() {
		return reaction;
	}

	public void setReaction(Reaction reaction) {
		this.reaction = reaction;
	}
}
