package controller;

import java.awt.Color;
import java.awt.Window;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import controller.helper.ConfiguracaoHelper;
import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelUsuario;
import model.dao.ConfiguracaoDAO;
import servico.JPAUtil;
import view.ConfiguracaoTela;
import view.DashboardTela;

public class ConfiguracaoController {
	
	private static ConfiguracaoTela configuracaoTela = null;
	private final ConfiguracaoHelper helper;
	private EntityManager em;

	public ConfiguracaoController(ConfiguracaoTela configuracaoTela) {
		super();
		this.em = new JPAUtil().getEntityManager();
		ConfiguracaoController.configuracaoTela = configuracaoTela;
		this.helper = new ConfiguracaoHelper(configuracaoTela);
	}
	
	public void atualizarTela(ModelUsuario usuario) {
		helper.preencherTela(usuario);
		
	}

	public void atualizarTabelas(ModelUsuario usuario) {
		helper.preencherTabelas(usuario);
		
	}

	public ModelConfiguracao confirmarAlteracao(ModelUsuario usuario) {
		
		em.getTransaction().begin();
		
		ModelConfiguracao configuracaoAtualizada = null;
		if(helper.verificarCampos()) {
			if(helper.verificarHorario(configuracaoTela.getTextFieldHoraAbrir().getText())) {
				if(helper.verificarHorario(configuracaoTela.getTextFieldHoraFechar().getText())) {

					configuracaoAtualizada = helper.obterModeloAtualizado(usuario);
						
					new ConfiguracaoDAO(em).insertOrUpdate(configuracaoAtualizada);
					
					ModelConfiguracao configuracao = usuario.getConfiguracao();
					
					configuracaoTela.setCorPrimaria(corPrimaria());
					configuracaoTela.setCorSecundaria(corSecundaria());
					
					atualizarTela(usuario);
					mudarCoresAtuais();
					
					if(configuracao != null) {
						if (configuracaoTela.exibirMensagemDecisao("Configuração realizada com sucesso! "
								+ "Deseja ir para a tela inicial atualizada?")) {
							for (Window window: Window.getWindows()) {
					        	window.dispose();
					        }
							irParaTelaInicial(usuario);	
						}
					}else {
						configuracaoTela.exibirMensagemInformacao("Configuração realizada com sucesso! Você será encaminhado para a tela inicial");
						irParaTelaInicial(usuario);
					}
				}else {
					configuracaoTela.exibirMensagemErro("Horário de fechar inválido. Tente Novamente!");
				}
			}else {
				configuracaoTela.exibirMensagemErro("Horário de abrir inválida. Tente Novamente");
			}
		}else {
			configuracaoTela.exibirMensagemErro("Por favor, informe os atributos importantes(*).");
		}
		
		em.getTransaction().commit();
		return configuracaoAtualizada;
		
	}

	public static Color corPrimaria() {
		if(configuracaoTela.getComboBoxMudarCorSecundaria() == null ) {
			return Color.WHITE;
		}
		
		if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Amarelo")) {
			return Color.YELLOW;
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Azul")) {
			return Color.BLUE;
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Branco")) {
			return Color.WHITE;
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Cinza")) {
			return new Color(225, 225, 225);
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Laranja")) {
			return new Color(255, 140, 0);
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Marrom")) {
			return new Color(140, 70, 35);
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Rosa")) {
			return new Color(255, 0, 255);
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Roxo")) {
			return new Color(170, 0, 255);
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Verde")) {
			return Color.GREEN;
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Vermelho")) {
			return Color.RED;
		}else if(configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem().equals("Preto")) {
			return Color.BLACK;
		}
		
		return Color.WHITE;
	}
	
	public static Color corSecundaria() {
		
		if(configuracaoTela.getComboBoxMudarCorSecundaria() == null ) {
			return Color.BLACK;
		}
		
		if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Amarelo")) {
			return Color.YELLOW;
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Azul")) {
			return Color.BLUE;
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Branco")) {
			return Color.WHITE;
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Cinza")) {
			return new Color(225, 225, 225);
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Laranja")) {
			return new Color(255, 140, 0);
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Marrom")) {
			return new Color(140, 70, 35);
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Rosa")) {
			return new Color(255, 0, 255);
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Roxo")) {
			return new Color(170, 0, 255);
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Verde")) {
			return Color.GREEN;
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Vermelho")) {
			return Color.RED;
		}else if(configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem().equals("Preto")) {
			return Color.BLACK;
		}
		return Color.BLACK;
	}

	public void mudarCoresAtuais() {
		
		if(configuracaoTela.getTextFieldCorAtualPrimaria().getText() != configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem()){
		
			String corAtual1 = (String) configuracaoTela.getComboBoxMudarCorPrimaria().getModel().getSelectedItem();	
			configuracaoTela.getTextFieldCorAtualPrimaria().setText(corAtual1);

		}
		if(configuracaoTela.getTextFieldCorAtualSecundaria().getText() != configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem()){
			
			String corAtual2 = (String) configuracaoTela.getComboBoxMudarCorSecundaria().getModel().getSelectedItem();	
			configuracaoTela.getTextFieldCorAtualSecundaria().setText(corAtual2);
		}
		
	}

	private void irParaTelaInicial(ModelUsuario usuario) {
		
		DashboardTela dashboardTela = new DashboardTela(usuario);
		dashboardTela.setVisible(true);
		dashboardTela.setLocationRelativeTo(null);
		dashboardTela.setResizable(false);
		dashboardTela.setTitle("DashBoard");
		configuracaoTela.dispose();
		
	}
	
	public void atualizarCores(ModelUsuario usuario) {
		
		if(usuario.getConfiguracao() != null) {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(corPrimaria());
			ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(corSecundaria());
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundarias);
		}else {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(corPrimaria());
			ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(corSecundaria());
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundarias);
		}
	}

	public void compararCores(ModelUsuario usuario) {
		if(corPrimaria().equals(corSecundaria())) {
			configuracaoTela.exibirMensagemErro("As cores primárias e secundárias não podem ser iguais");
			
			configuracaoTela.getComboBoxMudarCorPrimaria().getModel().setSelectedItem(usuario.getConfiguracao().getCorAtualPrimaria());
			configuracaoTela.getComboBoxMudarCorSecundaria().getModel().setSelectedItem(usuario.getConfiguracao().getCorAtualSecundaria());
		}
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

}
