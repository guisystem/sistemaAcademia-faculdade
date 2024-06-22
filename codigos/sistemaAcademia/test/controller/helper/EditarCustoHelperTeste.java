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
import model.ModelCustoBasico;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarCustosTela;

class EditarCustoHelperTeste {

	private EditarCustosTela editarCustosTela;
	private ModelUsuario usuario;
	private EditarCustoHelper helper;
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
		editarCustosTela = new EditarCustosTela(usuario);
		helper = new EditarCustoHelper(editarCustosTela);
		
	}
	
	@Test
	void testarPreencherTabelaVazia() {
		helper.preencherTabela(usuario.getCustosBasicos());
		assertEquals(0, editarCustosTela.getTableCustosBasicos().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComDados() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 100);
		usuario.getCustosBasicos().add(custo);
		
		helper.preencherTabela(usuario.getCustosBasicos());
		assertEquals(1, editarCustosTela.getTableCustosBasicos().getRowCount());
	}
	
	@Test
	void testarObterModelo() {
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("150");
		
		ModelCustoBasico custo = helper.obterModelo();
		assertEquals(150.0, custo.getValor());
	}
	
	@Test
	void testarObterModeloAtualizar() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 100);
		usuario.getCustosBasicos().add(custo);
		
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("150");
		
		ModelCustoBasico custoAtualizado = helper.obterModeloAtualizar(custo);
		assertEquals("NovoCusto", custoAtualizado.getNome());
	}
	
	@Test
	void testarVerificarCampoVazio() {
		assertFalse(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCampoComDados() {
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("150");
		
		assertTrue(helper.verificarCampos());
	}
	
	@Test
	void testarValidarValorInvalido() {
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("150re");
		
		assertFalse(helper.validarValor());
	}
	
	@Test
	void testarValidarValorValido() {
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("1500");
		
		assertTrue(helper.validarValor());
	}
	
	@Test
	void testarCompararCustosIgual() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 100);
		usuario.getCustosBasicos().add(custo);
		
		editarCustosTela.getTextFieldNomeCusto().setText("Custo");
		editarCustosTela.getTextFieldValorCusto().setText("100");
		ModelCustoBasico custoTela = helper.obterModelo();
		
		assertFalse(helper.compararCustos(custoTela, usuario));
	}
	
	@Test
	void testarCompararCustosDiferente() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 100);
		usuario.getCustosBasicos().add(custo);
		
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("100");
		ModelCustoBasico custoTela = helper.obterModelo();
		
		assertTrue(helper.compararCustos(custoTela, usuario));
	}
	
	@Test
	void testarSelecionarCustoNomeIgual() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 100);
		usuario.getCustosBasicos().add(custo);
		
		helper.preencherTabela(usuario.getCustosBasicos());
		
		editarCustosTela.getTextFieldNomeCusto().setText("Custo");
		editarCustosTela.getTextFieldValorCusto().setText("150");
		ModelCustoBasico custoTela = helper.obterModelo();
		
		helper.selecionarCustoIgual(custoTela);
		
		int i = editarCustosTela.getTableCustosBasicos().getSelectedRow();
		assertEquals(custo, editarCustosTela.getTableCustosBasicos().getValueAt(i, 0));
	}
	
	@Test
	void testarCompararDadosIgual() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 100);
		usuario.getCustosBasicos().add(custo);
		
		helper.preencherTabela(usuario.getCustosBasicos());
		
		editarCustosTela.getTextFieldNomeCusto().setText("Custo");
		editarCustosTela.getTextFieldValorCusto().setText("100");
		
		assertFalse(helper.compararDados(custo));
	}
	
	@Test
	void testarCompararDadosDiferente() {
		ModelCustoBasico custo = new ModelCustoBasico("Custo", 100);
		usuario.getCustosBasicos().add(custo);
		
		helper.preencherTabela(usuario.getCustosBasicos());
		
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("1500");
		
		assertTrue(helper.compararDados(custo));
	}
	
	@Test
	void testarLimparTela() {
		editarCustosTela.getTextFieldNomeCusto().setText("NovoCusto");
		editarCustosTela.getTextFieldValorCusto().setText("1500");
		
		helper.limparTela();
		
		assertEquals("", editarCustosTela.getTextFieldNomeCusto().getText());
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
		
		Color corP = editarCustosTela.getBtnAdicionar().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = editarCustosTela.getBtnAdicionar().getBackground();
		assertEquals(Color.WHITE, corS);
	}

}
