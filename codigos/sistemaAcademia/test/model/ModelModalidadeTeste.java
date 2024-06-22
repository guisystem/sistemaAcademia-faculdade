package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelModalidadeTeste {

	@SuppressWarnings("unused")
	private ModelModalidade modalidadeConstructor = new ModelModalidade();
	private ModelModalidade modalidade;
	private ModelAluno alunoNovo;
	
	@BeforeEach
	void iniciarTeste() {
		modalidade = new ModelModalidade("", 0);
		alunoNovo = new ModelAluno();
	}
	
	@Test
	void testarSetId() {
		modalidade.setId(1L);
		assertEquals(1L, modalidade.getId());
	}
	
	@Test
	void testarSetNome() {
		modalidade.setNome("NomeTeste");
		assertEquals("NomeTeste", modalidade.getNome());
	}
	
	@Test
	void testarSetTaxa(){
		modalidade.setTaxaExtra(10);
		assertEquals(10, modalidade.getTaxaExtra());
	}
	
	@Test
	void testarGetAluno(){
		ArrayList<ModelAluno> alunos = new ArrayList<>();
		alunos.add(alunoNovo);
		
		modalidade.setAlunos(alunos);
		assertEquals(alunoNovo, modalidade.getAlunos().get(0));
	}
	
	@Test
	void testarAdicionarAluno(){
		modalidade.adicionarAluno(alunoNovo);
		assertEquals(alunoNovo, modalidade.getAlunos().get(0));
	}
	
	@Test
	void testarGetAlunoAtivoSemAluno() {
		assertEquals(0, modalidade.getAlunosAtivos());
	}
	
	@Test
	void testarGetAlunoAtivoSemRegistro() {
		modalidade.getAlunos().add(alunoNovo);
		assertEquals(0, modalidade.getAlunosAtivos());
	}
	
	@Test
	void testarGetAlunoAtivoComRegistroSim() {
		ModelPlano plano = new ModelPlano("", 1, 0);
		alunoNovo.setPlano(plano);

		modalidade.getAlunos().add(alunoNovo);
		ModelRegistroAluno registroNovo = new ModelRegistroAluno(alunoNovo);
		alunoNovo.setRegistroAluno(registroNovo);
		
		assertEquals(1, modalidade.getAlunosAtivos());
	}
	
	@Test
	void testarGetAlunoAtivoComRegistroNao() {
		ModelPlano plano = new ModelPlano("", 1, 0);
		alunoNovo.setPlano(plano);
		
		modalidade.getAlunos().add(alunoNovo);
		ModelRegistroAluno registroNovo = new ModelRegistroAluno(alunoNovo);
		
		String pagamento = "01/01/2000";
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate proximoPagamento = LocalDate.parse(pagamento, dtf);
		registroNovo.setProximoPagamento(proximoPagamento);
		alunoNovo.setRegistroAluno(registroNovo);
		
		assertEquals(0, modalidade.getAlunosAtivos());
	}
	
	@Test
	void testarGetProfessorAtivoUsuarioNull() {
		assertEquals(0, modalidade.getProfessoresAtivos(modalidade));
	}
	
	@Test
	void testarGetProfessorAtivoNomeIgual() {
		ModelUsuario novoUsuario = new ModelUsuario();
		modalidade.setUsuario(novoUsuario);
		
		modalidade.setNome("Igual");
		ModelEspecialidade novaEspecialidade = new ModelEspecialidade("Igual");
		novoUsuario.getEspecialidades().add(novaEspecialidade);
		
		assertEquals(0, modalidade.getProfessoresAtivos(modalidade));
	}
	
	@Test
	void testarGetProfessorAtivoNomeDiferente() {
		ModelUsuario novoUsuario = new ModelUsuario();
		modalidade.setUsuario(novoUsuario);
		
		modalidade.setNome("Igual");
		ModelEspecialidade novaEspecialidade = new ModelEspecialidade("Diferente");
		novoUsuario.getEspecialidades().add(novaEspecialidade);
		
		assertEquals(0, modalidade.getProfessoresAtivos(modalidade));
	}

	@Test
	void testarToString() {
		modalidade.setNome("NomeTeste");
		
		assertEquals("NomeTeste R$:" + modalidade.getTaxaExtra(), modalidade.toString());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario novoUsuario = new ModelUsuario();
		modalidade.setUsuario(novoUsuario);
		assertEquals(novoUsuario, modalidade.getUsuario());
	}
	

}
