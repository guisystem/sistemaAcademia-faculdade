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
import model.ModelFuncionario;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarFuncionariosTela;

class EditarFuncionarioHelperTeste {

	private EditarFuncionariosTela editarFuncionariosTela;
	private ModelUsuario usuario;
	private EditarFuncionarioHelper helper;
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
		editarFuncionariosTela = new EditarFuncionariosTela(usuario);
		helper = new EditarFuncionarioHelper(editarFuncionariosTela);
	}
	
	@Test
	void testarPreencherTabelaVazia() {
		helper.preencherTabela(usuario.getFuncionarios());
		assertEquals(0, editarFuncionariosTela.getTableFuncionarios().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComDados() {
		ModelFuncionario funcionario = new ModelFuncionario("Funcionário", "Limpeza", 1500);
		usuario.getFuncionarios().add(funcionario);
		
		helper.preencherTabela(usuario.getFuncionarios());
		assertEquals(1, editarFuncionariosTela.getTableFuncionarios().getRowCount());
	}
	
	@Test
	void testarObterModelo() {
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("NovoCargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("150");
		
		ModelFuncionario funcionario = helper.obterModelo();
		assertEquals(150.0, funcionario.getSalario());
	}
	
	@Test
	void testarObterModeloAtualizar() {
		ModelFuncionario funcionario = new ModelFuncionario("Funcionário", "Limpeza", 1500);
		usuario.getFuncionarios().add(funcionario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("NovoCargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("150");
		
		ModelFuncionario funcionarioAtualizado = helper.obterModeloAtualizar(funcionario);
		assertEquals("NovoFuncionario", funcionarioAtualizado.getNome());
	}
	
	@Test
	void testarVerificarCampoVazio() {
		assertFalse(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCampoComDados() {
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("NovoCargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("150");
		
		assertTrue(helper.verificarCampos());
	}
	
	@Test
	void testarValidarSalarioInvalido() {
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("NovoCargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("150re");
		
		assertFalse(helper.validarSalario());
	}
	
	@Test
	void testarValidarSalarioValido() {
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionario");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("NovoCargo");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1500");
		
		assertTrue(helper.validarSalario());
	}
	
	@Test
	void testarCompararFuncionariosIgual() {
		ModelFuncionario funcionario = new ModelFuncionario("Funcionário", "Limpeza", 1500);
		usuario.getFuncionarios().add(funcionario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("Funcionário");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Limpeza");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1500");
		ModelFuncionario funcionarioTela = helper.obterModelo();
		
		assertFalse(helper.compararFuncionarios(funcionarioTela, usuario));
	}
	
	@Test
	void testarCompararFuncionariosDiferente() {
		ModelFuncionario funcionario = new ModelFuncionario("Funcionário", "Limpeza", 1500);
		usuario.getFuncionarios().add(funcionario);
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("NovoFuncionário");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Limpeza");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1500");
		ModelFuncionario funcionarioTela = helper.obterModelo();
		
		assertTrue(helper.compararFuncionarios(funcionarioTela, usuario));
	}
	
	@Test
	void testarSelecionarFuncionarioNomeIgual() {
		ModelFuncionario funcionario = new ModelFuncionario("Funcionário", "Limpeza", 1500);
		usuario.getFuncionarios().add(funcionario);
		
		helper.preencherTabela(usuario.getFuncionarios());
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("Funcionário");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Limpeza");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1500");
		ModelFuncionario funcionarioTela = helper.obterModelo();
		
		helper.selecionarFuncionarioIgual(funcionarioTela);
		
		int i = editarFuncionariosTela.getTableFuncionarios().getSelectedRow();
		assertEquals(funcionario, editarFuncionariosTela.getTableFuncionarios().getValueAt(i, 0));
	}
	
	@Test
	void testarCompararDadosIgual() {
		ModelFuncionario funcionario = new ModelFuncionario("Funcionário", "Limpeza", 1500);
		usuario.getFuncionarios().add(funcionario);
		
		helper.preencherTabela(usuario.getFuncionarios());
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("Funcionário");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Limpeza");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1500");
		
		assertFalse(helper.compararDados(funcionario));
	}
	
	@Test
	void testarCompararDadosDiferente() {
		ModelFuncionario funcionario = new ModelFuncionario("Funcionário", "Limpeza", 1500);
		usuario.getFuncionarios().add(funcionario);
		
		helper.preencherTabela(usuario.getFuncionarios());
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("Funcionário");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Limpeza");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1800");
		
		assertTrue(helper.compararDados(funcionario));
	}
	
	@Test
	void testarLimparTela() {
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("Funcionário");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("Limpeza");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("1800");
		
		helper.limparTela();
		
		assertEquals("", editarFuncionariosTela.getTextFieldNomeFuncionario().getText());
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
		
		Color corP = editarFuncionariosTela.getBtnAdicionar().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = editarFuncionariosTela.getBtnAdicionar().getBackground();
		assertEquals(Color.WHITE, corS);
	}

}
