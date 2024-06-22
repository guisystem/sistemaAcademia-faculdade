package controller.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;

import model.ModelCPF;
import model.ModelEspecialidade;
import model.ModelFoto;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
import view.CadastroAlunoTela;
import view.CadastroProfessorTela;
import view.DadosAlunoTela;

public class CadastroProfessorHelper implements Helper{

	private final CadastroProfessorTela cadastroProfessorTela;

	public CadastroProfessorHelper(CadastroProfessorTela cadastroProfessorTela) {
		super();
		this.cadastroProfessorTela = cadastroProfessorTela; 
	}

	@SuppressWarnings("rawtypes")
	public void atualizarEspecialidades(List<ModelEspecialidade> especialidades) {
		@SuppressWarnings("unchecked")
		DefaultComboBoxModel<ModelEspecialidade> comboBoxEspecialidade = (DefaultComboBoxModel) cadastroProfessorTela.getComboBoxEspecialidade().getModel();
		
		for(ModelEspecialidade especialidade: especialidades) {
			comboBoxEspecialidade.addElement(especialidade); 
		}
		
	}
	
	public ModelProfessor obterModelo() {
		String nome = cadastroProfessorTela.getTextFieldNomeProfessor().getText();
		String cpf = cadastroProfessorTela.getTextFieldCpfProfessor().getText();
		String email = cadastroProfessorTela.getTextFieldEmailProfessor().getText();
		String horaEntrada = cadastroProfessorTela.getTextFieldHoraEntrada().getText();
		String horaSaida = cadastroProfessorTela.getTextFieldHoraSaida().getText();
		String contrato = cadastroProfessorTela.getTextFieldTempoContrato().getText();
		String sal = cadastroProfessorTela.getTextFieldSalarioProfessor().getText();
		if(sal.equals("") || sal.isEmpty()) {
			sal = "0";
		}
		
		if(contrato.equals("") || contrato.isEmpty()) {
			contrato = "0";
		}
		
		String tipoDeData = null;
		if(cadastroProfessorTela.getRdbtnAno().isSelected()) {
			tipoDeData = cadastroProfessorTela.getRdbtnAno().getText();
		}else{
			tipoDeData = cadastroProfessorTela.getRdbtnMes().getText();
			
		}
		
		float salario = 0;
		int tempo = 0;
		try {
			salario = Float.parseFloat(sal);
			tempo = Integer.parseInt(contrato);
			
		}catch (Exception e) {
			
		}

		ModelEspecialidade especialidade = obterEspecialidade();
		
		ModelProfessor professor = new ModelProfessor(nome, cpf, email, horaEntrada, horaSaida, salario, especialidade);
		professor.setTempoContrato(tempo);
		professor.setDataAdmissao(new Date());
		professor.setTipoDeDataContrato(tipoDeData);
		
		if(cadastroProfessorTela.getChckbxSegunda().isSelected()) {
			professor.adicionarDias("Segunda");
		}
		if(cadastroProfessorTela.getChckbxTerca().isSelected()) {
			professor.adicionarDias("Terça");
		}
		if(cadastroProfessorTela.getChckbxQuarta().isSelected()) {
			professor.adicionarDias("Quarta");
		}
		if(cadastroProfessorTela.getChckbxQuinta().isSelected()) {
			professor.adicionarDias("Quinta");
		}
		if(cadastroProfessorTela.getChckbxSexta().isSelected()) {
			professor.adicionarDias("Sexta");
		}
		if(cadastroProfessorTela.getChckbxSabado().isSelected()) {
			professor.adicionarDias("Sábado");
		}
		if(cadastroProfessorTela.getChckbxDomingo().isSelected()) {
			professor.adicionarDias("Domingo");
		}
		
		return professor;
	}

	public ModelEspecialidade obterEspecialidade() {
		return (ModelEspecialidade) cadastroProfessorTela.getComboBoxEspecialidade().getSelectedItem();
	}

	public ModelFoto obterFoto() {
		String caminhoImagem = cadastroProfessorTela.getCaminhoImagem();
		String nomeImagem = cadastroProfessorTela.getNomeImagemField().getText();
		
		if(caminhoImagem == null || nomeImagem == null) {
			ModelFoto fotoProfessor = new ModelFoto("", "");
			return fotoProfessor;
		}
		
		ModelFoto fotoProfessor = new ModelFoto(nomeImagem, caminhoImagem);
		
		return fotoProfessor;
	}

	public ModelRegistroProfessor obterModeloRegistro() {
		int id = obterModelo().getId();
		ModelProfessor professor = obterModelo();
		String ativo = "Sim";
		 
		ModelRegistroProfessor novoRegistroProfessor = new ModelRegistroProfessor(id, professor, ativo);
		return novoRegistroProfessor;
	}
	
	public boolean verificarDados() {
		if(cadastroProfessorTela.getTextFieldEmailProfessor().getText().trim().isEmpty() || cadastroProfessorTela.getTextFieldNomeProfessor().getText().trim().isEmpty() ||
				cadastroProfessorTela.getTextFieldSalarioProfessor().getText().trim().isEmpty() || cadastroProfessorTela.getTextFieldTempoContrato().getText().trim().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public boolean verificarTipoDataDeContrato() {
		if(cadastroProfessorTela.getRdbtnMes().isSelected() && cadastroProfessorTela.getRdbtnAno().isSelected()) {
			return false;
		}

		if(!cadastroProfessorTela.getRdbtnMes().isSelected() && !cadastroProfessorTela.getRdbtnAno().isSelected()) {
			return false;
		}
		
		return true;
	}
	

	@SuppressWarnings("unused")
	public boolean verificarSalario() {
		String salarioTela = cadastroProfessorTela.getTextFieldSalarioProfessor().getText().trim();
	
		float salario = 0;
		try {
			salario = Float.parseFloat(salarioTela);
		}catch (Exception e) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unused")
	public boolean verificarHora(String hora) {
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
	
	@SuppressWarnings("unused")
	public boolean verificarTempoContrato(String tempo) {
		int tempoContrato = 0;
		
		try {
			tempoContrato = Integer.parseInt(tempo);
        }catch(Exception e) {
        	return false;
        }
		
		return true;
	}

	public boolean verificarCPF(String cpf) {
		String cpfTexto = cpf;
		
		ModelCPF cpfVerificar = new ModelCPF(cpfTexto);
		if(cpfVerificar.isCPF()) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean dadosDiferentes(ModelUsuario usuario, ModelProfessor professor) {
		
		professor.setCpf(Criptografar.criptografar(professor.getCpf()));
    	for(ModelProfessor professores: usuario.getProfessores()) {
    		if(professores.getCpf() != null) {
	    		if(professores.getCpf().equals(professor.getCpf())) {
	    			cadastroProfessorTela.exibirMensagemErro("CPF de professor já cadastrado");
	    			return false;
	    		}
    		}
    	}
    	
    	professor.setEmail(Criptografar.criptografar(professor.getEmail()));
    	for(ModelProfessor professores: usuario.getProfessores()) {
    		if(professores.getEmail() != null) {
	    		if(professores.getEmail().equals(professor.getEmail())) {
	    			cadastroProfessorTela.exibirMensagemErro("Email de professor já cadastrado");
	    			return false;
	    		}

    		}
    	}

    	professor.setCpf(Criptografar.descriptografar(professor.getCpf()));
    	professor.setEmail(Criptografar.descriptografar(professor.getEmail()));
		
		return true;
	}

	public boolean verificarDias() {
		if(!cadastroProfessorTela.getChckbxSegunda().isSelected() && !cadastroProfessorTela.getChckbxTerca().isSelected() && 
				!cadastroProfessorTela.getChckbxQuarta().isSelected() && !cadastroProfessorTela.getChckbxQuinta().isSelected() && 
				!cadastroProfessorTela.getChckbxSexta().isSelected() && !cadastroProfessorTela.getChckbxSabado().isSelected() && 
				!cadastroProfessorTela.getChckbxDomingo().isSelected()) {
			return false;
		}
		return true;
	}

	public void limparTela(ModelUsuario usuario) {
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("");
		
		cadastroProfessorTela.getChckbxSegunda().setSelected(false);
		cadastroProfessorTela.getChckbxTerca().setSelected(false);
		cadastroProfessorTela.getChckbxQuarta().setSelected(false);
		cadastroProfessorTela.getChckbxQuinta().setSelected(false);
		cadastroProfessorTela.getChckbxSexta().setSelected(false);
		cadastroProfessorTela.getChckbxSabado().setSelected(false);
		cadastroProfessorTela.getChckbxDomingo().setSelected(false);
		
		cadastroProfessorTela.getRdbtnAno().setSelected(true);
		cadastroProfessorTela.getRdbtnMes().setSelected(false);
		
		cadastroProfessorTela.getComboBoxEspecialidade().setSelectedItem(cadastroProfessorTela.getComboBoxEspecialidade().getItemAt(0));
		
		if(usuario.getConfiguracao() == null) {
			cadastroProfessorTela.getLblAdicionarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-foto.png")));			
		}else {
			cadastroProfessorTela.getChckbxSegunda().setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			cadastroProfessorTela.getChckbxTerca().setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			cadastroProfessorTela.getChckbxQuarta().setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			cadastroProfessorTela.getChckbxQuinta().setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			cadastroProfessorTela.getChckbxSexta().setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			cadastroProfessorTela.getChckbxSabado().setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			cadastroProfessorTela.getChckbxDomingo().setForeground(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			if(usuario.getConfiguracao().getCorSecundariaColor(usuario).equals(new Color(255, 255, 255))) {
				cadastroProfessorTela.getLblAdicionarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-foto-fundobranco.png")));			
			}else {
				cadastroProfessorTela.getLblAdicionarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-foto.png")));			
			}
		}
		
	}

	@Override
	public void limparTela() {
		// TODO Auto-generated method stub
		
	}

	ArrayList<Color> coresP = new ArrayList<>();
	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {

			cadastroProfessorTela.getLblAdicionarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0)));
			cadastroProfessorTela.getLblCadastroDeProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblCpfProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblDataDeAdmisso().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblEmailProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblEspecialidade().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblHorarioEntrada().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblHorarioSaida().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblMatriculaProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblNomeProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblPeriodoTrabalho().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getLblTempoDeContrato().setForeground(coresPrimarias.get(0));
			
			cadastroProfessorTela.getTextFieldCpfProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getTextFieldCpfProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroProfessorTela.getTextFieldEmailProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getTextFieldEmailProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroProfessorTela.getTextFieldHoraEntrada().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getTextFieldHoraEntrada().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroProfessorTela.getTextFieldHoraSaida().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getTextFieldHoraSaida().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroProfessorTela.getTextFieldNomeProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getTextFieldNomeProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroProfessorTela.getTextFieldSalarioProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getTextFieldSalarioProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroProfessorTela.getTextFieldTempoContrato().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getTextFieldTempoContrato().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			cadastroProfessorTela.getChckbxDomingo().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getChckbxQuarta().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getChckbxQuinta().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getChckbxSabado().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getChckbxSegunda().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getChckbxSexta().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getChckbxTerca().setForeground(coresPrimarias.get(0));
			
			cadastroProfessorTela.getBtnCadastrarProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getBtnCadastrarProfessor().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0)));
			cadastroProfessorTela.getBtnCarregarFoto().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getBtnCarregarFoto().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			cadastroProfessorTela.getComboBoxEspecialidade().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getComboBoxEspecialidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			cadastroProfessorTela.getRdbtnAno().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getRdbtnMes().setForeground(coresPrimarias.get(0));
			
			coresP.add(coresPrimarias.get(0));
		}else {
			
			cadastroProfessorTela.getLblAdicionarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(1), coresPrimarias.get(3)));
			cadastroProfessorTela.getLblCadastroDeProfessor().setForeground(coresPrimarias.get(01));
			cadastroProfessorTela.getLblCpfProfessor().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblDataDeAdmisso().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblEmailProfessor().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblEspecialidade().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblHorarioEntrada().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblHorarioSaida().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblMatriculaProfessor().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblNomeProfessor().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblPeriodoTrabalho().setForeground(coresPrimarias.get(1));
			cadastroProfessorTela.getLblTempoDeContrato().setForeground(coresPrimarias.get(1));
			
			cadastroProfessorTela.getTextFieldCpfProfessor().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getTextFieldCpfProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroProfessorTela.getTextFieldEmailProfessor().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getTextFieldEmailProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroProfessorTela.getTextFieldHoraEntrada().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getTextFieldHoraEntrada().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroProfessorTela.getTextFieldHoraSaida().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getTextFieldHoraSaida().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroProfessorTela.getTextFieldNomeProfessor().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getTextFieldNomeProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroProfessorTela.getTextFieldSalarioProfessor().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getTextFieldSalarioProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroProfessorTela.getTextFieldTempoContrato().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getTextFieldTempoContrato().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			cadastroProfessorTela.getChckbxDomingo().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getChckbxQuarta().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getChckbxQuinta().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getChckbxSabado().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getChckbxSegunda().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getChckbxSexta().setForeground(coresPrimarias.get(2));
			cadastroProfessorTela.getChckbxTerca().setForeground(coresPrimarias.get(2));
			
			cadastroProfessorTela.getBtnCadastrarProfessor().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getBtnCadastrarProfessor().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(4), coresPrimarias.get(0)));
			cadastroProfessorTela.getBtnCarregarFoto().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getBtnCarregarFoto().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			cadastroProfessorTela.getComboBoxEspecialidade().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getComboBoxEspecialidade().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			cadastroProfessorTela.getRdbtnAno().setForeground(coresPrimarias.get(0));
			cadastroProfessorTela.getRdbtnMes().setForeground(coresPrimarias.get(0));
			
			coresP.add(coresPrimarias.get(0));
			
		}
		
		mouseEnterExit(cadastroProfessorTela.getTextFieldCpfProfessor(), coresPrimarias);
		mouseEnterExit(cadastroProfessorTela.getTextFieldEmailProfessor(), coresPrimarias);
		mouseEnterExit(cadastroProfessorTela.getTextFieldHoraEntrada(), coresPrimarias);
		mouseEnterExit(cadastroProfessorTela.getTextFieldHoraSaida(), coresPrimarias);
		mouseEnterExit(cadastroProfessorTela.getTextFieldNomeProfessor(), coresPrimarias);
		mouseEnterExit(cadastroProfessorTela.getTextFieldSalarioProfessor(), coresPrimarias);
		mouseEnterExit(cadastroProfessorTela.getTextFieldTempoContrato(), coresPrimarias);
		
		mouseEnterExitDias(cadastroProfessorTela.getChckbxDomingo(), coresPrimarias);
		mouseEnterExitDias(cadastroProfessorTela.getChckbxQuarta(), coresPrimarias);
		mouseEnterExitDias(cadastroProfessorTela.getChckbxQuinta(), coresPrimarias);
		mouseEnterExitDias(cadastroProfessorTela.getChckbxSabado(), coresPrimarias);
		mouseEnterExitDias(cadastroProfessorTela.getChckbxSegunda(), coresPrimarias);
		mouseEnterExitDias(cadastroProfessorTela.getChckbxSexta(), coresPrimarias);
		mouseEnterExitDias(cadastroProfessorTela.getChckbxTerca(), coresPrimarias);
		
		cadastroProfessorTela.getRdbtnAno().setIcon(new ColoredRadioButtonIcon(coresPrimarias.get(0), coresPrimarias.get(0)));
		cadastroProfessorTela.getRdbtnMes().setIcon(new ColoredRadioButtonIcon(coresPrimarias.get(0), coresPrimarias.get(0)));
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {

			cadastroProfessorTela.getTextFieldCpfProfessor().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getTextFieldEmailProfessor().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getTextFieldHoraEntrada().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getTextFieldHoraSaida().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getTextFieldNomeProfessor().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getTextFieldSalarioProfessor().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getTextFieldTempoContrato().setBackground(coresSecundarias.get(0));
			
			cadastroProfessorTela.getBtnCadastrarProfessor().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getBtnCarregarFoto().setBackground(coresSecundarias.get(0));
			
			cadastroProfessorTela.getComboBoxEspecialidade().setBackground(coresSecundarias.get(0));
			
			cadastroProfessorTela.getRdbtnAno().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getRdbtnMes().setBackground(coresSecundarias.get(0));
			
			cadastroProfessorTela.getContentPane().setBackground(coresSecundarias.get(0));
			
			if(coresSecundarias.get(0).equals(new Color(255, 255, 255))) {
				cadastroProfessorTela.getLblPainelCadastroProfessor().setIcon(new ImageIcon(CadastroAlunoTela.class.getResource("/view/imagens/painel-cadastro-professor-fundobranco.png")));
				cadastroProfessorTela.getLblAdicionarFoto().setIcon(new ImageIcon(CadastroAlunoTela.class.getResource("/view/icones/icone-foto-fundobranco.png")));
			}
			
		}else {
			
			cadastroProfessorTela.getLblAdicionarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresSecundarias.get(1), coresSecundarias.get(2)));
			
			cadastroProfessorTela.getTextFieldCpfProfessor().setBackground(coresSecundarias.get(2));
			cadastroProfessorTela.getTextFieldEmailProfessor().setBackground(coresSecundarias.get(2));
			cadastroProfessorTela.getTextFieldHoraEntrada().setBackground(coresSecundarias.get(2));
			cadastroProfessorTela.getTextFieldHoraSaida().setBackground(coresSecundarias.get(2));
			cadastroProfessorTela.getTextFieldNomeProfessor().setBackground(coresSecundarias.get(2));
			cadastroProfessorTela.getTextFieldSalarioProfessor().setBackground(coresSecundarias.get(2));
			cadastroProfessorTela.getTextFieldTempoContrato().setBackground(coresSecundarias.get(2));
			
			cadastroProfessorTela.getBtnCadastrarProfessor().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getBtnCarregarFoto().setBackground(coresSecundarias.get(0));
			
			cadastroProfessorTela.getComboBoxEspecialidade().setBackground(coresSecundarias.get(1));
			
			cadastroProfessorTela.getRdbtnAno().setBackground(coresSecundarias.get(0));
			cadastroProfessorTela.getRdbtnMes().setBackground(coresSecundarias.get(0));
			
			cadastroProfessorTela.getContentPane().setBackground(coresSecundarias.get(0));
			
		}
	
		mudarComboBox(cadastroProfessorTela.getComboBoxEspecialidade(), coresP, coresSecundarias);
		
		cadastroProfessorTela.getChckbxDomingo().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		cadastroProfessorTela.getChckbxDomingo().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		cadastroProfessorTela.getChckbxSegunda().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		cadastroProfessorTela.getChckbxSegunda().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		cadastroProfessorTela.getChckbxTerca().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		cadastroProfessorTela.getChckbxTerca().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		cadastroProfessorTela.getChckbxQuarta().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		cadastroProfessorTela.getChckbxQuarta().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		cadastroProfessorTela.getChckbxQuinta().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		cadastroProfessorTela.getChckbxQuinta().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		cadastroProfessorTela.getChckbxSexta().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		cadastroProfessorTela.getChckbxSexta().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		cadastroProfessorTela.getChckbxSabado().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		cadastroProfessorTela.getChckbxSabado().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
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

	/** @implNote Método para dar um efeito ao passar o mouse por cima dos JtextFields */
	private void mouseEnterExit(JTextField campo, ArrayList<Color> coresPrimarias) {
	
		campo.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(coresPrimarias.size() == 1) {
					campo.setBorder(new LineBorder(coresPrimarias.get(0)));					
				}else {
					campo.setBorder(new LineBorder(coresPrimarias.get(1)));
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(coresPrimarias.size() == 1) {
					campo.setBorder(new LineBorder(new Color(125, 125, 125)));					
				}else {
					if(coresPrimarias.get(0).equals(new Color(255, 0, 255))) {
						campo.setBorder(new LineBorder(coresPrimarias.get(4)));
					}else {
						campo.setBorder(new LineBorder(coresPrimarias.get(0)));
					}
				}
				
			}
		});
	}

	/** @implNote Método para dar um efeito ao passar o mouse por cima dos JCheckBox */
	private void mouseEnterExitDias(JCheckBox checkBox, ArrayList<Color> coresPrimarias) {
		
		checkBox.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				if(coresPrimarias.size() == 1) {
					if(checkBox.isSelected()) {
						
					}else {
						checkBox.setForeground(coresPrimarias.get(0));					
					}
				}else {
					if(checkBox.isSelected()) {
					
					}else {
						checkBox.setForeground(coresPrimarias.get(2));
					}
				}
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				if(coresPrimarias.size() == 1) {
					checkBox.setForeground(new Color(125, 125, 125));					
				}else {
					if(coresPrimarias.get(0).equals(new Color(255, 0, 255))) {
						checkBox.setForeground(coresPrimarias.get(4));
					}else {
						checkBox.setForeground(coresPrimarias.get(0));
					}
				}
				
			}
		});
	}
	
	/** @implNote Altera o estilo do JCheckBox */
	class CustomCheckBoxIcon implements Icon {

	    private Color borderColor;
	    private Color fillColor;

	    public CustomCheckBoxIcon(Color borderColor, Color fillColor) {
	        this.borderColor = borderColor;
	        this.fillColor = fillColor;
	    }

	    @Override
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	        Graphics2D g2d = (Graphics2D) g.create();

	        g2d.setColor(borderColor);
	        g2d.drawRect(x, y, getIconWidth() - 1, getIconHeight() - 1);

	        AbstractButton button = (AbstractButton) c;
	        ButtonModel model = button.getModel();
	        if (model.isSelected()) {
	            g2d.setColor(fillColor);
	            g2d.fillRect(x + 1, y + 1, getIconWidth() - 2, getIconHeight() - 2);

	            g2d.setColor(borderColor);
	            int[] xPoints = { x + 4, x + 5, x + 11 };
	            int[] yPoints = { y + 7, y + 11, y + 5 };
	            g2d.drawPolyline(xPoints, yPoints, 3);
	        }

	        g2d.dispose();
	    }

	    @Override
	    public int getIconWidth() {
	        return 16;
	    }

	    @Override
	    public int getIconHeight() {
	        return 16;
	    }
	}
	
	/** @implNote Altera o estilo JRadioButton */
	class ColoredRadioButtonIcon implements Icon {
	    private final int size = 16;
	    private final Color fillColor;
	    private final Color borderColor;

	    public ColoredRadioButtonIcon(Color fillColor, Color borderColor) {
	        this.fillColor = fillColor;
	        this.borderColor = borderColor;
	    }

	    @Override
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        g2.setColor(borderColor);
	        g2.drawArc(x, y, size, size, size * 24, size * 24);

	        if (((AbstractButton) c).isSelected()) {
	            g2.setColor(fillColor);
	            g2.fillOval(x + 4, y + 4, size - 8, size - 8);
	        }
	    }

	    @Override
	    public int getIconWidth() {
	        return size;
	    }

	    @Override
	    public int getIconHeight() {
	        return size;
	    }
	}

}
