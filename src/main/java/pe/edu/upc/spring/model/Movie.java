package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Movie")
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMovie;
	
	@ManyToOne
	@JoinColumn(name = "idDirector", nullable = false)
	private Director director;
	
	@Column(name="nameMovie", nullable=false, length=60)
	private String nameMovie;
	
	@Column(name="synopsisMovie", nullable=false, length=400)
	private String synopsisMovie;
	
	@Column(name="plotMovie", nullable=false, length=800)
	private String plotMovie;
	
	@Column(name="coverMovie", nullable=false, length=200)
	private String coverMovie;
	
	@Column(name="imgMovie", nullable=false, length=200)
	private String imgMovie;
	
	@Column(name="imgCarrMovie1", nullable=false, length=200)
	private String imgCarrMovie1;
	
	@Column(name="imgCarrMovie2", nullable=false, length=200)
	private String imgCarrMovie2;
	
	@Column(name="imgCarrMovie3", nullable=false, length=200)
	private String imgCarrMovie3;

	public Movie() {
		super();
	}

	public Movie(int idMovie, Director director, String nameMovie, String synopsisMovie, String plotMovie,
			String coverMovie, String imgMovie, String imgCarrMovie1, String imgCarrMovie2, String imgCarrMovie3) {
		super();
		this.idMovie = idMovie;
		this.director = director;
		this.nameMovie = nameMovie;
		this.synopsisMovie = synopsisMovie;
		this.plotMovie = plotMovie;
		this.coverMovie = coverMovie;
		this.imgMovie = imgMovie;
		this.imgCarrMovie1 = imgCarrMovie1;
		this.imgCarrMovie2 = imgCarrMovie2;
		this.imgCarrMovie3 = imgCarrMovie3;
	}

	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public String getNameMovie() {
		return nameMovie;
	}

	public void setNameMovie(String nameMovie) {
		this.nameMovie = nameMovie;
	}

	public String getSynopsisMovie() {
		return synopsisMovie;
	}

	public void setSynopsisMovie(String synopsisMovie) {
		this.synopsisMovie = synopsisMovie;
	}

	public String getPlotMovie() {
		return plotMovie;
	}

	public void setPlotMovie(String plotMovie) {
		this.plotMovie = plotMovie;
	}

	public String getCoverMovie() {
		return coverMovie;
	}

	public void setCoverMovie(String coverMovie) {
		this.coverMovie = coverMovie;
	}

	public String getImgMovie() {
		return imgMovie;
	}

	public void setImgMovie(String imgMovie) {
		this.imgMovie = imgMovie;
	}

	public String getImgCarrMovie1() {
		return imgCarrMovie1;
	}

	public void setImgCarrMovie1(String imgCarrMovie1) {
		this.imgCarrMovie1 = imgCarrMovie1;
	}

	public String getImgCarrMovie2() {
		return imgCarrMovie2;
	}

	public void setImgCarrMovie2(String imgCarrMovie2) {
		this.imgCarrMovie2 = imgCarrMovie2;
	}

	public String getImgCarrMovie3() {
		return imgCarrMovie3;
	}

	public void setImgCarrMovie3(String imgCarrMovie3) {
		this.imgCarrMovie3 = imgCarrMovie3;
	}
}
