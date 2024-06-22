package controller.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableModel;

import model.ModelAluno;
import model.ModelCPF;
import model.ModelFoto;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import servico.Criptografar;
import view.DadosAlunoTela;

public class DadosAlunoHelper {

	private final DadosAlunoTela dadosAlunoTela;

	public DadosAlunoHelper(DadosAlunoTela dadosAlunoTela) {
		super();
		this.dadosAlunoTela = dadosAlunoTela;
	}

	public void preencherDadosAluno(ModelUsuario usuario, ModelRegistroAluno registroAluno) {
		preencherFoto(registroAluno, usuario);
		preencherCamposDados(registroAluno);
		preencherTabelaDados(registroAluno);
		preencherPlanoDados(registroAluno, usuario);
		preencherDataValorDinamicamente(registroAluno, usuario);
		preencherExcluirModalidadeDados(registroAluno);
		preencherAdicionarModalidadeDados(registroAluno, usuario);
		
	}
	
	private void preencherFoto(ModelRegistroAluno registroAluno, ModelUsuario usuario) {
		if(registroAluno.getAluno().getFoto() == null) {
			
		}else {
			dadosAlunoTela.getNomeImagemField().setText(registroAluno.getAluno().getFoto().getNomeImagemField());
			
			ImageIcon imagemIcon = new ImageIcon(registroAluno.getAluno().getFoto().getCaminhoImagem());
			Image imagem = imagemIcon.getImage().getScaledInstance(dadosAlunoTela.getLblMudarFoto().getWidth(), dadosAlunoTela.getLblMudarFoto().getHeight(), Image.SCALE_SMOOTH);
			dadosAlunoTela.getLblMudarFoto().setIcon(new ImageIcon(imagem));
			
			if(dadosAlunoTela.getLblMudarFoto().getIcon().getIconHeight() == -1 || registroAluno.getAluno().getFoto().getCaminhoImagem().equals("")) {
				if(!dadosAlunoTela.getLblMudarFoto().getText().equals(registroAluno.getAluno().getFoto().getCaminhoImagem())) { 
					corrigirProblemas(registroAluno);
				}

				if(usuario.getConfiguracao().getCorSecundariaColor(usuario).equals(new Color(255, 255, 255))) {
					dadosAlunoTela.getLblMudarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-mudar-foto-fundobranco.png")));
				
				}else {
					dadosAlunoTela.getLblMudarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-mudar-foto.png")));
				}
			}
		}
	}
	
	private void corrigirProblemas(ModelRegistroAluno registroAluno) {
		
		if(dadosAlunoTela.getLblMudarFoto().getIcon().getIconHeight() == -1) {
			if(!dadosAlunoTela.getLblMudarFoto().getText().equals(registroAluno.getAluno().getFoto().getCaminhoImagem())) {
				dadosAlunoTela.getContentPane().addMouseListener(new MouseAdapter() {
					private String resposta = "Sim";
					@Override
					public void mouseEntered(MouseEvent e) {
						if(resposta.equals("Sim")) {
							if(dadosAlunoTela.exibirMensagemDecisao("Esse aluno tinha uma foto porém o sistema não conseguiu encontrar-lá! Veja abaixo possíveis problemas \r\n"
									+ "que podem ter causado isso.\r\n\r\n"
									+ "1 - A foto foi excluida.\r\n"
									+ "2 - A foto foi movida para uma pasta diferente da original.\r\n"
									+ "      O caminho original para a foto é: " + registroAluno.getAluno().getFoto().getCaminhoImagem() + "\r\n"
									+ "3 - O nome de alguma pasta desse caminho ou da foto foi alterado.\r\n"
									+ "      O nome original da foto é: " + registroAluno.getAluno().getFoto().getNomeImagemField() + "\r\n\r\n"
									+ "Deseja que a foto desse aluno seja removida? Se sim, clique em \"Sim\" e logo após em \"Confirmar\"")){
								registroAluno.getAluno().getFoto().setCaminhoImagem("");
								registroAluno.getAluno().getFoto().setNomeImagemField("");
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

	private void preencherCamposDados(ModelRegistroAluno registroAluno) {
		
		dadosAlunoTela.getTextFieldNomeAluno().setText(registroAluno.getAluno().getNome());
		dadosAlunoTela.getTextFieldDataMatricula().setText(registroAluno.getAluno().getDataMatriculaFormatada());
		dadosAlunoTela.getTextFieldDataNascimento().setText(registroAluno.getAluno().getDataNascimentoFormatada());
		dadosAlunoTela.getTextFieldDataProximoPagamento().setText(registroAluno.getProximoPagamentoFormatada());
		dadosAlunoTela.getTextFieldDataUltimoPagamento().setText(registroAluno.getUltimoPagamentoFormatada());

		String emailDescriptografado = Criptografar.descriptografar(registroAluno.getAluno().getEmail());
		dadosAlunoTela.getTextFieldEmailAluno().setText(emailDescriptografado);
		
		String cpfDescriptografado = Criptografar.descriptografar(registroAluno.getAluno().getCpf());
		dadosAlunoTela.getTextFieldCpfAluno().setText(cpfDescriptografado);
	}
	
	public void preencherTabelaDados(ModelRegistroAluno registroAluno) {
		DefaultTableModel tableModel = (DefaultTableModel) dadosAlunoTela.getTableModalidadeAluno().getModel();
		tableModel.setNumRows(0);
		
		for (ModelModalidade modalidades : registroAluno.getAluno().getModalidades()) {
			
			tableModel.addRow(new Object[] {
					modalidades.getNome(),
					modalidades.getTaxaExtra()
			});
		}
	}
	
	@SuppressWarnings("unchecked")
	private void preencherPlanoDados(ModelRegistroAluno registroAluno, ModelUsuario usuario) {
		DefaultComboBoxModel<ModelPlano> comboBoxPlano = (DefaultComboBoxModel<ModelPlano>) dadosAlunoTela.getComboBoxPlano().getModel();
		
		comboBoxPlano.addElement(registroAluno.getAluno().getPlano());
		for(ModelPlano plano: usuario.getPlanos()) {
			if(plano.getId() != registroAluno.getAluno().getPlano().getId()) {
				comboBoxPlano.addElement(plano);
			}
		}
	}

	public void preencherDataValorDinamicamente(ModelRegistroAluno registroAluno, ModelUsuario usuario) {
		
		atualizarValor(registroAluno, usuario);
		
		dadosAlunoTela.getComboBoxPlano().addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				atualizarValor(registroAluno, usuario);
				atualizarData(registroAluno);
			}
		});
		
	}
	
	private void atualizarValor(ModelRegistroAluno registroAluno, ModelUsuario usuario) {
		
		ModelPlano plano = (ModelPlano) dadosAlunoTela.getComboBoxPlano().getSelectedItem();
		if(plano == null) {
			plano = registroAluno.getAluno().getPlano();
		}
		
		ModelRegistroAluno registro = usuario.getRegistroAlunos().get(usuario.getRegistroAlunos().size() - 1);	
		if(registro == null) {
			dadosAlunoTela.getTextFieldTotal().setText(plano.getValor() + "");			
		}else {
			float precoModalidades = 0;
			
			for(ModelModalidade modalidades: registroAluno.getAluno().getModalidades()) {
				precoModalidades += modalidades.getTaxaExtra();
			}
			
			precoModalidades = precoModalidades * plano.getPeriodo();
			
			dadosAlunoTela.getTextFieldTotal().setText(plano.getValor() + precoModalidades + "");		
		}
		
	}
	
	private void atualizarData(ModelRegistroAluno registroAluno) {
		ModelPlano plano = (ModelPlano) dadosAlunoTela.getComboBoxPlano().getSelectedItem();
		if(plano == null) {
			plano = registroAluno.getAluno().getPlano();
		}
		
		if(registroAluno.getAtivo().equalsIgnoreCase("Não")) {
			LocalDate data = LocalDate.parse(dadosAlunoTela.getTextFieldDataMatricula().getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate dataF = data.plusMonths(plano.getPeriodo());
			String dataFi = dataF.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setText(dataFi);
		}else {
			LocalDate data = LocalDate.parse(registroAluno.getUltimoPagamentoFormatada(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			LocalDate dataFinal = data.plusMonths(plano.getPeriodo());
			String dataFormatada = dataFinal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setText(dataFormatada);
		}
		
	}
	

	@SuppressWarnings("unchecked")
	private void preencherExcluirModalidadeDados(ModelRegistroAluno registroAluno) {

		DefaultComboBoxModel<ModelModalidade> comboBoxExcluirModalidade = (DefaultComboBoxModel<ModelModalidade>) dadosAlunoTela.getComboBoxExcluirModalidade().getModel();
		
		for(ModelModalidade modalidade: registroAluno.getAluno().getModalidades()) {
			comboBoxExcluirModalidade.addElement(modalidade);
		}
		
	}

	@SuppressWarnings("unchecked")
	private void preencherAdicionarModalidadeDados(ModelRegistroAluno registroAluno, ModelUsuario usuario) {
		
		DefaultComboBoxModel<ModelModalidade> comboBoxAdicionarModalidade = (DefaultComboBoxModel<ModelModalidade>) dadosAlunoTela.getComboBoxAdicionarModalidade().getModel();
		
		for(ModelModalidade modalidade: usuario.getModalidades()) {
			comboBoxAdicionarModalidade.addElement(modalidade);
		}
		
		for(int i = 0; i < comboBoxAdicionarModalidade.getSize(); i++) {
			for(int j = 0; j < registroAluno.getAluno().getModalidades().size(); j++) {
				if(comboBoxAdicionarModalidade.getElementAt(i) == null || comboBoxAdicionarModalidade.getElementAt(i).getId() == registroAluno.getAluno().getModalidades().get(j).getId()) {
					comboBoxAdicionarModalidade.removeElement(comboBoxAdicionarModalidade.getElementAt(i));
				}
			}
		}

		for(int i = 0; i < comboBoxAdicionarModalidade.getSize(); i++) {
			for(int j = 0; j < registroAluno.getAluno().getModalidades().size(); j++) {
				if(comboBoxAdicionarModalidade.getSize() == 0 || registroAluno.getAluno().getModalidades().size() == 0) {
					
				}else {
					if(comboBoxAdicionarModalidade.getElementAt(i).getId() == registroAluno.getAluno().getModalidades().get(j).getId()) {
						comboBoxAdicionarModalidade.removeElementAt(i);
					}
				}
			}
		}
		
	}
	
	public boolean compararDados(ModelRegistroAluno registroAluno, ArrayList<ModelModalidade> modalidades) {
		String valor = registroAluno.getTotal() + "";
		
		String cpfDescriptografado = Criptografar.descriptografar(registroAluno.getAluno().getCpf());
		String emailDescriptografado = Criptografar.descriptografar(registroAluno.getAluno().getEmail());
		
		if(!registroAluno.getAluno().getNome().equals(dadosAlunoTela.getTextFieldNomeAluno().getText().trim()) || 
				!cpfDescriptografado.equals(dadosAlunoTela.getTextFieldCpfAluno().getText().trim()) || 
				!emailDescriptografado.equals(dadosAlunoTela.getTextFieldEmailAluno().getText().trim()) || 
				!registroAluno.getAluno().getDataMatriculaFormatada().equals(dadosAlunoTela.getTextFieldDataMatricula().getText().trim()) || 
				!registroAluno.getAluno().getDataNascimentoFormatada().equals(dadosAlunoTela.getTextFieldDataNascimento().getText().trim()) || 
				!registroAluno.getProximoPagamentoFormatada().equals(dadosAlunoTela.getTextFieldDataProximoPagamento().getText()) || 
				!registroAluno.getUltimoPagamentoFormatada().equals(dadosAlunoTela.getTextFieldDataUltimoPagamento().getText()) || 
				!registroAluno.getAluno().getPlano().equals(dadosAlunoTela.getComboBoxPlano().getSelectedItem()) ||
				modalidades.size() != dadosAlunoTela.getTableModalidadeAluno().getRowCount() ||
				!compararModalidades(modalidades, registroAluno) || !valor.equals(dadosAlunoTela.getTextFieldTotal().getText())){
			
			return false;
			
		}else {
			if(registroAluno.getAluno().getFoto() != null) {
				if(!registroAluno.getAluno().getFoto().getNomeImagemField().equals(dadosAlunoTela.getNomeImagemField().getText())){
					return false;
				}else {
					return true;					
				}
			}else {
				if(dadosAlunoTela.getCaminhoImagem() != null) {
					return false;
				}
				return true;
			}
		}
		
	}
	
	private boolean compararModalidades(ArrayList<ModelModalidade> modalidades,
			ModelRegistroAluno registroAluno) {
		int cont = 0;
		
		for(ModelModalidade modalidadeAluno: registroAluno.getAluno().getModalidades()) {
			for(ModelModalidade modalidadeCombo: modalidades) {
				if(modalidadeAluno.getId() == modalidadeCombo.getId()) {
					cont += 1;
				}
			}
		}
		
		return cont != registroAluno.getAluno().getModalidades().size() ? false : true;
	}
	
	public ModelPlano obterPlano() {
		return (ModelPlano) dadosAlunoTela.getComboBoxPlano().getSelectedItem();
	}

	public ModelAluno obterModelo(ModelRegistroAluno registroAluno) {

		registroAluno.getAluno().setId(registroAluno.getId());
		registroAluno.getAluno().setNome(dadosAlunoTela.getTextFieldNomeAluno().getText().trim());
		registroAluno.getAluno().setCpf(dadosAlunoTela.getTextFieldCpfAluno().getText().trim());
		registroAluno.getAluno().setEmail(dadosAlunoTela.getTextFieldEmailAluno().getText().trim());
		registroAluno.getAluno().setDataNascimentoFormatada(dadosAlunoTela.getTextFieldDataNascimento().getText().trim());
		registroAluno.getAluno().setDataMatriculaFormatada(dadosAlunoTela.getTextFieldDataMatricula().getText().trim());
		registroAluno.getAluno().setPlano(obterPlano());
		
		return registroAluno.getAluno();
	}

	public ModelFoto obterFoto(ModelRegistroAluno registroAluno) {

		if(dadosAlunoTela.getCaminhoImagem() == null || dadosAlunoTela.getNomeImagemField().getText() == null) {
			dadosAlunoTela.setCaminhoImagem(registroAluno.getAluno().getFoto().getCaminhoImagem());
			dadosAlunoTela.getNomeImagemField().setText(registroAluno.getAluno().getFoto().getNomeImagemField());
		}
		
		if(registroAluno.getAluno().getFoto() == null) {
			String caminhoImagem = dadosAlunoTela.getCaminhoImagem();
			String nomeImagem = dadosAlunoTela.getNomeImagemField().getText();
			
			ModelFoto fotoAluno = new ModelFoto(nomeImagem, caminhoImagem);
			
			registroAluno.getAluno().setFoto(fotoAluno);
		}
		
		registroAluno.getAluno().getFoto().setCaminhoImagem(dadosAlunoTela.getCaminhoImagem());
		registroAluno.getAluno().getFoto().setNomeImagemField(dadosAlunoTela.getNomeImagemField().getText());
		
		return registroAluno.getAluno().getFoto();
	}

	public ModelRegistroAluno obterModeloRegistro(ModelRegistroAluno registroAluno) {
		registroAluno.setId(obterModelo(registroAluno).getId());
		registroAluno.setAluno(obterModelo(registroAluno));
		registroAluno.setAtivo();
		registroAluno.setTotal(registroAluno.getTotal());
		
		String dataMatriculaAluno = dadosAlunoTela.getTextFieldDataMatricula().getText();
		String ultimoPagamento = dadosAlunoTela.getTextFieldDataUltimoPagamento().getText();
		
		registroAluno.getAluno().setDataMatriculaFormatada(dataMatriculaAluno);
		registroAluno.setUltimoPagamento(LocalDate.parse(ultimoPagamento, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		registroAluno.setProximoPagamento(registroAluno.getUltimoPagamento().plusMonths(registroAluno.getAluno().getPlano().getPeriodo()));
		
		return registroAluno;
	}

	public boolean verificarDados() {
		if(dadosAlunoTela.getTextFieldNomeAluno().getText().trim().isEmpty() || dadosAlunoTela.getTextFieldCpfAluno().getText().trim().isEmpty() ||
				dadosAlunoTela.getTextFieldEmailAluno().getText().trim().isEmpty() || dadosAlunoTela.getTextFieldDataNascimento().getText().trim().isEmpty() ||
				dadosAlunoTela.getTextFieldDataMatricula().getText().trim().isEmpty() || 
				dadosAlunoTela.getTextFieldDataMatricula().getText().replace("/", "").trim().isEmpty()) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	public boolean verificarData(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataMatricula = null;
		
        if(data == null || data.equals("  /  /    ")) {
        	return false;
        }
        
        try {
        	dataMatricula = LocalDate.parse(data, dtf);
        }catch(DateTimeParseException e) {
        	return false;
        }

        LocalDate dataVerificada = LocalDate.parse(data, dtf);
        LocalDate hoje = LocalDate.now();        	
        int yearsBetween = (int) ChronoUnit.YEARS.between(dataVerificada, hoje);
    
        return dataVerificada.compareTo(hoje) > 0 || yearsBetween > 150 ? false : true;
	}

	public boolean compararDatas(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataMatricula = null;
		
		if(data == null || data.equals("  /  /    ")) {
        	return false;
        }
		
		try {
			dataMatricula = LocalDate.parse(data, dtf);
		}catch(DateTimeParseException e) {
			return false;
		}
		
        if(dadosAlunoTela.getTextFieldDataNascimento().getText() != null || !dadosAlunoTela.getTextFieldDataNascimento().getText().isEmpty()) {
        	LocalDate dataNascimento = LocalDate.parse(dadosAlunoTela.getTextFieldDataNascimento().getText(), dtf);
        	dataMatricula = LocalDate.parse(data, dtf);
        	LocalDate hoje = LocalDate.now();
        	
        	if(dataMatricula.isBefore(dataNascimento) || dataMatricula.isAfter(hoje)) {
        		return false;
        	}
        	
        }
        
        if(dadosAlunoTela.getTextFieldDataMatricula().getText() != null || !dadosAlunoTela.getTextFieldDataMatricula().getText().isEmpty()) {
        	dataMatricula = LocalDate.parse(dadosAlunoTela.getTextFieldDataMatricula().getText(), dtf);
        	LocalDate dataUltimoPagamento = LocalDate.parse(data, dtf);
        	LocalDate hoje = LocalDate.now();
        	
        	if(dataUltimoPagamento.isBefore(dataMatricula) || dataUltimoPagamento.isAfter(hoje)) {
        		return false;
        	}
        }
        
        LocalDate dataVerificada = LocalDate.parse(data, dtf);
        LocalDate hoje = LocalDate.now();
        int yearsBetween = (int) ChronoUnit.YEARS.between(dataVerificada, hoje);
    
        return dataVerificada.compareTo(hoje) > 0 || yearsBetween > 150 ? false : true;
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
	
	public boolean validarEmailECpf(ModelUsuario usuario, ModelRegistroAluno registroAluno, String cpf, String email) {
		String cpfCriptografado = Criptografar.criptografar(cpf);
    	for(ModelAluno alunos: usuario.getAlunos()) {
    		if(alunos.getCpf().equals(cpfCriptografado)) {
    			String cpfDescriptografado = Criptografar.descriptografar(registroAluno.getAluno().getCpf());
    			if(!cpfDescriptografado.equals(cpf)) {
    				dadosAlunoTela.exibirMensagemErro("CPF de aluno já cadastrado");
    				return false;
    			}
    		}
    	}
    	
    	String emailCriptografado = Criptografar.criptografar(email);
    	for(ModelAluno alunos: usuario.getAlunos()) {
    		if(alunos.getEmail().equals(emailCriptografado)) {
    			String emailDescriptografado = Criptografar.descriptografar(registroAluno.getAluno().getEmail());
    			if(!emailDescriptografado.equals(email)) {
    				dadosAlunoTela.exibirMensagemErro("Email de aluno já cadastrado");
    				return false;
    			}
    		}
    	}

    	return true;
	}

	public boolean verificarModalidade(ModelModalidade modalidade) {
		if(modalidade == null) {
			return false;
		}
		return true;
	}

	ArrayList<Color> coresP = new ArrayList<>();
	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {
			
			dadosAlunoTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0)));
			dadosAlunoTela.getLblCpfAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblDadosAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblDataDeMatriculaAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblDataDeNascimentoAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblEmailAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblModalidadesAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblNomeAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblPlano().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblProximoPagamento().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblTotal().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblUltimoPagamento().setForeground(coresPrimarias.get(0));
			
			dadosAlunoTela.getTextFieldCpfAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldCpfAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTextFieldDataMatricula().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldDataMatricula().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTextFieldDataNascimento().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldDataNascimento().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTextFieldDataUltimoPagamento().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldDataUltimoPagamento().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTextFieldEmailAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldEmailAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTextFieldNomeAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldNomeAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTextFieldTotal().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTextFieldTotal().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosAlunoTela.getComboBoxAdicionarModalidade().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getComboBoxAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getComboBoxExcluirModalidade().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getComboBoxExcluirModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getComboBoxPlano().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getComboBoxPlano().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosAlunoTela.getBtnAdicionarModalidade().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getBtnCancelar().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnCancelar().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getBtnConfirmar().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnConfirmar().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0)));
			dadosAlunoTela.getBtnExcluirModalidade().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnExcluirModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getBtnMudarFotoAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnMudarFotoAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosAlunoTela.getTableModalidadeAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTableModalidadeAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getTableModalidadeAluno().getTableHeader().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTableModalidadeAluno().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			coresP.add(coresPrimarias.get(0));
			
		}else {
			
			dadosAlunoTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(1), coresPrimarias.get(3)));

			dadosAlunoTela.getLblCpfAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblDadosAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getLblDataDeMatriculaAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblDataDeNascimentoAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblEmailAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblModalidadesAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblNomeAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblPlano().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblProximoPagamento().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblTotal().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getLblUltimoPagamento().setForeground(coresPrimarias.get(1));
			
			dadosAlunoTela.getTextFieldCpfAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldCpfAluno().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTextFieldDataMatricula().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldDataMatricula().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTextFieldDataNascimento().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldDataNascimento().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTextFieldDataUltimoPagamento().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldDataUltimoPagamento().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTextFieldEmailAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldEmailAluno().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTextFieldNomeAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldNomeAluno().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTextFieldTotal().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTextFieldTotal().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dadosAlunoTela.getComboBoxAdicionarModalidade().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getComboBoxAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getComboBoxExcluirModalidade().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getComboBoxExcluirModalidade().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getComboBoxPlano().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getComboBoxPlano().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dadosAlunoTela.getBtnAdicionarModalidade().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getBtnCancelar().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getBtnCancelar().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getBtnConfirmar().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnConfirmar().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(4), coresPrimarias.get(0)));
			dadosAlunoTela.getBtnExcluirModalidade().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnExcluirModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			dadosAlunoTela.getBtnMudarFotoAluno().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getBtnMudarFotoAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dadosAlunoTela.getTableModalidadeAluno().setForeground(coresPrimarias.get(1));
			dadosAlunoTela.getTableModalidadeAluno().setBorder(new LineBorder(coresPrimarias.get(1)));
			dadosAlunoTela.getTableModalidadeAluno().getTableHeader().setForeground(coresPrimarias.get(0));
			dadosAlunoTela.getTableModalidadeAluno().getTableHeader().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			coresP.add(coresPrimarias.get(0));
		}
		
		mouseEnterExit(dadosAlunoTela.getTextFieldCpfAluno(), coresPrimarias);
		mouseEnterExit(dadosAlunoTela.getTextFieldDataMatricula(), coresPrimarias);
		mouseEnterExit(dadosAlunoTela.getTextFieldDataNascimento(), coresPrimarias);
		mouseEnterExit(dadosAlunoTela.getTextFieldEmailAluno(), coresPrimarias);
		mouseEnterExit(dadosAlunoTela.getTextFieldNomeAluno(), coresPrimarias);
		mouseEnterExit(dadosAlunoTela.getTextFieldDataProximoPagamento(), coresPrimarias);
		mouseEnterExit(dadosAlunoTela.getTextFieldDataUltimoPagamento(), coresPrimarias);
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			dadosAlunoTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresSecundarias.get(0), coresSecundarias.get(0)));
			
			dadosAlunoTela.getTextFieldCpfAluno().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTextFieldDataMatricula().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTextFieldDataNascimento().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTextFieldDataUltimoPagamento().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTextFieldEmailAluno().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTextFieldNomeAluno().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTextFieldTotal().setBackground(coresSecundarias.get(0));
			
			dadosAlunoTela.getComboBoxAdicionarModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getComboBoxExcluirModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getComboBoxPlano().setBackground(coresSecundarias.get(0));
			
			dadosAlunoTela.getBtnAdicionarModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getBtnCancelar().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getBtnConfirmar().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getBtnExcluirModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getBtnMudarFotoAluno().setBackground(coresSecundarias.get(0));
			
			dadosAlunoTela.getTableModalidadeAluno().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getTableModalidadeAluno().getTableHeader().setBackground(coresSecundarias.get(0));
			
			dadosAlunoTela.getContentPane().setBackground(coresSecundarias.get(0));
			
		}else {
			
			dadosAlunoTela.getLblMudarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresSecundarias.get(1), coresSecundarias.get(2)));
			
			dadosAlunoTela.getTextFieldCpfAluno().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTextFieldDataMatricula().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTextFieldDataNascimento().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTextFieldDataProximoPagamento().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTextFieldDataUltimoPagamento().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTextFieldEmailAluno().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTextFieldNomeAluno().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTextFieldTotal().setBackground(coresSecundarias.get(2));
			
			dadosAlunoTela.getComboBoxAdicionarModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getComboBoxExcluirModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getComboBoxPlano().setBackground(coresSecundarias.get(0));
			
			dadosAlunoTela.getBtnAdicionarModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getBtnCancelar().setBackground(coresSecundarias.get(1));
			dadosAlunoTela.getBtnConfirmar().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getBtnExcluirModalidade().setBackground(coresSecundarias.get(0));
			dadosAlunoTela.getBtnMudarFotoAluno().setBackground(coresSecundarias.get(0));
			
			dadosAlunoTela.getTableModalidadeAluno().setBackground(coresSecundarias.get(2));
			dadosAlunoTela.getTableModalidadeAluno().getTableHeader().setBackground(coresSecundarias.get(1));
			
			dadosAlunoTela.getContentPane().setBackground(coresSecundarias.get(0));
		
		}
		
		mudarComboBox(dadosAlunoTela.getComboBoxPlano(), coresP, coresSecundarias);
		mudarComboBox(dadosAlunoTela.getComboBoxAdicionarModalidade(), coresP, coresSecundarias);
		mudarComboBox(dadosAlunoTela.getComboBoxExcluirModalidade(), coresP, coresSecundarias);
		
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
	
}
