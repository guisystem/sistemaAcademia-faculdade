package controller;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.swing.UIManager;

import controller.helper.CadastroUsuarioHelper;
import model.ModelConfiguracao;
import model.ModelUsuario;
import model.dao.ConfiguracaoDAO;
import model.dao.UsuarioDAO;
import servico.Criptografar;
import servico.JPAUtil;
import view.CadastroUsuarioTela;
import view.ConfiguracaoTela;
import view.LoginTela;

public class CadastroUsuarioController {

	private CadastroUsuarioTela cadastroUsuarioTela;
	private final CadastroUsuarioHelper helper;
	private EntityManager em;
	
	public CadastroUsuarioController(CadastroUsuarioTela cadastroUsuarioTela) {
		em = new JPAUtil().getEntityManager();
		this.cadastroUsuarioTela = cadastroUsuarioTela;
		this.helper = new CadastroUsuarioHelper(cadastroUsuarioTela);
	}

	public void voltarParaLogin() {
		UIManager.put("OptionPane.yesButtonText", "Sim"); 
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		
		LoginTela loginTela = new LoginTela();
		loginTela.setVisible(true);
		loginTela.setLocationRelativeTo(null);
		loginTela.setResizable(false);
		loginTela.setTitle("Login");
		cadastroUsuarioTela.dispose();
		
	}

	public ArrayList<Object> cadastrarNoSistema() {
		em.getTransaction().begin();
		
		ArrayList<Object> inseridos = new ArrayList<>();
		ModelUsuario usuario = helper.obterModelo();
		if(helper.verificarCampos()) {
			if(helper.verificarCpf()){
				if(helper.verificarDados(em, usuario)) {
					if(helper.verificarSenha()) {
						
						usuario.setSenha(Criptografar.criptografar(usuario.getSenha()));
						usuario.setCpf(Criptografar.criptografar(usuario.getCpf()));
						usuario.setEmail(Criptografar.criptografar(usuario.getEmail()));
						
						new UsuarioDAO(em).insert(usuario);
						
						ModelConfiguracao primeiraConfiguracao = helper.obterConfiguracao(usuario);
						
						usuario.setConfiguracao(primeiraConfiguracao);
						new ConfiguracaoDAO(em).insert(primeiraConfiguracao);
						
						inseridos.add(usuario);
						inseridos.add(primeiraConfiguracao);
						
						if (cadastroUsuarioTela.exibirMensagemDecisao("Usuário cadastrado com sucesso! "
								+ "Deseja entrar no Sistema ou ir para a tela de Login?")) {
							irParaConfiguracao(usuario);
							em.close();
						}else {

							voltarParaLogin();
						}

						helper.limparTela();
						
					}else {
						cadastroUsuarioTela.exibirMensagemErro("As senhas são diferentes");
					}
				}
			}else {
				cadastroUsuarioTela.exibirMensagemErro("CPF inválido. Tente novamente!");
			}
		}else {
			cadastroUsuarioTela.exibirMensagemErro("Por favor, preencher todos os campos");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}
	
	private void irParaConfiguracao(ModelUsuario usuario) {
		UIManager.put("OptionPane.yesButtonText", "Sim");
		UIManager.put("OptionPane.noButtonText", "Não");
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		
		ConfiguracaoTela configuracaoTela = new ConfiguracaoTela(usuario);
		configuracaoTela.setResizable(false);
		configuracaoTela.setTitle("Configuração");
		configuracaoTela.setLocationRelativeTo(null);
		configuracaoTela.setVisible(true);
		cadastroUsuarioTela.dispose();
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}


	public void setCadastroUsuarioTela(CadastroUsuarioTela cadastroUsuarioTela) {
		this.cadastroUsuarioTela = cadastroUsuarioTela;
	}
	
}
