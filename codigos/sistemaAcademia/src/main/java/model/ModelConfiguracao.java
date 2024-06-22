package model;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "configuracao")
public class ModelConfiguracao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String nomeDaAcademia;

	@Column(nullable = false)
	private String diaDeAbrir;

	@Column(nullable = false)
	private String diaDeFechar;

	@Column(nullable = false)
	private Date horaDeAbrir;

	@Column(nullable = false)
	private Date horaDeFechar;
	
	@Column(nullable = false)
	private String corAtualPrimaria;
	
	@Column(nullable = false)
	private String corAtualSecundaria;
	
	@OneToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_fk")
	private ModelUsuario usuario;
	
	public ModelConfiguracao() {

	}
	
	public ModelConfiguracao(String nomeDaAcademia, String diaDeAbrir, String diaDeFechar, String horaDeAbrir,
			String horaDeFechar, String corAtualPrimaria, String corAtualSecundaria, ModelUsuario usuario) {
		this(nomeDaAcademia, diaDeAbrir, diaDeFechar, horaDeAbrir, horaDeFechar, corAtualPrimaria, corAtualSecundaria);
		this.usuario = usuario;
	}
	
	public ModelConfiguracao(String nomeDaAcademia, String diaDeAbrir, String diaDeFechar, String horaDeAbrir,
			String horaDeFechar, String corAtualPrimaria, String corAtualSecundaria) {
		super();
		this.nomeDaAcademia = nomeDaAcademia;
		this.diaDeAbrir = diaDeAbrir;
		this.diaDeFechar = diaDeFechar;
		try {
			if(horaDeAbrir == null) {
				horaDeAbrir = "";
			}
			
			if(horaDeFechar == null) {
				horaDeFechar = "";
			}
			
			this.horaDeAbrir = new SimpleDateFormat("HH:mm").parse(horaDeAbrir);
			this.horaDeFechar = new SimpleDateFormat("HH:mm").parse(horaDeFechar);;
		} catch (ParseException e) {
			
		}
		this.corAtualPrimaria = corAtualPrimaria;
		this.corAtualSecundaria = corAtualSecundaria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeDaAcademia() {
		return nomeDaAcademia;
	}

	public void setNomeDaAcademia(String nomeDaAcademia) {
		this.nomeDaAcademia = nomeDaAcademia;
	}

	public String getDiaDeAbrir() {
		return diaDeAbrir;
	}

	public DayOfWeek getDiaDeAbrirDate() {
		if(diaDeAbrir.equalsIgnoreCase("Segunda")) {
			return DayOfWeek.MONDAY;
		}else if(diaDeAbrir.equalsIgnoreCase("Terça") || diaDeAbrir.equalsIgnoreCase("terca")) {
			return DayOfWeek.TUESDAY;
		}else if(diaDeAbrir.equalsIgnoreCase("Quarta")) {
			return DayOfWeek.WEDNESDAY;
		}else if(diaDeAbrir.equalsIgnoreCase("Quinta")) {
			return DayOfWeek.THURSDAY;
		}else if(diaDeAbrir.equalsIgnoreCase("Sexta")) {
			return DayOfWeek.FRIDAY;
		}else if(diaDeAbrir.equalsIgnoreCase("Sábado") || diaDeAbrir.equalsIgnoreCase("sabado")) {
			return DayOfWeek.SATURDAY;
		}else if(diaDeAbrir.equalsIgnoreCase("Domingo")) {
			return DayOfWeek.SUNDAY;
		}
		
		return null;
	}
	
	public void setDiaDeAbrir(String diaDeAbrir) {
		this.diaDeAbrir = diaDeAbrir;
	}

	public String getDiaDeFechar() {
		return diaDeFechar;
	}
	
	public DayOfWeek getDiaDeFecharDate() {
		if(diaDeFechar.equalsIgnoreCase("Segunda")) {
			return DayOfWeek.MONDAY;
		}else if(diaDeFechar.equalsIgnoreCase("Terça") || diaDeFechar.equalsIgnoreCase("terca")) {
			return DayOfWeek.TUESDAY;
		}else if(diaDeFechar.equalsIgnoreCase("Quarta")) {
			return DayOfWeek.WEDNESDAY;
		}else if(diaDeFechar.equalsIgnoreCase("Quinta")) {
			return DayOfWeek.THURSDAY;
		}else if(diaDeFechar.equalsIgnoreCase("Sexta")) {
			return DayOfWeek.FRIDAY;
		}else if(diaDeFechar.equalsIgnoreCase("Sábado") || diaDeFechar.equalsIgnoreCase("sabado")) {
			return DayOfWeek.SATURDAY;
		}else if(diaDeFechar.equalsIgnoreCase("Domingo")) {
			return DayOfWeek.SUNDAY;
		}
		
		return null;
	}

	public void setDiaDeFechar(String diaDeFechar) {
		this.diaDeFechar = diaDeFechar;
	}

	public Date getHoraDeAbrir() {
		return horaDeAbrir;
	}
	
	public String getHoraDeAbrirFormatada() {
		return new SimpleDateFormat("HH:mm").format(horaDeAbrir);
	}

	public void setHoraDeAbrir(Date horaDeAbrir) {
		this.horaDeAbrir = horaDeAbrir;
	}
	
	public void setHoraDeAbrirFormatado(String horaDeAbrir) {
		try {
			this.horaDeAbrir = new SimpleDateFormat("HH:mm").parse(horaDeAbrir);
		} catch (ParseException e) {
			
		}
	}
	
	public Date getHoraDeFechar() {
		return horaDeFechar;
	}
	
	public String getHoraDeFecharFormatada() {
		return new SimpleDateFormat("HH:mm").format(horaDeFechar);
	}

	public void setHoraDeFechar(Date horaDeFechar) {
		this.horaDeFechar = horaDeFechar;
	}
	
	public void setHoraDeFecharFormatado(String horaDeFechar) {
		try {
			this.horaDeFechar = new SimpleDateFormat("HH:mm").parse(horaDeFechar);
		} catch (ParseException e) {
			
		}
	}

	public String getCorAtualPrimaria() {
		return corAtualPrimaria;
	}

	public void setCorAtualPrimaria(String corAtualPrimaria) {
		this.corAtualPrimaria = corAtualPrimaria;
	}

	public String getCorAtualSecundaria() {
		return corAtualSecundaria;
	}

	public void setCorAtualSecundaria(String corAtualSecundaria) {
		this.corAtualSecundaria = corAtualSecundaria;
	}

	public ModelUsuario getUsuario() {
		return usuario;
	}

	public void setUsuario(ModelUsuario usuario) {
		this.usuario = usuario;
	}
	
	public Color getCorPrimariaColor(ModelUsuario usuario) {
		if(usuario.getConfiguracao() == null) {
			return Color.WHITE;
		}
		
		if(corAtualPrimaria.equals("Amarelo")) {
			return Color.YELLOW;
		}else if(corAtualPrimaria.equals("Azul")) {
			return Color.BLUE;
		}else if(corAtualPrimaria.equals("Branco")) {
			return Color.WHITE;
		}else if(corAtualPrimaria.equals("Cinza")) {
			return new Color(225, 225, 225);
		}else if(corAtualPrimaria.equals("Laranja")) {
			return new Color(255, 140, 0);
		}else if(corAtualPrimaria.equals("Marrom")) {
			return new Color(140, 70, 35);
		}else if(corAtualPrimaria.equals("Preto")) {
			return Color.BLACK;
		}else if(corAtualPrimaria.equals("Rosa")) {
			return new Color(255, 0, 255);
		}else if(corAtualPrimaria.equals("Roxo")) {
			return new Color(170, 0, 255);
		}else if(corAtualPrimaria.equals("Verde")) {
			return Color.GREEN;
		}else if(corAtualPrimaria.equals("Vermelho")) {
			return Color.RED;
		}
		
		return null;
	}
	
	public Color getCorSecundariaColor(ModelUsuario usuario) {
		if(usuario.getConfiguracao() == null) {
			return Color.BLACK;
		}
		
		if(corAtualSecundaria.equals("Amarelo")) {
			return Color.YELLOW;
		}else if(corAtualSecundaria.equals("Azul")) {
			return Color.BLUE;
		}else if(corAtualSecundaria.equals("Branco")) {
			return Color.WHITE;
		}else if(corAtualSecundaria.equals("Cinza")) {
			return new Color(225, 225, 225);
		}else if(corAtualSecundaria.equals("Laranja")) {
			return new Color(255, 140, 0);
		}else if(corAtualSecundaria.equals("Marrom")) {
			return new Color(140, 70, 35);
		}else if(corAtualSecundaria.equals("Preto")) {
			return Color.BLACK;
		}else if(corAtualSecundaria.equals("Rosa")) {
			return new Color(255, 0, 255);
		}else if(corAtualSecundaria.equals("Roxo")) {
			return new Color(170, 0, 255);
		}else if(corAtualSecundaria.equals("Verde")) {
			return Color.GREEN;
		}else if(corAtualSecundaria.equals("Vermelho")) {
			return Color.RED;
		}
		
		return null;
	}

	@Override
	public String toString() {
		return usuario.getNomeUsuario();
	}
}
