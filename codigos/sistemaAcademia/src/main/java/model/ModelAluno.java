package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "aluno")
public class ModelAluno extends ModelPessoa{
	
	@Column(nullable = false)
	private Date dataNascimento;
	
	@Column(nullable = false)	
	private LocalDate dataMatricula;
	
	@ManyToOne(cascade = CascadeType.MERGE) 
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "plano_fk")
	private ModelPlano plano;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "registro_fk")
	private ModelRegistroAluno registroAluno;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "aluno_modalidade",
	joinColumns = @JoinColumn(name = "aluno_id", referencedColumnName = "id", nullable = false),
	inverseJoinColumns = @JoinColumn(name = "modalidade_id", referencedColumnName = "id", nullable = false))
	private List<ModelModalidade> modalidades = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "foto_fk", nullable = true)
	private ModelFoto foto;
	
	public ModelAluno() {

	}
	
	public ModelAluno(int id, String nome, String cpf, String email, String dataNascimento, String dataMatricula, ModelPlano plano) {
		this(nome, cpf, email, dataNascimento, dataMatricula, plano);
		this.id = id;
	}
	
	public ModelAluno(String nome, String cpf, String email, String dataNascimento, String dataMatricula, ModelPlano plano) {
		super(nome, cpf, email);
		if(dataNascimento != null) {
			try {
				this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
			} catch (ParseException e) { 
				this.dataNascimento = null;
			}
		}else {
			this.dataNascimento = null;
		}

		if(dataMatricula == null || dataMatricula.equals("  /  /    ")) {
			this.dataMatricula = LocalDate.parse(getDataMatriculaFormatada(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}else {
			try {
				this.dataMatricula = LocalDate.parse(dataMatricula, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			}catch (DateTimeParseException e){
				
			}
		}
		this.plano = plano;
		
	}
	
	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public LocalDate getDataMatricula() {
		return dataMatricula;
	}

	public void setDataMatricula(LocalDate dataMatricula) {
		this.dataMatricula = dataMatricula;
	}
	
	public String getDataNascimentoFormatada() {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").format(dataNascimento);			
		}catch(NullPointerException e) {
			return null;			
		}
	}
	
	public void setDataNascimentoFormatada(String dataNascimento) {
		try {
			this.dataNascimento = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimento);
		} catch (ParseException e) {

		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	public String getDataMatriculaFormatada() {
		if(dataMatricula == null || dataMatricula.equals("  /  /    ")) {
			String dataMatricula2 = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
			this.dataMatricula = LocalDate.parse(dataMatricula2, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			return dataMatricula.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}else {
			return dataMatricula.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			
		}
	}
	
	public void setDataMatriculaFormatada(String dataMatricula) {
		if(dataMatricula == null || dataMatricula.equals("  /  /    ")) {
			this.dataMatricula = LocalDate.parse(getDataMatriculaFormatada(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));				
		}else {
			try {
				this.dataMatricula = LocalDate.parse(dataMatricula, DateTimeFormatter.ofPattern("dd/MM/yyyy"));					
			}catch (DateTimeParseException e){
				
			}
		}
	}

	public ModelPlano getPlano() {
		return plano;
	}

	public void setPlano(ModelPlano plano) {
		this.plano = plano;
	}

	public ModelUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}
	
	public List<ModelModalidade> getModalidades() {
		return modalidades;
	}

	public void setModalidades(ArrayList<ModelModalidade> modalidade) {
		this.modalidades = modalidade;
	}

	public ModelRegistroAluno getRegistroAluno() {
		return registroAluno;
	}

	public void setRegistroAluno(ModelRegistroAluno registroAluno) {
		this.registroAluno = registroAluno;
	}

	public void adicionarModalidade(ModelModalidade modalidades) {
		this.modalidades.add(modalidades);
		modalidades.adicionarAluno(this);
	}
	
	public void removerModalidade(ModelModalidade modalidades) {
		this.modalidades.remove(modalidades);
	}

	public ModelFoto getFoto() {
		return foto;
	}

	public void setFoto(ModelFoto foto) {
		this.foto = foto;
	}

}
