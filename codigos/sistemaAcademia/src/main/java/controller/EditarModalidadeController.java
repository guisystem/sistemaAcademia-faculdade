package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import controller.helper.EditarModalidadeHelper;
import model.ModelCores;
import model.ModelEspecialidade;
import model.ModelModalidade;
import model.ModelUsuario;
import model.dao.EspecialidadeDAO;
import model.dao.ModalidadeDAO;
import model.dao.UsuarioDAO;
import servico.JPAUtil;
import view.EditarModalidadeTela;

public class EditarModalidadeController {

	private final EditarModalidadeTela editarModalidadeTela;
	private final EditarModalidadeHelper helper;
	private EntityManager em;
	
	public EditarModalidadeController(EditarModalidadeTela editarModalidadeTela) {
		this.em = new JPAUtil().getEntityManager();
		this.editarModalidadeTela = editarModalidadeTela;
		this.helper = new EditarModalidadeHelper(editarModalidadeTela);
	}

	public void atualizarTabela(ModelUsuario usuario) {
		List<ModelModalidade> modalidades = usuario.getModalidades();
		helper.preencherTabela(modalidades);
		
	}

	public ArrayList<Object> adicionarModalidade(ModelUsuario usuario) {
		em.getTransaction().begin();
		
		ModelModalidade novaModalidade = helper.obterModelo();
		ModelEspecialidade novaEspecialidade = new ModelEspecialidade(novaModalidade.getNome());
		
		ArrayList<Object> inseridos = new ArrayList<>();
		if(helper.verificarCampos()) {
			if(helper.validarTaxa()) {
				if(helper.compararModalidades(novaModalidade, usuario)) {
				
					usuario.getModalidades().add(novaModalidade);
					usuario.getEspecialidades().add(novaEspecialidade);
					
					new ModalidadeDAO(em).insert(novaModalidade);
					new EspecialidadeDAO(em).insert(novaEspecialidade);
					
					new UsuarioDAO(em).insertOrUpdate(usuario);
					
					inseridos.add(novaModalidade);
					inseridos.add(novaEspecialidade);
					inseridos.add(usuario);
					
					editarModalidadeTela.exibirMensagemInformacao("A modalidade/especialidade foi adicionada com sucesso!");
					
					atualizarTabela(usuario);
					helper.limparTela();
				}else {
					editarModalidadeTela.exibirMensagemErro("Já existe uma modalidade com esse nome!");
					helper.selecionarModalidadeIgual(novaModalidade);
					mostrarDados();
				}
			}else {
				editarModalidadeTela.exibirMensagemErro("Insira um valor válido!");
			}
		}else {
			editarModalidadeTela.exibirMensagemErro("Por favor, preencher todos os campos!");
		}

		em.getTransaction().commit();
		return inseridos;
	}

	public void mostrarDados() {
		int l = editarModalidadeTela.getTableModalidades().getSelectedRow();
		String nome = (String) editarModalidadeTela.getTableModalidades().getValueAt(l, 0);
		String taxaExtraTela = (String) editarModalidadeTela.getTableModalidades().getValueAt(l, 1).toString();
		
		String taxaExtra = taxaExtraTela.replace("R$:", "").trim();
		
		editarModalidadeTela.getTextFieldNomeModalidade().setText(nome);
		editarModalidadeTela.getTextFieldlTaxaExtraModalidade().setText(taxaExtra);
		
	}

	public Object getDados(ModelUsuario usuario, int i) {
		try {
			int l = editarModalidadeTela.getTableModalidades().getSelectedRow();
			String j = (String) editarModalidadeTela.getTableModalidades().getValueAt(l, 0);
			
			if(i != 0) { /** NOTE: Se for diferente de 0, ele irá pegar a especialidade invés da modalidade */
				for(ModelEspecialidade especialidade: usuario.getEspecialidades()) {
					if(j.equals(especialidade.getNome())) {
						return especialidade;
					}
				}
			}
			
			for(ModelModalidade modalidade: usuario.getModalidades()) {
				if(j.equals(modalidade.getNome())) {
					return modalidade;
				}
			}	
		}catch (ArrayIndexOutOfBoundsException e) {
		
		}
		
		return null;
	}

	public ArrayList<Object> atualizarModalidade(ModelModalidade modalidade, ModelUsuario usuario) {
		em.getTransaction().begin();

		ModelEspecialidade atualizarEspecialidade = (ModelEspecialidade) getDados(usuario, 1);
		
		ArrayList<Object> inseridos = new ArrayList<>();
		try {
			if(helper.verificarCampos()) {
				if(helper.validarTaxa()) {
					if(helper.compararDados(modalidade)) {
						if(modalidade.getAlunosAtivos() == 0) {
							if(atualizarEspecialidade.getProfessoresAtivos() == 0) {
								
								ModelModalidade atualizarModalidade = helper.obterModeloAtualizar(modalidade);
								
								atualizarEspecialidade.setNome(atualizarModalidade.getNome());
								atualizarEspecialidade.setUsuario(atualizarModalidade.getUsuario());
								
								new ModalidadeDAO(em).insertOrUpdate(atualizarModalidade);
								new EspecialidadeDAO(em).insertOrUpdate(atualizarEspecialidade);
								
								new UsuarioDAO(em).insertOrUpdate(usuario);
								
								inseridos.add(modalidade);
								inseridos.add(atualizarEspecialidade);
								inseridos.add(usuario);
								
								editarModalidadeTela.exibirMensagemInformacao("A modalidade/especialidade foi atualizada com sucesso!");
								
								helper.limparTela();
								atualizarTabela(usuario);
								
							}else {
								editarModalidadeTela.exibirMensagemErro("Não pode atualizar o nome da modalidade que contém professores ativos cadastrados");
							}
						}else {
							editarModalidadeTela.exibirMensagemErro("Não pode atualizar o nome da modalidade que contém alunos ativos cadastrados");
						}
					}else {
						editarModalidadeTela.exibirMensagemInformacao("Nenhum registro foi alterado!");
					}
				}else {
					editarModalidadeTela.exibirMensagemErro("Insira uma taxa válida!");
				}
			}else {
				editarModalidadeTela.exibirMensagemErro("Não é possível atualizar uma modalidade! Selecione alguma linha da tabela.");
			}
			
		}catch (NullPointerException e) {
			editarModalidadeTela.exibirMensagemErro("Selecione uma modalidade antes de atualizar");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}

	public void excluirModalidade(ModelModalidade modalidade, ModelUsuario usuario) {
		em.getTransaction().begin();
		
		ModelEspecialidade atualizarEspecialidade = (ModelEspecialidade) getDados(usuario, 1);
		
		try {
			if(helper.verificarCampos()) {
				if(helper.validarTaxa()) {
					if(helper.compararDados(modalidade) == false) {
						if(modalidade.getAlunosAtivos() == 0) {
							if(atualizarEspecialidade.getProfessoresAtivos() == 0) {
								if (editarModalidadeTela.exibirMensagemDecisao("Deseja excluir a modalidade: " + modalidade.getNome() + "?")) {
									if(usuario.getModalidades().remove(modalidade) && 
											usuario.getEspecialidades().remove(atualizarEspecialidade)) {
										new ModalidadeDAO(em).deleteForUser();
										new EspecialidadeDAO(em).deleteForUser();
										
										new UsuarioDAO(em).insertOrUpdate(usuario);
										
										editarModalidadeTela.exibirMensagemInformacao("A modalidade/especialidade foi excluida com sucesso!");
										
										helper.limparTela();
										atualizarTabela(usuario);
										
									}else {
										editarModalidadeTela.exibirMensagemErro("Modalidade não encontrada no usuário");
						            }
								}
							}else {
								editarModalidadeTela.exibirMensagemErro("Não pode excluir a modalidade que contém professores ativos cadastrados.");
							}
						}else {
							editarModalidadeTela.exibirMensagemErro("Não pode excluir a modalidade que contém alunos ativos cadastrados.");
						}
					}else {						
						editarModalidadeTela.exibirMensagemErro("Não existe uma modalidade cadastrada com esses dados.");
					}
				}else {
					editarModalidadeTela.exibirMensagemErro("Taxa inválida!");
				}
			}else {				
				editarModalidadeTela.exibirMensagemErro("Não é possível excluir uma modalidade! Selecione alguma linha da tabela.");
			}

		}catch (NullPointerException e) {
			editarModalidadeTela.exibirMensagemErro("Selecione uma modalidade antes de excluir.");
		}
		
		em.getTransaction().commit();
	}

	public void atualizarCores(ModelUsuario usuario) {
		if(usuario.getConfiguracao() != null) {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(ConfiguracaoController.corPrimaria());
			ArrayList<Color> coresSecundaria = ModelCores.tonsDeCorSecundaria(ConfiguracaoController.corSecundaria());
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundaria);
			
		}else {
			ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(ConfiguracaoController.corPrimaria());
			ArrayList<Color> coresSecundaria = ModelCores.tonsDeCorSecundaria(ConfiguracaoController.corSecundaria());
			
			helper.setarCoresPrimariasNaTela(coresPrimarias);
			helper.setarCoresSecundariasNaTela(coresSecundaria);
			
		}
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
}
