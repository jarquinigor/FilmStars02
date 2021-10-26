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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "News")
public class News implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idNews;

	@Column(name="titleNews", nullable=false, length=80)
	private String titleNews;
	
	@Column(name="subtitleNews", nullable=false, length=100)
	private String subtitleNews;
	
	@Column(name="textNews", nullable=false, length=800)
	private String textNews;
	
	@Column(name="imgNews", nullable=false, length=200)
	private String imgNews;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dateNews")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
