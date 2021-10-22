package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Reaction")
public class Reaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReaction;

	@Column(name = "nameReaction", length = 30, nullable = false)
	private String nameReaction;
	
	@Column(name="imgLikeReaction", nullable=false, length=200)
	private String imgLikeReaction;
	
	@Column(name="imgDislikeReaction", nullable=false, length=200)
	private String imgDislikeReaction;

	public Reaction() {
		super();
	}

	public Reaction(int idReaction, String nameReaction, String imgLikeReaction, String imgDislikeReaction) {
		super();
		this.idReaction = idReaction;
		this.nameReaction = nameReaction;
		this.imgLikeReaction = imgLikeReaction;
		this.imgDislikeReaction = imgDislikeReaction;
	}

	public int getIdReaction() {
		return idReaction;
	}

	public void setIdReaction(int idReaction) {
		this.idReaction = idReaction;
	}

	public String getNameReaction() {
		return nameReaction;
	}

	public void setNameReaction(String nameReaction) {
		this.nameReaction = nameReaction;
	}

	public String getImgLikeReaction() {
		return imgLikeReaction;
	}

	public void setImgLikeReaction(String imgLikeReaction) {
		this.imgLikeReaction = imgLikeReaction;
	}

	public String getImgDislikeReaction() {
		return imgDislikeReaction;
	}

	public void setImgDislikeReaction(String imgDislikeReaction) {
		this.imgDislikeReaction = imgDislikeReaction;
	}
}
