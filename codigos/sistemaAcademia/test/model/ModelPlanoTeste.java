package model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelPlanoTeste {

	@SuppressWarnings("unused")
	private ModelPlano planoConstructor = new ModelPlano();
	private ModelPlano plano;
	private ModelAluno alunoNovo;
	
	@BeforeEach
	void iniciarTeste() {
		plano = new ModelPlano("", 1, 0);
		alunoNovo = new ModelAluno();
	}

	@Test
	void testarSetId() {
		plano.setId(1L);
		assertEquals(1L, plano.getId());
	}
	
	@Test
	void testarSetNome() {
		plano.setNome("NomeTeste");
		assertEquals("NomeTeste", plano.getNome());
	}
	
	@Test
	void testarSetPeriodo(){
		plano.setPeriodo(10);
		assertEquals(10, plano.getPeriodo());
	}
	
	@Test
	void testarSetValor(){
		plano.setValor(50);
		assertEquals(50, plano.getValor());
	}
	
	@Test
	void testarSetAluno(){
		ArrayList<ModelAluno> alunos = new ArrayList<>();
		alunos.add(alunoNovo);
		
		plano.setAlunos(alunos);
		assertEquals(alunoNovo, plano.getAlunos().get(0));
	}
	
	@Test
	void testarGetAlunoAtivoSemAluno() {
		assertEquals(0, plano.getAlunosAtivos());
	}
	
	@Test
	void testarGetAlunoAtivoSemRegistro() {
		plano.getAlunos().add(alunoNovo);
		assertEquals(0, plano.getAlunosAtivos());
	}
	
	@Test
	void testarGetAlunoAtivoComRegistroSim() {
		ModelPlano plano = new ModelPlano("", 1, 0);
		alunoNovo.setPlano(plano);

		plano.getAlunos().add(alunoNovo);
		ModelRegistroAluno registroNovo = new ModelRegistroAluno(alunoNovo);
		alunoNovo.setRegistroAluno(registroNovo);
		
		assertEquals(1, plano.getAlunosAtivos());
	}
	
	@Test
	void testarGetAlunoAtivoComRegistroNao() {
		ModelPlano plano = new ModelPlano("", 1, 0);
		alunoNovo.setPlano(plano);
		
		plano.getAlunos().add(alunoNovo);
		ModelRegistroAluno registroNovo = new ModelRegistroAluno(alunoNovo);
		
		String pagamento = "01/01/2000";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(pagamento, dtf);
		registroNovo.setProximoPagamento(proximoPagamento);
		alunoNovo.setRegistroAluno(registroNovo);
		
		assertEquals(0, plano.getAlunosAtivos());
	}

	@Test
	void testarToString() {
		plano.setNome("NomeTeste");
		
		assertEquals("NomeTeste", plano.toString());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario novoUsuario = new ModelUsuario();
		plano.setUsuario(novoUsuario);
		assertEquals(novoUsuario, plano.getUsuario());
	}
	
}
