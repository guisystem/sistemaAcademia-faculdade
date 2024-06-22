package controller.helper;

import java.util.List;

import javax.persistence.EntityManager;

import model.ModelCPF;
import model.ModelConfiguracao;
import model.ModelUsuario;
import model.dao.UsuarioDAO;
import servico.Criptografar;
import view.CadastroUsuarioTela;

public class CadastroUsuarioHelper {

	private CadastroUsuarioTela cadastroUsuarioTela;

	public CadastroUsuarioHelper(CadastroUsuarioTela cadastroUsuarioTela) {
		this.cadastroUsuarioTela = cadastroUsuarioTela; 
	}
	
	@SuppressWarnings("deprecation")
	public ModelUsuario obterModelo() {
		String nome = cadastroUsuarioTela.getTextNomeCompletoUsuario().getText();
		String cpf = cadastroUsuarioTela.getTextCpfUsuario().getText();
		String email = cadastroUsuarioTela.getTextEmailUsuario().getText();
		String usuario = cadastroUsuarioTela.getTextUsuario().getText();
		String senha = cadastroUsuarioTela.getPasswordSenhaUsuario().getText();
		ModelConfiguracao configuracao = null;
		
		ModelUsuario novoUsuario = new ModelUsuario(nome, cpf, email, usuario, senha, configuracao);
		return novoUsuario;
		
	}

	public boolean verificarDados(EntityManager em, ModelUsuario usuarioTela) {
		
		List<ModelUsuario> usuarios = new UsuarioDAO(em).selectAll();
		for(ModelUsuario usuario: usuarios) {
			if(usuario.getNomeUsuario() != null) {
				if(usuario.getNomeUsuario().equals(usuarioTela.getNomeUsuario())) {
					cadastroUsuarioTela.exibirMensagemErro("Nome de usuário já cadastrado");
					return false;
				}
			}
		}
		
		usuarioTela.setCpf(Criptografar.criptografar(usuarioTela.getCpf()));
    	for(ModelUsuario usuario: usuarios) {
    		if(usuario.getCpf() != null) {
	    		if(usuario.getCpf().equals(usuarioTela.getCpf())) {
	    			cadastroUsuarioTela.exibirMensagemErro("CPF de usuário já cadastrado");
	    			return false;
	    		}
    		}
    	}
    	
    	usuarioTela.setEmail(Criptografar.criptografar(usuarioTela.getEmail()));
    	for(ModelUsuario usuario: usuarios) {
    		if(usuario.getEmail() != null) {
	    		if(usuario.getEmail().equals(usuarioTela.getEmail())) {
	    			cadastroUsuarioTela.exibirMensagemErro("Email de usuário já cadastrado");
	    			return false;
	    		}

    		}
    	}

    	usuarioTela.setCpf(Criptografar.descriptografar(usuarioTela.getCpf()));
    	usuarioTela.setEmail(Criptografar.descriptografar(usuarioTela.getEmail()));
    	return true;
    	
	}

	@SuppressWarnings("deprecation")
	public boolean verificarCampos() {
		if(cadastroUsuarioTela.getTextNomeCompletoUsuario().getText().trim().isEmpty() || cadastroUsuarioTela.getTextUsuario().getText().trim().isEmpty() || 
				cadastroUsuarioTela.getTextCpfUsuario().getText().trim().isEmpty() || cadastroUsuarioTela.getTextEmailUsuario().getText().trim().isEmpty() || 
				cadastroUsuarioTela.getPasswordSenhaUsuario().getText().trim().isEmpty() || cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().getText().trim().isEmpty()) {
			return false;			
		}
		return true;
	}

	public boolean verificarCpf() {
		String cpfTexto = cadastroUsuarioTela.getTextCpfUsuario().getText();
		
		ModelCPF cpfVerificar = new ModelCPF(cpfTexto);
		if(cpfVerificar.isCPF()) {
			return true;
		}else {
			return false;
		}
	}

	@SuppressWarnings("deprecation")
	public boolean verificarSenha() {
		if(cadastroUsuarioTela.getPasswordSenhaUsuario().getText().equals(cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().getText())) {
			return true;
		}
		return false;
	}

	public void limparTela() {
		cadastroUsuarioTela.getTextNomeCompletoUsuario().setText("");
		cadastroUsuarioTela.getTextCpfUsuario().setText("");
		cadastroUsuarioTela.getTextEmailUsuario().setText("");
		cadastroUsuarioTela.getTextUsuario().setText("");
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("");
		cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().setText("");
		
	}

	public ModelConfiguracao obterConfiguracao(ModelUsuario usuario) {
		ModelConfiguracao configuracao = new ModelConfiguracao("", "Segunda", "Segunda", "00:00", "00:00", "Branco", "Preto", usuario);
		return configuracao;
	}

}
