package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity(name = "plano")
public class ModelPlano {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(nullable = false)
	private int periodo;
	
	@Column(nullable = false)
	private float valor;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "plano_fk")
	private List<ModelAluno> alunos = new ArrayList<>();
	
	public ModelPlano() {
	
	}

	public ModelPlano(String nome, int periodo, float valor) {
		super();
		this.nome = nome;
		this.periodo = periodo;
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public float getValor() {
		return valor;
	}

	public void setValor(float valor) {
		this.valor = valor;
	}
	
	public ModelUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}

	public List<ModelAluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<ModelAluno> alunos) {
		this.alunos = alunos;
	}
	
	public int getAlunosAtivos() {
    	int ativos = 0;
    	
    	for(ModelAluno alunos: this.alunos) {
    		if(alunos.getRegistroAluno() == null) {
    			return 0;
    		}
    		
    		if(alunos.getRegistroAluno().getAtivo().equals("Sim")) {
    			ativos = ativos + 1;
    		}
    	}
    	
    	return ativos;
	}

	@Override
	public String toString() {
		return getNome();
	}

}
