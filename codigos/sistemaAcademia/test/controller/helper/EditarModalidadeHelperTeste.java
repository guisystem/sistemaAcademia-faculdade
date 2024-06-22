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
import model.ModelModalidade;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarModalidadeTela;

class EditarModalidadeHelperTeste {

	private EditarModalidadeTela editarModalidadeTela;
	private ModelUsuario usuario;
	private EditarModalidadeHelper helper;
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
		editarModalidadeTela = new EditarModalidadeTela(usuario);
		helper = new EditarModalidadeHelper(editarModalidadeTela);
		
	}

	@Test
	void testarPreencherTabelaVazia() {
		helper.preencherTabela(usuario.getModalidades());
		assertEquals(0, editarModalidadeTela.getTableModalidades().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComDados() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		helper.preencherTabela(usuario.getModalidades());
		assertEquals(1, editarModalidadeTela.getTableModalidades().getRowCount());
	}
	
	@Test
	void testarObterModelo() {
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		ModelModalidade modalidade = helper.obterModelo();
		assertEquals(0.0, modalidade.getTaxaExtra());
	}
	
	@Test
	void testarObterModeloAtualizar() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		ModelModalidade modalidadeAtualizada = helper.obterModeloAtualizar(modalidade);
		assertEquals("NovaModalidade", modalidadeAtualizada.getNome());
	}
	
	@Test
	void testarVerificarCampoVazio() {
		assertFalse(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCampoComDados() {
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		assertTrue(helper.verificarCampos());
	}
	
	@Test
	void testarValidarTaxaInvalida() {
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0re");
		
		assertFalse(helper.validarTaxa());
	}
	
	@Test
	void testarValidarTaxaValida() {
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		assertTrue(helper.validarTaxa());
	}
	
	@Test
	void testarCompararModalidadesIgual() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("Modalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		ModelModalidade modalidadeTela = helper.obterModelo();
		
		assertFalse(helper.compararModalidades(modalidadeTela, usuario));
	}
	
	@Test
	void testarCompararModalidadesDiferente() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		ModelModalidade modalidadeTela = helper.obterModelo();
		
		assertTrue(helper.compararModalidades(modalidadeTela, usuario));
	}
	
	@Test
	void testarSelecionarModalidadeNomeIgual() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		helper.preencherTabela(usuario.getModalidades());
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("Modalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("10");
		ModelModalidade modalidadeTela = helper.obterModelo();
		
		helper.selecionarModalidadeIgual(modalidadeTela);
		
		int i = editarModalidadeTela.getTableModalidades().getSelectedRow();
		assertEquals("Modalidade", editarModalidadeTela.getTableModalidades().getValueAt(i, 0));
	}
	
	@Test
	void testarCompararDadosIgual() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		helper.preencherTabela(usuario.getModalidades());
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("Modalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		assertFalse(helper.compararDados(modalidade));
	}
	
	@Test
	void testarCompararDadosDiferente() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 0);
		usuario.getModalidades().add(modalidade);
		
		helper.preencherTabela(usuario.getModalidades());
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		assertTrue(helper.compararDados(modalidade));
	}
	
	@Test
	void testarLimparTela() {
		editarModalidadeTela.getTextFieldNomeModalidade().setText("NovaModalidade");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("0");
		
		helper.limparTela();
		
		assertEquals("", editarModalidadeTela.getTextFieldNomeModalidade().getText());
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
		
		Color corP = editarModalidadeTela.getBtnAdicionar().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = editarModalidadeTela.getBtnAdicionar().getBackground();
		assertEquals(Color.WHITE, corS);
	}
}
