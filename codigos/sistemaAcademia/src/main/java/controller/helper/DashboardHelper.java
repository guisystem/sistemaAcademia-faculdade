package controller.helper;

import java.awt.Color;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;

import model.ModelModalidade;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import view.DashboardTela;

public class DashboardHelper {

	private final DashboardTela dashboardTela;

	public DashboardHelper(DashboardTela helper) {
		super();
		this.dashboardTela = helper;
	}

	public void atualizarTela(ModelUsuario usuario) {
		dashboardTela.getLblNomeAcademia().setText(usuario.getConfiguracao().getNomeDaAcademia() + " - " + getStatus(usuario));
		
		dashboardTela.getTextFieldQuantAlunos().setText(usuario.getAlunosAtivos().size() + "");
		dashboardTela.getTextFieldQuantProfessores().setText(usuario.getProfessoresAtivos().size() + "");
		dashboardTela.getTextFieldQuantProfessoresExpediente().setText(getProfessoresExpediante(usuario).size() + "");
		
		dashboardTela.getTextFieldGastos().setText(usuario.getGastosTotal() + "");
		dashboardTela.getTextFieldGanhos().setText(usuario.getGanhosTotal() + "");
		dashboardTela.getTextFieldLucro().setText(usuario.getLucroTotal() + "");
		
		dashboardTela.getTextFieldQuantPlanos().setText(usuario.getQuantidadePlanos() + "");
		dashboardTela.getTextFieldPlanoPopular().setText(usuario.getPlanoPopular());
		
		List<ModelModalidade> modalidades = getModalidadesPopulares(usuario);
		
		if(modalidades.size() >= 3) {
			if(usuario.getAlunos().isEmpty() || !usuario.getAlunos().get(usuario.getAlunos().size() - 1).getNome().equals("")) {
				dashboardTela.getTextFieldNomeModalidade1().setText(modalidades.get(modalidades.size() - 1).getNome());
				dashboardTela.getTextFieldNomeModalidade2().setText(modalidades.get(modalidades.size() - 2).getNome());		
				dashboardTela.getTextFieldNomeModalidade3().setText(modalidades.get(modalidades.size() - 3).getNome());
				
				dashboardTela.getTextFieldQuantAluno1().setText(modalidades.get(modalidades.size() - 1).getAlunosAtivos() + "");
				dashboardTela.getTextFieldQuantAluno2().setText(modalidades.get(modalidades.size() - 2).getAlunosAtivos() + "");
				dashboardTela.getTextFieldQuantAluno3().setText(modalidades.get(modalidades.size() - 3).getAlunosAtivos() + "");
			}
			
		}else if(modalidades.size() == 2) {
			if(usuario.getAlunos().isEmpty() || !usuario.getAlunos().get(usuario.getAlunos().size() - 1).getNome().equals("")) {
				dashboardTela.getTextFieldNomeModalidade1().setText(modalidades.get(modalidades.size() - 1).getNome());
				dashboardTela.getTextFieldNomeModalidade2().setText(modalidades.get(modalidades.size() - 2).getNome());		
				
				dashboardTela.getTextFieldQuantAluno1().setText(modalidades.get(modalidades.size() - 1).getAlunosAtivos() + "");
				dashboardTela.getTextFieldQuantAluno2().setText(modalidades.get(modalidades.size() - 2).getAlunosAtivos() + "");
			}
			
		}else if(modalidades.size() == 1){
			if(usuario.getAlunos().isEmpty() || !usuario.getAlunos().get(usuario.getAlunos().size() - 1).getNome().equals("")) {
				dashboardTela.getTextFieldNomeModalidade1().setText(modalidades.get(modalidades.size() - 1).getNome());
				
				dashboardTela.getTextFieldQuantAluno1().setText(modalidades.get(modalidades.size() - 1).getAlunosAtivos() + "");
			}
			
		}else {
			
		}
		
	}
	
	public ArrayList<ModelProfessor> getProfessoresExpediante(ModelUsuario usuario) {
		
		ArrayList<ModelProfessor> professoresExpediente = new ArrayList<>();
		for(ModelRegistroProfessor registroProfessor: usuario.getProfessoresAtivos()) {
			if(diaDeTrabalho(registroProfessor.getProfessor()) && horaExpediente(registroProfessor.getProfessor())) {
				professoresExpediente.add(registroProfessor.getProfessor());
			}
		}
		
		return professoresExpediente;
	}

	private boolean diaDeTrabalho(ModelProfessor professor) {
		LocalDate hoje = LocalDate.now();
		
		DayOfWeek dia = hoje.getDayOfWeek();

		for(DayOfWeek dias: professor.getDiasDeTrabalho()) {
			if(dias.equals(dia)) {
				return true;
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("deprecation")
	private boolean horaExpediente(ModelProfessor professor) {
		Date hora = new Date();
		
		if(professor.getHorarioEntrada().equals(professor.getHorarioSaida())) {
			return true;			
		}else {
			if(professor.getHorarioEntrada().getHours() < hora.getHours() && 
					professor.getHorarioSaida().getHours() > hora.getHours() || 
					professor.getHorarioEntrada().getHours() < hora.getHours() && 
					professor.getHorarioSaida().getHours() == 0) {
				return true;
			}else {
				if(professor.getHorarioEntrada().getHours() == professor.getHorarioSaida().getHours()) {
					if(professor.getHorarioEntrada().getMinutes() <= hora.getMinutes() && 
							professor.getHorarioSaida().getMinutes() >= hora.getMinutes()) {
						return true;
					}else {
						return false;
					}
				}else {
					if(professor.getHorarioEntrada().getHours() == hora.getHours() && 
							professor.getHorarioEntrada().getMinutes() <= hora.getMinutes()) {
						return true;
					}else {
						if(professor.getHorarioSaida().getHours() == hora.getHours() &&
								professor.getHorarioSaida().getMinutes() >= hora.getMinutes()) {
							return true;
						}else {
							return false;									
						}
					}
				}
			}
		}	
	} 

	private String getStatus(ModelUsuario usuario) {
		
		if(diaAtualEntreDiaFecharEAbrir(usuario) && horaAtualEntreHoraFecharEAbrir(usuario)) {
			return "Aberto";							
		}else {
			return "Fechado";								
		}
		
	}

	private boolean diaAtualEntreDiaFecharEAbrir(ModelUsuario usuario) {
		LocalDate hoje = LocalDate.now();
		
		DayOfWeek dia = hoje.getDayOfWeek();
		
		if(usuario.getConfiguracao().getDiaDeAbrirDate().getValue() <= dia.getValue() && 
				usuario.getConfiguracao().getDiaDeFecharDate().getValue() >= dia.getValue()) {
			return true;
		}else {
			if(usuario.getConfiguracao().getDiaDeAbrirDate().getValue() == usuario.getConfiguracao().getDiaDeFecharDate().getValue()) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private boolean horaAtualEntreHoraFecharEAbrir(ModelUsuario usuario) {
		Date hora = new Date();
		
		if(usuario.getConfiguracao().getHoraDeAbrir().equals(usuario.getConfiguracao().getHoraDeFechar())) {
			return true;			
		}else {
			if(usuario.getConfiguracao().getHoraDeAbrir().getHours() < hora.getHours() && 
					usuario.getConfiguracao().getHoraDeFechar().getHours() > hora.getHours() || 
					usuario.getConfiguracao().getHoraDeAbrir().getHours() < hora.getHours() && 
					usuario.getConfiguracao().getHoraDeFechar().getHours() == 0) {
				return true;
			}else {
				if(usuario.getConfiguracao().getHoraDeAbrir().getHours() == usuario.getConfiguracao().getHoraDeFechar().getHours()) {
					if(usuario.getConfiguracao().getHoraDeAbrir().getMinutes() <= hora.getMinutes() && 
							usuario.getConfiguracao().getHoraDeFechar().getMinutes() >= hora.getMinutes()) {
						return true;
					}else {
						return false;
					}
				}else {
					if(usuario.getConfiguracao().getHoraDeAbrir().getHours() == hora.getHours() && 
							usuario.getConfiguracao().getHoraDeAbrir().getMinutes() <= hora.getMinutes()) {
						return true;
					}else {
						if(usuario.getConfiguracao().getHoraDeFechar().getHours() == hora.getHours() &&
								usuario.getConfiguracao().getHoraDeFechar().getMinutes() >= hora.getMinutes()) {
							return true;
						}else {
							return false;									
						}
					}
				}
			}
		}	
	}

	private List<ModelModalidade> getModalidadesPopulares(ModelUsuario usuario) {
	
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		
		int[] modal = new int[usuario.getModalidades().size()]; 
	
		for (int i = 0; i < usuario.getModalidades().size(); i++) {
			modal[i] = usuario.getModalidades().get(i).getAlunosAtivos();
		}
		
		Arrays.sort(modal);
		
		for(int j = 0; j < modal.length; j++) {
			for (int i = 0; i < usuario.getModalidades().size(); i++) {
				
				if(usuario.getModalidades().get(i).getAlunosAtivos() == modal[j]) {
					modalidades.add(usuario.getModalidades().get(i));
				}
			}
		}
		
		ModelModalidade[] ja = new ModelModalidade[modalidades.size()];
		
		for(int i = 0; i < modalidades.size(); i++) {
			ja[i] = modalidades.get(i);
		}
		
		List<ModelModalidade> distinctList = Arrays.asList(ja).stream().distinct().collect(Collectors.toList());
		
		return distinctList;
		
	}

	public void setarCoresPrimariasNaTela(ArrayList<Color> coresPrimarias) {
		
		if(coresPrimarias.size() == 1) {
			
			dashboardTela.getLblAlunoPorModalidade().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblEditarFinancas().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblFundoDashboard().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblGanhos().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblGastos().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblLucro().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblModalidades().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblNomeAcademia().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblPlanoPopular().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblQuantAlunos().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblQuantPlanos().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblQuantProfessores().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblProfessoresEmExpediente().setForeground(coresPrimarias.get(0));
			
			dashboardTela.getTextFieldGanhos().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldGanhos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldGastos().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldGastos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldLucro().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldLucro().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldNomeModalidade1().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldNomeModalidade1().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldNomeModalidade2().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldNomeModalidade2().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldNomeModalidade3().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldNomeModalidade3().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldPlanoPopular().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldPlanoPopular().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldQuantAluno1().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldQuantAluno1().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldQuantAluno2().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldQuantAluno2().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldQuantAluno3().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldQuantAluno3().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldQuantAlunos().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldQuantAlunos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldQuantPlanos().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldQuantPlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldQuantProfessores().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldQuantProfessores().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getTextFieldQuantProfessoresExpediente().setForeground(coresPrimarias.get(0));
			dashboardTela.getTextFieldQuantProfessoresExpediente().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnFinancas().setForeground(coresPrimarias.get(0));
			
			dashboardTela.getBtnVerMaisAlunos().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisAlunos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisPlanoPopular().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisPlanoPopular().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisProfessores().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisProfessores().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisProfessoresExpediente().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisProfessoresExpediente().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisQuantPlanos().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisQuantPlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			if(coresPrimarias.get(0).equals(new Color(0, 0, 0))) { /** NOTE: Mudar as cores do botão e painel de finanças se a tela for preta */
				dashboardTela.getBtnFinancas().setForeground(new Color(50, 50, 50)); 
				dashboardTela.getPainelFinancas().setBackground(new Color(23, 23, 21));
			}else { /** NOTE: Mudar as cores do botão e painel de finanças se a tela for branca */
				dashboardTela.getBtnFinancas().setForeground(new Color(205, 205, 205));
				dashboardTela.getPainelFinancas().setBackground(new Color(232, 232, 234));
			}
			
		}else {
			
			dashboardTela.getLblNomeAcademia().setForeground(coresPrimarias.get(0));
			dashboardTela.getLblAlunoPorModalidade().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblEditarFinancas().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblFundoDashboard().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblGanhos().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblGastos().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblLucro().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblModalidades().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblPlanoPopular().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblQuantAlunos().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblQuantPlanos().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblQuantProfessores().setForeground(coresPrimarias.get(1));
			dashboardTela.getLblProfessoresEmExpediente().setForeground(coresPrimarias.get(1));
			
			dashboardTela.getTextFieldGanhos().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldGanhos().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldGastos().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldGastos().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldLucro().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldLucro().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldNomeModalidade1().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldNomeModalidade1().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldNomeModalidade2().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldNomeModalidade2().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldNomeModalidade3().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldNomeModalidade3().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldPlanoPopular().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldPlanoPopular().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldQuantAluno1().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldQuantAluno1().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldQuantAluno2().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldQuantAluno2().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldQuantAluno3().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldQuantAluno3().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldQuantAlunos().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldQuantAlunos().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldQuantPlanos().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldQuantPlanos().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldQuantProfessores().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldQuantProfessores().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getTextFieldQuantProfessoresExpediente().setForeground(coresPrimarias.get(1));
			dashboardTela.getTextFieldQuantProfessoresExpediente().setBorder(new LineBorder(coresPrimarias.get(1)));
			
			dashboardTela.getBtnFinancas().setForeground(coresPrimarias.get(2));
			
			dashboardTela.getBtnVerMaisAlunos().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisAlunos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisPlanoPopular().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisPlanoPopular().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisProfessores().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisProfessores().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisProfessoresExpediente().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisProfessoresExpediente().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getBtnVerMaisQuantPlanos().setForeground(coresPrimarias.get(0));
			dashboardTela.getBtnVerMaisQuantPlanos().setBorder(new LineBorder(coresPrimarias.get(0)));
			
			dashboardTela.getPainelFinancas().setBackground(coresPrimarias.get(2));
			
		}
		
	}

	public void setarCoresSecundariasNaTela(ArrayList<Color> coresSecundarias) {
		if(coresSecundarias.size() == 1) {
			
			dashboardTela.getLblGanhos().setForeground(coresSecundarias.get(0));
			dashboardTela.getLblLucro().setForeground(coresSecundarias.get(0));
			dashboardTela.getLblGastos().setForeground(coresSecundarias.get(0));
			
			dashboardTela.getTextFieldGanhos().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldGastos().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldLucro().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldNomeModalidade1().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldNomeModalidade2().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldNomeModalidade3().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldPlanoPopular().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldQuantAluno1().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldQuantAluno2().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldQuantAluno3().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldQuantAlunos().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldQuantPlanos().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldQuantProfessores().setBackground(coresSecundarias.get(0));
			dashboardTela.getTextFieldQuantProfessoresExpediente().setBackground(coresSecundarias.get(0));
			
			dashboardTela.getBtnVerMaisAlunos().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisPlanoPopular().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisProfessores().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisProfessoresExpediente().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisQuantPlanos().setBackground(coresSecundarias.get(0));
			
			dashboardTela.getContentPane().setBackground(coresSecundarias.get(0));
			dashboardTela.getPainelFinancas().setForeground(coresSecundarias.get(0));
			
			if(coresSecundarias.get(0).equals(new Color(255, 255, 255))) { /** NOTE: Mudar os paineis do dashboard se a tela for branca */
				dashboardTela.getLblPainelDados().setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/painel-dados-dashboard-fundobranco.png")));
				dashboardTela.getLblPainelPlanos().setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/painel-planos-dashboard-fundobranco.png")));
				dashboardTela.getLblEditarFinancas().setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/botao-financas-fundobranco.png")));
			}else { /** NOTE: Mudar os paineis do dashboard se a tela for preta */
				dashboardTela.getLblPainelDados().setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/painel-dados-dashboard.png")));
				dashboardTela.getLblPainelPlanos().setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/painel-planos-dashboard.png")));
				dashboardTela.getLblEditarFinancas().setIcon(new ImageIcon(DashboardTela.class.getResource("/view/imagens/botao-financas.png")));
			}
			
		}else {
			
			dashboardTela.getLblGanhos().setForeground(coresSecundarias.get(0));
			dashboardTela.getLblLucro().setForeground(coresSecundarias.get(0));
			dashboardTela.getLblGastos().setForeground(coresSecundarias.get(0));
			
			dashboardTela.getTextFieldGanhos().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldGastos().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldLucro().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldNomeModalidade1().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldNomeModalidade2().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldNomeModalidade3().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldPlanoPopular().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldQuantAluno1().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldQuantAluno2().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldQuantAluno3().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldQuantAlunos().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldQuantPlanos().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldQuantProfessores().setBackground(coresSecundarias.get(2));
			dashboardTela.getTextFieldQuantProfessoresExpediente().setBackground(coresSecundarias.get(2));
			
			dashboardTela.getBtnVerMaisAlunos().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisPlanoPopular().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisProfessores().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisProfessoresExpediente().setBackground(coresSecundarias.get(0));
			dashboardTela.getBtnVerMaisQuantPlanos().setBackground(coresSecundarias.get(0));
			
			dashboardTela.getContentPane().setBackground(coresSecundarias.get(0));
			dashboardTela.getPainelFinancas().setForeground(coresSecundarias.get(0));
			
		}
	}
}
