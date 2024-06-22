package controller.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelCustoBasico;
import model.ModelFuncionario;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelUsuario;
import view.ConfiguracaoTela;

class ConfiguracaoHelperTeste {

	private ConfiguracaoTela configuracaoTela;
	private ConfiguracaoHelper helper;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		configuracaoTela = new ConfiguracaoTela(usuario);
		helper = new ConfiguracaoHelper(configuracaoTela);
	}
	
	@Test
	void testarPreencherTelaConfiguracaoNull() {
		helper.preencherTela(usuario);
		
		assertEquals("Branco", configuracaoTela.getComboBoxMudarCorPrimaria().getSelectedItem().toString());
		assertEquals("Preto", configuracaoTela.getComboBoxMudarCorSecundaria().getSelectedItem().toString());
	}
	
	@Test
	void testarPreencherTelaConfiguracaoNaoNull() {
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		
		helper.preencherTela(usuario);
		
		assertEquals("Amarelo", configuracaoTela.getComboBoxMudarCorPrimaria().getSelectedItem().toString());
		assertEquals("Azul", configuracaoTela.getComboBoxMudarCorSecundaria().getSelectedItem().toString());
	}
	
	@Test
	void testarPreencherTabelasSemDados() {
		helper.preencherTabelas(usuario);
		assertEquals(0, configuracaoTela.getTableCustosBasicos().getRowCount());
		assertEquals(0, configuracaoTela.getTableFuncionarios().getRowCount());
		assertEquals(0, configuracaoTela.getTableModalidades().getRowCount());
		assertEquals(0, configuracaoTela.getTablePlanos().getRowCount());
	}
	
	
	@Test
	void testarPreencherTabelasComDados() {
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

		helper.preencherTabelas(usuario);
		
		assertEquals(1, configuracaoTela.getTableCustosBasicos().getRowCount());
		assertEquals(1, configuracaoTela.getTableFuncionarios().getRowCount());
		assertEquals(1, configuracaoTela.getTableModalidades().getRowCount());
		assertEquals(2, configuracaoTela.getTablePlanos().getRowCount());
	}
	
	@Test
	void testarObterModelo() {
		configuracaoTela.getTextFieldHoraAbrir().setText("00:00");
		configuracaoTela.getTextFieldHoraFechar().setText("10:00");
		configuracaoTela.getTextFieldNomeAcademia().setText("Nome");
		configuracaoTela.getComboBoxMudarCorPrimaria().setSelectedItem("Amarelo");
		configuracaoTela.getComboBoxMudarCorSecundaria().setSelectedItem("Preto");
		
		ModelConfiguracao conf = helper.obterModelo(usuario);
		
		assertEquals("00:00", conf.getHoraDeAbrirFormatada());
		assertEquals("10:00", conf.getHoraDeFecharFormatada());
		assertEquals("Nome", conf.getNomeDaAcademia());
	}
	
	@Test
	void testarObterModeloAtualizado() {
		configuracaoTela.getTextFieldHoraAbrir().setText("00:00");
		configuracaoTela.getTextFieldHoraFechar().setText("10:00");
		configuracaoTela.getTextFieldNomeAcademia().setText("Nome");
		configuracaoTela.getComboBoxMudarCorPrimaria().setSelectedItem("Amarelo");
		configuracaoTela.getComboBoxMudarCorSecundaria().setSelectedItem("Preto");
		
		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "00:00", "00:00", "Amarelo", "Azul", usuario);
		usuario.setConfiguracao(configuracao);
		helper.obterModeloAtualizado(usuario);
		
		assertEquals("00:00", usuario.getConfiguracao().getHoraDeAbrirFormatada());
		assertEquals("10:00", usuario.getConfiguracao().getHoraDeFecharFormatada());
		assertEquals("Nome", usuario.getConfiguracao().getNomeDaAcademia());
	}
	
	@Test
	void testarVerificarCampoVazio() {
		assertFalse(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCampoNaoVazio() {
		configuracaoTela.getTextFieldHoraAbrir().setText("00:00");
		configuracaoTela.getTextFieldHoraFechar().setText("10:00");
		configuracaoTela.getTextFieldNomeAcademia().setText("Nome");
		assertTrue(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarHorahioVazio(){
		assertFalse(helper.verificarHorario(configuracaoTela.getTextFieldHoraAbrir().getText()));
	}
	
	@Test
	void testarVerificarHorarioInvalido(){
		configuracaoTela.getTextFieldHoraAbrir().setText("94:90");
		assertFalse(helper.verificarHorario(configuracaoTela.getTextFieldHoraAbrir().getText()));
	}
	
	@Test
	void testarVerificarHorarioValido(){
		configuracaoTela.getTextFieldHoraAbrir().setText("00:00");
		assertTrue(helper.verificarHorario(configuracaoTela.getTextFieldHoraAbrir().getText()));
	}
	
	@Test
	void testarAtualizarCoresCorSecundariaBranca(){
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = configuracaoTela.getBtnConfirmarAlteracoes().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = configuracaoTela.getBtnConfirmarAlteracoes().getBackground();
		assertEquals(Color.WHITE, corS);
	}
	
	@Test
	void testarAtualizarCoresMaisDeUm() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Rosa");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = configuracaoTela.getBtnConfirmarAlteracoes().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = configuracaoTela.getBtnConfirmarAlteracoes().getBackground();
		assertEquals(new Color(255, 0, 255), corS);
		
	}

}
