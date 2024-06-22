package controller;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import controller.helper.RegistroAlunoHelper;
import model.ModelCores;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import model.dao.RegistroAlunoDAO;
import servico.JPAUtil;
import view.RegistroAlunoTela;

public class RegistroAlunoController {

	private final RegistroAlunoTela registroAlunoTela;
	private static RegistroAlunoHelper helper = null;
	private EntityManager em;
	
	public RegistroAlunoController(RegistroAlunoTela registroAlunoTela) {
		this.em = new JPAUtil().getEntityManager();
		this.registroAlunoTela = registroAlunoTela;
		RegistroAlunoController.helper = new RegistroAlunoHelper(registroAlunoTela);
	}

	public static void atualizarTabela(ModelUsuario usuario) {
		List<ModelRegistroAluno> registroAlunos = usuario.getRegistroAlunos();
		helper.preencherTabela(registroAlunos);
	}

	public ModelRegistroAluno getDadosAlunoSelecionado(ModelUsuario usuario) {
		int i = registroAlunoTela.getTableRegistroAlunos().getSelectedRow();
		ModelRegistroAluno j = (ModelRegistroAluno) registroAlunoTela.getTableRegistroAlunos().getValueAt(i, 0);
		
		for(ModelRegistroAluno aluno: usuario.getRegistroAlunos()) {
			if(j.getId() == aluno.getId()) {
				return aluno;
			}
		}
		return null;
	}

	public ModelRegistroAluno atualizarProximoPagamento(ModelRegistroAluno registroAluno, ModelUsuario usuario) {
		em.getTransaction().begin();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		if(registroAluno.getPagar().equalsIgnoreCase("Não Disponível")) {
			registroAlunoTela.exibirMensagemErro("Não é possivel realizar o pagamento. A duração do plano ainda está em andamento");
		}else {
			if(helper.dataAntiga(registroAluno) == false) {
				if (registroAlunoTela.exibirMensagemDecisao("Deseja realizar o pagamento do plano " + registroAluno.getAluno().getPlano().getNome() + " do(a) aluno(a): " + registroAluno.getAluno().getNome() + "?")) {
					
					String dataPagamento = registroAluno.getProximoPagamentoFormatada();
					registroAluno.setUltimoPagamento(LocalDate.parse(dataPagamento, dtf));
					
					String dataProximoPagamento = registroAluno.getProximoPagamento().plusMonths(registroAluno.getAluno().getPlano().getPeriodo()).format(dtf);
					LocalDate ultimoPagamento = LocalDate.parse(dataProximoPagamento, dtf);
					registroAluno.setProximoPagamento(ultimoPagamento);
					
					registroAluno.setAtivo();
					
					new RegistroAlunoDAO(em).insertOrUpdate(registroAluno);
					
					atualizarTabela(usuario);
				}
			}else { 
				if(registroAlunoTela.exibirMensagemDecisao("Por fazer mais de 10 dias desde o ultimo pagamento, a data de último pagamento e de matrícula \r\n"
						+ "receberá a data de hoje. Deseja prosseguir com o pagamento?")) {
					
					LocalDate hoje = LocalDate.now();

					registroAluno.getAluno().setDataMatricula(hoje);
					registroAluno.setUltimoPagamento(hoje);
					
					String dataProximoPagamento = hoje.plusMonths(registroAluno.getAluno().getPlano().getPeriodo()).format(dtf);
					LocalDate proximoPagamento = LocalDate.parse(dataProximoPagamento, dtf);
					registroAluno.setProximoPagamento(proximoPagamento);
					
					registroAluno.setAtivo();
					
					new RegistroAlunoDAO(em).insertOrUpdate(registroAluno);
					atualizarTabela(usuario);					
				}
			}
		}
		
		em.getTransaction().commit();
		return registroAluno;
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
