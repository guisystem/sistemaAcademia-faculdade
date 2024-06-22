package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import controller.helper.EditarPlanoHelper;
import model.ModelCores;
import model.ModelPlano;
import model.ModelUsuario;
import model.dao.PlanoDAO;
import model.dao.UsuarioDAO;
import servico.JPAUtil;
import view.EditarPlanosTela;

public class EditarPlanoController {

	private final EditarPlanosTela editarPlanosTela;
	private final EditarPlanoHelper helper;
	private EntityManager em;
	
	public EditarPlanoController(EditarPlanosTela editarPlanosTela) {
		this.em = new JPAUtil().getEntityManager();
		this.editarPlanosTela = editarPlanosTela;
		this.helper = new EditarPlanoHelper(editarPlanosTela);
	}
	
	public void atualizarLabelTempo() {
		if(editarPlanosTela.getTextFieldTempoPlano().getText().equals("") || 
				editarPlanosTela.getTextFieldTempoPlano().getText().equals("1") ||
				editarPlanosTela.getTextFieldTempoPlano().getText().equals("0")) {
			editarPlanosTela.getLblMesesTempoPlano().setText("mês");
		}else {
			String tempoTela = editarPlanosTela.getTextFieldTempoPlano().getText().trim();
			
			@SuppressWarnings("unused")
			int tempo = 0;
			try {
				tempo = Integer.parseInt(tempoTela);
				editarPlanosTela.getLblMesesTempoPlano().setText("meses");
			} catch (Exception e) {
				editarPlanosTela.getLblMesesTempoPlano().setText("mês");
			}
			
		}
		
	}
	
	public void atualizarTabela(ModelUsuario usuario) {
		List<ModelPlano> planos = usuario.getPlanos();
		helper.preencherTabela(planos);
		
	}

	public ArrayList<Object> adicionarPlano(ModelUsuario usuario) {
		em.getTransaction().begin();
		
		ModelPlano novoPlano = helper.obterModelo();
		
		ArrayList<Object> inseridos = new ArrayList<>();
		if(helper.verificarCampos()) {
			if(helper.validarTempo()) {
				if(helper.validarValor()) {
					if(helper.compararPlanos(novoPlano, usuario)) {
						usuario.getPlanos().add(novoPlano);
						
						new PlanoDAO(em).insert(novoPlano);
						new UsuarioDAO(em).insertOrUpdate(usuario);
						
						inseridos.add(novoPlano);
						inseridos.add(usuario);
						
						editarPlanosTela.exibirMensagemInformacao("O plano foi adicionado com sucesso!");
						
						atualizarTabela(usuario);
						helper.limparTela();
					}else {
						editarPlanosTela.exibirMensagemErro("Já existe um plano com esse nome!");
						helper.selecionarPlanoIgual(novoPlano);
						mostrarDados();
					}
				}else {
					editarPlanosTela.exibirMensagemErro("Insira um valor válido!");
				}
			}else {
				editarPlanosTela.exibirMensagemErro("Insira um tempo válido!");
			}
		}else {
			editarPlanosTela.exibirMensagemErro("Por favor, preencher todos os campos!");
		}

		em.getTransaction().commit();
		return inseridos;
	}

	public void mostrarDados() { 
		int l = editarPlanosTela.getTablePlanos().getSelectedRow();
		ModelPlano plano = (ModelPlano) editarPlanosTela.getTablePlanos().getValueAt(l, 0);
		String nome = plano.getNome();
		String tempo = (String) editarPlanosTela.getTablePlanos().getValueAt(l, 1).toString();
		String valorTela = (String) editarPlanosTela.getTablePlanos().getValueAt(l, 2).toString();
		
		String valor = valorTela.replace("R$:", "").trim();
		
		editarPlanosTela.getTextFieldNomePlano().setText(nome);
		editarPlanosTela.getTextFieldTempoPlano().setText(tempo);
		editarPlanosTela.getTextFieldValorPlano().setText(valor);
		
	}

	public ModelPlano getDados(ModelUsuario usuario) {
		try {
			int l = editarPlanosTela.getTablePlanos().getSelectedRow();
			ModelPlano j = (ModelPlano) editarPlanosTela.getTablePlanos().getValueAt(l, 0);
			
			for(ModelPlano plano: usuario.getPlanos()) {
				if(j.getId() == plano.getId()) {
					return plano;
				}
			}	
		}catch (ArrayIndexOutOfBoundsException e) {
		
		}
		
		return null;
	}

	public ArrayList<Object> atualizarPlano(ModelPlano plano, ModelUsuario usuario) {
		em.getTransaction().begin();

		ArrayList<Object> inseridos = new ArrayList<>();
		try {
			if(helper.verificarCampos()) {
				if(helper.validarTempo()) {
					if(helper.validarValor()) {
						if(helper.compararDados(plano)) {
							if(plano.getAlunosAtivos() == 0){
								
								ModelPlano atualizarPlano = helper.obterModeloAtualizar(plano);
								
								new PlanoDAO(em).insertOrUpdate(atualizarPlano);
								new UsuarioDAO(em).insertOrUpdate(usuario);
								
								inseridos.add(plano);
								inseridos.add(usuario);
								
								editarPlanosTela.exibirMensagemInformacao("O plano foi atualizado com sucesso!");
								
								helper.limparTela();
								atualizarTabela(usuario);
							}else {
								editarPlanosTela.exibirMensagemErro("Não pode atualizar o nome do plano que contém alunos ativos cadastrados");
							}
						}else {
							editarPlanosTela.exibirMensagemInformacao("Nenhum registro foi alterado!");
						}
					}else {
						editarPlanosTela.exibirMensagemErro("Insira um valor válido!");
					}
				}else {
					editarPlanosTela.exibirMensagemErro("Insira um tempo válido!");
				}
			}else {
				editarPlanosTela.exibirMensagemErro("Não é possível atualizar o plano! Selecione alguma linha da tabela");
			}
			
		}catch (NullPointerException e) {
			editarPlanosTela.exibirMensagemErro("Selecione um plano antes de atualizar");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}

	public void excluirPlano(ModelPlano plano, ModelUsuario usuario) {
		em.getTransaction().begin();

		try { 
			if(helper.verificarCampos()) {
				if(helper.validarTempo()) {
					if(helper.validarValor()) {
						if(helper.compararDados(plano) == false) {
							if(plano.getAlunosAtivos() == 0){
								if (editarPlanosTela.exibirMensagemDecisao("Deseja excluir o funcionário: " + plano.getNome() + "?")) {
									if(usuario.getPlanos().remove(plano)) {
										new PlanoDAO(em).deleteForUser();
										new UsuarioDAO(em).insertOrUpdate(usuario);
										
										editarPlanosTela.exibirMensagemInformacao("O plano foi excluido com sucesso");
										
										helper.limparTela();
										atualizarTabela(usuario);
									}else {
										editarPlanosTela.exibirMensagemErro("Plano não encontrado no usuário");
									}
								}
							}else {
								editarPlanosTela.exibirMensagemErro("Não pode atualizar o nome do plano que contém alunos ativos cadastrados");
							}
						}else {
							editarPlanosTela.exibirMensagemErro("Não existe um plano cadastrado com esses dados");
						}				
					}else {
						editarPlanosTela.exibirMensagemErro("Valor inválido!");
					}
				}else {
					editarPlanosTela.exibirMensagemErro("Insira um tempo válido!");
				}
			}else {
				editarPlanosTela.exibirMensagemErro("Não é possível excluir o plano! Selecione alguma linha da tabela");
			}

		}catch (NullPointerException e) {
			editarPlanosTela.exibirMensagemErro("Selecione um plano antes de excluir");
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
