package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelAluno;
import model.ModelConfiguracao;
import model.ModelPlano;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import servico.Criptografar;
import view.RegistroAlunoTela;

class RegistroAlunoControllerTeste {

	private RegistroAlunoTela registroAlunoTela;
	private RegistroAlunoController controller;
	private ModelUsuario usuario;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		
		registroAlunoTela = new RegistroAlunoTela(usuario);
		controller = new RegistroAlunoController(registroAlunoTela);
		controller.setEm(em);
	}

	@Test
	void testarPreencherTabela() {
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
		aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(0, aluno);
		registroAluno.setUltimoPagamento(LocalDate.now());
		aluno.setRegistroAluno(registroAluno);
		registroAluno.setAluno(aluno);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		RegistroAlunoController.atualizarTabela(usuario);
		assertEquals(1, registroAlunoTela.getTableRegistroAlunos().getRowCount());
	}
	
	@Test
	void testarAtualizarPagamentoNaoDisponivel() {
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 50);
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano);
		aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
		aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(0, aluno);
		registroAluno.setUltimoPagamento(LocalDate.now());
		aluno.setRegistroAluno(registroAluno);
		registroAluno.setAluno(aluno);
		usuario.getPlanos().add(plano);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		controller.atualizarProximoPagamento(registroAluno, usuario);
		/** NOTE: Terá que exibir uma mensagem de pagamento não disponível */

	}
	
	@Test
	void testarAtualizarPagamentoDisponivelDataAntiga() {
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 50);
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano);
		aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
		aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(0, aluno);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataAntiga = LocalDate.parse("12/12/2010", dtf);
		registroAluno.setUltimoPagamento(dataAntiga);
		aluno.setRegistroAluno(registroAluno);
		registroAluno.setAluno(aluno);
		usuario.getPlanos().add(plano);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		ModelRegistroAluno inserir = controller.atualizarProximoPagamento(registroAluno, usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja realizar o pagamento antigo. Clicar em "Sim" para pagar */
		
		if(inserir.getUltimoPagamento() != dataAntiga) {
			verify(em, times(1)).persist(inserir);
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
		}
			
	
	}
	
	@Test
	void testarAtualizarPagamentoDisponivelDataRecente() {
		ModelPlano plano = new ModelPlano("NovoPlano", 1, 50);
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano);
		aluno.setCpf(Criptografar.criptografar(aluno.getCpf()));
		aluno.setEmail(Criptografar.criptografar(aluno.getEmail()));
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(0, aluno);
		registroAluno.setProximoPagamento(LocalDate.now());
		aluno.setRegistroAluno(registroAluno);
		registroAluno.setAluno(aluno);
		usuario.getPlanos().add(plano);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		ModelRegistroAluno inserir = controller.atualizarProximoPagamento(registroAluno, usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja realizar o pagamento. clicar em "Sim" para pagar */
		
		if(!inserir.getProximoPagamento().equals(LocalDate.now())) {
			verify(em, times(1)).persist(inserir);
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();

			LocalDate dataProximPagamento = LocalDate.now();
			assertEquals(dataProximPagamento.plusMonths(plano.getPeriodo()), registroAluno.getProximoPagamento());
		}else {
			LocalDate dataProximPagamento = LocalDate.now();
			assertEquals(dataProximPagamento, registroAluno.getProximoPagamento());
		}
			
	}
	
	@Test
	void testarAtualizarCores() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		controller.atualizarCores(usuario);
		
		Color corP = registroAlunoTela.getTableRegistroAlunos().getTableHeader().getForeground();
		assertEquals(Color.YELLOW, corP);
		
	}
}
