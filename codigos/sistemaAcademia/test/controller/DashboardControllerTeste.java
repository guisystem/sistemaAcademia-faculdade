package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelEspecialidade;
import model.ModelModalidade;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import view.DashboardTela;

class DashboardControllerTeste {

	private DashboardTela dashboardTela;
	private DashboardController controller;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		dashboardTela = new DashboardTela(usuario);
		controller = new DashboardController(dashboardTela);
	}
	
	@Test
	void testarPreencherTelaConfiguracaNull() {
		controller.preencherTela(usuario);
		assertEquals("Nome da sua academia", dashboardTela.getLblNomeAcademia().getText());
	}
	
	@Test
	void testarPreencherTelaSemNomeAcademia() {
		ModelConfiguracao conf = new ModelConfiguracao();
		conf.setNomeDaAcademia("");
		usuario.setConfiguracao(conf);
		
		controller.preencherTela(usuario);
		assertEquals("Nome da sua academia", dashboardTela.getLblNomeAcademia().getText());
	}
	
	@Test
	void testarPreencherTelaComConfiguracao() {
		ModelConfiguracao conf = new ModelConfiguracao();
		conf.setNomeDaAcademia("NomeAcademia");
		conf.setDiaDeAbrir("Segunda");
		conf.setDiaDeFechar("Segunda");
		conf.setHoraDeAbrirFormatado("00:00");
		conf.setHoraDeFecharFormatado("23:59");
		usuario.setConfiguracao(conf);
		
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade();
		ModelModalidade modalidade3 = new ModelModalidade();
		usuario.getModalidades().add(modalidade3);
		usuario.getModalidades().add(modalidade2);
		usuario.getModalidades().add(modalidade1);
		
		controller.preencherTela(usuario);
		assertEquals("NomeAcademia - Aberto", dashboardTela.getLblNomeAcademia().getText());
	}
	
	@Test
	void testarProfessoresEmExpedienteZero() {
		assertEquals(0, controller.getProfessores(usuario).size());
	}
	
	@Test
	void testarProfessoresEmExpediente() {
		ModelEspecialidade especialidade = new ModelEspecialidade();
		ModelProfessor professor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "23:59", 0, especialidade);
		professor.getDiasDeTrabalho().add(DayOfWeek.MONDAY);
		professor.getDiasDeTrabalho().add(DayOfWeek.TUESDAY);
		professor.getDiasDeTrabalho().add(DayOfWeek.WEDNESDAY);
		professor.getDiasDeTrabalho().add(DayOfWeek.THURSDAY);
		professor.getDiasDeTrabalho().add(DayOfWeek.FRIDAY);
		professor.getDiasDeTrabalho().add(DayOfWeek.SATURDAY);
		professor.getDiasDeTrabalho().add(DayOfWeek.SUNDAY);
		
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor(0, professor, "Sim");
		registroProfessor.setProfessor(professor);
		
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		assertEquals(1, controller.getProfessores(usuario).size());
	}

	@Test
	void testarAtualizarCores() {
		ModelConfiguracao conf = new ModelConfiguracao();
		conf.setCorAtualPrimaria("Amarelo");
		conf.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(conf);
		
		controller.atualizarCores(usuario);
		assertEquals("Amarelo", usuario.getConfiguracao().getCorAtualPrimaria());
		assertEquals("Azul", usuario.getConfiguracao().getCorAtualSecundaria());
		
	}

}
