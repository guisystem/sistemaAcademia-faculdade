package controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelEspecialidade;
import model.ModelFoto;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
import view.DadosProfessorTela;
import view.RegistroProfessorTela;

class DadosProfessorControllerTeste {

	private DadosProfessorTela dadosProfessorTela;
	private DadosProfessorController controller;
	private ModelUsuario usuario;
	private ModelProfessor professor;
	private ModelRegistroProfessor registroProfessor;
	private ModelEspecialidade especialidade;
	private ModelConfiguracao configuracao;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		especialidade = new ModelEspecialidade();
		
		professor = new ModelProfessor("testeNome" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, especialidade);
		registroProfessor = new ModelRegistroProfessor(0, professor, "Sim");
		registroProfessor.setProfessor(professor);
		registroProfessor.getProfessor().setDataAdmissao(new Date());
		registroProfessor.getProfessor().setTipoDeDataContrato("Ano");
		
		registroProfessor.getProfessor().setCpf(Criptografar.criptografar(registroProfessor.getProfessor().getCpf()));
		registroProfessor.getProfessor().setEmail(Criptografar.criptografar(registroProfessor.getProfessor().getEmail()));
		
		professor.adicionarDias("Sábado");
		professor.setRegistroProfessor(registroProfessor);
		
		usuario = new ModelUsuario();
		configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "00:00", "22:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		usuario.getEspecialidades().add(especialidade);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		
		dadosProfessorTela = new DadosProfessorTela(usuario, registroProfessor);
		controller = new DadosProfessorController(dadosProfessorTela);
		controller.setEm(em);
	}
	
	@Test
	void testarAtualizarDadosComFoto() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		controller.atualizarDados(usuario, registroProfessor);
	}
	
	@Test
	void testarCancelarAlteracoesDadosIguais() {
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(especialidade);
		
		controller.cancelarAlteracoes(registroProfessor, especialidades);
	}
	
	@Test
	void testarCancelarAlteracoesDadosDiferentes() {
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(especialidade);
		
		dadosProfessorTela.getTextFieldNomeProfessor().setText("NovoNome");
		
		controller.cancelarAlteracoes(registroProfessor, especialidades);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja cancelar */
	}
	
	@Test
	void testarConfirmarAlteracoesVerificarDadosIguais() {
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(especialidade);
		
		registroProfessor.getProfessor().setCpf(Criptografar.criptografar("454.159.584-28"));
		registroProfessor.getProfessor().setEmail(Criptografar.criptografar("testeEmail"));
		dadosProfessorTela.getChckbxSabado().setSelected(true);

		controller.atualizarDados(usuario, registroProfessor);
		controller.confirmarAlteracoes(usuario, registroProfessor, especialidades);
		
		registroProfessor.getProfessor().setCpf(Criptografar.descriptografar(registroProfessor.getProfessor().getCpf()));
		registroProfessor.getProfessor().setEmail(Criptografar.descriptografar(registroProfessor.getProfessor().getEmail()));
		
		assertTrue(registroProfessor.getProfessor().getCpf().equals(dadosProfessorTela.getTextFieldCpfProfessor().getText()));
		assertTrue(registroProfessor.getProfessor().getEmail().equals(dadosProfessorTela.getTextFieldEmailProfessor().getText()));
		/** Todos os dados serão iguais, portanto ao clicar no botão de confirmar, ela deve ser apenas fechada */
		
	}
	
	@Test
	void testarConfirmarAlteracoesVerificarDadosDiferentes() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		RegistroProfessorTela registroProfessorTela = new RegistroProfessorTela(usuario);
		@SuppressWarnings("unused")
		RegistroProfessorController controllerRegistro = new RegistroProfessorController(registroProfessorTela);
		
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(especialidade);
		
		registroProfessor.getProfessor().setCpf(Criptografar.criptografar("454.159.584-28"));
		registroProfessor.getProfessor().setEmail(Criptografar.criptografar("testeEmail"));
		dadosProfessorTela.getChckbxSabado().setSelected(true);
		
		controller.atualizarDados(usuario, registroProfessor);
		
		dadosProfessorTela.getTextFieldNomeProfessor().setText("NovoNome");
		
		ArrayList<Object> inseridos = controller.confirmarAlteracoes(usuario, registroProfessor, especialidades);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja alterar, clicar em "sim" e a tabela será atualizada */
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			verify(em, times(1)).persist(inseridos.get(2));
			verify(em, times(1)).persist(inseridos.get(3));
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
			
		}else {
			verify(em, times(0)).persist(any());
			verify(transaction, times(1)).begin();
		    verify(transaction, times(1)).commit();
		}
	}
	
	@Test
	void testarSairDaTelaCompararDadosIguais() {
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(especialidade);
		
		controller.sairDaTelaDefaultClose(registroProfessor, especialidades);
	}
	
	@Test
	void testarSairDaTelaCompararDadosDiferentes() {
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		especialidades.add(especialidade);
		
		dadosProfessorTela.getTextFieldNomeProfessor().setText("NovoNome");

		controller.sairDaTelaDefaultClose(registroProfessor, especialidades);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja sair */
	}
	
	@Test
	void testarAdicionarModalidadeComboBoxNull() {
		controller.atualizarDados(usuario, registroProfessor);
		dadosProfessorTela.getComboBoxAdicionarEspecialidade().setSelectedItem(null);
		controller.adicionarEspecialidade(usuario, registroProfessor);
		/** NOTE: Terá que exibir uma mensagem de nenhuma especialidade selecionada */
	}
	
	@Test
	void testarAdicionarEspecialidade() {
		ModelEspecialidade especialidade2 = new ModelEspecialidade("Especialidade2");
		especialidade2.setId(2L);
		
		usuario.getEspecialidades().add(especialidade2);
		especialidade2.setUsuario(usuario);
		
		controller.atualizarDados(usuario, registroProfessor);
		controller.adicionarEspecialidade(usuario, registroProfessor);
		
		assertEquals(especialidade2, registroProfessor.getProfessor().getEspecialidades().get(1));
	}
	
	@Test
	void testarRemoverEspecialidadeComboBoxNull() {
		controller.atualizarDados(usuario, registroProfessor);
		dadosProfessorTela.getComboBoxExcluirEspecialidade().setSelectedItem(null);
		controller.removerEspecialidade(usuario, registroProfessor);
		/** NOTE: Terá que exibir uma mensagem de nenhuma especialidade selecionada */
	}
	
	@Test
	void testarRemoverEspecialidade() {
		controller.removerEspecialidade(usuario, registroProfessor);
		assertEquals(0, registroProfessor.getProfessor().getEspecialidades().size());
	}
	
	@Test
	void testarCarregarFoto() {
		controller.carregarFoto();
		/** NOTE: Terá que abrir explore de arquivo do desktop */
	}
	
	@Test
	void testarAtualizarCoresPadrao() {
		usuario.setConfiguracao(null);
		
		controller.atualizarCores(usuario);
		
		Color corP = dadosProfessorTela.getBtnConfirmar().getForeground();
		assertEquals(Color.WHITE, corP);
		
		Color corS = dadosProfessorTela.getBtnConfirmar().getBackground();
		assertEquals(Color.BLACK, corS);
		
	}
	
	@Test
	void testarCalcularTempoTipoAno() {
		registroProfessor.getProfessor().setTempoContrato(1);
		Date dataAdmissaoNova = null;
		try {
			dataAdmissaoNova = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("30/05/2024 12:00");
		} catch (ParseException e) {
			
		}
		registroProfessor.getProfessor().setDataAdmissao(dataAdmissaoNova);
		
		controller.atualizarDados(usuario, registroProfessor);
		
		String tempoEncerramento = controller.calcularTempo(registroProfessor);
		
		assertEquals("Contrato se encerra em: 2025-05-30", tempoEncerramento);
		
	}
	
	@Test
	void testarCalcularTempoTipoMes() {
		registroProfessor.getProfessor().setTempoContrato(1);
		registroProfessor.getProfessor().setTipoDeDataContrato("Mês");
		Date dataAdmissaoNova = null;
		try {
			dataAdmissaoNova = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse("30/05/2024 12:00");
		} catch (ParseException e) {
			
		}
		registroProfessor.getProfessor().setDataAdmissao(dataAdmissaoNova);
		
		controller.atualizarDados(usuario, registroProfessor);
		
		String tempoEncerramento = controller.calcularTempo(registroProfessor);
		
		assertEquals("Contrato se encerra em: 2024-06-30", tempoEncerramento);
		
	}
	
}
