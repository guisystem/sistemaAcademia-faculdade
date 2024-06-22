package controller.helper;

import model.ModelUsuario;
import servico.Criptografar;
import view.MudarSenhaTela;

public class MudarSenhaHelper implements Helper {

	private final MudarSenhaTela mudarSenhaTela;
	
	public MudarSenhaHelper(MudarSenhaTela mudarSenhaTela) {
		this.mudarSenhaTela = mudarSenhaTela;
	}
	
	public void preencherTela(ModelUsuario usuario) {
		mudarSenhaTela.getTextFieldNomeUsuario().setText(usuario.getNomeUsuario());
		
	}

	@SuppressWarnings("deprecation") 
	public ModelUsuario obterModelo(ModelUsuario usuario) {
		usuario.setSenha(mudarSenhaTela.getPasswordFieldNovaSenha().getText());
		
		return usuario;
	}
	
	@Override
	public ModelUsuario obterModelo() {
		return null;
	}

	@SuppressWarnings("deprecation")
	public boolean verificarCampos() {
		if(mudarSenhaTela.getTextFieldSenhaAtual().getText().trim().isEmpty() ||
				mudarSenhaTela.getPasswordFieldNovaSenha().getText().trim().isEmpty() ||
				mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().getText().trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public boolean verificarSenhaAtual(ModelUsuario usuario) {
		String senhaCriptografada = Criptografar.criptografar(mudarSenhaTela.getTextFieldSenhaAtual().getText().trim());
		
		if(senhaCriptografada.equals(usuario.getSenha())) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean verificarSenhaNova() {
		if(mudarSenhaTela.getPasswordFieldNovaSenha().getText().trim().equals(mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().getText().trim())) {
			return true;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public boolean compararCampos() {
		if(!mudarSenhaTela.getTextFieldSenhaAtual().getText().trim().isEmpty() ||
				!mudarSenhaTela.getPasswordFieldNovaSenha().getText().trim().isEmpty() ||
				!mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().getText().trim().isEmpty()) {
			return false;
		}
		return true;
	}

	public void limparTela() {
		mudarSenhaTela.getTextFieldSenhaAtual().setText("");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("");
	}
	
}
