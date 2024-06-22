package controller;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;

import controller.helper.DadosAlunoHelper;
import model.ModelAluno;
import model.ModelCores;
import model.ModelFoto;
import model.ModelModalidade;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import model.dao.AlunoDAO;
import model.dao.FotoDAO;
import model.dao.RegistroAlunoDAO;
import model.dao.UsuarioDAO;
import servico.Criptografar;
import servico.JPAUtil;
import view.DadosAlunoTela;

public class DadosAlunoController {

	private final DadosAlunoTela dadosAlunoTela;
	private final DadosAlunoHelper helper;
	private EntityManager em;
	
	public DadosAlunoController(DadosAlunoTela dadosAlunoTela) {
		this.em = new JPAUtil().getEntityManager();
		this.dadosAlunoTela = dadosAlunoTela;
		this.helper = new DadosAlunoHelper(dadosAlunoTela);
	}

	public void atualizarDados(ModelUsuario usuario, ModelRegistroAluno registroAluno) {
		helper.preencherDadosAluno(usuario, registroAluno);
		
	}

	public void cancelarAlteracoes(ModelRegistroAluno registroAluno, ArrayList<ModelModalidade> modalidades) {
		
		if(helper.compararDados(registroAluno, modalidades)) {
			dadosAlunoTela.setVisible(false);
		}else {
			if (dadosAlunoTela.exibirMensagemDecisao("Se cancelar irá perder todas as alterações realizadas. "
					+ "Deseja mesmo sair?")) {
				dadosAlunoTela.setVisible(false);
			}
		}
		
		registroAluno.getAluno().getModalidades().clear();
		for (ModelModalidade modalidade: modalidades) {
			registroAluno.getAluno().getModalidades().add(modalidade);
		}
		
	}

	public ArrayList<Object> confirmarAlteracoes(ModelUsuario usuario, ModelRegistroAluno registroAluno, ArrayList<ModelModalidade> modalidades) {
		
		em.getTransaction().begin();
		
		ArrayList<Object> inseridos = new ArrayList<>();
		if(helper.verificarDados()) {
			if(helper.verificarData(dadosAlunoTela.getTextFieldDataNascimento().getText())) {
				if(helper.compararDatas(dadosAlunoTela.getTextFieldDataMatricula().getText())) {
					if(helper.compararDatas(dadosAlunoTela.getTextFieldDataUltimoPagamento().getText())) {
						if(helper.verificarCPF(dadosAlunoTela.getTextFieldCpfAluno().getText())) {
							if(helper.validarEmailECpf(usuario, registroAluno, dadosAlunoTela.getTextFieldCpfAluno().getText(), dadosAlunoTela.getTextFieldEmailAluno().getText())) {
								if(helper.verificarModalidade((ModelModalidade) dadosAlunoTela.getComboBoxExcluirModalidade().getSelectedItem())) {
									if(helper.compararDados(registroAluno, modalidades) == false) {
										if (dadosAlunoTela.exibirMensagemDecisao("Deseja confirmar as alterações?")) {
											
											ModelAluno aluno = helper.obterModelo(registroAluno);
											ModelFoto fotoAluno = helper.obterFoto(registroAluno);
											ModelRegistroAluno registro = helper.obterModeloRegistro(registroAluno);
											
											aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
											aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
											
											new UsuarioDAO(em).insertOrUpdate(usuario);
											new AlunoDAO(em).insertOrUpdate(aluno);
											new FotoDAO(em).insertOrUpdate(fotoAluno);
											new RegistroAlunoDAO(em).insertOrUpdate(registro);
											
											inseridos.add(usuario);
											inseridos.add(aluno);
											inseridos.add(fotoAluno);
											inseridos.add(registro);
											
											dadosAlunoTela.exibirMensagemInformacao("Dados do aluno foram alterados com sucesso!");
											
											dadosAlunoTela.setVisible(false);
											RegistroAlunoController.atualizarTabela(usuario);
										}											
									}else {
										dadosAlunoTela.setVisible(false);											
									}
								}else {
									dadosAlunoTela.exibirMensagemErro("Nenhuma modalidade adicionada");
								}
							}
						}else {
							dadosAlunoTela.exibirMensagemErro("CPF inválido. Tente Novamente");
						}
					}else {
						dadosAlunoTela.exibirMensagemErro("Data de Ultimo Pagamento inválido. Tente Novamente");
					}
				}else {
					dadosAlunoTela.exibirMensagemErro("Data de Matrícula inválida. Tente Novamente");
				}
			}else {
				dadosAlunoTela.exibirMensagemErro("Data de Nascimento inválida. Tente Novamente");
			}
		}else {
			dadosAlunoTela.exibirMensagemErro("Por favor, preencher todos os dados!");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}
	
	public void sairDaTelaDefaultClose(ModelRegistroAluno registroAluno, ArrayList<ModelModalidade> modalidades) {
		
    	if(helper.compararDados(registroAluno, modalidades)){
    		dadosAlunoTela.dispose();
    	}else {
    		if (dadosAlunoTela.exibirMensagemDecisao("Deseja fechar essa janela? Todas as alterações feitas serão perdidas")){
    			dadosAlunoTela.dispose();
    		}		    		
    	}
    	
    	registroAluno.getAluno().getModalidades().clear();
		for (ModelModalidade modalidade: modalidades) {
			registroAluno.getAluno().getModalidades().add(modalidade);
		}
	}

	@SuppressWarnings("unchecked")
	public void adicionarModalidade(ModelUsuario usuario, ModelRegistroAluno registroAluno) {
		DefaultComboBoxModel<ModelModalidade> comboBoxAdicionarModalidade = (DefaultComboBoxModel<ModelModalidade>)dadosAlunoTela.getComboBoxAdicionarModalidade().getModel();
		
		DefaultComboBoxModel<ModelModalidade> comboBoxExcluirModalidade = (DefaultComboBoxModel<ModelModalidade>) dadosAlunoTela.getComboBoxExcluirModalidade().getModel();
		
		ModelModalidade modalidade = (ModelModalidade) comboBoxAdicionarModalidade.getSelectedItem();
		if(modalidade != null) {
			comboBoxExcluirModalidade.addElement((ModelModalidade) comboBoxAdicionarModalidade.getSelectedItem());
			comboBoxAdicionarModalidade.removeElement(comboBoxAdicionarModalidade.getSelectedItem());
			
			registroAluno.getAluno().adicionarModalidade(modalidade);
			
			DefaultTableModel tableModel = (DefaultTableModel) dadosAlunoTela.getTableModalidadeAluno().getModel();
			tableModel.setNumRows(0);
			
			helper.preencherTabelaDados(registroAluno);
			helper.preencherDataValorDinamicamente(registroAluno, usuario);
		}else {
			
			dadosAlunoTela.exibirMensagemErro("Nenhuma modalidade selecionada");
		}
	}

	@SuppressWarnings("unchecked")
	public void removerModalidade(ModelUsuario usuario, ModelRegistroAluno registroAluno) {
		DefaultComboBoxModel<ModelModalidade> comboBoxAdicionarModalidade = (DefaultComboBoxModel<ModelModalidade>)dadosAlunoTela.getComboBoxAdicionarModalidade().getModel();
		
		DefaultComboBoxModel<ModelModalidade> comboBoxExcluirModalidade = (DefaultComboBoxModel<ModelModalidade>) dadosAlunoTela.getComboBoxExcluirModalidade().getModel();
		
		ModelModalidade modalidade = (ModelModalidade) comboBoxExcluirModalidade.getSelectedItem();
		if(modalidade != null) {
			comboBoxAdicionarModalidade.addElement((ModelModalidade) comboBoxExcluirModalidade.getSelectedItem());
			comboBoxExcluirModalidade.removeElement(comboBoxExcluirModalidade.getSelectedItem());
			
			registroAluno.getAluno().removerModalidade(modalidade);
			
			DefaultTableModel tableModel = (DefaultTableModel) dadosAlunoTela.getTableModalidadeAluno().getModel();
			tableModel.setNumRows(0);
			
			helper.preencherTabelaDados(registroAluno);
			helper.preencherDataValorDinamicamente(registroAluno, usuario);
		}else {
			dadosAlunoTela.exibirMensagemErro("Nenhuma modalidade selecionada");
		}
		
	}

	@SuppressWarnings("unchecked")
	public ArrayList<ModelModalidade> getModalidadesInicio() {
		DefaultComboBoxModel<ModelModalidade> comboBoxExcluirModalidade = (DefaultComboBoxModel<ModelModalidade>) dadosAlunoTela.getComboBoxExcluirModalidade().getModel();	
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		
		for(int i = 0; i < comboBoxExcluirModalidade.getSize(); i++) {
			modalidades.add(comboBoxExcluirModalidade.getElementAt(i));
		}
		
		return modalidades;
	}

	public void carregarFoto() {
		
		JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Escolha uma imagem");
        
        int userSelection = fileChooser.showOpenDialog(dadosAlunoTela);
        
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            dadosAlunoTela.setCaminhoImagem(file.getAbsolutePath());
            dadosAlunoTela.getNomeImagemField().setText(file.getName());
            
            ImageIcon imageIcon = new ImageIcon(dadosAlunoTela.getCaminhoImagem());
            Image image = imageIcon.getImage().getScaledInstance(dadosAlunoTela.getLblMudarFoto().getWidth(), dadosAlunoTela.getLblMudarFoto().getHeight(), Image.SCALE_SMOOTH);
            dadosAlunoTela.getLblMudarFoto().setIcon(new ImageIcon(image));
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
