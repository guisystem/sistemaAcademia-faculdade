package controller.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelUsuario;
import view.LoginTela;

class LoginHelperTeste {

	private LoginTela loginTela;
	private LoginHelper helper;
	
	@BeforeEach
	void iniciarTeste() {
		loginTela = new LoginTela();
		helper = new LoginHelper(loginTela);
	}

	@Test
	void testarObterModelo() {
		loginTela.getTextUsuario().setText("Usuario");
		loginTela.getTextSenha().setText("Senha");
		
		ModelUsuario usuarioTela = helper.obterModelo();
		
		assertEquals("Usuario", usuarioTela.getNomeUsuario());;
		assertEquals("Senha", usuarioTela.getSenha());
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void testarLimparTela() {
		loginTela.getTextUsuario().setText("UsuarioNaoExiste");
		loginTela.getTextSenha().setText("SenhaNaoExiste");

		helper.limparTela();
		
		assertEquals("", loginTela.getTextSenha().getText());
		assertEquals("", loginTela.getTextUsuario().getText());;
	}

}
