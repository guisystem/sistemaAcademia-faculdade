package controller;

import java.awt.Window;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelAluno;
import model.ModelConfiguracao;
import model.ModelEspecialidade;
import model.ModelPlano;
import model.ModelProfessor;
import model.ModelRegistroAluno;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
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
import view.MudarSenhaTela;
import view.RegistroAlunoTela;
import view.RegistroProfessorTela;

class NavegacaoControllerTeste {

	private DashboardTela dashboardTela;
	private ConfiguracaoTela configuracaoTela;
	@SuppressWarnings("unused")
	private ConfiguracaoController controllerConf;
	private CadastroAlunoTela cadastroAlunoTela;
	private CadastroProfessorTela cadastroProfessorTela;
	private RegistroAlunoTela registroAlunoTela;
	private RegistroProfessorTela registroProfessorTela;
	private MudarSenhaTela mudarSenhaTela;
	private NavegacaoController controller;
	private ModelUsuario usuario;
	private ModelConfiguracao configuracao;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Branco");
		configuracao.setCorAtualSecundaria("Preto");
		configuracao.setHoraDeAbrirFormatado("00:00");
		configuracao.setHoraDeFecharFormatado("10:00");
		configuracao.setNomeDaAcademia("NomeAcademia");
		configuracao.setDiaDeAbrir("Segunda");
		configuracao.setDiaDeFechar("Domingo");
		usuario.setConfiguracao(configuracao);
		configuracao.setUsuario(usuario);
		configuracaoTela = new ConfiguracaoTela(usuario);
		controllerConf = new ConfiguracaoController(configuracaoTela);
		controller = new NavegacaoController(dashboardTela, cadastroAlunoTela, cadastroProfessorTela, 
				registroAlunoTela, registroProfessorTela, configuracaoTela, mudarSenhaTela);
	}
	
	@Test
	void testarNavegarParaDashboard() {
		dashboardTela = new DashboardTela(usuario);
		controller = new NavegacaoController(dashboardTela);
		controller.navegarParaDashBoard(usuario);
		/** Irá fazer nada */
		
	}
	
	@Test
	void testarNavegarParaDashboardDeConfiguracaoSemNomeAcademia() {
		dashboardTela = new DashboardTela(usuario);
		controller = new NavegacaoController(dashboardTela);
		controller.navegarParaDashBoardDeConfiguracao(usuario, "");
		/** Irá abrir o dashboard */
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarNavegarParaDashboardDeConfiguracaoComNomeAcademia() {
		dashboardTela = new DashboardTela(usuario);
		controller = new NavegacaoController(dashboardTela);
		controller.navegarParaDashBoardDeConfiguracao(usuario, "NovoNomeAcademia");
		/** Irá fazer nada */
		
	}
	
	@Test
	void testarNavegarParaCadastroAluno() {
		controller = new NavegacaoController(cadastroAlunoTela);
		controller.navegarParaCadastroAluno(usuario);
		/** NOTE: Terá que abrir a tela de cadastro aluno */

		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarNavegarParaCadastroProfessor() {
		controller = new NavegacaoController(cadastroProfessorTela);
		controller.navegarParaCadastroProfessor(usuario);
		/** NOTE: Terá que abrir a tela de cadastro professor */
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarNavegarParaRegistroAluno() {
		controller = new NavegacaoController(registroAlunoTela);
		controller.navegarParaRegistroAluno(usuario);
		/** NOTE: Terá que abrir a tela de registro aluno */
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarNavegarParaRegistroProfessor() {
		controller = new NavegacaoController(registroProfessorTela);
		controller.navegarParaRegistroProfessor(usuario);
		/** NOTE: Terá que abrir a tela de registro professor */

		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarNavegarParaConfiguracao() {
		controller = new NavegacaoController(configuracaoTela);
		controller.navegarParaConfiguracao(usuario);
		/** NOTE: Terá que abrir a tela de configuração */
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarNavegarParaMudarSenha() {
		controller = new NavegacaoController(mudarSenhaTela);
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	MudarSenhaTela mudarSenha = controller.navegarParaMudarSenha(usuario);
            	/** NOTE: Terá que abrir a tela de mudar senha */
            	mudarSenha.dispose();
            }
        });
		
		
	}
	
	@Test
	void testarIrParaDadosAluno() {
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
		aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(0, aluno);
		aluno.setRegistroAluno(registroAluno);
		registroAluno.setAluno(aluno);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	DadosAlunoTela dadosAluno = controller.irParaDadosAluno(registroAluno, usuario);
            	/** NOTE: Terá que abrir a tela de dados aluno */
            	dadosAluno.dispose();
            }
        });
	}
	
	@Test
	void testarIrParaDadosProfessor() {
		ModelProfessor professor = new ModelProfessor("testeNome" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, new ModelEspecialidade());
		professor.setCpf(Criptografar.criptografar(professor.getCpf()));
		professor.setEmail(Criptografar.criptografar(professor.getEmail()));
		professor.setDataAdmissao(new Date());
		professor.setTipoDeDataContrato("Ano");
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor(0, professor, "Sim");
		professor.setRegistroProfessor(registroProfessor);
		registroProfessor.setProfessor(professor);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	DadosProfessorTela dadosProfessor = controller.irParaDadosProfessor(registroProfessor, usuario);
            	/** NOTE: Terá que abrir a tela de dados professor */
            	dadosProfessor.dispose();
            }
        });
	}
	
	@Test
	void testarIrParaEditarCusto() {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	EditarCustosTela editarCusto = controller.navegarParaEditarCustosBasicos(usuario);
            	/** NOTE: Terá que abrir a tela de editar custos */
            	fechar(editarCusto);
            }
        });
		
	}
	
	@Test
	void testarIrParaEditarFuncionario() {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	EditarFuncionariosTela editarFuncinario = controller.navegarParaEditarFuncionarios(usuario);
            	/** NOTE: Terá que abrir a tela de editar funcionario */
            	fechar(editarFuncinario);
            }
        });
		
	}
	
	@Test
	void testarIrParaEditarModalidade() {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	EditarModalidadeTela editarModalidade = controller.navegarParaEditarModalidades(usuario);
            	/** NOTE: Terá que abrir a tela de editar modalidade */
            	fechar(editarModalidade);;
            }
        });
		
	}
	
	@Test
	void testarIrParaEditarPlano() {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	EditarPlanosTela editarPlano = controller.navegarParaEditarPlanos(usuario);
            	/** NOTE: Terá que abrir a tela de editar plano */
            	fechar(editarPlano);
            }
        });
		
	}
	
	protected void fechar(JDialog editarPlano) {
		try {
			Thread.sleep(500);
			editarPlano.dispose();
		} catch (InterruptedException e) {

		}
		
	}

	@Test
	void testarIrParaRegistroProfessorEmExpediente() {
		ModelProfessor professor = new ModelProfessor("testeNome" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, new ModelEspecialidade());
		professor.setCpf(Criptografar.criptografar(professor.getCpf()));
		professor.setEmail(Criptografar.criptografar(professor.getEmail()));
		professor.setDataAdmissao(new Date());
		professor.setTipoDeDataContrato("Ano");
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor(0, professor, "Sim");
		professor.setRegistroProfessor(registroProfessor);
		registroProfessor.setProfessor(professor);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		ArrayList<ModelProfessor> professores = new ArrayList<>();
		professores.add(professor);
		controller.irParaRegistroProfessorEmExpediente(professores ,usuario);
		/** NOTE: Terá que abrir a tela de registro professor em expediente */

		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
	}
	
	@Test
	void testarParaConfiguracaoPeriodo() {
		controller.paraConfiguracaoPeriodo(usuario);
		/** NOTE: Terá que abrir a tela de configuração com animação */

		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
	}
	
	@Test
	void testarParaConfiguracaoPlano() {
		controller.paraConfiguracaoPlanos(usuario);
		/** NOTE: Terá que abrir a tela de configuração com animação */

		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
	}
	
	@Test
	void testarParaConfiguracaoFinancas() {
		controller.paraConfiguracaoFinancas(usuario);
		/** NOTE: Terá que abrir a tela de configuração com animação */

		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarSair() {
		controller.sair();
		/** NOTE: Terá que sair e abrir a tela de login */

		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
	} 
}
