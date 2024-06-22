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

import controller.helper.CadastroAlunoHelper;
import model.ModelAluno;
import model.ModelCores;
import model.ModelFoto;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import model.dao.AlunoDAO;
import model.dao.FotoDAO;
import model.dao.RegistroAlunoDAO;
import servico.Criptografar;
import servico.JPAUtil;
import view.CadastroAlunoTela;

public class CadastroAlunoController {

	private final CadastroAlunoTela cadastroAlunoTela;
	private final CadastroAlunoHelper helper;
	private EntityManager em;

	public CadastroAlunoController(CadastroAlunoTela cadastroAlunoTela) {
		super();
		this.em = new JPAUtil().getEntityManager();
		this.cadastroAlunoTela = cadastroAlunoTela;
		this.helper = new CadastroAlunoHelper(cadastroAlunoTela);
	}

	public void preencherComboBoxPlanos(ModelUsuario usuario) {
		
		List<ModelPlano> planos = usuario.getPlanos();
		helper.atualizarPlanos(planos);
		
	}

	public void preencherComboBoxModalidades(ModelUsuario usuario) {
		
		List<ModelModalidade> modalidades = usuario.getModalidades();
		helper.atualizarModalidades(modalidades, usuario);
		
	}

	public void atualizarValor(ModelUsuario usuario) {
		ModelPlano plano = helper.obterPlano();
		
		ModelAluno aluno = helper.obterAluno(usuario);
		
		if(aluno == null) {
			if(plano == null) {
				
			}else {
				helper.preencherValorInicial(plano.getValor());				
			}
		}else {
			if(plano == null) {
				helper.preencherValorDinamico(0, 1, aluno.getModalidades());			
			}else {
				helper.preencherValorDinamico(plano.getValor(), plano.getPeriodo(), aluno.getModalidades());			
			}
		}
		
	}

	public void atualizarPeriodo() {
		ModelPlano plano = helper.obterPlano();
		helper.preencherPeriodo(plano.getPeriodo());
		
	}

	public ArrayList<Object> cadastrarAluno(ModelUsuario usuario) {
		
		if(usuario.getPlanos() == null || usuario.getPlanos().isEmpty() || usuario.getModalidades() == null || usuario.getModalidades().isEmpty()) {
			cadastroAlunoTela.exibirMensagemErro("Não é possível cadastrar. Configure seu sistema adicionando os planos disponíveis \r\n"
					+ "e as modalidades na tela de configurações antes de cadastrar algum aluno.");
			return null;
		}
		
		em.getTransaction().begin();
		
		ArrayList<Object> inseridos = new ArrayList<>();
		ModelAluno aluno = helper.obterModelo();
		ModelFoto fotoAluno = helper.obterFoto();
		ModelRegistroAluno alunoRegistro = helper.obterModeloRegistro();
		
		if(helper.verificarDados()) {
			if(helper.verificarData(cadastroAlunoTela.getTextFieldDataNascimento().getText())) {
				if(helper.verificarData(cadastroAlunoTela.getTextFieldDataMatricula().getText())) {
					if(helper.verificarCPF(cadastroAlunoTela.getTextFieldCpfAluno().getText())) {
						if(helper.dadosDiferentes(usuario, aluno)) {
							if(helper.verificarModalidade((ModelModalidade) cadastroAlunoTela.getComboBoxRemoverModalidade().getSelectedItem())) {
								try {
									ModelAluno alunoComparar = usuario.getAlunos().get(usuario.getAlunos().size() - 1);
									if(alunoComparar.getId() == 0 && alunoComparar.getCpf().equals("")) {
										for(ModelModalidade modalidades: alunoComparar.getModalidades()) {
											aluno.adicionarModalidade(modalidades);
										}				
										
										usuario.getAlunos().remove(usuario.getAlunos().get(usuario.getAlunos().size() - 1));
									}
								}catch(IndexOutOfBoundsException e) {
									
								}
								
								aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
								aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
								
								aluno.setFoto(fotoAluno);
								fotoAluno.setAluno(aluno);
								
								aluno.setUsuario(usuario);
								usuario.getAlunos().add(aluno);
								
								alunoRegistro.setAluno(aluno);
								aluno.setRegistroAluno(alunoRegistro);
								
								alunoRegistro.setUsuario(usuario);
								usuario.getRegistroAlunos().add(alunoRegistro);
								
								new AlunoDAO(em).insert(aluno);
								new FotoDAO(em).insert(fotoAluno);
								new RegistroAlunoDAO(em).insert(alunoRegistro);
								
								inseridos.add(aluno);
								inseridos.add(fotoAluno);
								inseridos.add(alunoRegistro);
								
								cadastroAlunoTela.exibirMensagemInformacao("Aluno cadastrado com sucesso!");
								helper.limparTela(usuario);
								
							}else {
								cadastroAlunoTela.exibirMensagemErro("Nenhuma modalidade selecionada. Tente Novamente");
							}							
						}
					}else {
						cadastroAlunoTela.exibirMensagemErro("CPF inválido. Tente novamente");
					}
				}else {
					cadastroAlunoTela.exibirMensagemErro("Data de Matrícula inválida. Tente Novamente.");
				}
			}else {
				cadastroAlunoTela.exibirMensagemErro("Data de Nascimento inválida. Tente Novamente.");
			}
		}else {
			cadastroAlunoTela.exibirMensagemErro("Por favor, preencher todos os campos");
		}
		
		em.getTransaction().commit();
		return inseridos;
		
	}

	public void adicionarModalidade(ModelUsuario usuario) {
		
		if(cadastroAlunoTela.getComboBoxAdicionarModalidade().getSelectedItem() == null) {
			cadastroAlunoTela.exibirMensagemInformacao("Nenhuma modalidade selecionada");
		}else {

			try {
				ModelAluno alunoComparar = usuario.getAlunos().get(usuario.getAlunos().size() - 1);

				ModelAluno alunoParcial = new ModelAluno(0, "", "", "", "", "", (ModelPlano) cadastroAlunoTela.getComboBoxPlano().getSelectedItem());
				
				if(!alunoComparar.getCpf().equals("")) {
					
					ModelModalidade modalidade = (ModelModalidade) cadastroAlunoTela.getComboBoxAdicionarModalidade().getSelectedItem();
					alunoParcial.adicionarModalidade(modalidade);
					
					alunoParcial.setUsuario(usuario);
					usuario.getAlunos().add(alunoParcial);
					
				}else {
					
					usuario.getAlunos().remove(usuario.getAlunos().get(usuario.getAlunos().size() - 1));
					
					ModelModalidade modalidade = (ModelModalidade) cadastroAlunoTela.getComboBoxAdicionarModalidade().getSelectedItem();
					alunoParcial.adicionarModalidade(modalidade);
					
					for(ModelModalidade modalidades: alunoComparar.getModalidades()) {
						alunoParcial.adicionarModalidade(modalidades);
					}
					
					alunoParcial.setUsuario(usuario);
					usuario.getAlunos().add(alunoParcial);
					
				}
					
			}catch(IndexOutOfBoundsException e) {
				ModelAluno alunoParcial = new ModelAluno(0, "", "", "", "", "", (ModelPlano) cadastroAlunoTela.getComboBoxPlano().getSelectedItem());
	    	
				ModelModalidade modalidade = (ModelModalidade) cadastroAlunoTela.getComboBoxAdicionarModalidade().getSelectedItem();
				alunoParcial.adicionarModalidade(modalidade);
				
				alunoParcial.setUsuario(usuario);
				usuario.getAlunos().add(alunoParcial);
			}
			
			preencherComboBoxModalidades(usuario);
			atualizarValor(usuario);
		}
		
	}
	
	public void removerModalidade(ModelUsuario usuario) {
		if(cadastroAlunoTela.getComboBoxRemoverModalidade().getSelectedItem() == null) {
			cadastroAlunoTela.exibirMensagemInformacao("Nenhuma modalidade selecionada");
		}else {
			try {
				ModelAluno alunoComparar = usuario.getAlunos().get(usuario.getAlunos().size() - 1);
				
				if(alunoComparar.getModalidades().isEmpty()) {
					cadastroAlunoTela.getComboBoxRemoverModalidade().removeAllItems();
					preencherComboBoxModalidades(usuario);
				}else {
					if(alunoComparar.getCpf().equals("")) {
						ModelModalidade modalidade = (ModelModalidade) cadastroAlunoTela.getComboBoxRemoverModalidade().getSelectedItem();
						alunoComparar.getModalidades().remove(modalidade);					
						
					}else {
						cadastroAlunoTela.exibirMensagemInformacao("Nenhuma modalidade adicionada");
					}
					
					preencherComboBoxModalidades(usuario);
					atualizarValor(usuario);
				}
			}catch(IndexOutOfBoundsException e) {
	    		
	    	}
		}
		
		
	}

	public void carregarFoto() {
		cadastroAlunoTela.exibirMensagemInformacao("A imagem tem que ser no tamanho 3x4 para melhor enquadramento e do tipo .PNG, .JPG ou .JPEG");
		
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha uma imagem");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Arquivo de imagens (*.PNG, *.JPG, *.JPEG)", "png" ,"jpg", "jpeg"));
        int userSelection = fileChooser.showOpenDialog(cadastroAlunoTela);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            cadastroAlunoTela.setCaminhoImagem(file.getAbsolutePath());
            cadastroAlunoTela.getNomeImagemField().setText(file.getName());
            
            ImageIcon imageIcon = new ImageIcon(cadastroAlunoTela.getCaminhoImagem());
            Image image = imageIcon.getImage().getScaledInstance(cadastroAlunoTela.getLblAdicionarFoto().getWidth(), cadastroAlunoTela.getLblAdicionarFoto().getHeight(), Image.SCALE_SMOOTH);
            cadastroAlunoTela.getLblAdicionarFoto().setIcon(new ImageIcon(image));
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
