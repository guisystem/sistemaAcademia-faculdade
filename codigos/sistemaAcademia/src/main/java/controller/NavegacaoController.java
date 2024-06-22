package controller;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import model.ModelPlano;
import model.ModelProfessor;
import model.ModelRegistroAluno;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import view.CadastroAlunoTela;
import view.CadastroProfessorTela;
import view.ConfiguracaoTela;
import view.DadosAlunoTela;
import view.DadosProfessorTela;
import view.DashboardTela;
import view.EditarCustosTela;
import view.EditarFuncionariosTela;
import view.EditarModalidadeTela;
import view.EditarPlanosTela;
import view.LoginTela;
import view.MudarSenhaTela;
import view.RegistroAlunoTela;
import view.RegistroProfessorTela;

public class NavegacaoController {

	private DashboardTela dashboardTela;
	private ConfiguracaoTela configuracaoTela;
	private CadastroAlunoTela cadastroAlunoTela;
	private CadastroProfessorTela cadastroProfessorTela;
	private RegistroAlunoTela registroAlunoTela;
	private RegistroProfessorTela registroProfessorTela;
	private MudarSenhaTela mudarSenhaTela;
	private int r = 0, g = 0, b = 0;
	
	@SuppressWarnings("unused")
	private int tamanhoEmBits;
	
	public NavegacaoController(DashboardTela dashboardTela, CadastroAlunoTela cadastroAlunoTela,
			CadastroProfessorTela cadastroProfessorTela, RegistroAlunoTela registroAlunoTela,
			RegistroProfessorTela registroProfessorTela, ConfiguracaoTela configuracaoTela, MudarSenhaTela mudarSenhaTela) {
		super();
		this.dashboardTela = dashboardTela;
		this.cadastroAlunoTela = cadastroAlunoTela;
		this.cadastroProfessorTela = cadastroProfessorTela;
		this.registroAlunoTela = registroAlunoTela;
		this.registroProfessorTela = registroProfessorTela;
		this.configuracaoTela = configuracaoTela;
		this.mudarSenhaTela = mudarSenhaTela;
	}

	public NavegacaoController(DashboardTela dashboardTela) {
		super();
		this.dashboardTela = dashboardTela;
	}
	
	public NavegacaoController(ConfiguracaoTela configuracaoTela) {
		super();
		this.configuracaoTela = configuracaoTela;
	}
	
	public NavegacaoController(CadastroAlunoTela cadastroAlunoTela) {
		super();
		this.cadastroAlunoTela = cadastroAlunoTela;
	}
	
	public NavegacaoController(CadastroProfessorTela cadastroProfessorTela) {
		super();
		this.cadastroProfessorTela = cadastroProfessorTela;
	}
	
	public NavegacaoController(RegistroAlunoTela registroAlunoTela) {
		super();
		this.registroAlunoTela = registroAlunoTela;
	}
	
	public NavegacaoController(RegistroProfessorTela registroProfessorTela) {
		super();
		this.registroProfessorTela = registroProfessorTela;
	}
	
	public NavegacaoController(MudarSenhaTela mudarSenhaTela) {
		super();
		this.mudarSenhaTela = mudarSenhaTela;
	}

	public void navegarParaDashBoard(ModelUsuario usuario) {
		if(getTelaAtual() == dashboardTela) {
			
		}else {
			getTelaAtual().setState(Frame.ICONIFIED);
		}
	}
	
	public void navegarParaDashBoardDeConfiguracao(ModelUsuario usuario, String nomeAcademia) {
		if(nomeAcademia.equals("")) {
			for(Window windows: Window.getWindows()) {
				windows.dispose();
				paraDashboard(usuario);
			}
		}else {
			getTelaAtual().setState(Frame.ICONIFIED);
		}
	}
	
	public void navegarParaCadastroAluno(ModelUsuario usuario) {
		if(cadastroProfessorTela == null && registroAlunoTela == null && registroProfessorTela == null && configuracaoTela == null) {
			paraCadastroAluno(usuario);	
		}else if(cadastroProfessorTela != null) {
			cadastroProfessorTela.setVisible(false);
			paraCadastroAluno(usuario);
		}else if(registroAlunoTela != null){
			registroAlunoTela.setVisible(false);
			paraCadastroAluno(usuario);
		}else if(registroProfessorTela != null){
			registroProfessorTela.setVisible(false);
			paraCadastroAluno(usuario);
		}else if(configuracaoTela != null) {
			configuracaoTela.setVisible(false);
			paraCadastroAluno(usuario);
		}
	}
	
	public void navegarParaCadastroProfessor(ModelUsuario usuario) {
		if(cadastroAlunoTela == null && registroAlunoTela == null && registroProfessorTela == null && configuracaoTela == null) {
			paraCadastroProfessor(usuario);		
		}else if(cadastroAlunoTela != null) {
			cadastroAlunoTela.setVisible(false);
			paraCadastroProfessor(usuario);
		}else if(registroAlunoTela != null){
			registroAlunoTela.setVisible(false);
			paraCadastroProfessor(usuario);
		}else if(registroProfessorTela != null){
			registroProfessorTela.setVisible(false);
			paraCadastroProfessor(usuario);
		}else if(configuracaoTela != null) {
			configuracaoTela.setVisible(false);
			paraCadastroProfessor(usuario);
		}
	}
	
	public void navegarParaRegistroAluno(ModelUsuario usuario) {
		if(cadastroAlunoTela == null && cadastroProfessorTela == null && registroProfessorTela == null && configuracaoTela == null) {
			paraRegistroAluno(usuario);		
		}else if(cadastroAlunoTela != null) {
			cadastroAlunoTela.setVisible(false);
			paraRegistroAluno(usuario);
		}else if(cadastroProfessorTela != null){
			cadastroProfessorTela.setVisible(false);
			paraRegistroAluno(usuario);
		}else if(registroProfessorTela != null){
			registroProfessorTela.setVisible(false);
			paraRegistroAluno(usuario);
		}else if(configuracaoTela != null) {
			configuracaoTela.setVisible(false);
			paraRegistroAluno(usuario);
		}
	}
	
	public void navegarParaRegistroProfessor(ModelUsuario usuario) {
		if(cadastroAlunoTela == null && cadastroProfessorTela == null && registroAlunoTela == null && configuracaoTela == null) {
			paraRegistroProfessor(usuario);		
		}else if(cadastroAlunoTela != null) {
			cadastroAlunoTela.setVisible(false);
			paraRegistroProfessor(usuario);
		}else if(cadastroProfessorTela != null){
			cadastroProfessorTela.setVisible(false);
			paraRegistroProfessor(usuario);
		}else if(registroAlunoTela != null){
			registroAlunoTela.setVisible(false);
			paraRegistroProfessor(usuario);
		}else if(configuracaoTela != null) {
			configuracaoTela.setVisible(false);
			paraRegistroProfessor(usuario);
		}
	}
	
	public MudarSenhaTela navegarParaMudarSenha(ModelUsuario usuario) {
		MudarSenhaTela mudarSenha = null;
		if(mudarSenhaTela == null) {
			mudarSenha = paraMudarSenha(usuario);
		}
		return mudarSenha;
	}

	public void navegarParaConfiguracao(ModelUsuario usuario) {
		if(cadastroAlunoTela == null && cadastroProfessorTela == null && registroAlunoTela == null && registroProfessorTela == null) {
			paraConfiguracao(usuario);
		}else if(cadastroAlunoTela != null) {
			cadastroAlunoTela.setVisible(false);
			paraConfiguracao(usuario);	
		}else if(cadastroProfessorTela != null){
			cadastroProfessorTela.setVisible(false);
			paraConfiguracao(usuario);	
		}else if(registroAlunoTela != null){
			registroAlunoTela.setVisible(false);
			paraConfiguracao(usuario);
		}else if(registroProfessorTela != null) {
			registroProfessorTela.setVisible(false);
			paraConfiguracao(usuario);
		}
	}
	
	public EditarCustosTela navegarParaEditarCustosBasicos(ModelUsuario usuario) {
		EditarCustosTela editarCusto = paraEditarCustos(usuario);
		return editarCusto;
		
	}
	
	public EditarFuncionariosTela navegarParaEditarFuncionarios(ModelUsuario usuario) {
		EditarFuncionariosTela editarFuncionario = paraEditarFuncionarios(usuario);
		return editarFuncionario;
		
	}
	
	public EditarModalidadeTela navegarParaEditarModalidades(ModelUsuario usuario) {
		EditarModalidadeTela editarModalidade = paraEditarModalidades(usuario);
		return editarModalidade;
		
	}
	
	public EditarPlanosTela navegarParaEditarPlanos(ModelUsuario usuario) {
		EditarPlanosTela editarPlano = paraEditarPlanos(usuario);
		return editarPlano;
		
	}

	public void sair() {                                  
		  if (JOptionPane.showConfirmDialog(null, "Você deseja realmente sair?", "Deseja sair?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION) {
		      getTelaAtual().setVisible(false);   	
		      System.gc();
		        for (Window window: Window.getWindows()) {
		        	window.dispose();
		        }
		    paraSair();
		}			
	}
	
	public void paraConfiguracao(ModelUsuario usuario) {
		if(ConfiguracaoTela.getConfiguracao(usuario) == null) {
			ConfiguracaoTela.getConfiguracao(usuario);
		}else {
			ConfiguracaoTela.getConfiguracao(usuario).setState(JFrame.NORMAL);
			ConfiguracaoTela.getConfiguracao(usuario).setVisible(true);
		}
	}
	
	public void paraDashboard(ModelUsuario usuario) {
		if(DashboardTela.getDashboard(usuario) == null) {
			DashboardTela.getDashboard(usuario);
		}else {
			DashboardTela.getDashboard(usuario).setState(JFrame.NORMAL);
			DashboardTela.getDashboard(usuario).setVisible(true);
		}
	}
	
	private EditarCustosTela paraEditarCustos(ModelUsuario usuario) {
		EditarCustosTela editarCustosTela = new EditarCustosTela(usuario);
		editarCustosTela.setResizable(false);
		editarCustosTela.setTitle("Editar Custos Básicos");
		editarCustosTela.setLocationRelativeTo(null);
		editarCustosTela.setVisible(true);
		return editarCustosTela;
	}
	
	private EditarFuncionariosTela paraEditarFuncionarios(ModelUsuario usuario) { 
		EditarFuncionariosTela editarFuncionariosTela = new EditarFuncionariosTela(usuario);
		editarFuncionariosTela.setResizable(false);
		editarFuncionariosTela.setTitle("Editar Funcionários");
		editarFuncionariosTela.setLocationRelativeTo(null);
		editarFuncionariosTela.setVisible(true);
		return editarFuncionariosTela;
	}
	
	private EditarModalidadeTela paraEditarModalidades(ModelUsuario usuario) {
		EditarModalidadeTela editarModalidadeTela = new EditarModalidadeTela(usuario);
		editarModalidadeTela.setResizable(false);
		editarModalidadeTela.setTitle("Editar Modalidades");
		editarModalidadeTela.setLocationRelativeTo(null);
		editarModalidadeTela.setVisible(true);
		return editarModalidadeTela;
	}
	
	private EditarPlanosTela paraEditarPlanos(ModelUsuario usuario) {
		EditarPlanosTela editarPlanosTela = new EditarPlanosTela(usuario);
		editarPlanosTela.setResizable(false);
		editarPlanosTela.setTitle("Editar Planos");
		editarPlanosTela.setLocationRelativeTo(null);
		editarPlanosTela.setVisible(true);
		return editarPlanosTela;
	}
	
	private void paraCadastroAluno(ModelUsuario usuario) {
		if(CadastroAlunoTela.getCadastroAluno(usuario) == null) {
			CadastroAlunoTela.getCadastroAluno(usuario);
		}else {
			CadastroAlunoTela.getCadastroAluno(usuario).setState(JFrame.NORMAL);
		}
	}
	
	private void paraCadastroProfessor(ModelUsuario usuario) {
		if(CadastroProfessorTela.getCadastroProfessor(usuario) == null) {
			CadastroProfessorTela.getCadastroProfessor(usuario);
		}else {
			CadastroProfessorTela.getCadastroProfessor(usuario).setState(JFrame.NORMAL);
		}
	}
	
	private void paraRegistroAluno(ModelUsuario usuario) {
		if(RegistroAlunoTela.getRegistroAluno(usuario) == null) {
			RegistroAlunoTela.getRegistroAluno(usuario);
		}else {
			RegistroAlunoTela.getRegistroAluno(usuario).setState(JFrame.NORMAL);
		}
	}
	
	public DadosAlunoTela irParaDadosAluno(ModelRegistroAluno registroAluno, ModelUsuario usuario) {
		DadosAlunoTela dadosAlunoTela = new DadosAlunoTela(usuario, registroAluno);
		dadosAlunoTela.setResizable(false);
		dadosAlunoTela.setTitle("Dados do aluno: " + registroAluno.getAluno().getNome() + ".");
		dadosAlunoTela.setLocationRelativeTo(null);
		dadosAlunoTela.setVisible(true);
		return dadosAlunoTela;
	}
	
	public void paraRegistroProfessor(ModelUsuario usuario) {
		if(RegistroProfessorTela.getRegistroProfessor(usuario) == null) {
			RegistroProfessorTela.getRegistroProfessor(usuario);
		}else {
			RegistroProfessorTela.getRegistroProfessor(usuario).setState(JFrame.NORMAL);
		}
	}
	
	public DadosProfessorTela irParaDadosProfessor(ModelRegistroProfessor registroProfessor, ModelUsuario usuario) {
		DadosProfessorTela dadosProfessorTela = new DadosProfessorTela(usuario, registroProfessor);
		dadosProfessorTela.setResizable(false);
		dadosProfessorTela.setTitle("Dados do professor: " + registroProfessor.getProfessor().getNome() + ".");
		dadosProfessorTela.setLocationRelativeTo(null);
		dadosProfessorTela.setVisible(true);
		return dadosProfessorTela;
	}
	
	private MudarSenhaTela paraMudarSenha(ModelUsuario usuario) {
		MudarSenhaTela mudarSenhaTela = new MudarSenhaTela(usuario);
		mudarSenhaTela.setResizable(false);
		mudarSenhaTela.setTitle("Mudar Senha de usuário");
		mudarSenhaTela.setLocationRelativeTo(null);
		mudarSenhaTela.setVisible(true);
		return mudarSenhaTela;
	}
	
	public void paraSair() {
		LoginTela loginTela = new LoginTela();
		loginTela.setResizable(false);
		loginTela.setTitle("Login");
		loginTela.setLocationRelativeTo(null);
		loginTela.setVisible(true); 
	}
	
	public void irParaRegistroProfessorEmExpediente(ArrayList<ModelProfessor> professores, ModelUsuario usuario) {
		if(RegistroProfessorTela.getRegistroProfessor(usuario) == null) {
			RegistroProfessorTela.getRegistroProfessor(usuario);
		}else {
			RegistroProfessorTela.getRegistroProfessor(usuario).setState(JFrame.NORMAL);
		}
		
		DefaultTableModel tableModel = (DefaultTableModel) RegistroProfessorTela.getRegistroProfessor(usuario).getTableRegistroProfessores().getModel();
		
		tableModel.setNumRows(0);
		
		for(ModelProfessor professor: professores) {
			tableModel.addRow(new Object[] {
				professor.getId(),
				professor.getNome(),
				professor.getHoraEntradaFormatada(),
				professor.getHoraSaidaFormatada(),
				professor.getSalario(),
				professor.getRegistroProfessor().getAtivo(),
				professor.getRegistroProfessor().getDetalhe()
			});
		}
		
		RegistroProfessorTela.getRegistroProfessor(usuario).setTitle("Registro Professor em Expediente");
		RegistroProfessorTela.getRegistroProfessor(usuario).getLblRegistroProfessores().setText("Registro Professor em Expediente");
		RegistroProfessorTela.getRegistroProfessor(usuario).getLblRegistroProfessores().setBounds(400, 52, 545, 40);
		RegistroProfessorTela.getRegistroProfessor(usuario).getTextFieldTotalDeBuscas().setText(RegistroProfessorTela.getRegistroProfessor(usuario).getTableRegistroProfessores().getRowCount() + "");
	}

	public void paraConfiguracaoPeriodo(ModelUsuario usuario) {
		Timer timer1 = new Timer();

		if (JOptionPane.showConfirmDialog(null, 
				"Deseja ir para Configuração > Nome da Academia e Período", "Aviso",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		
			ConfiguracaoTela configuracaoTela = new ConfiguracaoTela(usuario);
			configuracaoTela.setVisible(true);
			configuracaoTela.setLocationRelativeTo(null);
			configuracaoTela.setTitle("Configurações");
	
			
			timer1.scheduleAtFixedRate(new TimerTask() {
	            public void run() {

	            	pegarCoresPrimariasESecundarias(usuario);
	            	
	            	int alpha = 30;
	            	for(int i = 1000; i <= 5000; i = i + 200) {
	
	                    configuracaoTela.getLblNomeAcademia().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getLblHorario().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getLblDias().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getLblPeriodoDiaA().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getLblPeriodoHorarioAS().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getLblPeriodoFuncionamento().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getComboBoxDiaComeco().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getComboBoxDiaFim().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getTextFieldHoraAbrir().setForeground(new Color(r, g, b, alpha + 10));
	                    configuracaoTela.getTextFieldHoraFechar().setForeground(new Color(r, g, b, alpha + 10));
	                    
	            		try {
	            			Thread.sleep(100);
						} catch (InterruptedException e) {
						
						}
	            		alpha = alpha + 10;
	            	}
	            }
	        }, 1, 90000000);
		}
	}
	
	public void paraConfiguracaoPlanos(ModelUsuario usuario) {
		Timer timer1 = new Timer();
		
		ConfiguracaoTela configuracaoTela = new ConfiguracaoTela(usuario);
		configuracaoTela.setVisible(true);
		configuracaoTela.setLocationRelativeTo(null);
		configuracaoTela.setTitle("Configurações");
		
		List<ModelPlano> planosPopulares = new ArrayList<>();
		
		for (int i = getPlanoPopular(usuario).size() - 1; i >= 0; i--) {
			planosPopulares.add(getPlanoPopular(usuario).get(i));
		}
		
		if(getPlanoPopular(usuario).size() == 0) {
			planosPopulares = usuario.getPlanos();
		}
		
		DefaultTableModel tableModel = (DefaultTableModel) configuracaoTela.getTablePlanos().getModel();
		
		tableModel.setNumRows(0);
		
		for(ModelPlano planos: planosPopulares) {
			tableModel.addRow(new Object[] {
				planos.getNome(),
				planos.getPeriodo(),
				planos.getValor(),
				planos.getAlunosAtivos()
			});
		}
        
        JTableHeader header =  configuracaoTela.getTablePlanos().getTableHeader();
        timer1.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	
            	pegarCoresPrimariasESecundarias(usuario);
            	
            	int alpha = 30;
            	for(int i = 1000; i <= 5000; i = i + 200) {

                    configuracaoTela.getTablePlanos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getLblPlanos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarPlanos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarPlanos().setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header.setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header.setForeground(new Color(r, g, b, alpha + 5));
            		try {
            			Thread.sleep(100);
					} catch (InterruptedException e) {
					
					}
            		alpha = alpha + 10;
            	}
            }
        }, 1, 90000000);
		
	}
	
	private List<ModelPlano> getPlanoPopular(ModelUsuario usuario) {
		
		ArrayList<ModelPlano> planos = new ArrayList<>();
		
		int[] modal = new int[usuario.getPlanos().size()];
	
		for (int i = 0; i < usuario.getPlanos().size(); i++) {
			modal[i] = usuario.getModalidades().get(i).getAlunosAtivos();
		}
		
		Arrays.sort(modal);
		
		for(int j = 0; j < modal.length; j++) {
			for (int i = 0; i < usuario.getPlanos().size(); i++) {
				if(usuario.getPlanos().get(i).getAlunos().size() == modal[j]) {
					planos.add(usuario.getPlanos().get(i));
				}
			}
		}
		
		ModelPlano[] ja = new ModelPlano[planos.size()];
		
		for(int i = 0; i < planos.size(); i++) {
			ja[i] = planos.get(i);
		}
		
		List<ModelPlano> distinctList = Arrays.asList(ja).stream().distinct().collect(Collectors.toList());
		
		return distinctList;
		
	}
	
	public void paraConfiguracaoFinancas(ModelUsuario usuario) {
		
		ConfiguracaoTela configuracaoTela = new ConfiguracaoTela(usuario);
		configuracaoTela.setVisible(true);
		configuracaoTela.setLocationRelativeTo(null);
		configuracaoTela.setTitle("Configurações");
        
        JTableHeader header =  configuracaoTela.getTablePlanos().getTableHeader();
        JTableHeader header2 =  configuracaoTela.getTableCustosBasicos().getTableHeader();
        JTableHeader header3 =  configuracaoTela.getTableFuncionarios().getTableHeader();
        JTableHeader header4 =  configuracaoTela.getTableModalidades().getTableHeader();
        
		Timer timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	
            	pegarCoresPrimariasESecundarias(usuario);
            	
            	int alpha = 30;
            	for(int i = 1000; i <= 5000; i = i + 200) {

            		configuracaoTela.getLblPlanos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getTablePlanos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarPlanos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarPlanos().setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header.setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header.setForeground(new Color(r, g, b, alpha + 5));
                    header.setEnabled(false);
                    
                    configuracaoTela.getTableCustosBasicos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getLblCustosBasicos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarCustos().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarCustos().setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header2.setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header2.setForeground(new Color(r, g, b, alpha + 5));
                    header2.setEnabled(false);
                    
                    configuracaoTela.getTableFuncionarios().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getLblFuncionarios().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarFuncionarios().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarFuncionarios().setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header3.setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header3.setForeground(new Color(r, g, b, alpha + 5));
                    header3.setEnabled(false);
                    
                    configuracaoTela.getTableModalidades().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getLblModalidadas().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarModalidades().setForeground(new Color(r, g, b, alpha + 10));
                    configuracaoTela.getBtnEditarModalidades().setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header4.setBorder(new LineBorder(new Color(r, g, b, alpha + 10)));
                    header4.setForeground(new Color(r, g, b, alpha + 5));
                    header4.setEnabled(false);
                    
            		try {
            			Thread.sleep(100);
					} catch (InterruptedException e) {
					
					}
            		alpha = alpha + 10;
            	}
            	
            }
        }, 1, 90000000);
		
	}
	
	private void pegarCoresPrimariasESecundarias(ModelUsuario usuario) {
		if(usuario.getConfiguracao() == null) {
    		r = 255; 
    		g = 255; 
    		b = 255;
    	}else {				
    		r = usuario.getConfiguracao().getCorPrimariaColor(usuario).getRed();
    		g = usuario.getConfiguracao().getCorPrimariaColor(usuario).getGreen();
    		b = usuario.getConfiguracao().getCorPrimariaColor(usuario).getBlue();
    	}
	}

	public JFrame getTelaAtual() {
		if(dashboardTela != null) {
			return dashboardTela;
		}else if(cadastroAlunoTela != null) {
			return cadastroAlunoTela;
		}else if(cadastroProfessorTela != null) {
			return cadastroProfessorTela;
		}else if(registroAlunoTela != null) {
			return registroAlunoTela;
		}else if(registroProfessorTela != null) {
			return registroProfessorTela;
		}else if(configuracaoTela != null){
			return configuracaoTela;
		}else {
			return null;
		}
	}



}
