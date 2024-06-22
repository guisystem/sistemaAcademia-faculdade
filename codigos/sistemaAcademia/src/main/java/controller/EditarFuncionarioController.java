package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import controller.helper.EditarFuncionarioHelper;
import model.ModelCores;
import model.ModelFuncionario;
import model.ModelUsuario;
import model.dao.FuncionarioDAO;
import model.dao.UsuarioDAO;
import servico.JPAUtil;
import view.EditarFuncionariosTela;

public class EditarFuncionarioController {

	private final EditarFuncionariosTela editarFuncionariosTela;
	private final EditarFuncionarioHelper helper;
	private EntityManager em;
	
	public EditarFuncionarioController(EditarFuncionariosTela editarFuncionariosTela) {
		this.em = new JPAUtil().getEntityManager();
		this.editarFuncionariosTela = editarFuncionariosTela;
		this.helper = new EditarFuncionarioHelper(editarFuncionariosTela);
	}

	public void atualizarTabela(ModelUsuario usuario) {
		List<ModelFuncionario> funcionarios = usuario.getFuncionarios();
		helper.preencherTabela(funcionarios);
		
	}

	public ArrayList<Object> adicionarFuncionario(ModelUsuario usuario) {
		em.getTransaction().begin();
		
		ModelFuncionario novoFuncionario = helper.obterModelo();
		
		ArrayList<Object> inseridos = new ArrayList<>();
		if(helper.verificarCampos()) {
			if(helper.validarSalario()) {
				if(helper.compararFuncionarios(novoFuncionario, usuario)) {
					usuario.getFuncionarios().add(novoFuncionario);
					
					new FuncionarioDAO(em).insert(novoFuncionario);
					new UsuarioDAO(em).insertOrUpdate(usuario);
					
					inseridos.add(novoFuncionario);
					inseridos.add(usuario);
					
					editarFuncionariosTela.exibirMensagemInformacao("O funcionário foi adicionado com sucesso!");
					
					helper.limparTela();
					atualizarTabela(usuario);
				}else {
					editarFuncionariosTela.exibirMensagemErro("Já existe um funcionário com esses mesmo dados!");
					helper.selecionarFuncionarioIgual(novoFuncionario);
					mostrarDados();
				}
			}else {
				editarFuncionariosTela.exibirMensagemErro("Insira um salário válido!");
			}
		}else {
			editarFuncionariosTela.exibirMensagemErro("Por favor, preencher todos os campos!");
		}

		em.getTransaction().commit();
		return inseridos;
	}

	public void mostrarDados() { 
		int l = editarFuncionariosTela.getTableFuncionarios().getSelectedRow();
		ModelFuncionario funcionario = (ModelFuncionario) editarFuncionariosTela.getTableFuncionarios().getValueAt(l, 0);
		String nome = funcionario.getNome();
		String cargo = (String) editarFuncionariosTela.getTableFuncionarios().getValueAt(l, 1);
		String salarioTela = (String) editarFuncionariosTela.getTableFuncionarios().getValueAt(l, 2).toString();
		
		String salario = salarioTela.replace("R$:", "").trim();
		
		editarFuncionariosTela.getTextFieldNomeFuncionario().setText(nome);
		editarFuncionariosTela.getTextFieldCargoFuncionario().setText(cargo);
		editarFuncionariosTela.getTextFieldSalarioFuncionario().setText(salario);
		
	}

	public ModelFuncionario getDados(ModelUsuario usuario) {
		try {
			int l = editarFuncionariosTela.getTableFuncionarios().getSelectedRow();
			ModelFuncionario j = (ModelFuncionario) editarFuncionariosTela.getTableFuncionarios().getValueAt(l, 0);
			
			for(ModelFuncionario funcionario: usuario.getFuncionarios()) {
				if(j.getId() == funcionario.getId()) {
					return funcionario;
				}
			}	
		}catch (ArrayIndexOutOfBoundsException e) {
		
		}
		
		return null;
	}

	public ArrayList<Object> atualizarFuncionario(ModelFuncionario funcionario, ModelUsuario usuario) {
		em.getTransaction().begin();

		ArrayList<Object> inseridos = new ArrayList<>();
		try {
			if(helper.verificarCampos()) {
				if(helper.validarSalario()) {
					if(helper.compararDados(funcionario)) {
						
						ModelFuncionario atualizarFuncionario = helper.obterModeloAtualizar(funcionario);
						
						new FuncionarioDAO(em).insertOrUpdate(atualizarFuncionario);
						new UsuarioDAO(em).insertOrUpdate(usuario);
						
						inseridos.add(funcionario);
						inseridos.add(usuario);
						
						editarFuncionariosTela.exibirMensagemInformacao("O funcionário foi atualizado com sucesso!");
						
						helper.limparTela();
						atualizarTabela(usuario);
							
					}else {
						editarFuncionariosTela.exibirMensagemInformacao("Nenhum registro foi alterado!");
					}
				}else {
					editarFuncionariosTela.exibirMensagemErro("Insira um salário válido!");
				}
			}else {
				editarFuncionariosTela.exibirMensagemErro("Não é possível atualizar o funcionario! Selecione alguma linha da tabela.");
			}
			
		}catch (NullPointerException e) {
			editarFuncionariosTela.exibirMensagemErro("Selecione um funcionario antes de atualizar.");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}

	public void excluirFuncionario(ModelFuncionario funcionario, ModelUsuario usuario) {
		em.getTransaction().begin();

		try { 
			if(helper.verificarCampos()) {
				if(helper.validarSalario()) {
					if(helper.compararDados(funcionario) == false) {
						if (editarFuncionariosTela.exibirMensagemDecisao("Deseja excluir o funcionário: " + funcionario.getNome() + "?")) {
							if(usuario.getFuncionarios().remove(funcionario)) {
								
								new FuncionarioDAO(em).deleteForUser();
								new UsuarioDAO(em).insertOrUpdate(usuario);
								
								editarFuncionariosTela.exibirMensagemInformacao("O funcionário foi excluido com sucesso!");
								
								helper.limparTela();
								atualizarTabela(usuario);
							}else {
								editarFuncionariosTela.exibirMensagemErro("Funcionário não encontrado no usuário");
				            }
						}
					}else {
						editarFuncionariosTela.exibirMensagemErro("Não existe um funcionário cadastrado com esses dados.");
					}				
				}else {
					editarFuncionariosTela.exibirMensagemErro("Salário inválido.");
				}
			}else {				
				editarFuncionariosTela.exibirMensagemErro("Não é possível excluir o funcionario! Selecione alguma linha da tabela.");
			}

		}catch (NullPointerException e) {
			editarFuncionariosTela.exibirMensagemErro("Selecione um funcionario antes de excluir.");
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
