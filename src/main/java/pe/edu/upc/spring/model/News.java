package pe.edu.upc.spring.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "News")
public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNews;

	@NotEmpty(message = "Debe ingresar un título para la noticia")
	@Column(name="titleNews", nullable=false, length=100)
	private String titleNews;
	
	@NotEmpty(message = "Debe ingresar un subtítulo para la noticia")
	@Column(name="subtitleNews", nullable=false, length=200)
	private String subtitleNews;
	
	@NotEmpty(message = "Debe ingresar el contenido de la noticia")
	@Column(name="textNews", nullable=false, length=1200) //800
	private String textNews;
	
	@NotEmpty(message = "Debe ingresar una imagen para la noticia")
	@Column(name="imgNews", nullable=false, length=200)
	private String imgNews;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dateNews")
	@DateTimeFormat(pattern="d MMM, yyyy") 
	private Date dateNews;

	public News() {
		super();
		dateNews = new Date();
	}

	public News(int idNews, String titleNews, String subtitleNews, String textNews, String imgNews, Date dateNews) {
		super();
		this.idNews = idNews;
		this.titleNews = titleNews;
		this.subtitleNews = subtitleNews;
		this.textNews = textNews;
		this.imgNews = imgNews;
		this.dateNews = dateNews;
	}

	public int getIdNews() {
		return idNews;
	}

	public void setIdNews(int idNews) {
		this.idNews = idNews;
	}

	public String getTitleNews() {
		return titleNews;
	}

	public void setTitleNews(String titleNews) {
		this.titleNews = titleNews;
	}

	public String getSubtitleNews() {
		return subtitleNews;
	}

	public void setSubtitleNews(String subtitleNews) {
		this.subtitleNews = subtitleNews;
	}

	public String getTextNews() {
		return textNews;
	}

	public void setTextNews(String textNews) {
		this.textNews = textNews;
	}

	public String getImgNews() {
		return imgNews;
	}

	public void setImgNews(String imgNews) {
		this.imgNews = imgNews;
	}

	public Date getDateNews() {
		return dateNews;
	}

	public void setDateNews(Date dateNews) {
		this.dateNews = dateNews;
	}
}
