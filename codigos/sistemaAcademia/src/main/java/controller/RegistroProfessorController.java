package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import controller.helper.RegistroProfessorHelper;
import model.ModelCores;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import view.RegistroProfessorTela;

public class RegistroProfessorController {

	private final RegistroProfessorTela registroProfessorTela;
	private static RegistroProfessorHelper helper = null;

	public RegistroProfessorController(RegistroProfessorTela registroProfessorTela) {
		this.registroProfessorTela = registroProfessorTela;
		RegistroProfessorController.helper = new RegistroProfessorHelper(registroProfessorTela);
	}
	
	public static void atualizarTabela(ModelUsuario usuario) {
	
		List<ModelRegistroProfessor> registros = usuario.getRegistroProfessores(); 
		helper.preencherTabela(registros);
	}

	public ModelRegistroProfessor getProfessorSelecionado(ModelUsuario usuario) {
		int i = registroProfessorTela.getTableRegistroProfessores().getSelectedRow();
		ModelRegistroProfessor j = (ModelRegistroProfessor) registroProfessorTela.getTableRegistroProfessores().getValueAt(i, 0);
		
		for(ModelRegistroProfessor professor: usuario.getRegistroProfessores()) {
			if(j.getId() == professor.getId()){ 
				return professor; 
			}
		}
		return null;
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
