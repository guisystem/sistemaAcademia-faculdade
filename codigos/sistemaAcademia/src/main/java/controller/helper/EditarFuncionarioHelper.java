package controller.helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.ModelFuncionario;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarFuncionariosTela;

public class EditarFuncionarioHelper implements Helper {

	private final EditarFuncionariosTela editarFuncionariosTela;

	public EditarFuncionarioHelper(EditarFuncionariosTela editarFuncionariosTela) {
		super();
		this.editarFuncionariosTela = editarFuncionariosTela; 
	}

	public void preencherTabela(List<ModelFuncionario> funcionarios) {
			DefaultTableModel tableModel = (DefaultTableModel) editarFuncionariosTela.getTableFuncionarios().getModel();
			
			tableModel.setNumRows(0);
			
			for(ModelFuncionario funcionario: funcionarios) {
				tableModel.addRow(new Object[]{
					funcionario,
					funcionario.getCargo(),
					funcionario.getSalario()
				});
			}
		
	}

	public ModelFuncionario obterModelo() {
		String nome = editarFuncionariosTela.getTextFieldNomeFuncionario().getText();
		String cargo = editarFuncionariosTela.getTextFieldCargoFuncionario().getText();
		String salarioTela = editarFuncionariosTela.getTextFieldSalarioFuncionario().getText();
		String salarioFormatado = salarioTela.replace(",", ".");
		
		float salario = 0;
		try {
			salario = Float.parseFloat(salarioFormatado);
		}catch (Exception e) {
			
		}
		
		ModelFuncionario novoFuncionario = new ModelFuncionario(nome, cargo, salario);
		return novoFuncionario;
	}

	public ModelFuncionario obterModeloAtualizar(ModelFuncionario funcionario) {
		funcionario.setNome(editarFuncionariosTela.getTextFieldNomeFuncionario().getText());
		funcionario.setCargo(editarFuncionariosTela.getTextFieldCargoFuncionario().getText());
		String salarioTela = editarFuncionariosTela.getTextFieldSalarioFuncionario().getText();
		String salarioFormatado = salarioTela.replace(",", ".");
		
		float salario = 0;
		try {
			salario = Float.parseFloat(salarioFormatado);
			funcionario.setSalario(salario);
		}catch (Exception e) {
			
		}
		
		return funcionario;
	}
	
	public boolean verificarCampos() {
		if(editarFuncionariosTela.getTextFieldNomeFuncionario().getText().trim().isEmpty() || 
				editarFuncionariosTela.getTextFieldCargoFuncionario().getText().trim().isEmpty() || 
				editarFuncionariosTela.getTextFieldSalarioFuncionario().getText().trim().isEmpty()) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unused")
	public boolean validarSalario() {
		String salarioTela = editarFuncionariosTela.getTextFieldSalarioFuncionario().getText();
		String salarioFormatado = salarioTela.replace(",", ".").trim();
		
		float salario = 0;
		try {
			salario = Float.parseFloat(salarioFormatado);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}

	public boolean compararFuncionarios(ModelFuncionario novoFuncionario, ModelUsuario usuario) {
		for(ModelFuncionario funcionario: usuario.getFuncionarios()) {
			if(funcionario.getNome().equalsIgnoreCase(novoFuncionario.getNome()) && 
					funcionario.getCargo().equalsIgnoreCase(novoFuncionario.getCargo()) &&
					funcionario.getSalario() == novoFuncionario.getSalario()) {
				return false;
			}
		}
		
		return true;
	}

	public void selecionarFuncionarioIgual(ModelFuncionario novoFuncionario) {
		for (int i = 0; i < editarFuncionariosTela.getTableFuncionarios().getRowCount(); i++) {
			String nome = editarFuncionariosTela.getTableFuncionarios().getValueAt(i, 0).toString();
			String cargo = editarFuncionariosTela.getTableFuncionarios().getValueAt(i, 1).toString();
			String salarioTabela = editarFuncionariosTela.getTableFuncionarios().getValueAt(i, 2).toString();
			
			float salario = Float.parseFloat(salarioTabela);
			
			if(nome.equalsIgnoreCase(novoFuncionario.getNome()) && cargo.equalsIgnoreCase(novoFuncionario.getCargo()) && salario == novoFuncionario.getSalario()) {
				editarFuncionariosTela.getTableFuncionarios().setRowSelectionInterval(i, i);
			}
		}
		
	}
	
	public boolean compararDados(ModelFuncionario funcionario) {
		String salarioTela = editarFuncionariosTela.getTextFieldSalarioFuncionario().getText();
		String salarioFormatado = salarioTela.replace(",", ".").trim();
		
		float salario = Float.parseFloat(salarioFormatado);
		
		if(editarFuncionariosTela.getTextFieldNomeFuncionario().getText().equals(funcionario.getNome()) && 
				editarFuncionariosTela.getTextFieldCargoFuncionario().getText().equals(funcionario.getCargo()) && 
				salario == funcionario.getSalario()) {
			return false;
		}
		return true;
	}

	public void limparTela() {
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText("");
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText("");
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText("");
		
	}

	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {
			
			editarFuncionariosTela.getLblFuncionario().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getLblInformacao().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getLblCargoFuncionario().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getLblNomeFuncionario().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getLblSalarioFuncionario().setForeground(coresPrimarias.get(0));
			
			editarFuncionariosTela.getTextFieldNomeFuncionario().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getTextFieldNomeFuncionario().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarFuncionariosTela.getTextFieldCargoFuncionario().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getTextFieldCargoFuncionario().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarFuncionariosTela.getTextFieldSalarioFuncionario().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getTextFieldSalarioFuncionario().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarFuncionariosTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarFuncionariosTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarFuncionariosTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarFuncionariosTela.getTableFuncionarios().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getTableFuncionarios().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarFuncionariosTela.getTableFuncionarios().getTableHeader().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getTableFuncionarios().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}else {
			
			editarFuncionariosTela.getLblFuncionario().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getLblInformacao().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getLblCargoFuncionario().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getLblNomeFuncionario().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getLblSalarioFuncionario().setForeground(coresPrimarias.get(1));
			
			editarFuncionariosTela.getTextFieldNomeFuncionario().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getTextFieldNomeFuncionario().setBorder(new LineBorder(coresPrimarias.get(2)));
			editarFuncionariosTela.getTextFieldCargoFuncionario().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getTextFieldCargoFuncionario().setBorder(new LineBorder(coresPrimarias.get(2)));
			editarFuncionariosTela.getTextFieldSalarioFuncionario().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getTextFieldSalarioFuncionario().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			editarFuncionariosTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarFuncionariosTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarFuncionariosTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarFuncionariosTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarFuncionariosTela.getTableFuncionarios().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getTableFuncionarios().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarFuncionariosTela.getTableFuncionarios().getTableHeader().setForeground(coresPrimarias.get(1));
			editarFuncionariosTela.getTableFuncionarios().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			editarFuncionariosTela.getTextFieldNomeFuncionario().setBackground(coresSecundarias.get(0));
			editarFuncionariosTela.getTextFieldCargoFuncionario().setBackground(coresSecundarias.get(0));
			editarFuncionariosTela.getTextFieldSalarioFuncionario().setBackground(coresSecundarias.get(0));
			
			editarFuncionariosTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			editarFuncionariosTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));
			editarFuncionariosTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarFuncionariosTela.getTableFuncionarios().setBackground(coresSecundarias.get(0));
			editarFuncionariosTela.getTableFuncionarios().getTableHeader().setBackground(coresSecundarias.get(0));
			
			editarFuncionariosTela.getContentPane().setBackground(coresSecundarias.get(coresSecundarias.size() - 1));
			
		}else {
			
			editarFuncionariosTela.getTextFieldNomeFuncionario().setBackground(coresSecundarias.get(2));
			editarFuncionariosTela.getTextFieldCargoFuncionario().setBackground(coresSecundarias.get(2));
			editarFuncionariosTela.getTextFieldSalarioFuncionario().setBackground(coresSecundarias.get(2));
			
			editarFuncionariosTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			editarFuncionariosTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));
			editarFuncionariosTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarFuncionariosTela.getTableFuncionarios().setBackground(coresSecundarias.get(2));
			editarFuncionariosTela.getTableFuncionarios().getTableHeader().setBackground(coresSecundarias.get(1));
			
			editarFuncionariosTela.getContentPane().setBackground(coresSecundarias.get(coresSecundarias.size() - 1));
		}
		
		if(coresSecundarias.get(0).equals(new Color(0, 0, 0)) || coresSecundarias.get(0).equals(new Color(0, 0, 255))) {
			editarFuncionariosTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios-tela-escura.png")));
		}else {
			editarFuncionariosTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));
			
		}
		
	}

}
