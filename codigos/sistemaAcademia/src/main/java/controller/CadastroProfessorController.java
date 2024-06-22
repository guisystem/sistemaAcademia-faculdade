package controller;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.helper.CadastroProfessorHelper;
import model.ModelCores;
import model.ModelEspecialidade;
import model.ModelFoto;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import model.dao.FotoDAO;
import model.dao.ProfessorDAO;
import model.dao.RegistroProfessorDAO;
import servico.Criptografar;
import servico.JPAUtil;
import view.CadastroProfessorTela;

public class CadastroProfessorController {

	private final CadastroProfessorTela cadastroProfessorTela;
	private final CadastroProfessorHelper helper;
	private EntityManager em;

	public CadastroProfessorController(CadastroProfessorTela cadastroProfessorTela) {
		super();
		this.em = new JPAUtil().getEntityManager();
		this.cadastroProfessorTela = cadastroProfessorTela;
		this.helper = new CadastroProfessorHelper(cadastroProfessorTela);
	}
	
	public void preencherEspecialidades(ModelUsuario usuario) {
		
		List<ModelEspecialidade> especialidades = usuario.getEspecialidades();
		helper.atualizarEspecialidades(especialidades);
	}

	public ArrayList<Object> cadastrarProfessor(ModelUsuario usuario) {
		
		if(usuario.getEspecialidades() == null || usuario.getEspecialidades().isEmpty()) {
			cadastroProfessorTela.exibirMensagemErro("Não é possível cadastrar. Configure seu sistema adicionando as especialidades/modalidades\r\n"
					+ "disponíveis na tela de configurações antes de cadastrar algum aluno.");
			return null;
		}
		
		em.getTransaction().begin();
		
		ArrayList<Object> inseridos = new ArrayList<>();
		ModelProfessor professor = helper.obterModelo();
		ModelFoto fotoProfessor = helper.obterFoto();
		ModelRegistroProfessor professorRegistro = helper.obterModeloRegistro();
		
		if(helper.verificarDados()) {
			if(helper.verificarTipoDataDeContrato()) {
				if(helper.verificarSalario()) {
					if(helper.verificarHora(cadastroProfessorTela.getTextFieldHoraEntrada().getText())) {
						if(helper.verificarHora(cadastroProfessorTela.getTextFieldHoraSaida().getText())) {
							if(helper.verificarTempoContrato(cadastroProfessorTela.getTextFieldTempoContrato().getText())) {
								if(helper.verificarCPF(cadastroProfessorTela.getTextFieldCpfProfessor().getText())) {
									if(helper.dadosDiferentes(usuario, professor)) {
										if(helper.verificarDias()) {
											
											cadastrarProfessor(inseridos, professor, fotoProfessor, professorRegistro, usuario);
											
											cadastroProfessorTela.exibirMensagemInformacao("Professor cadastrado com sucesso!");
											helper.limparTela(usuario);
										}else {
											if (cadastroProfessorTela.exibirMensagemDecisao("Nenhum dia de trabalho foi selecionado. "
													+ "Deseja cadastrar mesmo assim?")) {
												
												cadastrarProfessor(inseridos, professor, fotoProfessor, professorRegistro, usuario);
												
												cadastroProfessorTela.exibirMensagemInformacao("Professor cadastrado com sucesso!");
												helper.limparTela(usuario);
											}						
										}
									}
								}else {
									cadastroProfessorTela.exibirMensagemErro("CPF inválido. Tente Novamente!");
								}
							}else {
								cadastroProfessorTela.exibirMensagemErro("Tempo de contrato inválido. Tente Novamente!");
							}
						}else {
							cadastroProfessorTela.exibirMensagemErro("Hora de saida inválida. Tente Novamente!");
						}
					}else {
						cadastroProfessorTela.exibirMensagemErro("Hora de entrada inválida. Tente Novamente!");
					}
				}else {
					cadastroProfessorTela.exibirMensagemErro("Salário inválido. Tente Novamente!");					
				}
			}else {
				cadastroProfessorTela.exibirMensagemErro("Escolha 1(UM) tipo de contrato.");
			}
		}else {
			cadastroProfessorTela.exibirMensagemErro("Por favor, preencher todos os campos");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}

	private void cadastrarProfessor(ArrayList<Object> inseridos, ModelProfessor professor, ModelFoto fotoProfessor, ModelRegistroProfessor professorRegistro, ModelUsuario usuario) {
		
		inseridos.add(professor);
		inseridos.add(fotoProfessor);
		inseridos.add(professorRegistro);
		
		professor.setCpf(Criptografar.criptografar(professor.getCpf()));
		professor.setEmail(Criptografar.criptografar(professor.getEmail()));
		
		professor.setFoto(fotoProfessor);
		fotoProfessor.setProfessor(professor);
		
		professor.setUsuario(usuario);
		usuario.getProfessores().add(professor);
		
		professorRegistro.setProfessor(professor);
		professor.setRegistroProfessor(professorRegistro);
		
		professorRegistro.setUsuario(usuario);
		usuario.getRegistroProfessores().add(professorRegistro);
		
		new ProfessorDAO(em).insert(professor);
		new FotoDAO(em).insert(fotoProfessor);
		new RegistroProfessorDAO(em).insert(professorRegistro);
		
	}

	public void carregarFoto() {
		cadastroProfessorTela.exibirMensagemInformacao("A imagem tem que ser no tamanho 3x4 para melhor enquadramento e do tipo .PNG, .JPG ou .JPEG");
		
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha uma imagem");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens (*.PNG, *.JPG, *.JPEG)", "png" ,"jpg", "jpeg"));
        int userSelection = fileChooser.showOpenDialog(cadastroProfessorTela);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            cadastroProfessorTela.setCaminhoImagem(file.getAbsolutePath());
            cadastroProfessorTela.getNomeImagemField().setText(file.getName());
            
            ImageIcon imageIcon = new ImageIcon(cadastroProfessorTela.getCaminhoImagem());
            Image image = imageIcon.getImage().getScaledInstance(cadastroProfessorTela.getLblAdicionarFoto().getWidth(), cadastroProfessorTela.getLblAdicionarFoto().getHeight(), Image.SCALE_SMOOTH);
            cadastroProfessorTela.getLblAdicionarFoto().setIcon(new ImageIcon(image));
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

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
