package controller.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;

import model.ModelConfiguracao;
import model.ModelCustoBasico;
import model.ModelFuncionario;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelUsuario;
import view.ConfiguracaoTela;

public class ConfiguracaoHelper {

	private final ConfiguracaoTela configuracaoTela; 

	public ConfiguracaoHelper(ConfiguracaoTela configuracaoTela) {
		this.configuracaoTela = configuracaoTela;
	}
	
	public void preencherTela(ModelUsuario usuario) {
		configuracaoTela.getTextFieldCorAtualPrimaria().setText("Branco");
		configuracaoTela.getTextFieldCorAtualSecundaria().setText("Preto");
		preencherCores(usuario);
		
		if(usuario.getConfiguracao() != null) {
			configuracaoTela.getTextFieldNomeAcademia().setText(usuario.getConfiguracao().getNomeDaAcademia());
			configuracaoTela.getTextFieldHoraAbrir().setText(usuario.getConfiguracao().getHoraDeAbrirFormatada());
			configuracaoTela.getTextFieldHoraFechar().setText(usuario.getConfiguracao().getHoraDeFecharFormatada());
			configuracaoTela.getTextFieldCorAtualPrimaria().setText(usuario.getConfiguracao().getCorAtualPrimaria());
			configuracaoTela.getTextFieldCorAtualSecundaria().setText(usuario.getConfiguracao().getCorAtualSecundaria());
			configuracaoTela.getTextFieldNomeUsuario().setText(usuario.getConfiguracao().getUsuario().getNomeUsuario());
			
			preencherDias(usuario);
			preencherCores(usuario);
			preencherCustos(usuario);
			preencherFuncionarios(usuario);
			preencherModalidades(usuario);
			preencherPlanos(usuario);
		}else {
		
		}
		
	}
	
	public void preencherTabelas(ModelUsuario usuario) {
		preencherCustos(usuario);
		preencherFuncionarios(usuario);
		preencherModalidades(usuario);
		preencherPlanos(usuario);
	}

	@SuppressWarnings("unchecked")
	private void preencherDias(ModelUsuario usuario) {
		DefaultComboBoxModel<DayOfWeek> boxModelAbrir = (DefaultComboBoxModel<DayOfWeek>) configuracaoTela.getComboBoxDiaComeco().getModel();
		DefaultComboBoxModel<DayOfWeek> boxModelFechar = (DefaultComboBoxModel<DayOfWeek>) configuracaoTela.getComboBoxDiaFim().getModel();
		
		boxModelAbrir.setSelectedItem(usuario.getConfiguracao().getDiaDeAbrir());
		boxModelFechar.setSelectedItem(usuario.getConfiguracao().getDiaDeFechar());
		
	}

	@SuppressWarnings("unchecked")
	private void preencherCores(ModelUsuario usuario) {
		DefaultComboBoxModel<Color> boxModelPrimaria = (DefaultComboBoxModel<Color>) configuracaoTela.getComboBoxMudarCorPrimaria().getModel();
		DefaultComboBoxModel<Color> boxModelSecundaria = (DefaultComboBoxModel<Color>) configuracaoTela.getComboBoxMudarCorSecundaria().getModel();
		
		if(usuario.getConfiguracao() != null) {
			boxModelPrimaria.setSelectedItem(usuario.getConfiguracao().getCorAtualPrimaria());
			boxModelSecundaria.setSelectedItem(usuario.getConfiguracao().getCorAtualSecundaria());			
		}else {
			boxModelPrimaria.setSelectedItem(configuracaoTela.getTextFieldCorAtualPrimaria().getText());
			boxModelSecundaria.setSelectedItem(configuracaoTela.getTextFieldCorAtualSecundaria().getText());			
		}
		
	}

	private void preencherCustos(ModelUsuario usuario) {
		DefaultTableModel tableCustos = (DefaultTableModel) configuracaoTela.getTableCustosBasicos().getModel();
		tableCustos.setNumRows(0);
		
		for(ModelCustoBasico custo: usuario.getCustosBasicos()) {
			
			tableCustos.addRow(new Object[] {
					custo.getNome(),
					"R$: " + custo.getValor()
			});
		}
		
	}
	
	private void preencherFuncionarios(ModelUsuario usuario) {
		DefaultTableModel tableFuncionarios = (DefaultTableModel) configuracaoTela.getTableFuncionarios().getModel();
		tableFuncionarios.setNumRows(0);
		
		for(ModelFuncionario funcionario: usuario.getFuncionarios()) {
			
			tableFuncionarios.addRow(new Object[] {
					funcionario.getNome(),
					funcionario.getCargo(),
					"R$: " + funcionario.getSalario()
			});
		}
		
	}
	
	private void preencherModalidades(ModelUsuario usuario) {
		DefaultTableModel tableModalidades = (DefaultTableModel) configuracaoTela.getTableModalidades().getModel();
		tableModalidades.setNumRows(0);
		
		for(ModelModalidade modalidade: usuario.getModalidades()) {
			
			tableModalidades.addRow(new Object[] {
					modalidade.getNome(),
					"R$: " + modalidade.getTaxaExtra(),
					modalidade.getAlunosAtivos(),
					modalidade.getProfessoresAtivos(modalidade)
			});
		}
	}
	
	private void preencherPlanos(ModelUsuario usuario) {
		DefaultTableModel tablePlanos = (DefaultTableModel) configuracaoTela.getTablePlanos().getModel();
		tablePlanos.setNumRows(0);
		
		for(ModelPlano plano: usuario.getPlanos()) {
			
			tablePlanos.addRow(new Object[] {
					plano.getNome(),
					plano.getPeriodo(),
					"R$: " + plano.getValor(), 
					plano.getAlunosAtivos()
			});
		}
		
	}

	public ModelConfiguracao obterModelo(ModelUsuario usuarioConfiguracao) {
		String nomeAcademia = configuracaoTela.getTextFieldNomeAcademia().getText();
		String horaAbrir = configuracaoTela.getTextFieldHoraAbrir().getText();
		String horaFechar = configuracaoTela.getTextFieldHoraFechar().getText(); 
		String corPrimaria = (String) configuracaoTela.getComboBoxMudarCorPrimaria().getSelectedItem();
		String corSecundaria = (String) configuracaoTela.getComboBoxMudarCorSecundaria().getSelectedItem();
		String diaAbrir = (String) configuracaoTela.getComboBoxDiaComeco().getSelectedItem();
		String diaFechar = (String) configuracaoTela.getComboBoxDiaFim().getSelectedItem();
		ModelUsuario usuario = usuarioConfiguracao;
		
		ModelConfiguracao novaConfiguracao = new ModelConfiguracao(nomeAcademia, diaAbrir, diaFechar, horaAbrir, horaFechar, corPrimaria, corSecundaria, usuario);
		return novaConfiguracao;
		
	}

	public ModelConfiguracao obterModeloAtualizado(ModelUsuario usuario) {
		usuario.getConfiguracao().setNomeDaAcademia(configuracaoTela.getTextFieldNomeAcademia().getText());
		usuario.getConfiguracao().setHoraDeAbrirFormatado(configuracaoTela.getTextFieldHoraAbrir().getText());
		usuario.getConfiguracao().setHoraDeFecharFormatado(configuracaoTela.getTextFieldHoraFechar().getText());
		usuario.getConfiguracao().setCorAtualPrimaria((String) configuracaoTela.getComboBoxMudarCorPrimaria().getSelectedItem());
		usuario.getConfiguracao().setCorAtualSecundaria((String) configuracaoTela.getComboBoxMudarCorSecundaria().getSelectedItem());
		usuario.getConfiguracao().setDiaDeAbrir((String) configuracaoTela.getComboBoxDiaComeco().getSelectedItem());
		usuario.getConfiguracao().setDiaDeFechar((String) configuracaoTela.getComboBoxDiaFim().getSelectedItem());
		
		return usuario.getConfiguracao();
	}

	public boolean verificarCampos() {
		if(configuracaoTela.getTextFieldNomeAcademia().getText().trim().isEmpty() || configuracaoTela.getTextFieldHoraAbrir().getText().trim().isEmpty()
				|| configuracaoTela.getTextFieldHoraFechar().getText().trim().isEmpty()) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unused")
	public boolean verificarHorario(String hora) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime horaVerificada = null;
		
        if(hora == null || hora.equals("  :  ")) {
        	return false;
        }
        
        hora = "03/10/2000 " + hora;
        
        try {
        	horaVerificada = LocalDateTime.parse(hora, dtf);
        }catch(DateTimeParseException e) {
        	return false;
        }
        
        return true;
	}
	
	ArrayList<Color> coresP = new ArrayList<>();
	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		
		if(coresPrimarias.size() == 1) {
			
			configuracaoTela.getLblConfiguracao().setForeground(coresPrimarias.get(0));
			
			configuracaoTela.getLblNomeAcademia().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCustosBasicos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblDias().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblFuncionarios().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblHorario().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblModalidadas().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblPeriodoDiaA().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblPeriodoFuncionamento().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblPeriodoHorarioAS().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblPlanos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblExceçãoFuncionario().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblUsuarioPermitidos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCorPrimaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCorSecundaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblInformacaoConfiguracao().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblParaPrimaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblParaSecundaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblInformacoesAlteraveis().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCamposImportantes().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCampoImportanteFuncionamentoNome().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCampoImportanteCorPri().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCampoImportanteCorSec().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCampoImportanteNomeAcademia().setForeground(coresPrimarias.get(0));
			
			configuracaoTela.getTextFieldNomeAcademia().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTextFieldNomeAcademia().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTextFieldNomeUsuario().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTextFieldNomeUsuario().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTextFieldHoraAbrir().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTextFieldHoraAbrir().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTextFieldHoraFechar().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTextFieldHoraFechar().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTextFieldCorAtualPrimaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTextFieldCorAtualPrimaria().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTextFieldCorAtualSecundaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTextFieldCorAtualSecundaria().setBorder(new LineBorder(coresPrimarias.get(0)));

			configuracaoTela.getComboBoxDiaComeco().setForeground(coresPrimarias.get(0));
			configuracaoTela.getComboBoxDiaComeco().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getComboBoxDiaFim().setForeground(coresPrimarias.get(0));
			configuracaoTela.getComboBoxDiaFim().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getComboBoxMudarCorPrimaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getComboBoxMudarCorPrimaria().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getComboBoxMudarCorSecundaria().setForeground(coresPrimarias.get(0));
			configuracaoTela.getComboBoxMudarCorSecundaria().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getTableCustosBasicos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableCustosBasicos().setBorder(new LineBorder(coresPrimarias.get(0)));			
			configuracaoTela.getTableFuncionarios().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableFuncionarios().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTableModalidades().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableModalidades().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTablePlanos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTablePlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getTableCustosBasicos().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableCustosBasicos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTableFuncionarios().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableFuncionarios().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTableModalidades().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableModalidades().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTablePlanos().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTablePlanos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnConfirmarAlteracoes().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnConfirmarAlteracoes().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnEditarCustos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarCustos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnEditarFuncionarios().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarFuncionarios().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnEditarModalidades().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarModalidades().setBorder(new LineBorder(coresPrimarias.get(0)));

			configuracaoTela.getBtnEditarPlanos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarPlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getRoundedPanel().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}else {
			
			configuracaoTela.getLblConfiguracao().setForeground(coresPrimarias.get(0));
			
			configuracaoTela.getLblNomeAcademia().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblCustosBasicos().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblDias().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblFuncionarios().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblHorario().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblModalidadas().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblPeriodoDiaA().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblPeriodoFuncionamento().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblPeriodoHorarioAS().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblPlanos().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblExceçãoFuncionario().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblUsuarioPermitidos().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblCorPrimaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblCorSecundaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblInformacaoConfiguracao().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblParaPrimaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblParaSecundaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblInformacoesAlteraveis().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblCamposImportantes().setForeground(coresPrimarias.get(1));
			configuracaoTela.getLblCampoImportanteFuncionamentoNome().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCampoImportanteCorPri().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCampoImportanteCorSec().setForeground(coresPrimarias.get(0));
			configuracaoTela.getLblCampoImportanteNomeAcademia().setForeground(coresPrimarias.get(0));
			
			configuracaoTela.getTextFieldNomeAcademia().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTextFieldNomeAcademia().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTextFieldNomeUsuario().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTextFieldNomeUsuario().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTextFieldHoraAbrir().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTextFieldHoraAbrir().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTextFieldHoraFechar().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTextFieldHoraFechar().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTextFieldCorAtualPrimaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTextFieldCorAtualPrimaria().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTextFieldCorAtualSecundaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTextFieldCorAtualSecundaria().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			configuracaoTela.getComboBoxDiaComeco().setForeground(coresPrimarias.get(1));
			configuracaoTela.getComboBoxDiaComeco().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getComboBoxDiaFim().setForeground(coresPrimarias.get(1));
			configuracaoTela.getComboBoxDiaFim().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getComboBoxMudarCorPrimaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getComboBoxMudarCorPrimaria().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getComboBoxMudarCorSecundaria().setForeground(coresPrimarias.get(1));
			configuracaoTela.getComboBoxMudarCorSecundaria().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			configuracaoTela.getTableCustosBasicos().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTableCustosBasicos().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTableFuncionarios().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTableFuncionarios().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTableModalidades().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTableModalidades().setBorder(new LineBorder(coresPrimarias.get(1)));
			configuracaoTela.getTablePlanos().setForeground(coresPrimarias.get(1));
			configuracaoTela.getTablePlanos().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			configuracaoTela.getTableCustosBasicos().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableCustosBasicos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTableFuncionarios().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableFuncionarios().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTableModalidades().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTableModalidades().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			configuracaoTela.getTablePlanos().getTableHeader().setForeground(coresPrimarias.get(0));
			configuracaoTela.getTablePlanos().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnConfirmarAlteracoes().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnConfirmarAlteracoes().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnEditarCustos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarCustos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnEditarFuncionarios().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarFuncionarios().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getBtnEditarModalidades().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarModalidades().setBorder(new LineBorder(coresPrimarias.get(0)));

			configuracaoTela.getBtnEditarPlanos().setForeground(coresPrimarias.get(0));
			configuracaoTela.getBtnEditarPlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			configuracaoTela.getRoundedPanel().setBorder(new LineBorder(coresPrimarias.get(0)));
			
		}
		
		coresP.clear();
		for(Color coresUsuario: coresPrimarias) {
			coresP.add(coresUsuario);
		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			configuracaoTela.getTableCustosBasicos().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableFuncionarios().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableModalidades().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTablePlanos().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableCustosBasicos().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableFuncionarios().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableModalidades().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTablePlanos().setBackground(coresSecundarias.get(0));
			
			configuracaoTela.getTextFieldNomeUsuario().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTextFieldNomeAcademia().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTextFieldHoraAbrir().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTextFieldHoraFechar().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTextFieldCorAtualPrimaria().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTextFieldCorAtualSecundaria().setBackground(coresSecundarias.get(0));
			
			configuracaoTela.getComboBoxMudarCorPrimaria().setBackground(coresSecundarias.get(0));
			configuracaoTela.getComboBoxMudarCorSecundaria().setBackground(coresSecundarias.get(0));
			configuracaoTela.getComboBoxDiaComeco().setBackground(coresSecundarias.get(0));
			configuracaoTela.getComboBoxDiaFim().setBackground(coresSecundarias.get(0));
			configuracaoTela.getContentPane().setBackground(coresSecundarias.get(0));
			configuracaoTela.getRoundedPanel().setBackground(coresSecundarias.get(0));
			
			configuracaoTela.getBtnConfirmarAlteracoes().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarCustos().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarFuncionarios().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarModalidades().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarPlanos().setBackground(coresSecundarias.get(0));
			
			if(coresSecundarias.get(0).equals(new Color(255, 255, 255))) {
				configuracaoTela.getRoundedPanel().setBackground(new Color(200, 200, 200));				
			}
			
		}else {
			
			configuracaoTela.getTableCustosBasicos().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableFuncionarios().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableModalidades().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTablePlanos().getTableHeader().setBackground(coresSecundarias.get(0));
			configuracaoTela.getTableCustosBasicos().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTableFuncionarios().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTableModalidades().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTablePlanos().setBackground(coresSecundarias.get(2));
			
			configuracaoTela.getTextFieldNomeUsuario().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTextFieldNomeAcademia().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTextFieldHoraAbrir().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTextFieldHoraFechar().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTextFieldCorAtualPrimaria().setBackground(coresSecundarias.get(2));
			configuracaoTela.getTextFieldCorAtualSecundaria().setBackground(coresSecundarias.get(2));
			
			configuracaoTela.getComboBoxMudarCorPrimaria().setBackground(coresSecundarias.get(2));
			configuracaoTela.getComboBoxMudarCorSecundaria().setBackground(coresSecundarias.get(2));
			configuracaoTela.getComboBoxDiaComeco().setBackground(coresSecundarias.get(2));
			configuracaoTela.getComboBoxDiaFim().setBackground(coresSecundarias.get(2));
			
			configuracaoTela.getBtnConfirmarAlteracoes().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarCustos().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarFuncionarios().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarModalidades().setBackground(coresSecundarias.get(0));
			configuracaoTela.getBtnEditarPlanos().setBackground(coresSecundarias.get(0));

			configuracaoTela.getContentPane().setBackground(coresSecundarias.get(0));
			
			if(coresSecundarias.get(0).equals(new Color(255, 0, 255))) {
				configuracaoTela.getRoundedPanel().setBackground(coresSecundarias.get(2));
			}else {				
				configuracaoTela.getRoundedPanel().setBackground(coresSecundarias.get(coresSecundarias.size() - 1));
			}

		}

		if(coresSecundarias.get(0).equals(new Color(0, 0, 0)) || coresSecundarias.get(0).equals(new Color(0, 0, 255))) {
			configuracaoTela.getLblInformacaoCorS().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios-tela-escura.png")));
			configuracaoTela.getLblInformacaoCorP().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios-tela-escura.png")));				
		}else {
			configuracaoTela.getLblInformacaoCorS().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));
			configuracaoTela.getLblInformacaoCorP().setIcon(new ImageIcon(ConfiguracaoTela.class.getResource("/view/icones/informacoes-usuarios.png")));				
			
		}
		
		mudarComboBox(configuracaoTela.getComboBoxDiaComeco(), coresP, coresSecundarias);
		mudarComboBox(configuracaoTela.getComboBoxDiaFim(), coresP, coresSecundarias);
		mudarComboBox(configuracaoTela.getComboBoxMudarCorSecundaria(), coresP, coresSecundarias);
		mudarComboBox(configuracaoTela.getComboBoxMudarCorPrimaria(), coresP, coresSecundarias);
		
	}
	
	/** @implNote Altera o estilo do JComboBox */
	@SuppressWarnings("rawtypes")
	private void mudarComboBox(JComboBox combo, ArrayList<Color> coresPrimarias, ArrayList<Color> coresSecundarias) {
		
		combo.setUI(new BasicComboBoxUI() {
			@SuppressWarnings("serial")
			@Override
		    protected JButton createArrowButton() {
		        return new JButton() {
		        	@Override
		            public void paintComponent(Graphics g) {
		        		
		        		int arrowSize = combo.getHeight() / 3;
		        		
		                int arrowX = (getWidth() - arrowSize) / 2;
		                int arrowY = (getHeight() - arrowSize) / 2;
		                Polygon arrow = new Polygon();
		                arrow.addPoint(arrowX, arrowY);
		                arrow.addPoint(arrowX + arrowSize, arrowY);
		                arrow.addPoint(arrowX + arrowSize / 2, arrowY + arrowSize);

		                g.setColor(coresSecundarias.get(0));
		                g.fillRect(0, 0, getWidth(), getHeight());
		                
		                g.setColor(coresPrimarias.get(0));
		                g.fillPolygon(arrow);
		            }
		        	
		        	@Override
		        	public Border getBorder() {
		        		return BorderFactory.createLineBorder(coresSecundarias.get(0));
		        	}
		        	
		        };
		    }

		    @Override
		    public void configureArrowButton() {
		        super.configureArrowButton();
		        arrowButton.setFocusable(false);
		    }
		});
	}
}
