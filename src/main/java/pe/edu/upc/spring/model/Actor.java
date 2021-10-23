package pe.edu.upc.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Actor")
public class Actor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idActor;

	@Column(name = "nameActor", length = 60, nullable = false)
	private String nameActor;
	
	@Column(name="imgActor", length=200, nullable=false)
	private String imgActor;

	public Actor() {
		super();
	}

	public Actor(int idActor, String nameActor, String imgActor) {
		super();
		this.idActor = idActor;
		this.nameActor = nameActor;
		this.imgActor = imgActor;
	}

	public int getIdActor() {
		return idActor;
	}

	public void setIdActor(int idActor) {
		this.idActor = idActor;
	}

	public String getNameActor() {
		return nameActor;
	}

	public void setNameActor(String nameActor) {
		this.nameActor = nameActor;
	}

	public String getImgActor() {
		return imgActor;
	}

	public void setImgActor(String imgActor) {
		this.imgActor = imgActor;
	}
}
