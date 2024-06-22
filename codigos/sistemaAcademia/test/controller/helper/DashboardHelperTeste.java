package controller.helper;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelAluno;
import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelCustoBasico;
import model.ModelEspecialidade;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelProfessor;
import model.ModelRegistroAluno;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
import view.DashboardTela;

class DashboardHelperTeste {

	private DashboardTela dashboardTela;
	private DashboardHelper helper;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		dashboardTela = new DashboardTela(usuario);
		helper = new DashboardHelper(dashboardTela);
	}
	
	@Test
	void testarAtualizarTelaConfiguracaoNull() {
		assertThrows(NullPointerException.class, () -> {
			helper.atualizarTela(usuario);
		});
	}
	
	@Test
	void testarAtualizarTelaComConfiguracao() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Domingo", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		helper.atualizarTela(usuario);
		assertEquals("NomeAcademiaTeste - Aberto", dashboardTela.getLblNomeAcademia().getText());
	}
	
	@Test
	void testarAtualizarTelaComConfiguracaoFinancas() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Domingo", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		ModelEspecialidade especialidade = new ModelEspecialidade("Musculação");
		ModelProfessor professor = new ModelProfessor("testeNome1" ,"123.456.789-01", "testeEmail1", "00:00", "23:59", 1500, especialidade);
		ModelRegistroProfessor registro = new ModelRegistroProfessor(professor, "Sim");
		professor.setRegistroProfessor(registro);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registro);
		
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 150);
		usuario.getCustosBasicos().add(custo);
		
		helper.atualizarTela(usuario);
		assertEquals("-1650.0", dashboardTela.getTextFieldLucro().getText());
	}
	
	@Test
	void testarAtualizarTelaComConfiguracaoPlanoPopular() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Domingo", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		ModelPlano plano1 = new ModelPlano("Plano1", 12, 500);
		plano1.setId(1L);
		ModelPlano plano2 = new ModelPlano("Plano2", 6, 300);
		plano2.setId(2L);
		ModelPlano plano3 = new ModelPlano("Plano3", 1, 60);
		plano3.setId(3L);
		
		ModelAluno aluno1 = new ModelAluno("testeNome1", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano1);
		aluno1.setCpf(Criptografar.criptografar(aluno1.getCpf()));
		aluno1.setEmail(Criptografar.criptografar(aluno1.getEmail()));
		
		ModelRegistroAluno registroAluno1 = new ModelRegistroAluno(1, aluno1);
		aluno1.setRegistroAluno(registroAluno1);
		aluno1.getRegistroAluno().setProximoPagamento(LocalDate.now());
		
		ModelAluno aluno2 = new ModelAluno("testeNome2", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano3);
		aluno2.setCpf(Criptografar.criptografar(aluno1.getCpf()));
		aluno2.setEmail(Criptografar.criptografar(aluno1.getEmail()));
		
		ModelRegistroAluno registroAluno2 = new ModelRegistroAluno(2, aluno2);
		aluno2.setRegistroAluno(registroAluno2);
		aluno2.getRegistroAluno().setProximoPagamento(LocalDate.now());
		
		ModelAluno aluno3 = new ModelAluno("testeNome3", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano3);
		aluno3.setCpf(Criptografar.criptografar(aluno1.getCpf()));
		aluno3.setEmail(Criptografar.criptografar(aluno1.getEmail()));
		
		ModelRegistroAluno registroAluno3 = new ModelRegistroAluno(3, aluno3);
		aluno3.setRegistroAluno(registroAluno3);
		aluno3.getRegistroAluno().setProximoPagamento(LocalDate.now());
		
		usuario.getPlanos().add(plano1);
		usuario.getPlanos().add(plano2);
		usuario.getPlanos().add(plano3);
		usuario.getAlunos().add(aluno1);
		usuario.getAlunos().add(aluno2);
		usuario.getAlunos().add(aluno3);
		usuario.getRegistroAlunos().add(registroAluno1);
		usuario.getRegistroAlunos().add(registroAluno2);
		usuario.getRegistroAlunos().add(registroAluno3);
		
		helper.atualizarTela(usuario);
		assertEquals("Plano3", dashboardTela.getTextFieldPlanoPopular().getText());
	}
	
	@Test
	void testarAtualizarTelaComConfiguracaoAlunosAtivos() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Domingo", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		ModelPlano plano = new ModelPlano("Plano", 12, 500);
		plano.setId(1L);
		
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano);
		aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
		aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
		
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(0, aluno);
		aluno.setRegistroAluno(registroAluno);
		aluno.getRegistroAluno().setProximoPagamento(LocalDate.now());
		
		ModelAluno aluno2 = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano);
		aluno2.setCpf(Criptografar.criptografar(aluno2.getCpf()));
		aluno2.setEmail(Criptografar.criptografar(aluno2.getEmail()));
		
		ModelRegistroAluno registroAluno2 = new ModelRegistroAluno(0, aluno2);
		aluno2.setRegistroAluno(registroAluno2);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataAntiga = LocalDate.parse("01/01/1979", dtf);
		aluno2.getRegistroAluno().setProximoPagamento(dataAntiga);
		
		usuario.getPlanos().add(plano);
		usuario.getAlunos().add(aluno);
		usuario.getAlunos().add(aluno2);
		usuario.getRegistroAlunos().add(registroAluno);
		usuario.getRegistroAlunos().add(registroAluno2);
		
		helper.atualizarTela(usuario);
		assertEquals("1", dashboardTela.getTextFieldQuantAlunos().getText());
		assertEquals("500.0", dashboardTela.getTextFieldGanhos().getText());
	}
	
	@Test
	void testarAtualizarTelaComConfiguracaoModalidadePopular() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Domingo", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		ModelModalidade modalidade1 = new ModelModalidade("Modalidade1", 10);
		modalidade1.setId(1L);
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 5);
		modalidade2.setId(2L);
		ModelModalidade modalidade3 = new ModelModalidade("Modalidade3", 0);
		modalidade3.setId(3L);
		
		ModelAluno aluno1 = new ModelAluno("testeNome1", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		aluno1.setCpf(Criptografar.criptografar(aluno1.getCpf()));
		aluno1.setEmail(Criptografar.criptografar(aluno1.getEmail()));
		aluno1.getModalidades().add(modalidade2);
		modalidade2.getAlunos().add(aluno1);
		
		ModelRegistroAluno registroAluno1 = new ModelRegistroAluno(1, aluno1);
		aluno1.setRegistroAluno(registroAluno1);
		aluno1.getRegistroAluno().setProximoPagamento(LocalDate.now());
		
		ModelAluno aluno2 = new ModelAluno("testeNome2", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		aluno2.setCpf(Criptografar.criptografar(aluno1.getCpf()));
		aluno2.setEmail(Criptografar.criptografar(aluno1.getEmail()));
		aluno2.getModalidades().add(modalidade1);
		modalidade1.getAlunos().add(aluno2);
		
		ModelRegistroAluno registroAluno2 = new ModelRegistroAluno(2, aluno2);
		aluno2.setRegistroAluno(registroAluno2);
		aluno2.getRegistroAluno().setProximoPagamento(LocalDate.now());
		
		ModelAluno aluno3 = new ModelAluno("testeNome3", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		aluno3.setCpf(Criptografar.criptografar(aluno1.getCpf()));
		aluno3.setEmail(Criptografar.criptografar(aluno1.getEmail()));
		aluno3.getModalidades().add(modalidade2);
		modalidade2.getAlunos().add(aluno3);
		
		ModelRegistroAluno registroAluno3 = new ModelRegistroAluno(3, aluno3);
		aluno3.setRegistroAluno(registroAluno3);
		aluno3.getRegistroAluno().setProximoPagamento(LocalDate.now());
		
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		usuario.getModalidades().add(modalidade3);
		usuario.getAlunos().add(aluno1);
		usuario.getAlunos().add(aluno2);
		usuario.getAlunos().add(aluno3);
		usuario.getRegistroAlunos().add(registroAluno1);
		usuario.getRegistroAlunos().add(registroAluno2);
		usuario.getRegistroAlunos().add(registroAluno3);
		
		helper.atualizarTela(usuario);
		
		assertEquals("Modalidade2", dashboardTela.getTextFieldNomeModalidade1().getText());
		assertEquals("Modalidade1", dashboardTela.getTextFieldNomeModalidade2().getText());
		assertEquals("Modalidade3", dashboardTela.getTextFieldNomeModalidade3().getText());
	}
	
	@Test
	void testarAtualizarTelaComConfiguracaoETresModalidades() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Domingo", "00:00", "00:01", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		ModelModalidade modalidade1 = new ModelModalidade("Modalidade1", 0);
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 5);
		ModelModalidade modalidade3 = new ModelModalidade("Modalidade3", 5);
		
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		usuario.getModalidades().add(modalidade3);
		
		helper.atualizarTela(usuario);
		assertEquals("0", dashboardTela.getTextFieldQuantAluno1().getText());
	}
	
	@Test
	void testarAtualizarTelaComConfiguracaoEDuasModalidades() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Segunda", "00:00", "23:59", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		ModelModalidade modalidade1 = new ModelModalidade("Modalidade1", 0);
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 5);
		
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		helper.atualizarTela(usuario);
		assertEquals("0", dashboardTela.getTextFieldQuantAluno1().getText());
	}
	
	@Test
	void testarAtualizarTelaComConfiguracaoEUmaModalidade() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "23:59", "00:01", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		ModelModalidade modalidade1 = new ModelModalidade("Modalidade1", 0);
		
		usuario.getModalidades().add(modalidade1);
		
		helper.atualizarTela(usuario);
		assertEquals("0", dashboardTela.getTextFieldQuantAluno1().getText());
	}
	
	@Test
	void testarGetProfessoresEmExpediente() {
		ArrayList<DayOfWeek> diasTrabalho = new ArrayList<>();
		diasTrabalho.add(DayOfWeek.FRIDAY);
		diasTrabalho.add(DayOfWeek.MONDAY);
		diasTrabalho.add(DayOfWeek.SATURDAY);
		diasTrabalho.add(DayOfWeek.SUNDAY);
		diasTrabalho.add(DayOfWeek.THURSDAY);
		diasTrabalho.add(DayOfWeek.TUESDAY);
		diasTrabalho.add(DayOfWeek.WEDNESDAY);

		ModelEspecialidade especialidade = new ModelEspecialidade("Musculação");
		ModelProfessor professor1 = new ModelProfessor("testeNome1" ,"123.456.789-01", "testeEmail1", "00:00", "23:59", 0, especialidade);
		ModelRegistroProfessor registro1 = new ModelRegistroProfessor(professor1, "Sim");
		professor1.setDiasDeTrabalho(diasTrabalho);

		ModelProfessor professor2 = new ModelProfessor("testeNome2" ,"123.456.789-01", "testeEmail2", "23:59", "00:01", 0, especialidade);
		ModelRegistroProfessor registro2 = new ModelRegistroProfessor(professor2, "Sim");
		professor2.setDiasDeTrabalho(diasTrabalho);

		ModelProfessor professor3 = new ModelProfessor("testeNome3" ,"123.456.789-01", "testeEmail3", "11:15", "11:50", 0, especialidade);
		ModelRegistroProfessor registro3 = new ModelRegistroProfessor(professor3, "Sim");
		professor3.setDiasDeTrabalho(diasTrabalho);
		
		usuario.getProfessores().add(professor1);
		usuario.getProfessores().add(professor2);
		usuario.getProfessores().add(professor3);
		usuario.getRegistroProfessores().add(registro1);
		usuario.getRegistroProfessores().add(registro2);
		usuario.getRegistroProfessores().add(registro3);
		
		
		ArrayList<ModelProfessor> professores = helper.getProfessoresExpediante(usuario);
		assertTrue(professores.size() > 0);
	}
	
	@Test
	void testarSetarCoresPrimariaESecundariaUma(){
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = dashboardTela.getBtnVerMaisQuantPlanos().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = dashboardTela.getBtnVerMaisQuantPlanos().getBackground();
		assertEquals(Color.WHITE, corS);
	}
	
	@Test
	void testarSetarCoresPrimariaESecundariaMaisDeUma(){
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = dashboardTela.getBtnVerMaisQuantPlanos().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = dashboardTela.getBtnVerMaisQuantPlanos().getBackground();
		assertEquals(Color.BLUE, corS);
	}

}
