package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.awt.Window;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelCustoBasico;
import model.ModelFuncionario;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelUsuario;
import view.ConfiguracaoTela;

class ConfiguracaoControllerTeste {

	private ConfiguracaoTela configuracaoTela;
	private ConfiguracaoController controller;
	private ModelUsuario usuario;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		configuracaoTela = new ConfiguracaoTela(usuario);
		controller = new ConfiguracaoController(configuracaoTela);
		controller.setEm(em);
	}
	
	@Test
	void testarAtualizarTelaDataCorreta() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Preto");
		configuracao.setHoraDeAbrirFormatado("00:00");
		configuracao.setHoraDeFecharFormatado("10:00");
		usuario.setConfiguracao(configuracao);
		configuracao.setUsuario(usuario);
		
		controller.atualizarTela(usuario);

		assertEquals("Amarelo", configuracaoTela.getTextFieldCorAtualPrimaria().getText());
		assertEquals("10:00", configuracaoTela.getTextFieldHoraFechar().getText());
		
	}
	
	@Test
	void testarAtualizarTabelasComDados() {
		ModelCustoBasico custo1 = new ModelCustoBasico("Custo", 1000);
		ModelPlano plano1 = new ModelPlano("Plano1", 1, 50);
		ModelPlano plano2 = new ModelPlano("Plano2", 2, 80);
		ModelFuncionario funcionario1 = new ModelFuncionario();
		ModelModalidade modalidade1 = new ModelModalidade("Modalidade", 0);
		
		usuario.getModalidades().add(modalidade1);
		usuario.getFuncionarios().add(funcionario1);
		usuario.getCustosBasicos().add(custo1);
		usuario.getPlanos().add(plano2);
		usuario.getPlanos().add(plano1);

		controller.atualizarTabelas(usuario);
		
		assertEquals(1, configuracaoTela.getTableCustosBasicos().getRowCount());
		assertEquals(1, configuracaoTela.getTableFuncionarios().getRowCount());
		assertEquals(1, configuracaoTela.getTableModalidades().getRowCount());
		assertEquals(2, configuracaoTela.getTablePlanos().getRowCount());
	}
	
	@Test
	void testarConfirmarAlteracaoObterModeloSemConfiguracao() {
		configuracaoTela.getTextFieldHoraAbrir().setText("00:00");
		configuracaoTela.getTextFieldHoraFechar().setText("10:00");
		configuracaoTela.getTextFieldNomeAcademia().setText("Nome");
		configuracaoTela.getComboBoxMudarCorPrimaria().setSelectedItem("Amarelo");
		configuracaoTela.getComboBoxMudarCorSecundaria().setSelectedItem("Azul");
		
		ModelConfiguracao configuracao = new ModelConfiguracao();
		usuario.setConfiguracao(configuracao);
		configuracao.setUsuario(usuario);
		
		ModelConfiguracao configuracaoAtualizada = controller.confirmarAlteracao(usuario);
		/** Terá que exibir uma mensagem perguntando se deseja ir para tela inicial  */
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
		if(configuracaoAtualizada != null) {
			verify(em, times(1)).persist(configuracaoAtualizada);
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
			
		}else {
			verify(em, times(0)).persist(any());
			verify(transaction, times(1)).begin();
		    verify(transaction, times(1)).commit();
		}

	}
	
	@Test
	void testarCoresPrimarias() {
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("");
		assertEquals(Color.WHITE, ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Amarelo");
		assertEquals(Color.YELLOW, ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Azul");
		assertEquals(Color.BLUE, ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Branco");
		assertEquals(Color.WHITE, ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Cinza");
		assertEquals(new Color(225, 225, 225), ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Laranja");
		assertEquals(new Color(255, 140, 0), ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Marrom");
		assertEquals(new Color(140, 70, 35), ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Rosa");
		assertEquals(new Color(255, 0, 255), ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Roxo");
		assertEquals(new Color(170, 0, 255), ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Verde");
		assertEquals(Color.GREEN, ConfiguracaoController.corPrimaria());
		
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Vermelho");
		assertEquals(Color.RED, ConfiguracaoController.corPrimaria());
		
	}
	
	@Test
	void testarCoresSecundarias() {
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("");
		assertEquals(Color.BLACK, ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Amarelo");
		assertEquals(Color.YELLOW, ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Azul");
		assertEquals(Color.BLUE, ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Cinza");
		assertEquals(new Color(225, 225, 225), ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Laranja");
		assertEquals(new Color(255, 140, 0), ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Marrom");
		assertEquals(new Color(140, 70, 35), ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Rosa");
		assertEquals(new Color(255, 0, 255), ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Roxo");
		assertEquals(new Color(170, 0, 255), ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Verde");
		assertEquals(Color.GREEN, ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Vermelho");
		assertEquals(Color.RED, ConfiguracaoController.corSecundaria());
		
		configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Preto");
		assertEquals(Color.BLACK, ConfiguracaoController.corSecundaria());
		
	}
	
	@Test
	void testarCompararCoresIguaisPretoExcecaoConfiguracaoNull() {
		controller.atualizarTela(usuario);
		
		assertThrows(NullPointerException.class, () -> {
			configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Preto");
			controller.compararCores(usuario);
		});
	}
	
	@Test
	void testarCompararCoresIguaisBrancoExcecaoConfiguracaoNull() {
		controller.atualizarTela(usuario);
		
		assertThrows(NullPointerException.class, () -> {
			configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem("Branco");
			controller.compararCores(usuario);
		});
	}
	
	@Test
	void testarCompararCoresIguais() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Rosa");
		configuracao.setCorAtualSecundaria("Preto");
		configuracao.setHoraDeAbrirFormatado("00:00");
		configuracao.setHoraDeFecharFormatado("10:00");
		usuario.setConfiguracao(configuracao);
		configuracao.setUsuario(usuario);
		
		controller.atualizarTela(usuario);
		configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem("Preto");
	
		assertEquals("Rosa", configuracaoTela.getComboBoxMudarCorPrimaria().getSelectedItem().toString());
	}
	
}
