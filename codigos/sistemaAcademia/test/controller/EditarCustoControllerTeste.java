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
import model.ModelCustoBasico;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarCustosTela;

class EditarCustoControllerTeste {
	
	private EditarCustosTela editarCustosTela;
	private EditarCustoController controller;
	private ModelUsuario usuario;
	private ModelConfiguracao configuracao;
	private ConfiguracaoTela configuracaoTela;
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
		
        configuracaoTela = new ConfiguracaoTela(usuario);
		controllerConf = new ConfiguracaoController(configuracaoTela);
		editarCustosTela = new EditarCustosTela(usuario);
		controller = new EditarCustoController(editarCustosTela);
		controller.setEm(em);
		
	}
	
	@Test
	void testarPreencherTabelaZerada() {
		controller.atualizarTabela(usuario);
		assertEquals(0, editarCustosTela.getTableCustosBasicos().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComCusto() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 1000);
		usuario.getCustosBasicos().add(custo);
		
		controller.atualizarTabela(usuario);
		assertEquals(1, editarCustosTela.getTableCustosBasicos().getRowCount());
	}
	
	@Test
	void testarAdicionarCustoCompararCamposIgual() {
		ModelCustoBasico custo = new ModelCustoBasico("NovoCusto", 1000);
		usuario.getCustosBasicos().add(custo);
		
		controller.atualizarTabela(usuario);
		
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("1000");
		
		controller.adicionarCusto(usuario);
		/** NOTE: Terá que exibir uma mensagem de custo já existe */
		
		assertEquals(custo, editarCustosTela.getTableCustosBasicos().getValueAt(editarCustosTela.getTableCustosBasicos().getSelectedRow(), 0));
	}
	
	@Test
	void testarAdicionarCustoCompararCamposDiferente() {
		ModelCustoBasico custo = new ModelCustoBasico("NovoCusto", 1000);
		usuario.getCustosBasicos().add(custo);
		
		controller.atualizarTabela(usuario);
		
		editarCustosTela.getTextFieldNomeCusto().setText("OutroCusto");
		editarCustosTela.getTextFieldValorCusto().setText("1005");
		
		ArrayList<Object> inseridos = controller.adicionarCusto(usuario);
		/** NOTE: Terá que exibir uma mensagem de custo adicionado e limpar a tela */
		
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
		ModelCustoBasico custo = new ModelCustoBasico("NovoCusto", 1000);
		custo.setId(1L);
		usuario.getCustosBasicos().add(custo);
		
		controller.atualizarTabela(usuario);
		
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("1000");
		
		controller.adicionarCusto(usuario);
		/** NOTE: Terá que exibir uma mensagem de custo já existe e selecionar uma linha*/
		
		ModelCustoBasico custoTabela = controller.getDados(usuario);
		
		assertEquals(1000.0, custoTabela.getValor());
		assertEquals("NovoCusto", custoTabela.getNome());
	}
	
	@Test
	void testarAtualizarCustoAltera() {
		ModelCustoBasico custo = new ModelCustoBasico("NovoCusto", 1000);
		usuario.getCustosBasicos().add(custo);
		
		controller.atualizarTabela(usuario);
		
		editarCustosTela.getTextFieldNomeCusto().setText("CustoDiferente");
		editarCustosTela.getTextFieldValorCusto().setText("1000");
		
		ArrayList<Object> inseridos = controller.atualizarCustos(custo, usuario);
		/** NOTE: Terá que exibir uma mensagem de custo alterado com sucesso */
		
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
	void testarExcluirCustoExclui() {
		ModelCustoBasico custo = new ModelCustoBasico("NovoCusto", 1000);
		usuario.getCustosBasicos().add(custo);
		
		controller.atualizarTabela(usuario);
		
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("1000");
		
		int quant = usuario.getCustosBasicos().size();
		
		controller.excluirCustos(custo, usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se dejesa excluir o custo, clicar em "sim", para excluir */
		
		if(usuario.getCustosBasicos().size() == quant - 1) {
			
			verify(em).createQuery("SELECT u FROM custosbasicos as u WHERE u.usuario IS NULL");
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
		}
	}
	
	@Test
	void testarAtualizarCoresPadrao() {
		usuario.setConfiguracao(null);
		configuracaoTela.getComboBoxMudarCorPrimaria().setSelectedItem("Branco");
		configuracaoTela.getComboBoxMudarCorSecundaria().setSelectedItem("Preto");
		
		controller.atualizarCores(usuario);
		
		Color corP = editarCustosTela.getBtnAdicionar().getForeground();
		assertEquals(Color.WHITE, corP);
		
		Color corS = editarCustosTela.getBtnAdicionar().getBackground();
		assertEquals(Color.BLACK, corS);
		
	}

}
