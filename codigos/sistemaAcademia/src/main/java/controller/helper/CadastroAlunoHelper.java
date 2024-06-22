package controller.helper;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

import model.ModelAluno;
import model.ModelCPF;
import model.ModelFoto;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import servico.Criptografar;
import view.CadastroAlunoTela;
import view.DadosAlunoTela;

public class CadastroAlunoHelper implements Helper{

	private final CadastroAlunoTela cadastroAlunoTela;

	public CadastroAlunoHelper(CadastroAlunoTela cadastroAlunoTela) {
		super();
		this.cadastroAlunoTela = cadastroAlunoTela;
	}

	@SuppressWarnings("unchecked")
	public void atualizarPlanos(List<ModelPlano> planos) {
		DefaultComboBoxModel<ModelPlano> comboBoxModel = (DefaultComboBoxModel<ModelPlano>) cadastroAlunoTela.getComboBoxPlano().getModel();
		
		for(ModelPlano plano: planos) {
			comboBoxModel.addElement(plano);			
		}
	}
 
	@SuppressWarnings("unchecked")
	public void atualizarModalidades(List<ModelModalidade> modalidades, ModelUsuario usuario) {
		
		DefaultComboBoxModel<ModelModalidade> comboBoxAdicionarModalidade = (DefaultComboBoxModel<ModelModalidade>) cadastroAlunoTela.getComboBoxAdicionarModalidade().getModel();
		
		DefaultComboBoxModel<ModelModalidade> comboBoxExcluirModalidade = (DefaultComboBoxModel<ModelModalidade>) cadastroAlunoTela.getComboBoxRemoverModalidade().getModel();
		
		ModelAluno alunoComparar = null;
		
		try {
			alunoComparar = usuario.getAlunos().get(usuario.getAlunos().size() - 1);
		}catch(IndexOutOfBoundsException e) {
			comboBoxAdicionarModalidade.removeAllElements();
			for(ModelModalidade modalidade: modalidades) {
				comboBoxAdicionarModalidade.addElement(modalidade);
			}
			
			comboBoxExcluirModalidade.removeAllElements();
		}
		
		if(alunoComparar == null) {
			comboBoxAdicionarModalidade.removeAllElements();
			for(ModelModalidade modalidade: modalidades) {
				comboBoxAdicionarModalidade.addElement(modalidade);
			}
			
			comboBoxExcluirModalidade.removeAllElements();	
			
		}else if(alunoComparar.getCpf() != null && !alunoComparar.getCpf().equals("") || alunoComparar.getModalidades().isEmpty()){
			comboBoxAdicionarModalidade.removeAllElements();
			for(ModelModalidade modalidade: modalidades) {
				comboBoxAdicionarModalidade.addElement(modalidade);
			}
			
			comboBoxExcluirModalidade.removeAllElements();		
			
		}else {
			comboBoxAdicionarModalidade.removeAllElements();
			
			for(ModelModalidade modalidade: modalidades) {
				comboBoxAdicionarModalidade.addElement(modalidade);
				
				for(ModelModalidade modalidadeAluno: alunoComparar.getModalidades()) {
					
					if(modalidade.getId() == modalidadeAluno.getId()) {
						comboBoxAdicionarModalidade.removeElement(modalidadeAluno);

					}
					
					comboBoxExcluirModalidade.removeAllElements();
					for(ModelModalidade modalidadeAluno2: alunoComparar.getModalidades()) {
						comboBoxExcluirModalidade.addElement(modalidadeAluno2);
					}
				}
			}
		}
		
		/** NOTE: Remove em ultimo caso, caso a modalidade que estiver nos dois comboBox (se estiver) */
		for(int i = 0; i < comboBoxAdicionarModalidade.getSize(); i++) {
			for(int j = 0; j < comboBoxExcluirModalidade.getSize(); j++) {
				if(comboBoxAdicionarModalidade.getSize() == 0 || comboBoxExcluirModalidade.getSize() == 0) {
					
				}else {
					if(comboBoxAdicionarModalidade.getElementAt(i).getId() == comboBoxExcluirModalidade.getElementAt(j).getId()) {
						comboBoxAdicionarModalidade.removeElementAt(i);
					}
				}
			}
		}
		
	}

	public ModelPlano obterPlano() {
		return (ModelPlano) cadastroAlunoTela.getComboBoxPlano().getSelectedItem();
	}
	
	public ModelAluno obterAluno(ModelUsuario usuario) {
		
		try{
			ModelAluno alunoAComparar = usuario.getAlunos().get(usuario.getAlunos().size() - 1);
		
			if(!alunoAComparar.getCpf().equals("")) {
				return null;
			}else {
				return alunoAComparar;	
			}
		}catch(IndexOutOfBoundsException e) {			
			return null;
		}
		
	}

	public void preencherValorInicial(float valor) {
		cadastroAlunoTela.getTextFieldTotal().setText(valor + "");
		
	}

	public void preencherValorDinamico(float valor, int periodo, List<ModelModalidade> modalidade) {
		float precoModalidades = 0;
		
		for(ModelModalidade modalidades: modalidade) {
			precoModalidades += modalidades.getTaxaExtra();
		}
		
		precoModalidades = precoModalidades * periodo;
		
		cadastroAlunoTela.getTextFieldTotal().setText(valor + precoModalidades + "");
		
	}

	public void preencherPeriodo(int periodo) {
		if(periodo == 1) {
			cadastroAlunoTela.getLblTempoPagamento().setText("* A cada " + periodo + " mês.");
		}else {
			cadastroAlunoTela.getLblTempoPagamento().setText("* A cada " + periodo + " meses.");
		}
	}

	public ModelAluno obterModelo() {
		String nome = cadastroAlunoTela.getTextFieldNomeAluno().getText();
		String cpf = cadastroAlunoTela.getTextFieldCpfAluno().getText();
		String dataNascimento = cadastroAlunoTela.getTextFieldDataNascimento().getText();
		String dataMatricula = cadastroAlunoTela.getTextFieldDataMatricula().getText();
		String email = cadastroAlunoTela.getTextFieldEmailAluno().getText();
		ModelPlano plano = obterPlano();
		
		ModelAluno novoAluno = new ModelAluno(nome, cpf, email, dataNascimento, dataMatricula, plano);
		
		return novoAluno;
	}
	

	public ModelFoto obterFoto() {
		String caminhoImagem = cadastroAlunoTela.getCaminhoImagem();
		String nomeImagem = cadastroAlunoTela.getNomeImagemField().getText();
		
		if(caminhoImagem == null || nomeImagem == null) {
			ModelFoto fotoAluno = new ModelFoto("", "");
			return fotoAluno;
		}
		
		ModelFoto fotoAluno = new ModelFoto(nomeImagem, caminhoImagem);
		
		return fotoAluno;
	}

	public ModelRegistroAluno obterModeloRegistro() {
		int id = obterModelo().getId();
		ModelAluno aluno = obterModelo();
		 
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno(id, aluno);
		return novoRegistroAluno;
	}

	public boolean verificarDados() {
		if(cadastroAlunoTela.getTextFieldNomeAluno().getText().trim().isEmpty() || cadastroAlunoTela.getTextFieldCpfAluno().getText().trim().isEmpty() ||
				cadastroAlunoTela.getTextFieldEmailAluno().getText().trim().isEmpty() || cadastroAlunoTela.getTextFieldDataNascimento().getText().trim().isEmpty() ||
				cadastroAlunoTela.getTextFieldDataMatricula().getText().trim().isEmpty() || 
				cadastroAlunoTela.getTextFieldDataMatricula().getText().replace("/", "").trim().isEmpty()) {
				return false;
			}
		
		return true;
	}

	public boolean verificarData(String data) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataVerificada = null;
		
        if(data == null || data.equals("  /  /    ")) {
        	return false;
        }
        
        try {
        	dataVerificada = LocalDate.parse(data, dtf);
        }catch(DateTimeParseException e) {
        	return false;
        }
        
        if(cadastroAlunoTela.getTextFieldDataNascimento().getText() != null || !cadastroAlunoTela.getTextFieldDataNascimento().getText().isEmpty()) {
        	LocalDate dataComparacao = LocalDate.parse(cadastroAlunoTela.getTextFieldDataNascimento().getText(), dtf);        	        	
        	LocalDate dataMatricula = LocalDate.parse(data, dtf);
        	LocalDate hoje = LocalDate.now();
        	
        	if(dataMatricula.isBefore(dataComparacao) || dataMatricula.isAfter(hoje)) {
        		return false;
        	}
        }
        
        dataVerificada = LocalDate.parse(data, dtf);
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
	
	public boolean dadosDiferentes(ModelUsuario usuario, ModelAluno aluno) {
		
		aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
    	for(ModelAluno alunos: usuario.getAlunos()) {
    		if(alunos.getCpf() != null) {
	    		if(alunos.getCpf().equals(aluno.getCpf())) {
	    			cadastroAlunoTela.exibirMensagemErro("CPF de aluno já cadastrado");
	    			return false;
	    		}
    		}
    	}
    	
    	aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
    	for(ModelAluno alunos: usuario.getAlunos()) {
    		if(alunos.getEmail() != null) {
	    		if(alunos.getEmail().equals(aluno.getEmail())) {
	    			cadastroAlunoTela.exibirMensagemErro("Email de aluno já cadastrado");
	    			return false;
	    		}

    		}
    	}

    	aluno.setCpf(Criptografar.descriptografar(aluno.getCpf()));
    	aluno.setEmail(Criptografar.descriptografar(aluno.getEmail()));
    	return true;
	}

	public boolean verificarModalidade(ModelModalidade modalidade) {
		if(modalidade == null) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public void limparTela(ModelUsuario usuario) {
		cadastroAlunoTela.getTextFieldNomeAluno().setText("");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("");
				
		DefaultComboBoxModel<ModelPlano> comboBoxModel = (DefaultComboBoxModel<ModelPlano>) cadastroAlunoTela.getComboBoxPlano().getModel();
		comboBoxModel.setSelectedItem(usuario.getPlanos().get(0));
		
		preencherValorInicial(usuario.getPlanos().get(0).getValor());
		
		atualizarModalidades(usuario.getModalidades(), usuario);
		
		if(usuario.getConfiguracao() == null) {
			cadastroAlunoTela.getLblAdicionarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-foto.png")));			
		}else {
			if(usuario.getConfiguracao().getCorSecundariaColor(usuario).equals(new Color(255, 255, 255))) {
				cadastroAlunoTela.getLblAdicionarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-foto-fundobranco.png")));			
			}else {
				cadastroAlunoTela.getLblAdicionarFoto().setIcon(new ImageIcon(DadosAlunoTela.class.getResource("/view/icones/icone-foto.png")));			
			}
		}
		
	}

	@Override
	public void limparTela() {
		
	}

	ArrayList<Color> coresP = new ArrayList<>();
	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		if(coresPrimarias.size() == 1) {

			cadastroAlunoTela.getLblAdicionarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0), coresPrimarias.get(0)));
			cadastroAlunoTela.getLblCadastroDeAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblCpfAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblEmailAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblMatriculaAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblModalidades().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblNascimentoAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblNomeAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblPlano().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblTempoPagamento().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblTotal().setForeground(coresPrimarias.get(0));
			
			cadastroAlunoTela.getTextFieldCpfAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getTextFieldCpfAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getTextFieldDataMatricula().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getTextFieldDataMatricula().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getTextFieldDataNascimento().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getTextFieldDataNascimento().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getTextFieldEmailAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getTextFieldEmailAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getTextFieldNomeAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getTextFieldNomeAluno().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getTextFieldTotal().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getTextFieldTotal().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			cadastroAlunoTela.getBtnCadastrarAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnCadastrarAluno().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(0), coresPrimarias.get(0)));
			cadastroAlunoTela.getBtnAdicionarModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getBtnCarregarFoto().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnCarregarFoto().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getBtnRemoverModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnRemoverModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			cadastroAlunoTela.getComboBoxPlano().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getComboBoxPlano().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getComboBoxAdicionarModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getComboBoxAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getComboBoxRemoverModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getComboBoxRemoverModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			coresP.add(coresPrimarias.get(0));
		}else {
			
			cadastroAlunoTela.getLblAdicionarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(1), coresPrimarias.get(3)));
			cadastroAlunoTela.getLblCadastroDeAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getLblCpfAluno().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblEmailAluno().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblMatriculaAluno().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblModalidades().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblNascimentoAluno().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblNomeAluno().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblPlano().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblTempoPagamento().setForeground(coresPrimarias.get(1));
			cadastroAlunoTela.getLblTotal().setForeground(coresPrimarias.get(1));
			
			cadastroAlunoTela.getTextFieldCpfAluno().setForeground(coresPrimarias.get(2));
			cadastroAlunoTela.getTextFieldCpfAluno().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroAlunoTela.getTextFieldDataMatricula().setForeground(coresPrimarias.get(2));
			cadastroAlunoTela.getTextFieldDataMatricula().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroAlunoTela.getTextFieldDataNascimento().setForeground(coresPrimarias.get(2));
			cadastroAlunoTela.getTextFieldDataNascimento().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroAlunoTela.getTextFieldEmailAluno().setForeground(coresPrimarias.get(2));
			cadastroAlunoTela.getTextFieldEmailAluno().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroAlunoTela.getTextFieldNomeAluno().setForeground(coresPrimarias.get(2));
			cadastroAlunoTela.getTextFieldNomeAluno().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroAlunoTela.getTextFieldTotal().setForeground(coresPrimarias.get(2));
			cadastroAlunoTela.getTextFieldTotal().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			cadastroAlunoTela.getBtnCadastrarAluno().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnCadastrarAluno().setBorder(new BevelBorder(BevelBorder.LOWERED, coresPrimarias.get(4), coresPrimarias.get(0)));
			cadastroAlunoTela.getBtnAdicionarModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getBtnCarregarFoto().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnCarregarFoto().setBorder(new LineBorder(coresPrimarias.get(0)));
			cadastroAlunoTela.getBtnRemoverModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getBtnRemoverModalidade().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			cadastroAlunoTela.getComboBoxPlano().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getComboBoxPlano().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroAlunoTela.getComboBoxAdicionarModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getComboBoxAdicionarModalidade().setBorder(new LineBorder(coresPrimarias.get(2)));
			cadastroAlunoTela.getComboBoxRemoverModalidade().setForeground(coresPrimarias.get(0));
			cadastroAlunoTela.getComboBoxRemoverModalidade().setBorder(new LineBorder(coresPrimarias.get(2)));
			
			coresP.add(coresPrimarias.get(0));
			
		}
		
		mouseEnterExit(cadastroAlunoTela.getTextFieldCpfAluno(), coresPrimarias);
		mouseEnterExit(cadastroAlunoTela.getTextFieldDataMatricula(), coresPrimarias);
		mouseEnterExit(cadastroAlunoTela.getTextFieldDataNascimento(), coresPrimarias);
		mouseEnterExit(cadastroAlunoTela.getTextFieldEmailAluno(), coresPrimarias);
		mouseEnterExit(cadastroAlunoTela.getTextFieldNomeAluno(), coresPrimarias);
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			cadastroAlunoTela.getLblAdicionarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresSecundarias.get(0), coresSecundarias.get(0)));
			
			cadastroAlunoTela.getTextFieldCpfAluno().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getTextFieldDataMatricula().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getTextFieldDataNascimento().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getTextFieldEmailAluno().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getTextFieldNomeAluno().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getTextFieldTotal().setBackground(coresSecundarias.get(0));
			
			cadastroAlunoTela.getBtnAdicionarModalidade().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getBtnCadastrarAluno().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getBtnCarregarFoto().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getBtnRemoverModalidade().setBackground(coresSecundarias.get(0));
			
			cadastroAlunoTela.getComboBoxAdicionarModalidade().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getComboBoxRemoverModalidade().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getComboBoxPlano().setBackground(coresSecundarias.get(0));
			
			cadastroAlunoTela.getContentPane().setBackground(coresSecundarias.get(0));
			
			if(coresSecundarias.get(0).equals(new Color(255, 255, 255))) {
				cadastroAlunoTela.getLblPainelCadastroAluno().setIcon(new ImageIcon(CadastroAlunoTela.class.getResource("/view/imagens/painel-cadastro-aluno-fundobranco.png")));
				cadastroAlunoTela.getLblAdicionarFoto().setIcon(new ImageIcon(CadastroAlunoTela.class.getResource("/view/icones/icone-foto-fundobranco.png")));
			}
			
		}else {
			
			cadastroAlunoTela.getLblAdicionarFoto().setBorder(new BevelBorder(BevelBorder.LOWERED, coresSecundarias.get(1), coresSecundarias.get(2)));
			
			cadastroAlunoTela.getTextFieldCpfAluno().setBackground(coresSecundarias.get(2));
			cadastroAlunoTela.getTextFieldDataMatricula().setBackground(coresSecundarias.get(2));
			cadastroAlunoTela.getTextFieldDataNascimento().setBackground(coresSecundarias.get(2));
			cadastroAlunoTela.getTextFieldEmailAluno().setBackground(coresSecundarias.get(2));
			cadastroAlunoTela.getTextFieldNomeAluno().setBackground(coresSecundarias.get(2));
			cadastroAlunoTela.getTextFieldTotal().setBackground(coresSecundarias.get(2));
			
			cadastroAlunoTela.getBtnAdicionarModalidade().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getBtnCadastrarAluno().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getBtnCarregarFoto().setBackground(coresSecundarias.get(0));
			cadastroAlunoTela.getBtnRemoverModalidade().setBackground(coresSecundarias.get(0));
			
			cadastroAlunoTela.getComboBoxAdicionarModalidade().setBackground(coresSecundarias.get(1));
			cadastroAlunoTela.getComboBoxRemoverModalidade().setBackground(coresSecundarias.get(1));
			cadastroAlunoTela.getComboBoxPlano().setBackground(coresSecundarias.get(1));
			
			cadastroAlunoTela.getContentPane().setBackground(coresSecundarias.get(0));
		}
		
		mudarComboBox(cadastroAlunoTela.getComboBoxPlano(), coresP, coresSecundarias);
		mudarComboBox(cadastroAlunoTela.getComboBoxAdicionarModalidade(), coresP, coresSecundarias);
		mudarComboBox(cadastroAlunoTela.getComboBoxRemoverModalidade(), coresP, coresSecundarias);
		
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
