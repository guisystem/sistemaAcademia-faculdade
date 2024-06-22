package model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "foto")
public class ModelFoto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
    private String nomeImagemField;
	
	@Column(nullable = false)
    private String caminhoImagem;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "aluno_fk", nullable = true)
    private ModelAluno aluno;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "professor_fk", nullable = true)
	private ModelProfessor professor;
    
    public ModelFoto() {
		
	}
    
	public ModelFoto(String nomeImagemField, String caminhoImagem, ModelProfessor professor) {
		this(nomeImagemField, caminhoImagem);
		this.professor = professor;
	}

	public ModelFoto(String nomeImagemField, String caminhoImagem, ModelAluno aluno) {
		this(nomeImagemField, caminhoImagem);
		this.aluno = aluno;
	}

	public ModelFoto(String nomeImagemField, String caminhoImagem) {
		super();
		this.nomeImagemField = nomeImagemField;
		this.caminhoImagem = caminhoImagem;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeImagemField() {
		return nomeImagemField;
	}

	public void setNomeImagemField(String nomeImagemField) {
		this.nomeImagemField = nomeImagemField;
	}

	public String getCaminhoImagem() {
		return caminhoImagem;
	}

	public void setCaminhoImagem(String caminhoImagem) {
		this.caminhoImagem = caminhoImagem;
	}

	public ModelAluno getAluno() {
		return aluno;
	}

	public void setAluno(ModelAluno aluno) {
		this.aluno = aluno;
	}

	public ModelProfessor getProfessor() {
		return professor;
	}

	public void setProfessor(ModelProfessor professor) {
		this.professor = professor;
	}
    
}
