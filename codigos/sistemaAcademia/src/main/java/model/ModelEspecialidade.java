package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity(name = "especialidade")
public class ModelEspecialidade extends ModelAtividadeFisica{
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	@ManyToMany(mappedBy = "especialidades")
	private List<ModelProfessor> professores = new ArrayList<>();
	
	public ModelEspecialidade() {

	}

	public ModelEspecialidade(String nome) {
		super(nome);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ModelUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}

	public List<ModelProfessor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<ModelProfessor> professores) {
		this.professores = professores;
	}
	
	public int getProfessoresAtivos() {
    	int ativos = 0;
    	
    	for(ModelProfessor professores: this.professores) {
    		if(professores.getRegistroProfessor() == null) {
    			return 0;
    		}
    		
    		if(professores.getRegistroProfessor().getAtivo().equals("Sim")) {
    			ativos = ativos + 1;
    		}
    	}
    	
    	return ativos;
    }

	public void adicionarProfessor(ModelProfessor professor) {
		professores.add(professor);
	}

	@Override
	public String toString() {
		return getNome();
	}
}
