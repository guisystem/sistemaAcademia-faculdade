package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name = "usuario")
public class ModelUsuario extends ModelPessoa{

	@Column(unique = true, nullable = false)
	private String nomeUsuario;
	
	@Column(nullable = false)
	private String senha;
	 
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "settings_fk")
	private ModelConfiguracao configuracao;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelCustoBasico> custosBasicos = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelFuncionario> funcionarios = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelModalidade> modalidades = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelEspecialidade> especialidades = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelPlano> planos = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelAluno> alunos = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelProfessor> professores = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelRegistroAluno> registroAlunos = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk")
	private List<ModelRegistroProfessor> registroProfessores = new ArrayList<>();
	
	public ModelUsuario() {

	}
	
	public ModelUsuario(String nomeUsuario, String senha) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
	}

	public ModelUsuario(String nome, String cpf, String email, String nomeUsuario, String senha,
			ModelConfiguracao configuracao) {
		super(nome, cpf, email);
		this.nomeUsuario = nomeUsuario;
		this.senha = senha;
		this.configuracao = configuracao;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public ModelConfiguracao getConfiguracao() {
		return configuracao;
	}

	public void setConfiguracao(ModelConfiguracao configuracao) {
		this.configuracao = configuracao;
	}

	public List<ModelCustoBasico> getCustosBasicos() {
		return custosBasicos;
	}

	public void setCustosBasicos(List<ModelCustoBasico> custosBasicos) {
		this.custosBasicos = custosBasicos;
	}

	public List<ModelFuncionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<ModelFuncionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<ModelModalidade> getModalidades() {
		return modalidades;
	}

	public void setModalidades(List<ModelModalidade> modalidades) {
		this.modalidades = modalidades;
	}

	public List<ModelEspecialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<ModelEspecialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<ModelPlano> getPlanos() {
		return planos;
	}

	public void setPlanos(List<ModelPlano> planos) {
		this.planos = planos;
	}

	public List<ModelAluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<ModelAluno> alunos) {
		this.alunos = alunos;
	}

	public List<ModelProfessor> getProfessores() {
		return professores;
	}

	public void setProfessores(List<ModelProfessor> professor) {
		this.professores = professor;
	}
	
	public List<ModelRegistroAluno> getRegistroAlunos() {
		return registroAlunos;
	}

	public void setRegistroAlunos(List<ModelRegistroAluno> registroAlunos) {
		this.registroAlunos = registroAlunos; 
	}

	public List<ModelRegistroProfessor> getRegistroProfessores() {
		return registroProfessores;
	}

	public void setRegistroProfessores(List<ModelRegistroProfessor> registroProfessor) {
		this.registroProfessores = registroProfessor;
	}

	public float getGastosTotal() {
		float gastos = 0;
		
		for(ModelFuncionario funcionario: funcionarios) {
			gastos += funcionario.getSalario();
		}
		
		for(ModelCustoBasico custos: custosBasicos) {
			gastos += custos.getValor();
		}
		
		for(ModelProfessor professores: professores) {
			if(professores.getRegistroProfessor().getAtivo().equalsIgnoreCase("Sim")) {
				gastos += professores.getSalario();				
			}
		}
		
		return gastos;
	}
	
	public float getGanhosTotal() {
		float ganho = 0;
		
		for(ModelRegistroAluno alunos: getAlunosAtivos()) {
			ganho += alunos.getAluno().getPlano().getValor();
			for(ModelModalidade modalidades: alunos.getAluno().getModalidades()) {
				ganho += modalidades.getTaxaExtra();
			}
		}

		return ganho;
	}

	public float getLucroTotal() {
		return getGanhosTotal() - getGastosTotal();
	}
	
	public int getQuantidadePlanos() {
		return planos.size();
	}

    public ArrayList<ModelRegistroAluno> getAlunosAtivos() {
    	int ativos = 0;
    	ArrayList<ModelRegistroAluno> alunosAtivos = new ArrayList<>();
    	
    	for(ModelRegistroAluno alunos: this.registroAlunos) {
    		if(alunos.getAtivo().equalsIgnoreCase("Sim")) {
    			ativos = ativos + 1;
    			alunosAtivos.add(alunos);
    		}
    	}
    	
    	return alunosAtivos;
    }
    
    public ArrayList<ModelRegistroProfessor> getProfessoresAtivos() {
    	int ativos = 0;
    	ArrayList<ModelRegistroProfessor> professoresAtivos = new ArrayList<>();
    	
    	if(registroProfessores != null) {
    		for(ModelRegistroProfessor professores: this.registroProfessores) {
    			if(professores.getAtivo().equalsIgnoreCase("Sim")) {
    				ativos = ativos + 1;
    				professoresAtivos.add(professores);
    			}
    		}
    	}
    	
    	return professoresAtivos;
    }

	public String getPlanoPopular() {
    	List<ModelRegistroAluno> alunos = getAlunosAtivos();
        
        String planoPopular = null;
        int cont = 0;

        for (int i = 0; i < alunos.size(); i++) {
            int quantidade = 0;
            
            for (int j = 0; j < alunos.size(); j++) {
                if (alunos.get(i).getAluno().getPlano().getId() == alunos.get(j).getAluno().getPlano().getId()) {
                    quantidade++;
                }
            }

            if (quantidade > cont) {
            	planoPopular = alunos.get(i).getAluno().getPlano().getNome();
            	cont = quantidade;
             
            }
        }
        return planoPopular;
	}

	public void removerEspecialidadePorId(ModelModalidade modalidade) {
		for(Iterator<ModelEspecialidade> especialidade = this.especialidades.iterator(); especialidade.hasNext(); ) {
			ModelEspecialidade especialidades = especialidade.next(); 
			if(especialidades.getId() == modalidade.getId()) {
				especialidade.remove();
			}
		}
	}
	
	@Override
	public String toString() {
		return getNomeUsuario() + " - " + getNome();
	}
}
