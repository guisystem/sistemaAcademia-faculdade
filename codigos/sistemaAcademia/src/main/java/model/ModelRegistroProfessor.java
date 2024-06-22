package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity(name = "registroprofessor")
public class ModelRegistroProfessor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String ativo;
	
	@Transient
	private String detalhe = "VER MAIS";

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "professor_fk")
	private ModelProfessor professor;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	public ModelRegistroProfessor() {

	}
	
	public ModelRegistroProfessor(int id, ModelProfessor professor, String ativo) {
		this(professor, ativo);
		this.id = id;
		this.detalhe = "VER MAIS";
	}
	
	public ModelRegistroProfessor(ModelProfessor professor, String ativo) {
		this.professor = professor;
		this.ativo = ativo;
		this.detalhe = "VER MAIS";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ModelProfessor getProfessor() {
		return professor;
	}

	public void setProfessor(ModelProfessor professor) {
		this.professor = professor;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public ModelUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}
	
	@Override
	public String toString() {
		return professor.getNome();
	}
	
}
