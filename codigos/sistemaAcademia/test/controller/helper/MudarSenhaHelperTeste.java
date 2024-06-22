package controller.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelUsuario;
import servico.Criptografar;
import view.MudarSenhaTela;

class MudarSenhaHelperTeste {

	private MudarSenhaTela mudarSenhaTela;
	private MudarSenhaHelper helper;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario("Nome", "Senha");
		
		mudarSenhaTela = new MudarSenhaTela(usuario);
		helper = new MudarSenhaHelper(mudarSenhaTela);
	}
	
	@Test
	void testarPreencherTela() {
		helper.preencherTela(usuario);
		assertEquals("Nome", mudarSenhaTela.getTextFieldNomeUsuario().getText());
	}
	
	@Test
	void testarObterModeloComParametro() {
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("NovaSenha");
		ModelUsuario usuarioSenhaNova = helper.obterModelo(usuario);
		assertEquals("NovaSenha", usuarioSenhaNova.getSenha());
	}
	
	@Test
	void testarObterModeloSemParametro() {
		ModelUsuario usuario = helper.obterModelo();
		assertEquals(null, usuario);
	}
	
	@Test
	void testarVerificarCamposVazio() {
		assertFalse(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCamposComDados() {
		mudarSenhaTela.getTextFieldSenhaAtual().setText("Senha");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("SenhaNova");
		assertTrue(helper.verificarCampos());
	}

	@Test
	void testarVerificarSenhaAtualErrada() {
		helper.preencherTela(usuario);
		usuario.setSenha(Criptografar.criptografar(usuario.getSenha()));
		
		mudarSenhaTela.getTextFieldSenhaAtual().setText("OutraSenha");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("SenhaNova");
		
		assertFalse(helper.verificarSenhaAtual(usuario));
	}
	
	@Test
	void testarVerificarSenhaAtualCerta() {
		helper.preencherTela(usuario);
		usuario.setSenha(Criptografar.criptografar(usuario.getSenha()));
		
		mudarSenhaTela.getTextFieldSenhaAtual().setText("Senha");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("SenhaNova");
		
		assertTrue(helper.verificarSenhaAtual(usuario));
	}
	
	@Test
	void testarVerificarSenhaNovaDiferentes() {
		mudarSenhaTela.getTextFieldSenhaAtual().setText("Senha");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("SenhaNova2");
		
		assertFalse(helper.verificarSenhaNova());
	}
	
	@Test
	void testarVerificarSenhaNovaIguais() {
		mudarSenhaTela.getTextFieldSenhaAtual().setText("Senha");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("SenhaNova");
		
		assertTrue(helper.verificarSenhaNova());
	}
	
	@Test
	void testarCompararCamposVazio() {
		assertTrue(helper.compararCampos());
	}
	
	@Test
	void testarCompararCamposComDados() {
		mudarSenhaTela.getTextFieldSenhaAtual().setText("Senha");
		assertFalse(helper.compararCampos());
	}
	
	@Test
	void testarLimparTela() {
		mudarSenhaTela.getTextFieldSenhaAtual().setText("Senha");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("SenhaNova");
		
		helper.limparTela();
		assertEquals("", mudarSenhaTela.getTextFieldSenhaAtual().getText());
	}
	

}
