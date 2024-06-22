package controller;

import javax.persistence.EntityManager;
import javax.swing.UIManager;

import controller.helper.LoginHelper;
import model.ModelUsuario;
import model.dao.UsuarioDAO;
import servico.JPAUtil;
import view.CadastroUsuarioTela;
import view.DashboardTela;
import view.LoginTela;

public class LoginController {

	private final LoginTela loginTela;
	private final LoginHelper helper;
	private EntityManager em;
	
	public LoginController(LoginTela loginTela) {
		em = new JPAUtil().getEntityManager();
		this.helper = new LoginHelper(loginTela);
		this.loginTela = loginTela;
	}

	public void entrarNoSistema() {
		
		ModelUsuario usuario = helper.obterModelo();
        
        boolean usuarioAutenticado = new UsuarioDAO(em).selectPorUsuarioESenha(usuario);
        ModelUsuario usuarioEncontrado = new UsuarioDAO(em).selectUsuarioPorUsuarioESenha(usuario); 
        
        if(usuarioAutenticado) {
        	DashboardTela dashboardTela = new DashboardTela(usuarioEncontrado);
			dashboardTela.setVisible(true);
			dashboardTela.setLocationRelativeTo(null);
			dashboardTela.setResizable(false);
			dashboardTela.setTitle("Dashboard");
			helper.limparTela();
			loginTela.dispose();
        }else {
        	loginTela.exibirMensagemErro("Usuario ou/e Senha inválidos");
        }
		
	}

	public void irParaCadastro() {
		UIManager.put("OptionPane.yesButtonText", "Entrar no sistema");
		UIManager.put("OptionPane.noButtonText", "Ir para a tela de login");
		
		CadastroUsuarioTela cadastroUsuarioTela = new CadastroUsuarioTela();
		cadastroUsuarioTela.setVisible(true);
		cadastroUsuarioTela.setLocationRelativeTo(null);
		cadastroUsuarioTela.setResizable(false);
		cadastroUsuarioTela.setTitle("Cadastro de Usuário");
		loginTela.dispose();
		
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
