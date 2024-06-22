package controller.helper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

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
import javax.swing.table.DefaultTableModel;

import model.ModelCPF;
import model.ModelEspecialidade;
import model.ModelFoto;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
import view.DadosProfessorTela;

public class DadosProfessorHelper {

	private final DadosProfessorTela dadosProfessorTela;

	public DadosProfessorHelper(DadosProfessorTela dadosProfessorTela) {
		super();
		this.dadosProfessorTela = dadosProfessorTela;
	}

	public void preencherDadosProfessor(ModelUsuario usuario, ModelRegistroProfessor registroProfessor) {
		preencherFoto(registroProfessor, usuario);
		atualizarAtivo(registroProfessor);
		preencherCamposDados(registroProfessor);
		preencherTabelaDados(registroProfessor);
		preencherDiasTrabalho(registroProfessor);
		preencherExcluirEspecialidadeDados(registroProfessor);
		preencherAdicionarEspecialidadeDados(registroProfessor, usuario);
		verificarDias(registroProfessor);
		
	}
	
	private void preencherFoto(ModelRegistroProfessor registroProfessor, ModelUsuario usuario) {
		if(registroProfessor.getProfessor().getFoto() == null) {
			
		}else {
			dadosProfessorTela.getNomeImagemField().setText(registroProfessor.getProfessor().getFoto().getNomeImagemField());
			
			ImageIcon imagemIcon = new ImageIcon(registroProfessor.getProfessor().getFoto().getCaminhoImagem());
			Image imagem = imagemIcon.getImage().getScaledInstance(dadosProfessorTela.getLblMudarFoto().getWidth(), dadosProfessorTela.getLblMudarFoto().getHeight(), Image.SCALE_SMOOTH);
			dadosProfessorTela.getLblMudarFoto().setIcon(new ImageIcon(imagem));
			
			if(dadosProfessorTela.getLblMudarFoto().getIcon().getIconHeight() == -1 || registroProfessor.getProfessor().getFoto().getCaminhoImagem().equals("")) {
				if(!dadosProfessorTela.getLblMudarFoto().getText().equals(registroProfessor.getProfessor().getFoto().getCaminhoImagem())) { 
					corrigirProblemas(registroProfessor);
				}

				if(usuario.getConfiguracao().getCorSecundariaColor(usuario).equals(new Color(255, 255, 255))) {
					dadosProfessorTela.getLblMudarFoto().setIcon(new ImageIcon(DadosProfessorTela.class.getResource("/view/icones/icone-mudar-foto-fundobranco.png")));
				
				}else {
					
					dadosProfessorTela.getLblMudarFoto().setIcon(new ImageIcon(DadosProfessorTela.class.getResource("/view/icones/icone-mudar-foto.png")));
				}
			}
		}
		
	}

	private void corrigirProblemas(ModelRegistroProfessor registroProfessor) {
		
		if(dadosProfessorTela.getLblMudarFoto().getIcon().getIconHeight() == -1) {
			if(!dadosProfessorTela.getLblMudarFoto().getText().equals(registroProfessor.getProfessor().getFoto().getCaminhoImagem())) { 
				dadosProfessorTela.getContentPane().addMouseListener(new MouseAdapter() {
					private String resposta = "Sim";
					@Override
					public void mouseEntered(MouseEvent e) {
						if(resposta.equals("Sim")) {
							if(dadosProfessorTela.exibirMensagemDecisao("Esse professor tinha uma foto porém o sistema não conseguiu encontrar-lá! Veja abaixo possíveis problemas \r\n"
									+ "que podem ter causado isso.\r\n\r\n"
									+ "1 - A foto foi excluida.\r\n"
									+ "2 - A foto foi movida para uma pasta diferente da original.\r\n"
									+ "      O caminho original para a foto é: " + registroProfessor.getProfessor().getFoto().getCaminhoImagem() + "\r\n"
									+ "3 - O nome de alguma pasta desse caminho ou da foto foi alterado.\r\n"
									+ "      O nome original da foto é: " + registroProfessor.getProfessor().getFoto().getNomeImagemField() + "\r\n\r\n"
									+ "Deseja que a foto desse professor seja removida? Se sim, clique em \"Sim\" e logo após em \"Confirmar\"")){
								registroProfessor.getProfessor().getFoto().setCaminhoImagem("");
								registroProfessor.getProfessor().getFoto().setNomeImagemField("");
								resposta = "Não";
							}else {
								resposta = "Não";
							}
						}
					}
				});
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void atualizarAtivo(ModelRegistroProfessor registroProfessor) {

		DefaultComboBoxModel<String> comboBoxAtivo = (DefaultComboBoxModel<String>) dadosProfessorTela.getComboBoxAtivo().getModel();
		
		comboBoxAtivo.addElement(registroProfessor.getAtivo());
		if(comboBoxAtivo.getElementAt(0).equalsIgnoreCase("Sim")) {
			comboBoxAtivo.addElement("Não");
		}else {
			comboBoxAtivo.addElement("Sim");
		}

	}
	
	@SuppressWarnings("unchecked")
	private void preencherCamposDados(ModelRegistroProfessor registroProfessor) {

		dadosProfessorTela.getTextFieldNomeProfessor().setText(registroProfessor.getProfessor().getNome());
		dadosProfessorTela.getTextFieldDataAdmissao().setText(registroProfessor.getProfessor().getDataAdmissaoFormatada());
		dadosProfessorTela.getTextFieldHoraEntrada().setText(registroProfessor.getProfessor().getHoraEntradaFormatada());
		dadosProfessorTela.getTextFieldHoraSaida().setText(registroProfessor.getProfessor().getHoraSaidaFormatada());
		dadosProfessorTela.getTextFieldSalarioProfessor().setText(registroProfessor.getProfessor().getSalario() + "");
		dadosProfessorTela.getTextFieldContrato().setText(registroProfessor.getProfessor().getTempoContrato() + "");
		
		String emailDescriptografado = Criptografar.descriptografar(registroProfessor.getProfessor().getEmail());
		dadosProfessorTela.getTextFieldEmailProfessor().setText(emailDescriptografado);
		
		String cpfDescriptografado = Criptografar.descriptografar(registroProfessor.getProfessor().getCpf());
		dadosProfessorTela.getTextFieldCpfProfessor().setText(cpfDescriptografado);
		
		DefaultComboBoxModel<String> comboBoxAtivo = (DefaultComboBoxModel<String>) dadosProfessorTela.getComboBoxAtivo().getModel();
		
		if(comboBoxAtivo.getElementAt(0).equals("Sim")) {
			dadosProfessorTela.getTextFieldDataAdmissao().setEditable(false);
		}
		
		dadosProfessorTela.getComboBoxAtivo().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				atualizarData(comboBoxAtivo);
			}
		});
		
		if(registroProfessor.getProfessor().getTipoDeDataContrato().equals("Mês")) {
			dadosProfessorTela.getRdbtnMes().setSelected(true);
		}else {
			dadosProfessorTela.getRdbtnAno().setSelected(true);
		}
	}

	private void atualizarData(DefaultComboBoxModel<String> comboBoxAtivo) {
		
		if(comboBoxAtivo.getSelectedItem().equals("Sim")) {
			dadosProfessorTela.getTextFieldDataAdmissao().setEditable(false);
		}
		if(comboBoxAtivo.getSelectedItem().equals("Não")) {
			dadosProfessorTela.getTextFieldDataAdmissao().setEditable(true);			
		}
		
	}

	public void preencherTabelaDados(ModelRegistroProfessor registroProfessor) {

		DefaultTableModel tableEspecialidades = (DefaultTableModel) dadosProfessorTela.getTableEspecialidadeProfessor().getModel();
		tableEspecialidades.setNumRows(0);
		
		for(ModelEspecialidade especialidade: registroProfessor.getProfessor().getEspecialidades()) {
			tableEspecialidades.addRow(new Object[] {
					especialidade.getNome()
			});
		}
	}
		

	private void preencherDiasTrabalho(ModelRegistroProfessor registroProfessor) {
		for(DayOfWeek dias: registroProfessor.getProfessor().getDiasDeTrabalho()) {
			if(dias.equals(DayOfWeek.MONDAY)) {
				dadosProfessorTela.getChckbxSegunda().setSelected(true);
			}
			if(dias.equals(DayOfWeek.TUESDAY)) {
				dadosProfessorTela.getChckbxTerca().setSelected(true);
			}
			if(dias.equals(DayOfWeek.WEDNESDAY)) {
				dadosProfessorTela.getChckbxQuarta().setSelected(true);
			}
			if(dias.equals(DayOfWeek.THURSDAY)) {
				dadosProfessorTela.getChckbxQuinta().setSelected(true);
			}
			if(dias.equals(DayOfWeek.FRIDAY)) {
				dadosProfessorTela.getChckbxSexta().setSelected(true);
			}
			if(dias.equals(DayOfWeek.SATURDAY)) {
				dadosProfessorTela.getChckbxSabado().setSelected(true);
			}
			if(dias.equals(DayOfWeek.SUNDAY)) {
				dadosProfessorTela.getChckbxDomingo().setSelected(true);
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private void preencherExcluirEspecialidadeDados(ModelRegistroProfessor registroProfessor) {

		DefaultComboBoxModel<ModelEspecialidade> comboBoxExcluirEspecialidade = (DefaultComboBoxModel<ModelEspecialidade>) dadosProfessorTela.getComboBoxExcluirEspecialidade().getModel();
		
		for(ModelEspecialidade especialidade: registroProfessor.getProfessor().getEspecialidades()) {
			comboBoxExcluirEspecialidade.addElement(especialidade);
		}
		
	}

	@SuppressWarnings("unchecked")
	private void preencherAdicionarEspecialidadeDados(ModelRegistroProfessor registroProfessor, ModelUsuario usuario) {
		DefaultComboBoxModel<ModelEspecialidade> comboBoxAdicionarEspecialidade = (DefaultComboBoxModel<ModelEspecialidade>) dadosProfessorTela.getComboBoxAdicionarEspecialidade().getModel();
		
		for(ModelEspecialidade especialidade: usuario.getEspecialidades()) {
			comboBoxAdicionarEspecialidade.addElement(especialidade);
		}
		
		for(int i = 0; i < comboBoxAdicionarEspecialidade.getSize(); i++) {
			for(int j = 0; j < registroProfessor.getProfessor().getEspecialidades().size(); j++) {
				if(comboBoxAdicionarEspecialidade.getElementAt(i).getId() == registroProfessor.getProfessor().getEspecialidades().get(j).getId()) {
					comboBoxAdicionarEspecialidade.removeElement(comboBoxAdicionarEspecialidade.getElementAt(i));
				}
			}
		}
		
	}
	
	public boolean compararDados(ModelRegistroProfessor registroProfessor, ArrayList<ModelEspecialidade> especialidades) {
		
		String salario = registroProfessor.getProfessor().getSalario() + "";
		String tempoContrato = registroProfessor.getProfessor().getTempoContrato() + "";
		
		String cpfDescriptografado = Criptografar.descriptografar(registroProfessor.getProfessor().getCpf());
		String emailDescriptografado = Criptografar.descriptografar(registroProfessor.getProfessor().getEmail());
		
		if(!registroProfessor.getProfessor().getNome().equals(dadosProfessorTela.getTextFieldNomeProfessor().getText().trim()) || 
			!cpfDescriptografado.equals(dadosProfessorTela.getTextFieldCpfProfessor().getText().trim()) || 
			!emailDescriptografado.equals(dadosProfessorTela.getTextFieldEmailProfessor().getText().trim()) || 
			!registroProfessor.getProfessor().getDataAdmissaoFormatada().equals(dadosProfessorTela.getTextFieldDataAdmissao().getText()) || 
			!registroProfessor.getProfessor().getHoraEntradaFormatada().equals(dadosProfessorTela.getTextFieldHoraEntrada().getText()) || 
			!registroProfessor.getProfessor().getHoraSaidaFormatada().equals(dadosProfessorTela.getTextFieldHoraSaida().getText()) || 
			!registroProfessor.getAtivo().equals(dadosProfessorTela.getComboBoxAtivo().getSelectedItem()) ||
			!tempoContrato.equals(dadosProfessorTela.getTextFieldContrato().getText().trim()) ||
			!salario.equals(dadosProfessorTela.getTextFieldSalarioProfessor().getText().trim()) || 
			!tipoDeDataEscolhido().equals(registroProfessor.getProfessor().getTipoDeDataContrato()) ||
			!verificarDias(registroProfessor) ||
			!compararEspecialidades(especialidades, registroProfessor)){
			
				
				return false;
				
		}else {
			
			if(registroProfessor.getProfessor().getFoto() != null) {
				if(!registroProfessor.getProfessor().getFoto().getNomeImagemField().equals(dadosProfessorTela.getNomeImagemField().getText())){
					return false;
				}else {
					return true;					
				}
			}else {
				if(dadosProfessorTela.getCaminhoImagem() != null) {
					return false;
				}
				return true;
			}
		}
	}

	private String tipoDeDataEscolhido() {
		if(dadosProfessorTela.getRdbtnMes().isSelected() && dadosProfessorTela.getRdbtnAno().isSelected()) {
			return "";
		}

		if(!dadosProfessorTela.getRdbtnMes().isSelected() && !dadosProfessorTela.getRdbtnAno().isSelected()) {
			return "";
		}
		
		if(dadosProfessorTela.getRdbtnMes().isSelected()) {
			return "Mês";
		}else if(dadosProfessorTela.getRdbtnAno().isSelected()) {
			return "Ano";
		}
		
		return "";
	}
	
	public boolean verificarDias(ModelRegistroProfessor professor) {
		ArrayList<String> dias = professor.getProfessor().getDiaFormatado();

		int cont = 0;
		int contSelecionados = 0;
		
		if(dadosProfessorTela.getChckbxSegunda().isSelected()) {
			contSelecionados += 1;
			for(String dia: dias) {
				if(dia.equals("Segunda")) {
					cont += 1;
				}
			}
		}
		if(dadosProfessorTela.getChckbxTerca().isSelected()) {
			contSelecionados += 1;
			for(String dia: dias) {
				if(dia.equals("Terça")) {
					cont += 1;
				}
			}
		}
		if(dadosProfessorTela.getChckbxQuarta().isSelected()) {
			contSelecionados += 1;
			for(String dia: dias) {
				if(dia.equals("Quarta")) {
					cont += 1;
				}
			}
		}	
		if(dadosProfessorTela.getChckbxQuinta().isSelected()) {
			contSelecionados += 1;
			for(String dia: dias) {
				if(dia.equals("Quinta")) {
					cont += 1;
				}
			}
		}
		if(dadosProfessorTela.getChckbxSexta().isSelected()) {
			contSelecionados += 1;
			for(String dia: dias) {
				if(dia.equals("Sexta")) {
					cont += 1;
				}
			}
		}
		if(dadosProfessorTela.getChckbxSabado().isSelected()) {
			contSelecionados += 1;
			for(String dia: dias) {
				if(dia.equals("Sábado")) {
					cont += 1;
				}
			}
		}
		if(dadosProfessorTela.getChckbxDomingo().isSelected()) {
			contSelecionados += 1;
			for(String dia: dias) {
				if(dia.equals("Domingo")) {
					cont += 1;
				}
			}
		}
		
		return cont != professor.getProfessor().getDiaFormatado().size() ? false : contSelecionados != professor.getProfessor().getDiaFormatado().size() ? 
				false : contSelecionados != cont ? false : true;
	}

	protected boolean compararEspecialidades(ArrayList<ModelEspecialidade> especialidades, ModelRegistroProfessor registroProfessor) {
		
		int cont = 0;
		
		for(ModelEspecialidade especialidadeProfessor: registroProfessor.getProfessor().getEspecialidades()) {
			for(ModelEspecialidade modalidadeCombo: especialidades) {
				if(especialidadeProfessor.getId() == modalidadeCombo.getId()) {
					cont += 1;
				}
			}
		}
		
		return cont != registroProfessor.getProfessor().getEspecialidades().size() ? false : true;
	}
	
	public ModelEspecialidade obterEspecialidade() {
		return (ModelEspecialidade) dadosProfessorTela.getComboBoxExcluirEspecialidade().getSelectedItem();
	}

	public ModelProfessor obterModelo(ModelRegistroProfessor registroProfessor) {
		registroProfessor.getProfessor().setId(registroProfessor.getId());
		registroProfessor.getProfessor().setNome(dadosProfessorTela.getTextFieldNomeProfessor().getText()); 
		registroProfessor.getProfessor().setCpf(dadosProfessorTela.getTextFieldCpfProfessor().getText()); 
		registroProfessor.getProfessor().setEmail(dadosProfessorTela.getTextFieldEmailProfessor().getText());
		registroProfessor.getProfessor().setHoraEntradaFormatada(dadosProfessorTela.getTextFieldHoraEntrada().getText()); 
		registroProfessor.getProfessor().setHoraSaidaFormatada(dadosProfessorTela.getTextFieldHoraSaida().getText()); 
		
		String tempoContrato = dadosProfessorTela.getTextFieldContrato().getText();
		String sal = dadosProfessorTela.getTextFieldSalarioProfessor().getText();
		String data = dadosProfessorTela.getTextFieldDataAdmissao().getText();
		
		float salario = 0; 
		int tempo = 0;
		
		try {
			salario = Float.parseFloat(sal);	
			tempo = Integer.parseInt(tempoContrato);
		}catch(NumberFormatException e) {
			
		}
		
		registroProfessor.getProfessor().setTempoContrato(tempo);
		registroProfessor.getProfessor().setSalario(salario);
		
		try {
			Date dataAdmissao = new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(data);
			registroProfessor.getProfessor().setDataAdmissao(dataAdmissao);
		} catch (ParseException e) {
			
		}
		
		registroProfessor.getProfessor().getDiasDeTrabalho().clear();
		
		if(dadosProfessorTela.getChckbxSegunda().isSelected()) {
			registroProfessor.getProfessor().adicionarDias("Segunda");
		}
		if(dadosProfessorTela.getChckbxTerca().isSelected()) {
			registroProfessor.getProfessor().adicionarDias("Terça");
		}
		if(dadosProfessorTela.getChckbxQuarta().isSelected()) {
			registroProfessor.getProfessor().adicionarDias("Quarta");
		}
		if(dadosProfessorTela.getChckbxQuinta().isSelected()) {
			registroProfessor.getProfessor().adicionarDias("Quinta");
		}
		if(dadosProfessorTela.getChckbxSexta().isSelected()) {
			registroProfessor.getProfessor().adicionarDias("Sexta");
		}
		if(dadosProfessorTela.getChckbxSabado().isSelected()) {
			registroProfessor.getProfessor().adicionarDias("Sábado");
		}
		if(dadosProfessorTela.getChckbxDomingo().isSelected()) {
			registroProfessor.getProfessor().adicionarDias("Domingo");
		}
		
		return registroProfessor.getProfessor();
	}
	
	public ModelFoto obterFoto(ModelRegistroProfessor registroProfessor) {
		
		if(dadosProfessorTela.getCaminhoImagem() == null || dadosProfessorTela.getNomeImagemField().getText() == null) {
			dadosProfessorTela.setCaminhoImagem(registroProfessor.getProfessor().getFoto().getCaminhoImagem());
			dadosProfessorTela.getNomeImagemField().setText(registroProfessor.getProfessor().getFoto().getNomeImagemField());			
		}
		
		if(registroProfessor.getProfessor().getFoto() == null) {
			String caminhoImagem = dadosProfessorTela.getCaminhoImagem();
			String nomeImagem = dadosProfessorTela.getNomeImagemField().getText();
			
			ModelFoto fotoProfessor = new ModelFoto(nomeImagem, caminhoImagem);
			
			registroProfessor.getProfessor().setFoto(fotoProfessor);
		}
		
		registroProfessor.getProfessor().getFoto().setCaminhoImagem(dadosProfessorTela.getCaminhoImagem());
		registroProfessor.getProfessor().getFoto().setNomeImagemField(dadosProfessorTela.getNomeImagemField().getText());
		
		return registroProfessor.getProfessor().getFoto();
	}

	public ModelRegistroProfessor obterModeloRegistro(ModelRegistroProfessor registroProfessor) {

		registroProfessor.setId(obterModelo(registroProfessor).getId());
		registroProfessor.setProfessor(obterModelo(registroProfessor));
		registroProfessor.setAtivo((String) dadosProfessorTela.getComboBoxAtivo().getSelectedItem()); 
		
		return registroProfessor;
	}

	public boolean verificarDados() {
		if(dadosProfessorTela.getTextFieldEmailProfessor().getText().trim().isEmpty() || dadosProfessorTela.getTextFieldNomeProfessor().getText().trim().isEmpty() ||
				dadosProfessorTela.getTextFieldSalarioProfessor().getText().trim().isEmpty() || dadosProfessorTela.getTextFieldContrato().getText().trim().isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public boolean verificarTipoDataDeContrato() {
		if(dadosProfessorTela.getRdbtnMes().isSelected() && dadosProfessorTela.getRdbtnAno().isSelected()) {
			return false;
		}

		if(!dadosProfessorTela.getRdbtnMes().isSelected() && !dadosProfessorTela.getRdbtnAno().isSelected()) {
			return false;
		}
		
		return true;
	}

	@SuppressWarnings("unused")
	public boolean verificarSalario() {
		String sal = dadosProfessorTela.getTextFieldSalarioProfessor().getText();

		float salario = 0;
		
		try {
			salario = Float.parseFloat(sal);			
		}catch(NumberFormatException e) {
			return false;
		}
		
		return true;
	}

	public boolean verificarData(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		LocalDateTime horaVerificada = null;
		LocalDateTime horaAgora = LocalDateTime.now();
		
        if(data == null || data.equals("  :  ")) {
        	return false;
        }
        
        try {
        	horaVerificada = LocalDateTime.parse(data, dtf);
		}catch(DateTimeParseException e) {
			return false;
		}
        
        return horaVerificada.isAfter(horaAgora) ? false : true;
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
        }catch(Exception e) {
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
	
	public boolean validarEmailECpf(ModelUsuario usuario, ModelRegistroProfessor registroProfessor, String cpf,
			String email) {
		String cpfCriptografado = Criptografar.criptografar(cpf);
    	for(ModelProfessor professores: usuario.getProfessores()) {
    		if(professores.getCpf().equals(cpfCriptografado)) {
    			String cpfDescriptografado = Criptografar.descriptografar(registroProfessor.getProfessor().getCpf());
    			if(!cpfDescriptografado.equals(cpf)) {
    				dadosProfessorTela.exibirMensagemErro("CPF de professor já cadastrado");
    				return false;
    			}
    		}
    	}
    	
    	String emailCriptografado = Criptografar.criptografar(email);
    	for(ModelProfessor professores: usuario.getProfessores()) {
    		if(professores.getEmail().equals(emailCriptografado)) {
    			String emailDescriptografado = Criptografar.descriptografar(registroProfessor.getProfessor().getEmail());
    			if(!emailDescriptografado.equals(email)) {
    				dadosProfessorTela.exibirMensagemErro("Email de professor já cadastrado");
    				return false;
    			}
    		}
    	}

    	return true;
	}

	public boolean verificarDiasSelecionados() {
		if(!dadosProfessorTela.getChckbxSegunda().isSelected() && !dadosProfessorTela.getChckbxTerca().isSelected() && 
				!dadosProfessorTela.getChckbxQuarta().isSelected() && !dadosProfessorTela.getChckbxQuinta().isSelected() && 
				!dadosProfessorTela.getChckbxSexta().isSelected() && !dadosProfessorTela.getChckbxSabado().isSelected() && 
				!dadosProfessorTela.getChckbxDomingo().isSelected()) {
			return false;
		}
		return true;
	}

	ArrayList<Color> coresP = new ArrayList<>();
	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {

			dadosProfessorTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0)));
			dadosProfessorTela.getLblDadosProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblAtivoProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblCpfProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblContrato().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblEmailProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblHorarioEntradaProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblHorarioSaidaProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblDataDeAdmissaoProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblNomeProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblDiasDeTrabalho().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblEspecialidadeProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblSalarioProfessor().setForeground(coresPrimarias.get(0));
			
			dadosProfessorTela.getTextFieldCpfProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldCpfProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTextFieldEmailProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldEmailProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTextFieldHoraEntrada().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldHoraEntrada().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTextFieldHoraSaida().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldHoraSaida().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTextFieldNomeProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldNomeProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTextFieldSalarioProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldSalarioProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTextFieldContrato().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldContrato().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTextFieldDataAdmissao().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTextFieldDataAdmissao().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosProfessorTela.getChckbxDomingo().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getChckbxQuarta().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getChckbxQuinta().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getChckbxSabado().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getChckbxSegunda().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getChckbxSexta().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getChckbxTerca().setForeground(coresPrimarias.get(0));
			
			dadosProfessorTela.getBtnAdicionarEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnAdicionarEspecialidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getBtnConfirmar().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnConfirmar().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0)));
			dadosProfessorTela.getBtnCancelar().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnCancelar().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getBtnExcluirEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnExcluirEspecialidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getBtnMudarFotoProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnMudarFotoProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosProfessorTela.getComboBoxAdicionarEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getComboBoxAdicionarEspecialidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getComboBoxExcluirEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getComboBoxExcluirEspecialidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getComboBoxAtivo().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getComboBoxAtivo().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosProfessorTela.getTableEspecialidadeProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTableEspecialidadeProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getTableEspecialidadeProfessor().getTableHeader().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTableEspecialidadeProfessor().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosProfessorTela.getRdbtnAno().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getRdbtnMes().setForeground(coresPrimarias.get(0));
			
			coresP.add(coresPrimarias.get(0));
		}else {
			
			dadosProfessorTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(1), coresPrimarias.get(3)));
			dadosProfessorTela.getLblDadosProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getLblAtivoProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblCpfProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblContrato().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblEmailProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblHorarioEntradaProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblHorarioSaidaProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblDataDeAdmissaoProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblNomeProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblDiasDeTrabalho().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblEspecialidadeProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getLblSalarioProfessor().setForeground(coresPrimarias.get(1));
			
			dadosProfessorTela.getTextFieldCpfProfessor().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldCpfProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getTextFieldEmailProfessor().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldEmailProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getTextFieldHoraEntrada().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldHoraEntrada().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getTextFieldHoraSaida().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldHoraSaida().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getTextFieldNomeProfessor().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldNomeProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getTextFieldSalarioProfessor().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldSalarioProfessor().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getTextFieldContrato().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldContrato().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getTextFieldDataAdmissao().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getTextFieldDataAdmissao().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			dadosProfessorTela.getChckbxDomingo().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getChckbxQuarta().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getChckbxQuinta().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getChckbxSabado().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getChckbxSegunda().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getChckbxSexta().setForeground(coresPrimarias.get(2));
			dadosProfessorTela.getChckbxTerca().setForeground(coresPrimarias.get(2));
			
			dadosProfessorTela.getBtnAdicionarEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnAdicionarEspecialidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getBtnConfirmar().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnConfirmar().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(4), coresPrimarias.get(0)));
			dadosProfessorTela.getBtnCancelar().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnCancelar().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getBtnExcluirEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnExcluirEspecialidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosProfessorTela.getBtnMudarFotoProfessor().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getBtnMudarFotoProfessor().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosProfessorTela.getComboBoxAdicionarEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getComboBoxAdicionarEspecialidade().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getComboBoxExcluirEspecialidade().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getComboBoxExcluirEspecialidade().setBorder(new LineBorder(coresPrimarias.get(2)));
			dadosProfessorTela.getComboBoxAtivo().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getComboBoxAtivo().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			dadosProfessorTela.getTableEspecialidadeProfessor().setForeground(coresPrimarias.get(1));
			dadosProfessorTela.getTableEspecialidadeProfessor().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosProfessorTela.getTableEspecialidadeProfessor().getTableHeader().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getTableEspecialidadeProfessor().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dadosProfessorTela.getRdbtnAno().setForeground(coresPrimarias.get(0));
			dadosProfessorTela.getRdbtnMes().setForeground(coresPrimarias.get(0));
			
			coresP.add(coresPrimarias.get(0));
			
		}
		
		mouseEnterExit(dadosProfessorTela.getTextFieldCpfProfessor(), coresPrimarias);
		mouseEnterExit(dadosProfessorTela.getTextFieldEmailProfessor(), coresPrimarias);
		mouseEnterExit(dadosProfessorTela.getTextFieldHoraEntrada(), coresPrimarias);
		mouseEnterExit(dadosProfessorTela.getTextFieldHoraSaida(), coresPrimarias);
		mouseEnterExit(dadosProfessorTela.getTextFieldNomeProfessor(), coresPrimarias);
		mouseEnterExit(dadosProfessorTela.getTextFieldSalarioProfessor(), coresPrimarias);
		mouseEnterExit(dadosProfessorTela.getTextFieldContrato(), coresPrimarias);
		mouseEnterExit(dadosProfessorTela.getTextFieldDataAdmissao(), coresPrimarias);
		
		mouseEnterExitDias(dadosProfessorTela.getChckbxDomingo(), coresPrimarias);
		mouseEnterExitDias(dadosProfessorTela.getChckbxQuarta(), coresPrimarias);
		mouseEnterExitDias(dadosProfessorTela.getChckbxQuinta(), coresPrimarias);
		mouseEnterExitDias(dadosProfessorTela.getChckbxSabado(), coresPrimarias);
		mouseEnterExitDias(dadosProfessorTela.getChckbxSegunda(), coresPrimarias);
		mouseEnterExitDias(dadosProfessorTela.getChckbxSexta(), coresPrimarias);
		mouseEnterExitDias(dadosProfessorTela.getChckbxTerca(), coresPrimarias);
		
		dadosProfessorTela.getRdbtnAno().setIcon(new ColoredRadioButtonIcon(coresPrimarias.get(0), coresPrimarias.get(0)));
		dadosProfessorTela.getRdbtnMes().setIcon(new ColoredRadioButtonIcon(coresPrimarias.get(0), coresPrimarias.get(0)));
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			dadosProfessorTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresSecundarias.get(0), coresSecundarias.get(0)));

			dadosProfessorTela.getTextFieldCpfProfessor().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTextFieldEmailProfessor().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTextFieldHoraEntrada().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTextFieldHoraSaida().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTextFieldNomeProfessor().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTextFieldSalarioProfessor().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTextFieldContrato().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTextFieldDataAdmissao().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getBtnAdicionarEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnConfirmar().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnCancelar().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnExcluirEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnMudarFotoProfessor().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getComboBoxAdicionarEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getComboBoxExcluirEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getComboBoxAtivo().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getTableEspecialidadeProfessor().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getTableEspecialidadeProfessor().getTableHeader().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getRdbtnAno().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getRdbtnMes().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getContentPane().setBackground(coresSecundarias.get(0));
			
		}else {
			
			dadosProfessorTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresSecundarias.get(1), coresSecundarias.get(2)));
			
			dadosProfessorTela.getTextFieldCpfProfessor().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTextFieldEmailProfessor().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTextFieldHoraEntrada().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTextFieldHoraSaida().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTextFieldNomeProfessor().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTextFieldSalarioProfessor().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTextFieldContrato().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTextFieldDataAdmissao().setBackground(coresSecundarias.get(2));
			
			dadosProfessorTela.getBtnAdicionarEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnConfirmar().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnCancelar().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnExcluirEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getBtnMudarFotoProfessor().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getComboBoxAdicionarEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getComboBoxExcluirEspecialidade().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getComboBoxAtivo().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getTableEspecialidadeProfessor().setBackground(coresSecundarias.get(2));
			dadosProfessorTela.getTableEspecialidadeProfessor().getTableHeader().setBackground(coresSecundarias.get(1));
			
			dadosProfessorTela.getRdbtnAno().setBackground(coresSecundarias.get(0));
			dadosProfessorTela.getRdbtnMes().setBackground(coresSecundarias.get(0));
			
			dadosProfessorTela.getContentPane().setBackground(coresSecundarias.get(0));
			
		}
		
		mudarComboBox(dadosProfessorTela.getComboBoxAdicionarEspecialidade(), coresP, coresSecundarias);
		mudarComboBox(dadosProfessorTela.getComboBoxAtivo(), coresP, coresSecundarias);
		mudarComboBox(dadosProfessorTela.getComboBoxExcluirEspecialidade(), coresP, coresSecundarias);
		
		dadosProfessorTela.getChckbxDomingo().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		dadosProfessorTela.getChckbxDomingo().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		dadosProfessorTela.getChckbxSegunda().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		dadosProfessorTela.getChckbxSegunda().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		dadosProfessorTela.getChckbxTerca().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		dadosProfessorTela.getChckbxTerca().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		dadosProfessorTela.getChckbxQuarta().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		dadosProfessorTela.getChckbxQuarta().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		dadosProfessorTela.getChckbxQuinta().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		dadosProfessorTela.getChckbxQuinta().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		dadosProfessorTela.getChckbxSexta().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		dadosProfessorTela.getChckbxSexta().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		dadosProfessorTela.getChckbxSabado().setIcon(new CustomCheckBoxIcon(coresP.get(0), coresSecundarias.get(0)));
		dadosProfessorTela.getChckbxSabado().setSelectedIcon(new CustomCheckBoxIcon(coresSecundarias.get(0), coresP.get(0)));
		
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
	        g2.drawArc(x, y + 2, size - 4, size - 4, size * 24, size * 24);

	        if (((AbstractButton) c).isSelected()) {
	            g2.setColor(fillColor);
	            g2.fillOval(x + 3, y + 5, size - 10, size - 10);
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
