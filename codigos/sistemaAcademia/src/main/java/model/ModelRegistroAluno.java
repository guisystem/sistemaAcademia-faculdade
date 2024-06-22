package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity(name = "registroaluno")
public class ModelRegistroAluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private LocalDate ultimoPagamento;
	
	@Column(nullable = false)
	private LocalDate proximoPagamento;
	
	@Column(nullable = false)
	private String ativo;
	
	@Column(nullable = false)
	private String detalhe = "VER MAIS";
	
	@Column(nullable = false)
	private float total;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "aluno_fk")
	private ModelAluno aluno;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	public ModelRegistroAluno() {

	}
	
	public ModelRegistroAluno(int id, ModelAluno aluno) {
		this(aluno);
		this.id = id;
		this.detalhe = "VER MAIS";
	}
	
	public ModelRegistroAluno(ModelAluno aluno) {
		this.aluno = aluno;
		this.ultimoPagamento = LocalDate.parse(aluno.getDataMatriculaFormatada(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		this.proximoPagamento = this.ultimoPagamento.plusMonths(aluno.getPlano().getPeriodo());
		this.total = getTotal();
		setAtivo();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ModelAluno getAluno() {
		return aluno;
	}

	public void setAluno(ModelAluno aluno) {
		this.aluno = aluno;
	}

	public LocalDate getUltimoPagamento() {
		return ultimoPagamento;
	}

	public void setUltimoPagamento(LocalDate ultimoPagamento) {
		this.ultimoPagamento = ultimoPagamento;
	}

	public LocalDate getProximoPagamento() {
		return proximoPagamento;
	}

	public void setProximoPagamento(LocalDate proximoPagamento) {
		this.proximoPagamento = proximoPagamento;
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
	
	public String getAtivo() {
		setAtivo();
		return ativo;
	}
	
	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}
	
	public void setAtivo() {
		LocalDate dataAtual = LocalDate.now();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(getProximoPagamentoFormatada(), dtf);
		int daysBetween = (int) ChronoUnit.DAYS.between(proximoPagamento, dataAtual);
		
		if(daysBetween > 5) {
			this.ativo = "Não";
		}else {
			this.ativo = "Sim";			
		}
	}
	
	public float getTotal() {
		total = 0;
		for(ModelModalidade modalidades: aluno.getModalidades()) {
			total += modalidades.getTaxaExtra();
		}
		total = total * aluno.getPlano().getPeriodo();
		return aluno.getPlano().getValor() + total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getUltimoPagamentoFormatada() {
		return ultimoPagamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public String getProximoPagamentoFormatada() {
		return proximoPagamento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}
	
	public String getPagar() {
		
		int periodoAluno = aluno.getPlano().getPeriodo();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate ultimoPagamento = LocalDate.parse(getUltimoPagamentoFormatada(), dtf);
        LocalDate hoje = LocalDate.now();
        int monthBetween = (int) ChronoUnit.MONTHS.between(ultimoPagamento, hoje);
    
        return monthBetween >= periodoAluno ? "Pagar" : "Não Disponível";
	}
	
	@Override
	public String toString() {
		return aluno.getNome();
	}
	
}
