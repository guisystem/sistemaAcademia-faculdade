package controller.helper;

import java.awt.Color;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.ModelRegistroAluno;
import view.RegistroAlunoTela;

public class RegistroAlunoHelper{

	private final RegistroAlunoTela registroAlunoTela;

	public RegistroAlunoHelper(RegistroAlunoTela registroAlunoTela) {
		this.registroAlunoTela = registroAlunoTela;
	}

	public void preencherTabela(List<ModelRegistroAluno> registroAlunos) {
		DefaultTableModel tableModel = (DefaultTableModel) registroAlunoTela.getTableRegistroAlunos().getModel();
		tableModel.setNumRows(0);
		
		for (ModelRegistroAluno registro : registroAlunos) {
			
			tableModel.addRow(new Object[] {
				registro,
				registro.getProximoPagamentoFormatada(),
				registro.getPagar(),
				registro.getAtivo(),
				registro.getTotal(),
				registro.getDetalhe()
			});
		}
		
	}

	public boolean dataAntiga(ModelRegistroAluno registroAluno) {
		
		LocalDate proximoPagamento = registroAluno.getProximoPagamento();
		LocalDate agora = LocalDate.now();
		
		int daysBetween = (int) ChronoUnit.DAYS.between(proximoPagamento, agora);
		
		if(daysBetween > 10) {
			return true;
		}
		
		return false;
	
	}

	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {
			
			registroAlunoTela.getLblRegistroAlunos().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getLblPesquisarAluno().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getLblTotalDeBuscas().setForeground(coresPrimarias.get(0));
			
			registroAlunoTela.getTextFieldPesquisar().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getTextFieldPesquisar().setBorder(new LineBorder(coresPrimarias.get(0)));
			registroAlunoTela.getTextFieldTotalDeBuscas().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getTextFieldTotalDeBuscas().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			registroAlunoTela.getTableRegistroAlunos().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getTableRegistroAlunos().setBorder(new LineBorder(coresPrimarias.get(0)));
			registroAlunoTela.getTableRegistroAlunos().getTableHeader().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getTableRegistroAlunos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}else {
			
			registroAlunoTela.getLblRegistroAlunos().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getLblPesquisarAluno().setForeground(coresPrimarias.get(1));
			registroAlunoTela.getLblTotalDeBuscas().setForeground(coresPrimarias.get(1));
			
			registroAlunoTela.getTextFieldPesquisar().setForeground(coresPrimarias.get(1));
			registroAlunoTela.getTextFieldPesquisar().setBorder(new LineBorder(coresPrimarias.get(1)));
			registroAlunoTela.getTextFieldTotalDeBuscas().setForeground(coresPrimarias.get(1));
			registroAlunoTela.getTextFieldTotalDeBuscas().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			registroAlunoTela.getTableRegistroAlunos().setForeground(coresPrimarias.get(1));
			registroAlunoTela.getTableRegistroAlunos().setBorder(new LineBorder(coresPrimarias.get(1)));
			registroAlunoTela.getTableRegistroAlunos().getTableHeader().setForeground(coresPrimarias.get(0));
			registroAlunoTela.getTableRegistroAlunos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(1)));

		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		
		if(coresSecundarias.size() == 1) {
			
			registroAlunoTela.getTextFieldPesquisar().setBackground(coresSecundarias.get(0));
			registroAlunoTela.getTextFieldTotalDeBuscas().setBackground(coresSecundarias.get(0));
			
			registroAlunoTela.getContentPane().setBackground(coresSecundarias.get(0));
			
			if(coresSecundarias.get(0).equals(new Color(255, 255, 255))) {
				registroAlunoTela.getTableRegistroAlunos().setBackground(new Color(245, 245, 245));
				registroAlunoTela.getTableRegistroAlunos().getTableHeader().setBackground(new Color(245, 245, 245));
			}else {
				registroAlunoTela.getTableRegistroAlunos().setBackground(coresSecundarias.get(0));
				registroAlunoTela.getTableRegistroAlunos().getTableHeader().setBackground(coresSecundarias.get(0));
			}
			
		}else {
			
			registroAlunoTela.getTextFieldPesquisar().setBackground(coresSecundarias.get(2));
			registroAlunoTela.getTextFieldTotalDeBuscas().setBackground(coresSecundarias.get(2));
			
			registroAlunoTela.getTableRegistroAlunos().setBackground(coresSecundarias.get(2));
			registroAlunoTela.getTableRegistroAlunos().getTableHeader().setBackground(coresSecundarias.get(1));

			registroAlunoTela.getContentPane().setBackground(coresSecundarias.get(3));
		}
		
	}

}
