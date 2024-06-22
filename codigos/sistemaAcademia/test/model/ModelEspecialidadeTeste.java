package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelEspecialidadeTeste {

	@SuppressWarnings("unused")
	private ModelEspecialidade especialidadeConstructor = new ModelEspecialidade();
	private ModelEspecialidade especialidade;
	private ModelProfessor professorNovo;
	
	@BeforeEach
	void iniciarTeste() {
		especialidade = new ModelEspecialidade("");
		professorNovo = new ModelProfessor();
	}
	
	@Test
	void testarSetId() {
		especialidade.setId(1L);
		assertEquals(1L, especialidade.getId());
	}
	
	@Test
	void testarSetNome() {
		especialidade.setNome("NomeTeste");
		assertEquals("NomeTeste", especialidade.getNome());
	}
	
	@Test
	void testarGetProfessor() {
		especialidade.getProfessores().add(professorNovo);
		assertEquals(professorNovo, especialidade.getProfessores().get(0));
		
		ModelProfessor professorNovo2 = new ModelProfessor();
		especialidade.getProfessores().add(professorNovo2);

		assertEquals(2, especialidade.getProfessores().size());
	}
	
	@Test
	void testarSetProfessor() {
		ModelProfessor professorNovo2 = new ModelProfessor();
		
		ArrayList<ModelProfessor> professores = new ArrayList<>();
		professores.add(professorNovo);
		professores.add(professorNovo2);

		especialidade.setProfessores(professores);
		
		assertEquals(professorNovo, especialidade.getProfessores().get(0));
		assertEquals(2, especialidade.getProfessores().size());
	}
	
	@Test
	void testarProfessoresAtivoSemProfessor() {
		assertEquals(0, especialidade.getProfessoresAtivos());
	}
	
	@Test
	void testarProfessoresAtivoSemRegistro() {
		especialidade.getProfessores().add(professorNovo);
		assertEquals(0, especialidade.getProfessoresAtivos());
	}
	
	@Test
	void testarProfessoresAtivoComRegistroSim() {
		especialidade.getProfessores().add(professorNovo);
		ModelRegistroProfessor registroNovo = new ModelRegistroProfessor(professorNovo, "Sim");
		professorNovo.setRegistroProfessor(registroNovo);
		
		assertEquals(1, especialidade.getProfessoresAtivos());
	}
	
	@Test
	void testarProfessoresAtivoComRegistroNao() {
		especialidade.getProfessores().add(professorNovo);
		ModelRegistroProfessor registroNovo = new ModelRegistroProfessor(professorNovo, "Não");
		professorNovo.setRegistroProfessor(registroNovo);
		
		assertEquals(0, especialidade.getProfessoresAtivos());
	}
	
	@Test
	void testarAdicionarProfessor() {
		especialidade.adicionarProfessor(professorNovo);
		
		assertEquals(professorNovo, especialidade.getProfessores().get(0));
	}
	
	@Test
	void testarToString() {
		especialidade.setNome("NomeTeste");
		
		assertEquals("NomeTeste", especialidade.toString());
	}

	@Test
	void testarSetUsuario() {
		ModelUsuario novoUsuario = new ModelUsuario();
		especialidade.setUsuario(novoUsuario);
		assertEquals(novoUsuario, especialidade.getUsuario());
	}

}
