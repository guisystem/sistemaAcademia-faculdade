package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
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

@Entity(name = "professor")
public class ModelProfessor extends ModelPessoa{
	
	@Column(nullable = false)
	private Date dataAdmissao;

	@Column(nullable = false)
	private int tempoContrato;
	
	@Column(nullable = false)
	private String tipoDeDataContrato;
	
	@Column(nullable = false)
	private Date horarioEntrada;

	@Column(nullable = false)
	private Date horarioSaida;

	@Column(nullable = false)
	private float salario;

	private ArrayList<DayOfWeek> diasDeTrabalho = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "registro_fk")
	private ModelRegistroProfessor registroProfessor;
	
	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "professor_especialidade",
	joinColumns = @JoinColumn(name = "professor_id", referencedColumnName = "id", nullable = false),
	inverseJoinColumns = @JoinColumn(name = "especialidade_id", referencedColumnName = "id", nullable = false))
	private List<ModelEspecialidade> especialidades = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "foto_fk", nullable = true)
	private ModelFoto foto;
	
	public ModelProfessor() {
		
	}
	
	public ModelProfessor(String nome, String cpf, String email, String horarioEntrada,
			String horarioSaida, float salario, ModelEspecialidade especialidade) {
		super(nome, cpf, email);
		this.especialidades.add(especialidade);
		if(horarioEntrada != null && horarioSaida != null) {
			try {
				this.horarioEntrada = new SimpleDateFormat("HH:mm").parse(horarioEntrada);
				this.horarioSaida = new SimpleDateFormat("HH:mm").parse(horarioSaida);
			} catch (ParseException e) {
				
			}			
		}
		this.salario = salario;
	}

	public List<ModelEspecialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<ModelEspecialidade> especialidade) {
		this.especialidades = especialidade;
	}

	public ArrayList<DayOfWeek> getDiasDeTrabalho() {
		return diasDeTrabalho;
	}

	public void setDiasDeTrabalho(ArrayList<DayOfWeek> dias) {
		this.diasDeTrabalho = dias;
	}

	public void adicionarDias(String dia) {
		
		if(dia.equalsIgnoreCase("segunda")) {
			diasDeTrabalho.add(DayOfWeek.MONDAY);
		}else if(dia.equalsIgnoreCase("terça") || dia.equalsIgnoreCase("terca")) {
			diasDeTrabalho.add(DayOfWeek.TUESDAY);
		}else if(dia.equalsIgnoreCase("quarta")) {
			diasDeTrabalho.add(DayOfWeek.WEDNESDAY);
		}else if(dia.equalsIgnoreCase("quinta")) {
			diasDeTrabalho.add(DayOfWeek.THURSDAY);
		}else if(dia.equalsIgnoreCase("sexta")) {
			diasDeTrabalho.add(DayOfWeek.FRIDAY);
		}else if(dia.equalsIgnoreCase("sábado") || dia.equalsIgnoreCase("sabado")) {
			diasDeTrabalho.add(DayOfWeek.SATURDAY);
		}else if(dia.equalsIgnoreCase("domingo")) {
			diasDeTrabalho.add(DayOfWeek.SUNDAY);
		}
	}
	
	public ArrayList<String> getDiaFormatado() {
		ArrayList<String> diasFormatados = new ArrayList<>();
		for(DayOfWeek dia: this.diasDeTrabalho) {
			if(dia.equals(DayOfWeek.MONDAY)) {
				diasFormatados.add("Segunda");
			}else if(dia.equals(DayOfWeek.TUESDAY)) {
				diasFormatados.add("Terça");
			}else if(dia.equals(DayOfWeek.WEDNESDAY)) {
				diasFormatados.add("Quarta");
			}else if(dia.equals(DayOfWeek.THURSDAY)) {
				diasFormatados.add("Quinta");
			}else if(dia.equals(DayOfWeek.FRIDAY)) {
				diasFormatados.add("Sexta");
			}else if(dia.equals(DayOfWeek.SATURDAY)) {
				diasFormatados.add("Sábado");
			}else if(dia.equals(DayOfWeek.SUNDAY)) {
				diasFormatados.add("Domingo");
			}
		}
		return diasFormatados;
	}

	public Date getHorarioEntrada() {
		return horarioEntrada;
	}
	
	public String getHoraEntradaFormatada() {
		return new SimpleDateFormat("HH:mm").format(horarioEntrada);
	}

	public void setHorarioEntrada(Date horarioEntrada) {
		this.horarioEntrada = horarioEntrada;
	}
	
	public void setHoraEntradaFormatada(String horaEntrada) {
		try {
			this.horarioEntrada = new SimpleDateFormat("HH:mm").parse(horaEntrada);
		} catch (ParseException e) {

		}
	}

	public Date getHorarioSaida() {
		return horarioSaida;
	}
	
	public String getHoraSaidaFormatada() {
		return new SimpleDateFormat("HH:mm").format(horarioSaida);
	}

	public void setHorarioSaida(Date horarioSaida) {
		this.horarioSaida = horarioSaida;
	}
	
	public void setHoraSaidaFormatada(String horaSaida) {
		try {
			this.horarioSaida = new SimpleDateFormat("HH:mm").parse(horaSaida);
		} catch (ParseException e) {

		}
	}
	
	public Date getDataAdmissao() {
		return dataAdmissao;
	}
	
	public String getDataAdmissaoFormatada() {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(dataAdmissao);
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}
	
	public String getTipoDeDataContrato() {
		return tipoDeDataContrato;
	}

	public void setTipoDeDataContrato(String tipoDeDataContrato) {
		this.tipoDeDataContrato = tipoDeDataContrato;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}
	
	public int getTempoContrato() {
		return tempoContrato;
	}

	public void setTempoContrato(int tempoContrato) {
		this.tempoContrato = tempoContrato;
	}

	public ModelUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}

	public ModelRegistroProfessor getRegistroProfessor() {
		return registroProfessor;
	}

	public void setRegistroProfessor(ModelRegistroProfessor registroProfessor) {
		this.registroProfessor = registroProfessor;
	}

	public ModelFoto getFoto() {
		return foto;
	}

	public void setFoto(ModelFoto foto) {
		this.foto = foto;
	}

	public void adicionarEspecialidade(ModelEspecialidade especialidade) {
		this.especialidades.add(especialidade);
		especialidade.adicionarProfessor(this);
	}

}
