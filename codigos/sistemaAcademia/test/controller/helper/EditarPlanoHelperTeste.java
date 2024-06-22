package controller.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.ConfiguracaoController;
import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelPlano;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarPlanosTela;

class EditarPlanoHelperTeste {

	private EditarPlanosTela editarPlanosTela;
	private ModelUsuario usuario;
	private EditarPlanoHelper helper;
	private ModelConfiguracao configuracao;
	private ConfiguracaoTela configuracaoTela;
	@SuppressWarnings("unused")
	private ConfiguracaoController controllerConf;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Domingo", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		configuracaoTela = new ConfiguracaoTela(usuario);
		controllerConf = new ConfiguracaoController(configuracaoTela);
		editarPlanosTela = new EditarPlanosTela(usuario);
		helper = new EditarPlanoHelper(editarPlanosTela);
	}
	
	@Test
	void testarPreencherTabelaVazia() {
		helper.preencherTabela(usuario.getPlanos());
		assertEquals(0, editarPlanosTela.getTablePlanos().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComDados() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		helper.preencherTabela(usuario.getPlanos());
		assertEquals(1, editarPlanosTela.getTablePlanos().getRowCount());
	}
	
	@Test
	void testarObterModelo() {
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		
		ModelPlano plano = helper.obterModelo();
		assertEquals(50.0, plano.getValor());
	}
	
	@Test
	void testarObterModeloAtualizar() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		
		ModelPlano planoAtualizado = helper.obterModeloAtualizar(plano);
		assertEquals("NovoPlano", planoAtualizado.getNome());
	}
	
	@Test
	void testarVerificarCampoVazio() {
		assertFalse(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCampoComDados() {
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		
		assertTrue(helper.verificarCampos());
	}
	
	@Test
	void testarValidarTempoInvalido() {
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1t");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		
		assertFalse(helper.validarTempo());
	}
	
	@Test
	void testarValidarTempoValido() {
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		
		assertTrue(helper.validarTempo());
	}
	
	@Test
	void testarValidarValorInvalido() {
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50re");
		
		assertFalse(helper.validarValor());
	}
	
	@Test
	void testarValidarValorValido() {
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		
		assertTrue(helper.validarValor());
	}
	
	@Test
	void testarCompararPlanosIgual() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		editarPlanosTela.getTextFieldNomePlano().setText("Plano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		ModelPlano planoTela = helper.obterModelo();
		
		assertFalse(helper.compararPlanos(planoTela, usuario));
	}
	
	@Test
	void testarCompararPlanosDiferente() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		editarPlanosTela.getTextFieldNomePlano().setText("NovoPlano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		ModelPlano planoTela = helper.obterModelo();
		
		assertTrue(helper.compararPlanos(planoTela, usuario));
	}
	
	@Test
	void testarSelecionarPlanoNomeIgual() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		helper.preencherTabela(usuario.getPlanos());
		
		editarPlanosTela.getTextFieldNomePlano().setText("Plano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("150");
		ModelPlano planoTela = helper.obterModelo();
		
		helper.selecionarPlanoIgual(planoTela);
		
		int i = editarPlanosTela.getTablePlanos().getSelectedRow();
		assertEquals(plano, editarPlanosTela.getTablePlanos().getValueAt(i, 0));
	}
	
	@Test
	void testarCompararDadosIgual() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		helper.preencherTabela(usuario.getPlanos());
		
		editarPlanosTela.getTextFieldNomePlano().setText("Plano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("50");
		
		assertFalse(helper.compararDados(plano));
	}
	
	@Test
	void testarCompararDadosDiferente() {
		ModelPlano plano = new ModelPlano("plano", 1, 50);
		usuario.getPlanos().add(plano);
		
		helper.preencherTabela(usuario.getPlanos());
		
		editarPlanosTela.getTextFieldNomePlano().setText("Plano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("150");
		
		assertTrue(helper.compararDados(plano));
	}
	
	@Test
	void testarLimparTela() {
		editarPlanosTela.getTextFieldNomePlano().setText("Plano");
		editarPlanosTela.getTextFieldTempoPlano().setText("1");
		editarPlanosTela.getTextFieldValorPlano().setText("150");
		
		helper.limparTela();
		
		assertEquals("", editarPlanosTela.getTextFieldNomePlano().getText());
	}
	
	@Test
	void testarSetarCoresPrimariaESecundariaUma() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = editarPlanosTela.getBtnAdicionar().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = editarPlanosTela.getBtnAdicionar().getBackground();
		assertEquals(Color.WHITE, corS);
	}

}
