package controller;

import java.awt.Window;

import javax.persistence.EntityManager;

import controller.helper.MudarSenhaHelper;
import model.ModelUsuario;
import model.dao.UsuarioDAO;
import servico.Criptografar;
import servico.JPAUtil;
import view.LoginTela;
import view.MudarSenhaTela;

public class MudarSenhaController {

	private final MudarSenhaTela mudarSenhaTela;
	private final MudarSenhaHelper helper;
	private EntityManager em;
	
	public MudarSenhaController(MudarSenhaTela mudarSenhaTela) {
		this.em = new JPAUtil().getEntityManager();
		this.mudarSenhaTela = mudarSenhaTela;
		this.helper = new MudarSenhaHelper(mudarSenhaTela);
	}
	
	public void atualizarTela(ModelUsuario usuario) {
		helper.preencherTela(usuario);
		
	}
	
	public ModelUsuario confirmarMudancaSenha(ModelUsuario usuario) {
		em.getTransaction().begin();
		
		ModelUsuario usuarioNovaSenha = null;
		if(helper.verificarCampos()) {
			if(helper.verificarSenhaAtual(usuario)) {
				if(helper.verificarSenhaNova()) {
					if(mudarSenhaTela.exibirMensagemDecisao("Quer confirmar sua nova senha?")) {

						usuarioNovaSenha = helper.obterModelo(usuario);
						
						usuario.setSenha(Criptografar.criptografar(usuarioNovaSenha.getSenha()));
						
						new UsuarioDAO(em).update(usuarioNovaSenha);
						helper.limparTela();
						
						mudarSenhaTela.exibirMensagemInformacao("Senha alterada com sucesso! Agora você precisará fazer\r\n"
								+ "o login novamente com a nova senha.");
						
						deslogar();

					}
				}else {
					mudarSenhaTela.exibirMensagemErro("As senhas nos campos de \"Nova senha\" e \"Digite novamente\" são diferentes!");
				}
			}else {
				mudarSenhaTela.exibirMensagemErro("A senha atual é diferente da que foi digitada.");
			}
		}else {
			mudarSenhaTela.exibirMensagemErro("Por favor, preencher todos os campos!");
		}
		
		em.getTransaction().commit();
		return usuarioNovaSenha;
	}

	public void cancelarMudancaSenha(ModelUsuario usuario) {
	
		if(helper.compararCampos()) {
			mudarSenhaTela.dispose();
		}else {
			if(mudarSenhaTela.exibirMensagemDecisao("Quer desistir de alterar sua senha?")) {
				mudarSenhaTela.dispose();
			}
		}
		
	}
	
	private void deslogar() {
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
		LoginTela loginTela = new LoginTela();
		loginTela.setResizable(false);
		loginTela.setLocationRelativeTo(null);
		loginTela.setTitle("Login");
		loginTela.setVisible(true);
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
