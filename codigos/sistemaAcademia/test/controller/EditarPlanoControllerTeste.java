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
import model.ModelPlano;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarPlanosTela;

class EditarPlanoControllerTeste {

	private EditarPlanosTela editarPlanosTela;
	private EditarPlanoController controller;
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
		editarPlanosTela = new EditarPlanosTela(usuario);
		controller = new EditarPlanoController(editarPlanosTela);
		controller.setEm(em);
	}
	
	@Test
	void testarAtualizarLabelTempoVaziaUmOuZero() {
		editarPlanosTela.getTextFieldTempoPlano().setText("");
		controller.atualizarLabelTempo();
		
		assertEquals("mês", editarPlanosTela.getLblMesesTempoPlano().getText());
		
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		controller.atualizarLabelTempo();
		
		assertEquals("mês", editarPlanosTela.getLblMesesTempoPlano().getText());
		
		editarPlanosTela.getTextFieldTempoPlano().setText("0");
		controller.atualizarLabelTempo();
		
		assertEquals("mês", editarPlanosTela.getLblMesesTempoPlano().getText());
		
	}
	
	@Test
	void testarAtualizarLabelTempoMaisQueUm() {
		editarPlanosTela.getTextFieldTempoPlano().setText("2");
		controller.atualizarLabelTempo();
		
		assertEquals("meses", editarPlanosTela.getLblMesesTempoPlano().getText());
		
		editarPlanosTela.getTextFieldTempoPlano().setText("3");
		controller.atualizarLabelTempo();
		
		assertEquals("meses", editarPlanosTela.getLblMesesTempoPlano().getText());
		
	}
	
	@Test
	void testarAtualizarLabelTempoInvalido() {
		editarPlanosTela.getTextFieldTempoPlano().setText("tempo");
		controller.atualizarLabelTempo();
		
		assertEquals("mês", editarPlanosTela.getLblMesesTempoPlano().getText());
		
		editarPlanosTela.getTextFieldTempoPlano().setText("3e");
		controller.atualizarLabelTempo();
		
		assertEquals("mês", editarPlanosTela.getLblMesesTempoPlano().getText());
		
	}
	
	@Test
	void testarPreencherTabelaZerada() {
		controller.atualizarTabela(usuario);
		assertEquals(0, editarPlanosTela.getTablePlanos().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComPlano() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		controller.atualizarTabela(usuario);
		assertEquals(1, editarPlanosTela.getTablePlanos().getRowCount());
	}
	
	@Test
	void testarAdicionarFuncionarioCompararPlanoIgual() {
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 40);
		usuario.getPlanos().add(plano);
		
		controller.atualizarTabela(usuario);
		
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("40");
		
		controller.adicionarPlano(usuario);
		/** NOTE: Terá que exibir uma mensagem de plano já existe */
		
		assertEquals(plano, editarPlanosTela.getTablePlanos().getValueAt(editarPlanosTela.getTablePlanos().getSelectedRow(), 0));
	}
	
	@Test
	void testarAdicionarFuncionarioCompararPlanoDiferente() {
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 40);
		usuario.getPlanos().add(plano);
		
		controller.atualizarTabela(usuario);
		
		editarPlanosTela.getTextFieldNomePlano().setText("OutroPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("40");
		
		ArrayList<Object> inseridos = controller.adicionarPlano(usuario);
		/** NOTE: Terá que exibir uma mensagem de plano adicionado e limpar a tela */
		
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
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 40);
		plano.setId(1L);
		usuario.getPlanos().add(plano);
		
		controller.atualizarTabela(usuario);
		
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("40");
		
		controller.adicionarPlano(usuario);
		/** NOTE: Terá que exibir uma mensagem de plano já existe e selecionar uma linha*/
		
		ModelPlano planoTabela = controller.getDados(usuario);
		
		assertEquals(40.0, planoTabela.getValor());
		assertEquals("NovoPlano", planoTabela.getNome());
	}
	
	@Test
	void testarStualizarPlanoSemAlunoAtivo() {
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 40);
		usuario.getPlanos().add(plano);
		
		controller.atualizarTabela(usuario);
		
		editarPlanosTela.getTextFieldNomePlano().setText("OutroPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("40");
		
		ArrayList<Object> inseridos = controller.atualizarPlano(plano, usuario);
		/** NOTE: Terá que exibir uma mensagem de plano adicionado e limpar a tela */
		
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
		
		assertEquals("OutroPlano", editarPlanosTela.getTablePlanos().getValueAt(0, 0).toString());
	}
	
	@Test
	void testarExcluirPlanoSemAlunoAtivo() {
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 40);
		usuario.getPlanos().add(plano);
		
		controller.atualizarTabela(usuario);
		
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("40");
		
		int quant = usuario.getPlanos().size();
		
		controller.excluirPlano(plano, usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se dejesa excluir o plano, clicar em "sim", para excluir */
		
		if(usuario.getPlanos().size() == quant - 1) {
			
			verify(em).createQuery("SELECT u FROM plano as u WHERE u.usuario IS NULL");
			
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
		
		Color corP = editarPlanosTela.getBtnAdicionar().getForeground();
		assertEquals(Color.WHITE, corP);
		
		Color corS = editarPlanosTela.getBtnAdicionar().getBackground();
		assertEquals(Color.BLACK, corS);
		
	}

}
