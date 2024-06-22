package controller;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import controller.helper.DadosProfessorHelper;
import model.ModelCores;
import model.ModelEspecialidade;
import model.ModelFoto;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import model.dao.FotoDAO;
import model.dao.ProfessorDAO;
import model.dao.RegistroProfessorDAO;
import model.dao.UsuarioDAO;
import servico.Criptografar;
import servico.JPAUtil;
import view.DadosProfessorTela;

public class DadosProfessorController {

	private final DadosProfessorTela dadosProfessorTela;
	private final DadosProfessorHelper helper;
	private EntityManager em;
	
	public DadosProfessorController(DadosProfessorTela dadosProfessorTela) {
		this.em = new JPAUtil().getEntityManager();
		this.dadosProfessorTela = dadosProfessorTela;
		this.helper = new DadosProfessorHelper(dadosProfessorTela);
	}

	public void atualizarDados(ModelUsuario usuario, ModelRegistroProfessor registroProfessor) {
		helper.preencherDadosProfessor(usuario, registroProfessor);
		
	}

	public void cancelarAlteracoes(ModelRegistroProfessor registroProfessor, ArrayList<ModelEspecialidade> especialidades) {
		
		if(helper.compararDados(registroProfessor, especialidades)) {
			dadosProfessorTela.setVisible(false);
		}else {
			if (dadosProfessorTela.exibirMensagemDecisao("Se cancelar irá perder todas as alterações realizadas. "
					+ "Deseja mesmo sair?")) {
				dadosProfessorTela.setVisible(false);					
			}
		}
		
		registroProfessor.getProfessor().getEspecialidades().clear();
		for (ModelEspecialidade especialidade : especialidades) {
			registroProfessor.getProfessor().getEspecialidades().add(especialidade);
		}
		
	}

	public ArrayList<Object> confirmarAlteracoes(ModelUsuario usuario, ModelRegistroProfessor registroProfessor, ArrayList<ModelEspecialidade> especialidades) {
		
		em.getTransaction().begin(); 
		
		ArrayList<Object> inseridos = new ArrayList<>();
		if(helper.verificarDados()) {
			if(helper.verificarTipoDataDeContrato()) {
				if(helper.verificarSalario()) {
					if(helper.verificarData(dadosProfessorTela.getTextFieldDataAdmissao().getText())) {
						if(helper.verificarHora(dadosProfessorTela.getTextFieldHoraEntrada().getText())) {
							if(helper.verificarHora(dadosProfessorTela.getTextFieldHoraSaida().getText())) {
								if(helper.verificarTempoContrato(dadosProfessorTela.getTextFieldContrato().getText())) {
									if(helper.verificarCPF(dadosProfessorTela.getTextFieldCpfProfessor().getText())) {
										if(helper.validarEmailECpf(usuario, registroProfessor, dadosProfessorTela.getTextFieldCpfProfessor().getText(), dadosProfessorTela.getTextFieldEmailProfessor().getText())) {
											if(helper.verificarDiasSelecionados()) {
												if(helper.compararDados(registroProfessor, especialidades) == false) {
													if (dadosProfessorTela.exibirMensagemDecisao("Deseja confirmar as alterações?")) {
														
														ModelProfessor professor = helper.obterModelo(registroProfessor);
														ModelFoto fotoProfessor = helper.obterFoto(registroProfessor);
														ModelRegistroProfessor registro = helper.obterModeloRegistro(registroProfessor);
														
														professor.setCpf(Criptografar.criptografar(professor.getCpf()));
														professor.setEmail(Criptografar.criptografar(professor.getEmail()));
														
														new UsuarioDAO(em).insertOrUpdate(usuario);
														new ProfessorDAO(em).insertOrUpdate(professor); 
														new FotoDAO(em).insertOrUpdate(fotoProfessor);
														new RegistroProfessorDAO(em).insertOrUpdate(registro);
														
														inseridos.add(usuario);
														inseridos.add(professor);
														inseridos.add(fotoProfessor);
														inseridos.add(registro);
														
														dadosProfessorTela.exibirMensagemInformacao("Dados do professor foram alterados com sucesso!");
														
														dadosProfessorTela.setVisible(false);
														RegistroProfessorController.atualizarTabela(usuario);
													}													
												}else {													
													dadosProfessorTela.setVisible(false);
												}
											}else {
												dadosProfessorTela.exibirMensagemErro("Nenhum dia de trabalho foi selecionado. Tente Novamente!");
											}
										}
									}else {
										dadosProfessorTela.exibirMensagemErro("CPF inválido. Tente Novamente!");
									}
								}else {
									dadosProfessorTela.exibirMensagemErro("Tempo de contrato inválido. Tente Novamente!");
								}
							}else {
								dadosProfessorTela.exibirMensagemErro("Hora de saida inválida. Tente Novamente!");
							}
						}else {
							dadosProfessorTela.exibirMensagemErro("Hora de entrada inválida. Tente Novamente!");
						}
					}else {
						dadosProfessorTela.exibirMensagemErro("Data de Admissão inválida. Tente Novamente");
					}
				}else {
					dadosProfessorTela.exibirMensagemErro("Coloque um valor de salário válido.");
				}
			}else {
				dadosProfessorTela.exibirMensagemErro("Escolha 1(UM) tipo de contrato.");
			}
		}else {
			dadosProfessorTela.exibirMensagemErro("Por favor, preencher todos os campos.");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}

	public void sairDaTelaDefaultClose(ModelRegistroProfessor registroProfessor, ArrayList<ModelEspecialidade> especialidades) {
		
		if(helper.compararDados(registroProfessor, especialidades)){
			dadosProfessorTela.dispose();
    	}else {
    		if (dadosProfessorTela.exibirMensagemDecisao("Deseja fechar essa janela? Todas as alterações feitas serão perdidas")){
    			dadosProfessorTela.dispose();
    		}	    		
    	}
    	
    	registroProfessor.getProfessor().getEspecialidades().clear();
		for (ModelEspecialidade especialidade : especialidades) {
			registroProfessor.getProfessor().getEspecialidades().add(especialidade);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void adicionarEspecialidade(ModelUsuario usuario, ModelRegistroProfessor registroProfessor) {
		DefaultComboBoxModel<ModelEspecialidade> comboBoxAdicionarEspecialidade = (DefaultComboBoxModel<ModelEspecialidade>)dadosProfessorTela.getComboBoxAdicionarEspecialidade().getModel();
				
		DefaultComboBoxModel<ModelEspecialidade> comboBoxExcluirEspecialidade = (DefaultComboBoxModel<ModelEspecialidade>) dadosProfessorTela.getComboBoxExcluirEspecialidade().getModel();
		
		ModelEspecialidade especialidade = (ModelEspecialidade) comboBoxAdicionarEspecialidade.getSelectedItem();
		if(especialidade != null) {
			comboBoxExcluirEspecialidade.addElement((ModelEspecialidade) comboBoxAdicionarEspecialidade.getSelectedItem());
			comboBoxAdicionarEspecialidade.removeElement(comboBoxAdicionarEspecialidade.getSelectedItem());
			
			registroProfessor.getProfessor().adicionarEspecialidade(especialidade);
			
			DefaultTableModel tableModel = (DefaultTableModel) dadosProfessorTela.getTableEspecialidadeProfessor().getModel();
			tableModel.setNumRows(0);
			
			helper.preencherTabelaDados(registroProfessor);							
		}else {
			dadosProfessorTela.exibirMensagemErro("Nenhuma especialidade foi selecionada.");
		}
	}

	@SuppressWarnings("unchecked")
	public void removerEspecialidade(ModelUsuario usuario, ModelRegistroProfessor registroProfessor) {
		DefaultComboBoxModel<ModelEspecialidade> comboBoxAdicionarEspecialidade = (DefaultComboBoxModel<ModelEspecialidade>)dadosProfessorTela.getComboBoxAdicionarEspecialidade().getModel();
				
		DefaultComboBoxModel<ModelEspecialidade> comboBoxExcluirEspecialidade = (DefaultComboBoxModel<ModelEspecialidade>) dadosProfessorTela.getComboBoxExcluirEspecialidade().getModel();
		
		ModelEspecialidade especialidade = (ModelEspecialidade) comboBoxExcluirEspecialidade.getSelectedItem();
		if(especialidade != null) {
			comboBoxAdicionarEspecialidade.addElement((ModelEspecialidade) comboBoxExcluirEspecialidade.getSelectedItem());
			comboBoxExcluirEspecialidade.removeElement(comboBoxExcluirEspecialidade.getSelectedItem());

			registroProfessor.getProfessor().getEspecialidades().remove(especialidade);

			DefaultTableModel tableModel = (DefaultTableModel) dadosProfessorTela.getTableEspecialidadeProfessor().getModel();
			tableModel.setNumRows(0);

			helper.preencherTabelaDados(registroProfessor);
		}else {
			dadosProfessorTela.exibirMensagemErro("Nenhuma especialidade foi selecionada.");
		}
		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ModelEspecialidade> getEspecialidadesInicio() {
		DefaultComboBoxModel<ModelEspecialidade> comboBoxExcluirEspecialidade = (DefaultComboBoxModel<ModelEspecialidade>) dadosProfessorTela.getComboBoxExcluirEspecialidade().getModel();	
		ArrayList<ModelEspecialidade> especialidades = new ArrayList<>();
		
		for(int i = 0; i < comboBoxExcluirEspecialidade.getSize(); i++) {
			especialidades.add(comboBoxExcluirEspecialidade.getElementAt(i));
		}
		
		return especialidades;
	}

	public void carregarFoto() {
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha uma imagem");
        
        int userSelection = fileChooser.showOpenDialog(dadosProfessorTela);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            dadosProfessorTela.setCaminhoImagem(file.getAbsolutePath());
            dadosProfessorTela.getNomeImagemField().setText(file.getName());
            
            ImageIcon imageIcon = new ImageIcon(dadosProfessorTela.getCaminhoImagem());
            Image image = imageIcon.getImage().getScaledInstance(dadosProfessorTela.getLblMudarFoto().getWidth(), dadosProfessorTela.getLblMudarFoto().getHeight(), Image.SCALE_SMOOTH);
            dadosProfessorTela.getLblMudarFoto().setIcon(new ImageIcon(image));
        }
		
	}

	public void atualizarCores(ModelUsuario usuario) {
		if(usuario.getConfiguracao() != null) {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
			ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundarias);
		}else {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(Color.WHITE);
			ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(Color.BLACK);
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundarias);
		}
		
	}

	public String calcularTempo(ModelRegistroProfessor registroProfessor) {
		
		LocalDate dataInicial = LocalDate.parse(registroProfessor.getProfessor().getDataAdmissaoFormatada(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
		
		if(dadosProfessorTela.getRdbtnMes().isSelected()) {
			String tempoTela = dadosProfessorTela.getTextFieldContrato().getText().trim();

			int tempo = 0;
			try {
				tempo = Integer.parseInt(tempoTela);
				return "Contrato se encerra em: " + dataInicial.plusMonths(tempo);
			}catch (Exception e) {
				return "Valor de tempo de contrato inválido";
			}
			
		}else {
			String tempoTela = dadosProfessorTela.getTextFieldContrato().getText().trim();
			
			int tempo = 0;
			try {
				tempo = Integer.parseInt(tempoTela);
				return "Contrato se encerra em: " + dataInicial.plusYears(tempo);
			}catch (Exception e) {
				return "Valor de tempo de contrato inválido";
			}
			
		}
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
