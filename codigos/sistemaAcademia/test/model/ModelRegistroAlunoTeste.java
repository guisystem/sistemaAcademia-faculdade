package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelRegistroAlunoTeste {

	@SuppressWarnings("unused")
	private ModelRegistroAluno registroAlunoConstructor = new ModelRegistroAluno();
	private ModelRegistroAluno registroAluno;
	private ModelAluno aluno;
			
	@BeforeEach
	void iniciarTeste() {
		aluno = new ModelAluno("testeNomeID", "testeCpfID" ,"testeEmailID", "01/01/1999", "01/01/1999", new ModelPlano());
		registroAluno = new ModelRegistroAluno(0, aluno);
	}
	
	@Test
	void testarSetId() {
		registroAluno.setId(1);
		assertEquals(1, registroAluno.getId());
	}
	
	@Test
	void testarSetAluno() {
		registroAluno.setAluno(aluno);
		
		assertEquals(aluno, registroAluno.getAluno());
	}
	
	@Test
	void testarSetNaoAtivoSemParametro() {
		
		String pagamento = "01/01/2000";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(pagamento, dtf);
		registroAluno.setProximoPagamento(proximoPagamento);
		
		assertEquals("Não", registroAluno.getAtivo());
	}
	
	@Test
	void testarSetAtivoComParametro() {
		
		registroAluno.setAtivo("Sim"); /** NOTE: Mesmo setando o valor diretamente, a definição de um aluno está ativo 
		 							       ou não é baseada em calculos relacionado a regra de negócio do aplicação */
		
		assertEquals("Não", registroAluno.getAtivo());
	}
	
	@Test
	void testarSetAtivoSemParametro() {
		
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		String pagamento = dataHoje;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(pagamento, dtf);
		registroAluno.setProximoPagamento(proximoPagamento);

		assertEquals("Sim", registroAluno.getAtivo());
	}
	
	@Test
	void testarSetDetalhe() {
		registroAluno.setDetalhe("Detalhes");
		assertEquals("Detalhes", registroAluno.getDetalhe());
	}
	
	@Test
	void testarSetUltimoPagamento() {
		String pagamento = "01/01/2000";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ultimoPagamento = LocalDate.parse(pagamento, dtf);
		registroAluno.setUltimoPagamento(ultimoPagamento);
		
		assertEquals(ultimoPagamento, registroAluno.getUltimoPagamento());
	}
	
	@Test
	void testarSetProximoPagamento() {
		String pagamento = "11/11/2025";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(pagamento, dtf);
		registroAluno.setProximoPagamento(proximoPagamento);
		
		assertEquals(proximoPagamento, registroAluno.getProximoPagamento());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario novoUsuario = new ModelUsuario();
		registroAluno.setUsuario(novoUsuario);
		assertEquals(novoUsuario, registroAluno.getUsuario());
	}
	
	@Test
	void testarGetTotalSemPlanoSemModalidade() {
		assertEquals(0, registroAluno.getTotal());
	}
	
	@Test
	void testarGetTotalComPlanoSemModalidae() {
		ModelPlano planoNovo = new ModelPlano("Nome", 1, 50);
		registroAluno.getAluno().setPlano(planoNovo);
		assertEquals(50, registroAluno.getTotal());
	}
	
	@Test
	void testarGetTotalComPlanoComModalidae() {
		ModelPlano planoNovo = new ModelPlano("Nome", 1, 50);
		registroAluno.getAluno().setPlano(planoNovo);
		
		ModelModalidade modalidadeNova = new ModelModalidade("NomeModalidade", 10);
		registroAluno.getAluno().adicionarModalidade(modalidadeNova);
		
		assertEquals(60, registroAluno.getTotal());
	}
	
	@Test
	void testarSetTotal() {
		registroAluno.setTotal(100); /** NOTE: A mesma regra do ativo ou não também se aplica aqui. O total a pagar 
									 de um aluno é baseado no plano escolhido e modalidades em que se cadastrou. */
		assertEquals(0, registroAluno.getTotal());
	}
	
	@Test
	void testarGetPagarDisponivel() {
		String pagamento = "11/11/2023";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ultimoPagamento = LocalDate.parse(pagamento, dtf);
		registroAluno.setUltimoPagamento(ultimoPagamento);
		
		assertEquals("Pagar", registroAluno.getPagar());
	}
	
	@Test
	void testarGetPagarIndisponivel() {
		
		String pagamento = "11/11/2025";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ultimoPagamento = LocalDate.parse(pagamento, dtf);
		registroAluno.setUltimoPagamento(ultimoPagamento);
		
		assertEquals("Não Disponível", registroAluno.getPagar());
	}
	
	@Test
	void testarToString() {
		registroAluno.getAluno().setNome("NomeTeste");
		
		assertEquals("NomeTeste", registroAluno.toString());
	}
	
}
