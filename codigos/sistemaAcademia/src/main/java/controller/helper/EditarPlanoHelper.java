package controller.helper;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import model.ModelPlano;
import model.ModelUsuario;
import view.ConfiguracaoTela;
import view.EditarPlanosTela;

public class EditarPlanoHelper implements Helper {

	private final EditarPlanosTela editarPlanosTela;

	public EditarPlanoHelper(EditarPlanosTela editarPlanosTela) {
		super();
		this.editarPlanosTela = editarPlanosTela;
	}
	
	public void preencherTabela(List<ModelPlano> planos) {
		DefaultTableModel tableModel = (DefaultTableModel) editarPlanosTela.getTablePlanos().getModel();
		
		tableModel.setNumRows(0);
		
		for(ModelPlano plano: planos) {
			tableModel.addRow(new Object[]{
					plano,
					plano.getPeriodo(),
					plano.getValor()
			});
		}
	
	}
	
	public ModelPlano obterModelo() {
		String nome = editarPlanosTela.getTextFieldNomePlano().getText();
		
		String tempoTela = editarPlanosTela.getTextFieldTempoPlano().getText();
		
		String valorTela = editarPlanosTela.getTextFieldValorPlano().getText();
		String valorFormatado = valorTela.replace(",", ".");
		
		int tempo = 0;
		float valor = 0;
		try {
			tempo = Integer.parseInt(tempoTela);
			valor = Float.parseFloat(valorFormatado);
		}catch (Exception e) {
			
		}
		
		ModelPlano novoPlano = new ModelPlano(nome, tempo, valor);
		return novoPlano;
	}
	
	public ModelPlano obterModeloAtualizar(ModelPlano plano) {
		plano.setNome(editarPlanosTela.getTextFieldNomePlano().getText());
		
		String tempoTela = editarPlanosTela.getTextFieldTempoPlano().getText();
		
		String valorTela = editarPlanosTela.getTextFieldValorPlano().getText();
		String valorFormatado = valorTela.replace(",", ".");
		
		int tempo = 0;
		float valor = 0;
		try {
			tempo = Integer.parseInt(tempoTela);
			valor = Float.parseFloat(valorFormatado);

			plano.setPeriodo(tempo);
			plano.setValor(valor);
		}catch (Exception e) {
			
		}
		
		return plano;
	}
	
	public boolean verificarCampos() {
		if(editarPlanosTela.getTextFieldNomePlano().getText().trim().isEmpty() || 
				editarPlanosTela.getTextFieldTempoPlano().getText().trim().isEmpty() || 
				editarPlanosTela.getTextFieldValorPlano().getText().trim().isEmpty()) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unused")
	public boolean validarTempo() {
		String tempoTela = editarPlanosTela.getTextFieldTempoPlano().getText();
		
		int tempo = 0;
		try {
			tempo = Integer.parseInt(tempoTela);
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("unused")
	public boolean validarValor() {
		String valorTela = editarPlanosTela.getTextFieldValorPlano().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		float valor = 0;
		try {
			valor = Float.parseFloat(valorFormatado);
			return true;
		}catch (Exception e) {
			return false;
		}
		
	}
	
	public boolean compararPlanos(ModelPlano novoPlano, ModelUsuario usuario) {
		for(ModelPlano plano: usuario.getPlanos()) {
			if(plano.getNome().equalsIgnoreCase(novoPlano.getNome())) {
				return false;
			}
		}
		
		return true;
	}
	
	public void selecionarPlanoIgual(ModelPlano novoPlano) {
		for (int i = 0; i < editarPlanosTela.getTablePlanos().getRowCount(); i++) {
			String nome = editarPlanosTela.getTablePlanos().getValueAt(i, 0).toString();
			if(nome.equalsIgnoreCase(novoPlano.getNome())) {
				editarPlanosTela.getTablePlanos().setRowSelectionInterval(i, i);
			}
		}
		
	}
	
	public boolean compararDados(ModelPlano plano) {
		String tempoTela = editarPlanosTela.getTextFieldTempoPlano().getText();

		String valorTela = editarPlanosTela.getTextFieldValorPlano().getText();
		String valorFormatado = valorTela.replace(",", ".").trim();
		
		int tempo = Integer.parseInt(tempoTela);
		float valor = Float.parseFloat(valorFormatado);
		
		if(editarPlanosTela.getTextFieldNomePlano().getText().equals(plano.getNome()) && 
				tempo == plano.getPeriodo() && valor == plano.getValor()) {
			return false;
		}
		return true;
	}
	
	public void limparTela() {
		editarPlanosTela.getTextFieldNomePlano().setText("");
		editarPlanosTela.getTextFieldTempoPlano().setText("");
		editarPlanosTela.getTextFieldValorPlano().setText("");
		
	}

	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {
			
			editarPlanosTela.getLblInformacao().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getLblMesesTempoPlano().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getLblNomePlano().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getLblPlanos().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getLblTempoPlano().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getLblValorPlano().setForeground(coresPrimarias.get(0));
			
			editarPlanosTela.getTextFieldNomePlano().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getTextFieldNomePlano().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarPlanosTela.getTextFieldTempoPlano().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getTextFieldTempoPlano().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarPlanosTela.getTextFieldValorPlano().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getTextFieldValorPlano().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarPlanosTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarPlanosTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarPlanosTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarPlanosTela.getTablePlanos().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getTablePlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarPlanosTela.getTablePlanos().getTableHeader().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getTablePlanos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}else {
			
			editarPlanosTela.getLblInformacao().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getLblMesesTempoPlano().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getLblNomePlano().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getLblPlanos().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getLblTempoPlano().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getLblValorPlano().setForeground(coresPrimarias.get(1));
			
			editarPlanosTela.getTextFieldNomePlano().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getTextFieldNomePlano().setBorder(new LineBorder(coresPrimarias.get(2)));
			editarPlanosTela.getTextFieldTempoPlano().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getTextFieldTempoPlano().setBorder(new LineBorder(coresPrimarias.get(2)));
			editarPlanosTela.getTextFieldValorPlano().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getTextFieldValorPlano().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			editarPlanosTela.getBtnAdicionar().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getBtnAdicionar().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarPlanosTela.getBtnAtualizar().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getBtnAtualizar().setBorder(new LineBorder(coresPrimarias.get(0)));

			editarPlanosTela.getBtnExcluir().setForeground(coresPrimarias.get(0));
			editarPlanosTela.getBtnExcluir().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			editarPlanosTela.getTablePlanos().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getTablePlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			editarPlanosTela.getTablePlanos().getTableHeader().setForeground(coresPrimarias.get(1));
			editarPlanosTela.getTablePlanos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			editarPlanosTela.getTextFieldNomePlano().setBackground(coresSecundarias.get(0));
			editarPlanosTela.getTextFieldTempoPlano().setBackground(coresSecundarias.get(0));
			editarPlanosTela.getTextFieldValorPlano().setBackground(coresSecundarias.get(0));
			
			editarPlanosTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			editarPlanosTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));
			editarPlanosTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarPlanosTela.getTablePlanos().getTableHeader().setBackground(coresSecundarias.get(0));
			editarPlanosTela.getTablePlanos().setBackground(coresSecundarias.get(0));
			
			editarPlanosTela.getContentPane().setBackground(coresSecundarias.get(0));
			
		}else {
			
			editarPlanosTela.getTextFieldNomePlano().setBackground(coresSecundarias.get(2));
			editarPlanosTela.getTextFieldTempoPlano().setBackground(coresSecundarias.get(2));
			editarPlanosTela.getTextFieldValorPlano().setBackground(coresSecundarias.get(2));
			
			editarPlanosTela.getBtnAdicionar().setBackground(coresSecundarias.get(0));
			editarPlanosTela.getBtnAtualizar().setBackground(coresSecundarias.get(0));
			editarPlanosTela.getBtnExcluir().setBackground(coresSecundarias.get(0));
			
			editarPlanosTela.getTablePlanos().getTableHeader().setBackground(coresSecundarias.get(1));
			editarPlanosTela.getTablePlanos().setBackground(coresSecundarias.get(2));
			
			editarPlanosTela.getContentPane().setBackground(coresSecundarias.get(coresSecundarias.size() - 1));
		}
		
		if(coresSecundarias.get(0).equals(new Color(0, 0, 0)) || coresSecundarias.get(0).equals(new Color(0, 0, 255))) {
			editarPlanosTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios-tela-escura.png")));
		}else {
			editarPlanosTela.getLblInformacao().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));
			
		}
		
	}
	
}
