package controller.helper;

import model.ModelUsuario;
import view.LoginTela;

public class LoginHelper implements Helper {

	private final LoginTela loginTela;
	
	public LoginHelper(LoginTela loginTela) {
		this.loginTela = loginTela;
	}

	@SuppressWarnings("deprecation")
	public ModelUsuario obterModelo() {
		String usuario = loginTela.getTextUsuario().getText();
		String senha = loginTela.getTextSenha().getText();
		
		ModelUsuario verificarUsuario = new ModelUsuario(usuario, senha);
		
		return verificarUsuario;
	}

	@Override
	public void limparTela() {
		loginTela.getTextUsuario().setText("");
		loginTela.getTextSenha().setText("");
	}
}
