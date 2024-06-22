package controller.helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.ModelModalidade;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarModalidadeTela;

public class EditarModalidadeHelper implements Helper {

	private final EditarModalidadeTela editarModalidadeTela;

	public EditarModalidadeHelper(EditarModalidadeTela editarModalidadeTela) {
		super();
		this.editarModalidadeTela = editarModalidadeTela;
	}

	public void preencherTabela(List<ModelModalidade> modalidades) {
		DefaultTableModel tableModel = (DefaultTableModel) editarModalidadeTela.getTableModalidades().getModel();
		
		tableModel.setNumRows(0); 
		
		for(ModelModalidade modalidade: modalidades) {
			tableModel.addRow(new Object[] {
					modalidade.getNome(),
					modalidade.getTaxaExtra()
			});
		}
		
	}

	public ModelModalidade obterModelo() {
		String nome = editarModalidadeTela.getTextFieldNomeModalidade().getText();
		String valorTela = editarModalidadeTela.getTextFieldlTaxaExtraModalidade().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = 0;
		try {
			valor = Float.parseFloat(valorFormatado);
		}catch (Exception e) {
			
		}
		
		ModelModalidade novaModalidade = new ModelModalidade(nome, valor);
		return novaModalidade;
	}
	
	
	public ModelModalidade obterModeloAtualizar(ModelModalidade modalidade) {
		modalidade.setNome(editarModalidadeTela.getTextFieldNomeModalidade().getText());
		String valorTela = editarModalidadeTela.getTextFieldlTaxaExtraModalidade().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = 0;
		try {
			valor = Float.parseFloat(valorFormatado);
			modalidade.setTaxaExtra(valor);
		}catch (Exception e) {
			
		}
		
		return modalidade;
	}

	public boolean verificarCampos() {
		if(editarModalidadeTela.getTextFieldNomeModalidade().getText().trim().isEmpty() || 
				editarModalidadeTela.getTextFieldlTaxaExtraModalidade().getText().trim().isEmpty()) {
			
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unused")
	public boolean validarTaxa() {
		String valorTela = editarModalidadeTela.getTextFieldlTaxaExtraModalidade().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = 0;
		try {
			valor = Float.parseFloat(valorFormatado);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public boolean compararModalidades(ModelModalidade novaModalidade, ModelUsuario usuario) {
		for(ModelModalidade modalidade: usuario.getModalidades()) {
			if(modalidade.getNome().equalsIgnoreCase(novaModalidade.getNome())) {
				return false;
			}
		}
		
		return true;
	}

	public void selecionarModalidadeIgual(ModelModalidade novaModalidade) {
		for (int i = 0; i < editarModalidadeTela.getTableModalidades().getRowCount(); i++) {
			String nome = editarModalidadeTela.getTableModalidades().getValueAt(i, 0).toString();
			if(nome.equalsIgnoreCase(novaModalidade.getNome())) {
				editarModalidadeTela.getTableModalidades().setRowSelectionInterval(i, i);
			}
		}
	}
	
	public boolean compararDados(ModelModalidade modalidade) {
		
		String valorTela = editarModalidadeTela.getTextFieldlTaxaExtraModalidade().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = Float.parseFloat(valorFormatado);
		
		if(editarModalidadeTela.getTextFieldNomeModalidade().getText().equals(modalidade.getNome()) && 
				valor == modalidade.getTaxaExtra()) {
			return false;
		}
		return true;
	}

	public void limparTela() {
		editarModalidadeTela.getTextFieldNomeModalidade().setText("");
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText("");
		
	}
	
	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {
			
			
			editarModalidadeTela.getLblModalidades().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getLblInformacao().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getLblNomeModalidade().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getLblTaxaExtraModalidade().setForeground(coresPrimarias.get(0));
			
			editarModalidadeTela.getTextFieldNomeModalidade().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getTextFieldNomeModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarModalidadeTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarModalidadeTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarModalidadeTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarModalidadeTela.getTableModalidades().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getTableModalidades().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarModalidadeTela.getTableModalidades().getTableHeader().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getTableModalidades().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}else {
			
			editarModalidadeTela.getLblModalidades().setForeground(coresPrimarias.get(1));
			editarModalidadeTela.getLblInformacao().setForeground(coresPrimarias.get(1));
			editarModalidadeTela.getLblNomeModalidade().setForeground(coresPrimarias.get(1));
			editarModalidadeTela.getLblTaxaExtraModalidade().setForeground(coresPrimarias.get(1));
			
			editarModalidadeTela.getTextFieldNomeModalidade().setForeground(coresPrimarias.get(1));
			editarModalidadeTela.getTextFieldNomeModalidade().setBorder(new LineBorder(coresPrimarias.get(2)));
			editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setForeground(coresPrimarias.get(1));
			editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			editarModalidadeTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarModalidadeTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarModalidadeTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarModalidadeTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarModalidadeTela.getTableModalidades().setForeground(coresPrimarias.get(1));
			editarModalidadeTela.getTableModalidades().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarModalidadeTela.getTableModalidades().getTableHeader().setForeground(coresPrimarias.get(1));
			editarModalidadeTela.getTableModalidades().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			editarModalidadeTela.getTextFieldNomeModalidade().setBackground(coresSecundarias.get(0));
			editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setBackground(coresSecundarias.get(0));
			
			editarModalidadeTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			editarModalidadeTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));
			editarModalidadeTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarModalidadeTela.getTableModalidades().setBackground(coresSecundarias.get(0));
			editarModalidadeTela.getTableModalidades().getTableHeader().setBackground(coresSecundarias.get(0));
			
			editarModalidadeTela.getContentPane().setBackground(coresSecundarias.get(0));
			
		}else {
			
			editarModalidadeTela.getTextFieldNomeModalidade().setBackground(coresSecundarias.get(2));
			editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setBackground(coresSecundarias.get(2));
			
			editarModalidadeTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			editarModalidadeTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));
			editarModalidadeTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarModalidadeTela.getTableModalidades().setBackground(coresSecundarias.get(2));
			editarModalidadeTela.getTableModalidades().getTableHeader().setBackground(coresSecundarias.get(1));
			
			editarModalidadeTela.getContentPane().setBackground(coresSecundarias.get(coresSecundarias.size() - 1));
		}
		
		if(coresSecundarias.get(0).equals(new Color(0, 0, 0)) || coresSecundarias.get(0).equals(new Color(0, 0, 255))) {
			editarModalidadeTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios-tela-escura.png")));
		}else {
			editarModalidadeTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));
			
		}
		
	}
	
}
