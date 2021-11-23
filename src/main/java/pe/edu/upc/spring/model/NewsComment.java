package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "NewsComment")
public class NewsComment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNewsComment;
	
	@ManyToOne
	@JoinColumn(name = "idNews", nullable = false)
	private News news;
	
	@ManyToOne
	@JoinColumn(name = "idUser", nullable = false)
	private Users user;
	
	@Column(name="textNewsComment", nullable=false, length=400)
	private String textNewsComment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dateNewsComment")
	@DateTimeFormat(pattern="d MMM, yyyy" )
	private Date dateNewsComment;
	
	@Column(name="likesNewsComment", nullable=false)
	private int likesNewsComment;
	
	@Column(name="dislikesNewsComment", nullable=false)
	private int dislikesNewsComment;
	
	@Column(name="textDateNewsComment", nullable=true, length=70)
	private String textDateNewsComment;
	
	public NewsComment() {
		super();
		dateNewsComment = new Date();
		likesNewsComment=0;
		dislikesNewsComment=0;
	}

	public NewsComment(int idNewsComment, News news, Users user, String textNewsComment, Date dateNewsComment,
			int likesNewsComment, int dislikesNewsComment, String textDateNewsComment) {
		super();
		this.idNewsComment = idNewsComment;
		this.news = news;
		this.user = user;
		this.textNewsComment = textNewsComment;
		this.dateNewsComment = dateNewsComment;
		this.likesNewsComment = likesNewsComment;
		this.dislikesNewsComment = dislikesNewsComment;
		this.textDateNewsComment = textDateNewsComment;
	}

	public int getIdNewsComment() {
		return idNewsComment;
	}

	public void setIdNewsComment(int idNewsComment) {
		this.idNewsComment = idNewsComment;
	}

	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public String getTextNewsComment() {
		return textNewsComment;
	}

	public void setTextNewsComment(String textNewsComment) {
		this.textNewsComment = textNewsComment;
	}

	public Date getDateNewsComment() {
		return dateNewsComment;
	}

	public void setDateNewsComment(Date dateNewsComment) {
		this.dateNewsComment = dateNewsComment;
	}

	public int getLikesNewsComment() {
		return likesNewsComment;
	}

	public void setLikesNewsComment(int likesNewsComment) {
		this.likesNewsComment = likesNewsComment;
	}

	public int getDislikesNewsComment() {
		return dislikesNewsComment;
	}

	public void setDislikesNewsComment(int dislikesNewsComment) {
		this.dislikesNewsComment = dislikesNewsComment;
	}

	public String getTextDateNewsComment() {
		return textDateNewsComment;
	}

	public void setTextDateNewsComment(String textDateNewsComment) {
		this.textDateNewsComment = textDateNewsComment;
	}
}
