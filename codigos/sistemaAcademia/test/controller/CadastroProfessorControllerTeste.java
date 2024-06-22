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

import model.ModelConfiguracao;
import model.ModelEspecialidade;
import model.ModelUsuario;
import view.CadastroProfessorTela;

class CadastroProfessorControllerTeste {

	private CadastroProfessorController controller;
	private CadastroProfessorTela cadastroProfessorTela;
	private ModelUsuario usuario;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		cadastroProfessorTela = new CadastroProfessorTela(usuario);
		controller = new CadastroProfessorController(cadastroProfessorTela);
		controller.setEm(em);
	}
	
	@Test
	void testarPreencherEspecialidade() {
		ModelEspecialidade especialidade1 = new ModelEspecialidade();
		ModelEspecialidade especialidade2 = new ModelEspecialidade("especialidade2");
		
		usuario.getEspecialidades().add(especialidade1);
		usuario.getEspecialidades().add(especialidade2);
		
		controller.preencherEspecialidades(usuario);
		int i = cadastroProfessorTela.getComboBoxEspecialidade().getItemCount();
		
		assertEquals(2, i);
	}
	
	@Test
	void testarBtnCadastrarProfessorEspecialidadeNullMensagem(){
		controller.cadastrarProfessor(usuario);
		//** NOTE: Terá que exibir uma mensagem erro especialidade*/
	}
	
	@Test
	void testarBtnCadastrarProfessorSemDiaSelecionado(){
		ModelEspecialidade especialidade = new ModelEspecialidade("especialidade");
		usuario.getEspecialidades().add(especialidade);
		
		controller.preencherEspecialidades(usuario);
		
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(true);
		cadastroProfessorTela.getRdbtnMes().setSelected(false);
		
		ArrayList<Object> inseridos = controller.cadastrarProfessor(usuario);
		
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
	void testarBtnCadastrarProfessorComDiaSelecionado(){
		ModelEspecialidade especialidade = new ModelEspecialidade("especialidade");
		usuario.getEspecialidades().add(especialidade);
		
		controller.preencherEspecialidades(usuario);
		
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(true);
		cadastroProfessorTela.getRdbtnMes().setSelected(false);
		
		cadastroProfessorTela.getChckbxSegunda().setSelected(true);
		
		ArrayList<Object> inseridos = controller.cadastrarProfessor(usuario);
		
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
		
		Color corP = cadastroProfessorTela.getBtnCadastrarProfessor().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = cadastroProfessorTela.getBtnCadastrarProfessor().getBackground();
		assertEquals(Color.BLUE, corS);
		
	}

}
