package controller.helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.ModelCustoBasico;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarCustosTela;

public class EditarCustoHelper implements Helper{

	private final EditarCustosTela editarCustosTela;

	public EditarCustoHelper(EditarCustosTela editarCustosTela) {
		super();
		this.editarCustosTela = editarCustosTela; 
	}
	
	public void preencherTabela(List<ModelCustoBasico> custos) {
		DefaultTableModel tableModel = (DefaultTableModel) editarCustosTela.getTableCustosBasicos().getModel();
		
		tableModel.setNumRows(0);
		
		for(ModelCustoBasico custo: custos) {
			tableModel.addRow(new Object[]{
				custo,
				custo.getValor()
			});
		}
		
	}

	public ModelCustoBasico obterModelo() {
		String nome = editarCustosTela.getTextFieldNomeCusto().getText();
		String valorTela = editarCustosTela.getTextFieldValorCusto().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = 0;
		try {
			valor = Float.parseFloat(valorFormatado);
		}catch (Exception e) {
			
		}
		
		ModelCustoBasico novoCusto = new ModelCustoBasico(nome, valor);
		return novoCusto;
	}
	
	public ModelCustoBasico obterModeloAtualizar(ModelCustoBasico custosBasicos) {
		custosBasicos.setNome(editarCustosTela.getTextFieldNomeCusto().getText());
		String valorTela = editarCustosTela.getTextFieldValorCusto().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = 0;
		try {
			valor = Float.parseFloat(valorFormatado);
			custosBasicos.setValor(valor);
		}catch (Exception e) {
			
		}
		
		return custosBasicos;
	}

	public boolean verificarCampos() {
		if(editarCustosTela.getTextFieldNomeCusto().getText().trim().isEmpty() || editarCustosTela.getTextFieldValorCusto().getText().trim().isEmpty()) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unused")
	public boolean validarValor() {
		String valorTela = editarCustosTela.getTextFieldValorCusto().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = 0;
		try {
			valor = Float.parseFloat(valorFormatado);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public boolean compararCustos(ModelCustoBasico novoCusto, ModelUsuario usuario) {
		for(ModelCustoBasico custo: usuario.getCustosBasicos()) {
			if(custo.getNome().equalsIgnoreCase(novoCusto.getNome())) {
				return false;
			}
		}
		
		return true;
	}
	
	public void selecionarCustoIgual(ModelCustoBasico novoCusto) {
		for (int i = 0; i < editarCustosTela.getTableCustosBasicos().getRowCount(); i++) {
			String nome = editarCustosTela.getTableCustosBasicos().getValueAt(i, 0).toString();
			if(nome.equalsIgnoreCase(novoCusto.getNome())) {
				editarCustosTela.getTableCustosBasicos().setRowSelectionInterval(i, i);
			}
		}
		
	}
	
	public boolean compararDados(ModelCustoBasico custosBasicos) {
		
		String valorTela = editarCustosTela.getTextFieldValorCusto().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = Float.parseFloat(valorFormatado);
		
		if(editarCustosTela.getTextFieldNomeCusto().getText().equals(custosBasicos.getNome()) && 
				valor == custosBasicos.getValor()) {
			return false;
		}
		return true;
	}

	public void limparTela() {
		editarCustosTela.getTextFieldNomeCusto().setText("");
		editarCustosTela.getTextFieldValorCusto().setText("");
	}

	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {
			
			editarCustosTela.getLblInformacao().setForeground(coresPrimarias.get(0));
			editarCustosTela.getLblCustosBasicos().setForeground(coresPrimarias.get(0));
			editarCustosTela.getLblNomeCusto().setForeground(coresPrimarias.get(0));
			editarCustosTela.getLblValorCusto().setForeground(coresPrimarias.get(0));
			
			editarCustosTela.getTextFieldNomeCusto().setForeground(coresPrimarias.get(0));
			editarCustosTela.getTextFieldNomeCusto().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarCustosTela.getTextFieldValorCusto().setForeground(coresPrimarias.get(0));
			editarCustosTela.getTextFieldValorCusto().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarCustosTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarCustosTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarCustosTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarCustosTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarCustosTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarCustosTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarCustosTela.getTableCustosBasicos().setForeground(coresPrimarias.get(0));
			editarCustosTela.getTableCustosBasicos().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarCustosTela.getTableCustosBasicos().getTableHeader().setForeground(coresPrimarias.get(0));
			editarCustosTela.getTableCustosBasicos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}else {
			
			editarCustosTela.getLblInformacao().setForeground(coresPrimarias.get(1));
			editarCustosTela.getLblCustosBasicos().setForeground(coresPrimarias.get(1));
			editarCustosTela.getLblNomeCusto().setForeground(coresPrimarias.get(1));
			editarCustosTela.getLblValorCusto().setForeground(coresPrimarias.get(1));
			
			editarCustosTela.getTextFieldNomeCusto().setForeground(coresPrimarias.get(1));
			editarCustosTela.getTextFieldNomeCusto().setBorder(new LineBorder(coresPrimarias.get(2)));
			editarCustosTela.getTextFieldValorCusto().setForeground(coresPrimarias.get(1));
			editarCustosTela.getTextFieldValorCusto().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			editarCustosTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarCustosTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarCustosTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarCustosTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarCustosTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarCustosTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarCustosTela.getTableCustosBasicos().setForeground(coresPrimarias.get(1));
			editarCustosTela.getTableCustosBasicos().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarCustosTela.getTableCustosBasicos().getTableHeader().setForeground(coresPrimarias.get(1));
			editarCustosTela.getTableCustosBasicos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			editarCustosTela.getTextFieldNomeCusto().setBackground(coresSecundarias.get(0));
			editarCustosTela.getTextFieldValorCusto().setBackground(coresSecundarias.get(0));
			
			editarCustosTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			
			editarCustosTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));

			editarCustosTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarCustosTela.getTableCustosBasicos().setBackground(coresSecundarias.get(0));
			editarCustosTela.getTableCustosBasicos().getTableHeader().setBackground(coresSecundarias.get(0));
			
			editarCustosTela.getContentPane().setBackground(coresSecundarias.get(coresSecundarias.size() - 1));
			
		}else {
			
			editarCustosTela.getTextFieldNomeCusto().setBackground(coresSecundarias.get(2));
			editarCustosTela.getTextFieldValorCusto().setBackground(coresSecundarias.get(2));
			
			editarCustosTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			editarCustosTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));
			editarCustosTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarCustosTela.getTableCustosBasicos().setBackground(coresSecundarias.get(2));
			editarCustosTela.getTableCustosBasicos().getTableHeader().setBackground(coresSecundarias.get(1));
			
			editarCustosTela.getContentPane().setBackground(coresSecundarias.get(coresSecundarias.size() - 1));
		}
		
		if(coresSecundarias.get(0).equals(new Color(0, 0, 0)) || coresSecundarias.get(0).equals(new Color(0, 0, 255))) {
			editarCustosTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios-tela-escura.png")));
		}else {
			editarCustosTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));
			
		}
		
	}

}
