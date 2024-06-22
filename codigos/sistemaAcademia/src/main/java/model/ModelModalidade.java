package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "modalidade")
public class ModelModalidade extends ModelAtividadeFisica{
	
	@Column(nullable = false)
	private float taxaExtra;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	@ManyToMany(mappedBy = "modalidades")
	private List<ModelAluno> alunos = new ArrayList<>();
	
	public ModelModalidade() {

	}

	public ModelModalidade(String nome, float taxaExtra) {
		super(nome);
		this.taxaExtra = taxaExtra;
	}

	public float getTaxaExtra() {
		return taxaExtra;
	}

	public void setTaxaExtra(float taxaExtra) {
		this.taxaExtra = taxaExtra;
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

	public void adicionarAluno(ModelAluno aluno) {
		alunos.add(aluno);
	}
	
    public int getAlunosAtivos() {
    	int ativos = 0;
    	
    	
    	for(ModelAluno alunos: this.alunos) {
    		if(alunos.getRegistroAluno() == null || this.alunos.size() == 0) { // Estava &&
    			return 0;
    		}
    		
    		if(alunos.getRegistroAluno() != null) {
    			if(alunos.getRegistroAluno().getAtivo().equals("Sim")) {
    				ativos = ativos + 1;    			
    			}
    		}
    	}
    	
    	return ativos;
    }
    
    public int getProfessoresAtivos(ModelModalidade modalidade) {

    	if(usuario == null) {
    		return 0;
    	}
    	
    	for(ModelEspecialidade especialidade: usuario.getEspecialidades()) {
    		if(modalidade.getNome().equals(especialidade.getNome())) {
    			return especialidade.getProfessoresAtivos();
    		}
    	}
    	
    	return 0;
    }

	@Override
	public String toString() {
		return getNome() + " R$:" + getTaxaExtra();
	}
}
