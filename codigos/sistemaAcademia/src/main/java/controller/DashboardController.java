package controller;

import java.awt.Color;
import java.util.ArrayList;

import controller.helper.DashboardHelper;
import model.ModelCores;
import model.ModelProfessor;
import model.ModelUsuario;
import view.DashboardTela;

public class DashboardController {

	private final DashboardTela dashboardTela;
	private final DashboardHelper helper;
	
	public DashboardController(DashboardTela dashboardTela) {
		this.dashboardTela = dashboardTela;
		this.helper = new DashboardHelper(dashboardTela);
	}

	public void preencherTela(ModelUsuario usuario) {
		if(usuario.getConfiguracao() == null || usuario.getConfiguracao().getNomeDaAcademia().equalsIgnoreCase("")) {
			dashboardTela.getLblNomeAcademia().setText("Nome da sua academia");
		}else {
			helper.atualizarTela(usuario);
		}
	}

	public ArrayList<ModelProfessor> getProfessores(ModelUsuario usuario) {
		return helper.getProfessoresExpediante(usuario);
	}

	public void atualizarCores(ModelUsuario usuario) {
		if(usuario.getConfiguracao() != null) {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundarias);
		}else {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(Color.WHITE);
			ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(Color.BLACK);
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundarias);
		}

		
	}
	
}