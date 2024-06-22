package controller;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import controller.helper.EditarCustoHelper;
import model.ModelCores;
import model.ModelCustoBasico;
import model.ModelUsuario;
import model.dao.CustosBasicosDAO;
import model.dao.UsuarioDAO;
import servico.JPAUtil;
import view.EditarCustosTela;

public class EditarCustoController {

	private final EditarCustosTela editarCustosTela;
	private final EditarCustoHelper helper;
	private EntityManager em;
	
	public EditarCustoController(EditarCustosTela editarCustosTela) {
		super();
		this.em = new JPAUtil().getEntityManager();
		this.editarCustosTela = editarCustosTela;
		this.helper = new EditarCustoHelper(editarCustosTela);
	}

	public void atualizarTabela(ModelUsuario usuario) {
		List<ModelCustoBasico> custos = usuario.getCustosBasicos();
		helper.preencherTabela(custos);
		
	}

	public ArrayList<Object> adicionarCusto(ModelUsuario usuario) {
		em.getTransaction().begin();
		
		ArrayList<Object> inseridos = new ArrayList<>();
		ModelCustoBasico novoCusto = helper.obterModelo();
		
		if(helper.verificarCampos()) {
			if(helper.validarValor()) {
				if(helper.compararCustos(novoCusto, usuario)) {
				
					usuario.getCustosBasicos().add(novoCusto);
					
					new CustosBasicosDAO(em).insert(novoCusto);
					new UsuarioDAO(em).insertOrUpdate(usuario);
					
					inseridos.add(novoCusto);
					inseridos.add(usuario);
					
					editarCustosTela.exibirMensagemInformacao("O custo foi adicionado com sucesso!");
					
					helper.limparTela();
					atualizarTabela(usuario);
				}else {
					editarCustosTela.exibirMensagemErro("Já existe um custo com esse nome!");
					helper.selecionarCustoIgual(novoCusto);
					mostrarDados();
				}
			}else {
				editarCustosTela.exibirMensagemErro("Insira um valor válido!");
			}
		}else {
			editarCustosTela.exibirMensagemErro("Por favor, preencher todos os campos!");
		}

		em.getTransaction().commit();
		return inseridos;
	}
	
	public void mostrarDados() {

		int l = editarCustosTela.getTableCustosBasicos().getSelectedRow();
		ModelCustoBasico custo = (ModelCustoBasico) editarCustosTela.getTableCustosBasicos().getValueAt(l, 0);
		String nome = custo.getNome();
		String valorTela = (String) editarCustosTela.getTableCustosBasicos().getValueAt(l, 1).toString();
		
		String valor = valorTela.replace("R$:", "").trim();
		
		editarCustosTela.getTextFieldNomeCusto().setText(nome);
		editarCustosTela.getTextFieldValorCusto().setText(valor);
		
	}
	
	public ModelCustoBasico getDados(ModelUsuario usuario) {
		
		try {
			int l = editarCustosTela.getTableCustosBasicos().getSelectedRow();
			ModelCustoBasico j = (ModelCustoBasico) editarCustosTela.getTableCustosBasicos().getValueAt(l, 0);
			
			for(ModelCustoBasico custo: usuario.getCustosBasicos()) {
				if(j.getId() == custo.getId()) {
					return custo;
				}
			}	
		}catch (ArrayIndexOutOfBoundsException e) {
		
		}
		
		return null;
	}

	public ArrayList<Object> atualizarCustos(ModelCustoBasico custosBasicos, ModelUsuario usuario) {

		em.getTransaction().begin();

		ArrayList<Object> inseridos = new ArrayList<>();
		try {
			if(helper.verificarCampos()) {
				if(helper.validarValor()) {
					if(helper.compararDados(custosBasicos)) {
						
						ModelCustoBasico atualizarCusto = helper.obterModeloAtualizar(custosBasicos);
						
						new CustosBasicosDAO(em).insertOrUpdate(atualizarCusto);
						new UsuarioDAO(em).insertOrUpdate(usuario);
						
						inseridos.add(atualizarCusto);
						inseridos.add(usuario);
						
						editarCustosTela.exibirMensagemInformacao("O custo foi atualizado com sucesso!");
						
						helper.limparTela();
						atualizarTabela(usuario);
							
					}else {
						editarCustosTela.exibirMensagemInformacao("Nenhum registro foi alterado!");
					}
				}else {
					editarCustosTela.exibirMensagemErro("Insira um valor válido!");
				}
			}else {
				editarCustosTela.exibirMensagemErro("Não é possível atualizar um custo! Selecione alguma linha da tabela.");
			}
			
		}catch (NullPointerException e) {
			editarCustosTela.exibirMensagemErro("Selecione um custo antes de atualizar.");
		}
		
		em.getTransaction().commit();
		return inseridos;
	}

	public void excluirCustos(ModelCustoBasico custosBasicos, ModelUsuario usuario) {
		
		em.getTransaction().begin();

		try {
			if(helper.verificarCampos()) {
				if(helper.validarValor()) {
					if(helper.compararDados(custosBasicos) == false) {
						if (editarCustosTela.exibirMensagemDecisao("Deseja excluir o custo: " + custosBasicos.getNome() + "?")) {
							if (usuario.getCustosBasicos().remove(custosBasicos)) {
								
								new CustosBasicosDAO(em).deleteForUser();
				                new UsuarioDAO(em).insertOrUpdate(usuario);
				                
				                editarCustosTela.exibirMensagemInformacao("O custo foi excluido com sucesso");
				                
				                helper.limparTela();
				                atualizarTabela(usuario);
				            }else {
				                editarCustosTela.exibirMensagemErro("Custo não encontrado no usuário");
				            }
						}	
					}else {
						editarCustosTela.exibirMensagemErro("Não existe um custo cadastrado com esses dados.");
					}	
				}else {
					editarCustosTela.exibirMensagemErro("Valor inválido!");
				}
			}else {				
				editarCustosTela.exibirMensagemErro("Não é possível excluir um custo! Selecione alguma linha da tabela.");
			}

		}catch (NullPointerException e) {
			editarCustosTela.exibirMensagemErro("Selecione um custo antes de excluir");
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
