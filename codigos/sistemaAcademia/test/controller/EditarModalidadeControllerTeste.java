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
import model.ModelModalidade;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarModalidadeTela;

class EditarModalidadeControllerTeste {

	private EditarModalidadeTela editarModalidadeTela;
	private EditarModalidadeController controller;
	private ModelUsuario usuario;
	private ModelConfiguracao configuracao;
	private ConfiguracaoTela view;
	@SuppressWarnings("unused")
	private ConfiguracaoController controllerConf;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		configuracao.setHoraDeAbrirFormatado("00:00");
		configuracao.setHoraDeFecharFormatado("10:00");
		
		usuario = new ModelUsuario();
		usuario.setConfiguracao(configuracao);
		configuracao.setUsuario(usuario);
		
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		
		view = new ConfiguracaoTela(usuario);
		controllerConf = new ConfiguracaoController(view);
		editarModalidadeTela = new EditarModalidadeTela(usuario);
		controller = new EditarModalidadeController(editarModalidadeTela);
		controller.setEm(em);
		
	}
	
	@Test
	void testarPreencherTabelaZerada() {
		controller.atualizarTabela(usuario);
		assertEquals(0, editarModalidadeTela.getTableModalidades().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComModalidade() {
		ModelModalidade modalidade = new ModelModalidade("NovaModalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		controller.atualizarTabela(usuario);
		assertEquals(1, editarModalidadeTela.getTableModalidades().getRowCount());
	}
	
	@Test
	void testarAdicionarModalidadeVerificarCampoIgual() {
		ModelModalidade modalidade = new ModelModalidade("NovaModalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		controller.atualizarTabela(usuario);
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		controller.adicionarModalidade(usuario);
		/** NOTE: Terá que exibir uma mensagem de modalidade já existe */
		
		assertEquals("NovaModalidade", editarModalidadeTela.getTableModalidades().getValueAt(
				editarModalidadeTela.getTableModalidades().getSelectedRow(), 0));
	}
	
	@Test
	void testarAdicionarModalidadeVerificarCampoDiferente() {
		ModelModalidade modalidade = new ModelModalidade("NovaModalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		controller.atualizarTabela(usuario);
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("OutraModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		ArrayList<Object> inseridos = controller.adicionarModalidade(usuario);
		/** NOTE: Terá que exibir uma mensagem de modaldiadade adicionada e limpar a tela */
		
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
	void testarGetDados() {
		ModelModalidade modalidade = new ModelModalidade("NovaModalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		controller.atualizarTabela(usuario);
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		controller.adicionarModalidade(usuario);
		/** NOTE: Terá que exibir uma mensagem de modalidade já existe e selecionar uma linha*/
		
		ModelModalidade modalidadeTabela = (ModelModalidade) controller.getDados(usuario, 0);
		
		assertEquals(0.0, modalidadeTabela.getTaxaExtra());
		assertEquals("NovaModalidade", modalidadeTabela.getNome());
	}
	
	@Test
	void testarAtualizarModalidadeSemProfessorEAlunoAtivo() {
		ModelProfessor professor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 0, new ModelEspecialidade());
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor(professor, "Não");
		professor.setRegistroProfessor(registroProfessor);
		registroProfessor.setProfessor(professor);
		
		ModelModalidade modalidade = new ModelModalidade("NovaModalidade", 0);
		
		ModelEspecialidade especialidade = new ModelEspecialidade("NovaModalidade");
		especialidade.getProfessores().add(professor);
		professor.getEspecialidades().add(especialidade);
		
		usuario.getEspecialidades().add(especialidade);
		usuario.getModalidades().add(modalidade);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		controller.atualizarTabela(usuario);
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		controller.adicionarModalidade(usuario); /** NOTE: É preciso que adicione uma modalidade igual para que seja selecionado uma linha
		 											 e assim o método "getDados" pegar a especialidade */
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("OutraModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		ArrayList<Object> inseridos = controller.atualizarModalidade(modalidade, usuario);
		/** NOTE: Terá que exibir uma mensagem confirmando a alteração e limparTela */
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			verify(em, times(1)).persist(inseridos.get(2));
			
			verify(transaction, times(2)).begin();
			verify(transaction, times(2)).commit();
			
		}else {
			verify(em, times(0)).persist(any());
			verify(transaction, times(1)).begin();
		    verify(transaction, times(1)).commit();
		}
		
	}
	
	@Test
	void testarExcluirModalidadeSemProfessorEAlunoAtivo() {
		ModelProfessor professor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 0, new ModelEspecialidade());
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor(professor, "Não");
		professor.setRegistroProfessor(registroProfessor);
		registroProfessor.setProfessor(professor);
		
		ModelModalidade modalidade = new ModelModalidade("NovaModalidade", 0);
		
		ModelEspecialidade especialidade = new ModelEspecialidade("NovaModalidade");
		especialidade.getProfessores().add(professor);
		professor.getEspecialidades().add(especialidade);
		
		usuario.getEspecialidades().add(especialidade);
		usuario.getModalidades().add(modalidade);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		controller.atualizarTabela(usuario);
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		controller.adicionarModalidade(usuario); /** NOTE: É preciso que adicione uma modalidade igual para que seja selecionado uma linha
		 											 e assim o método "getDados" pegar a especialidade */
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		int quant = usuario.getModalidades().size();
		
		controller.excluirModalidade(modalidade, usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se dejesa excluir a modalidade, clicar em "sim", para excluir */
		
		if(usuario.getModalidades().size() == quant - 1) {
			
			verify(em).createQuery("SELECT u FROM modalidade as u WHERE u.usuario IS NULL");
			
			verify(transaction, times(2)).begin();
			verify(transaction, times(2)).commit();
		}
			
	}
	
	@Test
	void testarAtualizarCoresPadrao() {
		usuario.setConfiguracao(null);
		view.getComboBoxMudarCorPrimaria().setSelectedItem("Branco");
		view.getComboBoxMudarCorSecundaria().setSelectedItem("Preto");
		
		controller.atualizarCores(usuario);
		
		Color corP = editarModalidadeTela.getBtnAdicionar().getForeground();
		assertEquals(Color.WHITE, corP);
		
		Color corS = editarModalidadeTela.getBtnAdicionar().getBackground();
		assertEquals(Color.BLACK, corS);
		
	}

}
