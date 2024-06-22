package controller.helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.ModelRegistroProfessor;
import view.RegistroProfessorTela;

public class RegistroProfessorHelper{

	private final RegistroProfessorTela registroProfessorTela;

	public RegistroProfessorHelper(RegistroProfessorTela registroProfessorTela) {
		this.registroProfessorTela = registroProfessorTela;
	}
	
	public void preencherTabela(List<ModelRegistroProfessor> registros) {
		
		DefaultTableModel tableModel = (DefaultTableModel) registroProfessorTela.getTableRegistroProfessores().getModel();
		tableModel.setNumRows(0);
		
		for(ModelRegistroProfessor registro: registros) {
			
			tableModel.addRow(new Object[] {
				registro,
				registro.getProfessor().getHoraEntradaFormatada(),
				registro.getProfessor().getHoraSaidaFormatada(),
				registro.getProfessor().getSalario(),
				registro.getAtivo(),
				registro.getDetalhe()
			});
		}
	}
	
	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {
			
			registroProfessorTela.getLblRegistroProfessores().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getLblPesquisarProfessor().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getLblTotalDeBuscas().setForeground(coresPrimarias.get(0));
			
			registroProfessorTela.getTextFieldPesquisar().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getTextFieldPesquisar().setBorder(new LineBorder(coresPrimarias.get(0)));
			registroProfessorTela.getTextFieldTotalDeBuscas().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getTextFieldTotalDeBuscas().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			registroProfessorTela.getTableRegistroProfessores().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getTableRegistroProfessores().setBorder(new LineBorder(coresPrimarias.get(0)));
			registroProfessorTela.getTableRegistroProfessores().getTableHeader().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getTableRegistroProfessores().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}else {
			
			registroProfessorTela.getLblRegistroProfessores().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getLblPesquisarProfessor().setForeground(coresPrimarias.get(1));
			registroProfessorTela.getLblTotalDeBuscas().setForeground(coresPrimarias.get(1));
			
			registroProfessorTela.getTextFieldPesquisar().setForeground(coresPrimarias.get(1));
			registroProfessorTela.getTextFieldPesquisar().setBorder(new LineBorder(coresPrimarias.get(1)));
			registroProfessorTela.getTextFieldTotalDeBuscas().setForeground(coresPrimarias.get(1));
			registroProfessorTela.getTextFieldTotalDeBuscas().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			registroProfessorTela.getTableRegistroProfessores().setForeground(coresPrimarias.get(1));
			registroProfessorTela.getTableRegistroProfessores().setBorder(new LineBorder(coresPrimarias.get(1)));
			registroProfessorTela.getTableRegistroProfessores().getTableHeader().setForeground(coresPrimarias.get(0));
			registroProfessorTela.getTableRegistroProfessores().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(1)));
			
		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		
		if(coresSecundarias.size() == 1) {
			
			registroProfessorTela.getTextFieldPesquisar().setBackground(coresSecundarias.get(0));
			registroProfessorTela.getTextFieldTotalDeBuscas().setBackground(coresSecundarias.get(0));
			
			registroProfessorTela.getContentPane().setBackground(coresSecundarias.get(0));
			
			if(coresSecundarias.get(0).equals(new Color(255, 255, 255))) {
				registroProfessorTela.getTableRegistroProfessores().setBackground(new Color(245, 245, 245));
				registroProfessorTela.getTableRegistroProfessores().getTableHeader().setBackground(new Color(245, 245, 245));
			}else {
				registroProfessorTela.getTableRegistroProfessores().setBackground(coresSecundarias.get(0));
				registroProfessorTela.getTableRegistroProfessores().getTableHeader().setBackground(coresSecundarias.get(0));
			}
			
		}else {
			
			registroProfessorTela.getTextFieldPesquisar().setBackground(coresSecundarias.get(2));
			registroProfessorTela.getTextFieldTotalDeBuscas().setBackground(coresSecundarias.get(2));
			
			registroProfessorTela.getTableRegistroProfessores().setBackground(coresSecundarias.get(2));
			registroProfessorTela.getTableRegistroProfessores().getTableHeader().setBackground(coresSecundarias.get(1));

			registroProfessorTela.getContentPane().setBackground(coresSecundarias.get(3));
		}
	}
}
