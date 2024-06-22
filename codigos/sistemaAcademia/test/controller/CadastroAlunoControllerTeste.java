package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelAluno;
import model.ModelConfiguracao;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelUsuario;
import view.CadastroAlunoTela;

class CadastroAlunoControllerTeste {

	private CadastroAlunoController controller;
	private CadastroAlunoTela cadastroAlunoTela;
	private ModelUsuario usuario;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		
		cadastroAlunoTela = new CadastroAlunoTela(usuario);
		controller = new CadastroAlunoController(cadastroAlunoTela);
		controller.setEm(em);
		
	}
	 
	@Test
	void testarPreencherComboBoxPlano() {
		ModelPlano plano1 = new ModelPlano();
		ModelPlano plano2 = new ModelPlano("Plano2", 4, 200);
		ModelPlano plano3 = new ModelPlano("Plano3", 1, 50);
		
		usuario.getPlanos().add(plano3);
		usuario.getPlanos().add(plano2);
		usuario.getPlanos().add(plano1);
		
		controller.preencherComboBoxPlanos(usuario);
		int i = cadastroAlunoTela.getComboBoxPlano().getItemCount();
		
		assertEquals(3, i);
	}
	
	@Test
	void testarPreencherComboBoxModalidade() {
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		controller.preencherComboBoxModalidades(usuario);
		int i = cadastroAlunoTela.getComboBoxAdicionarModalidade().getItemCount();
		
		assertEquals(2, i);
	}
	
	@Test
	void testarAtualizarValor() {
		controller.atualizarValor(usuario);
		
		String i = cadastroAlunoTela.getTextFieldTotal().getText();
		assertEquals("", i);
	}
	
	@Test
	void testarAtualizarValorAlunoNullComPlano() {
		controller.atualizarValor(usuario);
		
		ModelPlano plano1 = new ModelPlano();
		ModelPlano plano2 = new ModelPlano("Plano2", 4, 200);
		
		usuario.getPlanos().add(plano1);
		usuario.getPlanos().add(plano2);
		
		controller.preencherComboBoxPlanos(usuario);
		cadastroAlunoTela.getComboBoxPlano().setSelectedItem(plano2);
		
		String i = cadastroAlunoTela.getTextFieldTotal().getText();
		float n = Float.parseFloat(i);
		
		assertEquals(200.0, n);
	}
	
	@Test
	void testarAtualizarValorComAlunoPlanoNull() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		usuario.getAlunos().add(aluno);
		
		controller.preencherComboBoxPlanos(usuario);
		
		String i = cadastroAlunoTela.getTextFieldTotal().getText();

		controller.atualizarValor(usuario);
		
		assertEquals("", i);
	}
	
	@Test
	void testarAtualizarValorComAlunoComPlano() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		usuario.getAlunos().add(aluno);
		
		ModelPlano plano = new ModelPlano("Plano", 4, 300);
		usuario.getPlanos().add(plano);
		
		controller.preencherComboBoxPlanos(usuario);
		
		String i = cadastroAlunoTela.getTextFieldTotal().getText();
		float n = Float.parseFloat(i);
		
		controller.atualizarValor(usuario);
		
		assertEquals(300.0, n);
	}
	
	@Test
	void testarAtualizarPeriodo() {
		ModelPlano plano = new ModelPlano("Plano", 4, 200);
		usuario.getPlanos().add(plano);
		
		controller.preencherComboBoxPlanos(usuario);
		
		controller.atualizarPeriodo();
		
		String msg = cadastroAlunoTela.getLblTempoPagamento().getText();
		
		assertEquals("* A cada 4 meses.", msg);
		
	}
	
	@Test
	void testarBtnRemoverModalidadeComModalidade(){
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		aluno.adicionarModalidade(modalidade2);
		aluno.adicionarModalidade(modalidade1);

		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		controller.preencherComboBoxModalidades(usuario);
		cadastroAlunoTela.getComboBoxRemoverModalidade().setSelectedItem(modalidade1);

		controller.removerModalidade(usuario);
		
		assertEquals(modalidade2, aluno.getModalidades().get(0));
		
	}
	
	@Test
	void testarBtnCadastrarAlunoPlanoNullMensagem(){
		controller.cadastrarAluno(usuario);
		/** NOTE: Terá que exibir uma mensagem erro plano */
	}
	
	@Test
	void testarBtnCadastrarAlunoModalidadeNullMensagem(){
		controller.cadastrarAluno(usuario);
		/** NOTE: Terá que exibir uma mensagem erro modalidade */

	}
	
	@Test
	void testarCadastrarAlunoSemModalidadeAntes() {
		ModelPlano plano = new ModelPlano("Plano", 4, 200);
		usuario.getPlanos().add(plano);
		
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 4);
		usuario.getModalidades().add(modalidade);
		
		ModelAluno aluno = new ModelAluno();
		aluno.setId(1);
		aluno.setCpf("");
		aluno.getModalidades().add(modalidade);
		usuario.getAlunos().add(aluno);
		
		controller.preencherComboBoxModalidades(usuario);
		controller.preencherComboBoxPlanos(usuario);

		cadastroAlunoTela.getComboBoxRemoverModalidade().setSelectedItem(modalidade);
		
		cadastroAlunoTela.getTextFieldNomeAluno().setText("NomeObterModelo");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("454.159.584-28");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("03/10/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("03/10/2023");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("EmailObterModelo");
		
		ArrayList<Object> inseridos = controller.cadastrarAluno(usuario);
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			verify(em, times(1)).persist(inseridos.get(2));
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
			
		}else {
			verify(em, times(0)).persist(any());
			verify(transaction, times(1)).begin();
		    verify(transaction, times(1)).commit();
		}
		
	}
	
	@Test
	void testarCadastrarAlunoComModalidadeAntes() {
		ModelPlano plano = new ModelPlano("Plano", 4, 200);
		usuario.getPlanos().add(plano);
		
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 4);
		usuario.getModalidades().add(modalidade);
		
		ModelAluno alunoModalidade = new ModelAluno();
		alunoModalidade.setId(0);
		alunoModalidade.setCpf("");
		alunoModalidade.getModalidades().add(modalidade);
		usuario.getModalidades().add(modalidade);
		usuario.getAlunos().add(alunoModalidade);
		
		controller.preencherComboBoxModalidades(usuario);
		controller.preencherComboBoxPlanos(usuario);
		
		cadastroAlunoTela.getComboBoxRemoverModalidade().setSelectedItem(modalidade);
		
		cadastroAlunoTela.getTextFieldNomeAluno().setText("NomeObterModelo");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("454.159.584-28");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("03/10/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("03/10/2023");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("EmailObterModelo");
		
		ArrayList<Object> inseridos = controller.cadastrarAluno(usuario);
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			verify(em, times(1)).persist(inseridos.get(2));
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
		}
		
	}
	
	@Test
	void testarBtnAdicionarModalidadeMensagem() {
		controller.adicionarModalidade(usuario);
		/** NOTE: Terá que exibir uma mensagem nenhuma modalidade*/
		
	}
	
	@Test
	void testarBtnAdicionarModalidadeComCpf() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("12345678901");
		
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		controller.preencherComboBoxModalidades(usuario);
		cadastroAlunoTela.getComboBoxAdicionarModalidade().setSelectedItem(modalidade2);
		controller.adicionarModalidade(usuario);
		
		assertEquals(modalidade2, cadastroAlunoTela.getComboBoxRemoverModalidade().getItemAt(0));
	}
	
	@Test
	void testarBtnAdicionarModalidadeSemCpf() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		controller.preencherComboBoxModalidades(usuario);
		cadastroAlunoTela.getComboBoxAdicionarModalidade().setSelectedItem(modalidade2);
		controller.adicionarModalidade(usuario);
		
		assertEquals(modalidade2, cadastroAlunoTela.getComboBoxRemoverModalidade().getItemAt(0));
	}
	
	@Test
	void testarBtnAdicionarModalidadeSemAluno() {
		
		ModelModalidade modalidade1 = new ModelModalidade("Teste", 0);
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);

		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		controller.preencherComboBoxModalidades(usuario);
		cadastroAlunoTela.getComboBoxAdicionarModalidade().setSelectedItem(modalidade2);
		controller.adicionarModalidade(usuario);
		
		assertEquals(1, cadastroAlunoTela.getComboBoxRemoverModalidade().getItemCount());
	}
	
	@Test
	void testarBtnRemoverModalidadeSemModalidadeMensagem(){
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		controller.preencherComboBoxModalidades(usuario);
		
		controller.removerModalidade(usuario);
		/** NOTE: Terá que exibir uma mensagem nenhuma modalidade */
	}
	
	@Test
	void testarBtnRemoverModalidadeSemModalidade(){
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		aluno.adicionarModalidade(modalidade1);
		
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		controller.preencherComboBoxModalidades(usuario);
		cadastroAlunoTela.getComboBoxRemoverModalidade().setSelectedItem(modalidade1);
		
		aluno.getModalidades().clear();
		controller.removerModalidade(usuario);
		
		assertEquals(modalidade1, cadastroAlunoTela.getComboBoxAdicionarModalidade().getItemAt(0));
		assertEquals(modalidade2, cadastroAlunoTela.getComboBoxAdicionarModalidade().getItemAt(1));
		
	}
	
	@Test
	void testarCarregarFoto() {
		controller.carregarFoto();
		/** NOTE: Terá que abrir explore de arquivo do desktop */
	}
	
	@Test
	void testarAtualizarCores() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		controller.atualizarCores(usuario);
		
		Color corP = cadastroAlunoTela.getBtnCadastrarAluno().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = cadastroAlunoTela.getBtnCadastrarAluno().getBackground();
		assertEquals(Color.BLUE, corS);
		
	}
	
}
