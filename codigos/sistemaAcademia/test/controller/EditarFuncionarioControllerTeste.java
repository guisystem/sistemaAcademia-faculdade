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
import model.ModelFuncionario;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarFuncionariosTela;

class EditarFuncionarioControllerTeste {

	private EditarFuncionariosTela editarFuncionariosTela;
	private EditarFuncionarioController controller;
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
		editarFuncionariosTela = new EditarFuncionariosTela(usuario);
		controller = new EditarFuncionarioController(editarFuncionariosTela);
		controller.setEm(em);
		
	}
	
	@Test
	void testarPreencherTabelaZerada() {
		controller.atualizarTabela(usuario);
		assertEquals(0, editarFuncionariosTela.getTableFuncionarios().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComFuncionario() {
		ModelFuncionario funcionario = new ModelFuncionario("NovoFuncionario", "Cargo", 1000);
		usuario.getFuncionarios().add(funcionario);
		
		controller.atualizarTabela(usuario);
		assertEquals(1, editarFuncionariosTela.getTableFuncionarios().getRowCount());
	}
	
	@Test
	void testarAdicionarFuncionarioCompararCamposIgual() {
		ModelFuncionario funcionario = new ModelFuncionario("NovoFuncionario", "Cargo", 1000);
		usuario.getFuncionarios().add(funcionario);
		
		controller.atualizarTabela(usuario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Cargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1000");
		
		controller.adicionarFuncionario(usuario);
		/** NOTE: Terá que exibir uma mensagem de funcionario já existe */
		
		assertEquals(funcionario, editarFuncionariosTela.getTableFuncionarios().getValueAt(editarFuncionariosTela.getTableFuncionarios().getSelectedRow(), 0));
	}
	
	@Test
	void testarAdicionarFuncionarioCompararCamposDiferente() {
		ModelFuncionario funcionario = new ModelFuncionario("NovoFuncionario", "Cargo", 1000);
		usuario.getFuncionarios().add(funcionario);
		
		controller.atualizarTabela(usuario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("OutroFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Cargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1005");
		
		ArrayList<Object> inseridos = controller.adicionarFuncionario(usuario);
		/** NOTE: Terá que exibir uma mensagem de funcionario adicionado e limpar a tela */
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			
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
		ModelFuncionario funcionario = new ModelFuncionario("NovoFuncionario", "Cargo", 1000);
		funcionario.setId(1L);
		usuario.getFuncionarios().add(funcionario);
		
		controller.atualizarTabela(usuario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Cargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1000");
		
		controller.adicionarFuncionario(usuario);
		/** NOTE: Terá que exibir uma mensagem de funcionario já existe e selecionar uma linha*/
		
		ModelFuncionario funcionarioTabela = controller.getDados(usuario);
		
		assertEquals(1000.0, funcionarioTabela.getSalario());
		assertEquals("NovoFuncionario", funcionarioTabela.getNome());
	}
	
	@Test
	void testarAtualizarFuncionarioAltera() {
		ModelFuncionario funcionario = new ModelFuncionario("NovoFuncionario", "Cargo", 1000);
		usuario.getFuncionarios().add(funcionario);
		
		controller.atualizarTabela(usuario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("OutroFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Cargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1000");
		
		ArrayList<Object> inseridos = controller.atualizarFuncionario(funcionario, usuario);
		/** NOTE: Terá que exibir uma mensagem de funcionario alterado com sucesso */
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
			
		}else {
			verify(em, times(0)).persist(any());
			verify(transaction, times(1)).begin();
		    verify(transaction, times(1)).commit();
		}
		
	}
	
	@Test
	void testarExcluirFuncionarioVerificarCampos() {
		ModelFuncionario funcionario = new ModelFuncionario("NovoFuncionario", "Cargo", 1000);
		usuario.getFuncionarios().add(funcionario);
		
		controller.atualizarTabela(usuario);
		controller.excluirFuncionario(funcionario, usuario);
		/** NOTE: Terá que exibir uma mensagem de campos vazios */
	}
	
	@Test
	void testarExcluirFuncionarioExclui() {
		ModelFuncionario funcionario = new ModelFuncionario("NovoFuncionario", "Cargo", 1000);
		usuario.getFuncionarios().add(funcionario);
		
		controller.atualizarTabela(usuario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Cargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1000");
		
		int quant = usuario.getFuncionarios().size();
		
		controller.excluirFuncionario(funcionario, usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se dejesa excluir o funcionario, clicar em "sim", para excluir */
	
		if(usuario.getFuncionarios().size() == quant - 1) {
			
			verify(em).createQuery("SELECT u FROM funcionario as u WHERE u.usuario IS NULL");
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
		}
	}
	
	@Test
	void testarAtualizarCoresPadrao() {
		usuario.setConfiguracao(null);
		view.getComboBoxMudarCorPrimaria().setSelectedItem("Branco");
		view.getComboBoxMudarCorSecundaria().setSelectedItem("Preto");
		
		controller.atualizarCores(usuario);
		
		Color corP = editarFuncionariosTela.getBtnAdicionar().getForeground();
		assertEquals(Color.WHITE, corP);
		
		Color corS = editarFuncionariosTela.getBtnAdicionar().getBackground();
		assertEquals(Color.BLACK, corS);
		
	}

}
