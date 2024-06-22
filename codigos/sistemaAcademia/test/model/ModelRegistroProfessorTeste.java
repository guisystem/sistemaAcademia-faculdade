package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelRegistroProfessorTeste {

	@SuppressWarnings("unused")
	private ModelRegistroProfessor registroProfessorConstructor = new ModelRegistroProfessor();
	private ModelRegistroProfessor registroProfessor;
	private ModelProfessor professor;
	
	@BeforeEach
	void iniciarTeste() {
		ModelEspecialidade especialidadeNova = new ModelEspecialidade("Nome");
		professor = new ModelProfessor("testeNome" ,"testeCpf", "testeEmail", "00:00", "00:00", 0, especialidadeNova);
		registroProfessor = new ModelRegistroProfessor(0, professor, "Sim");
	}
	
	@Test
	void testarSetId() {
		registroProfessor.setId(1);
		assertEquals(1, registroProfessor.getId());
	}
	
	@Test
	void testarSetProfessor() {
		registroProfessor.setProfessor(professor);
		assertEquals(professor, registroProfessor.getProfessor());
	}
	
	@Test
	void testarSetAtivo() {
		registroProfessor.setAtivo("Não");
		assertEquals("Não", registroProfessor.getAtivo());
	}
	
	@Test
	void testarSetDetalhes() {
		registroProfessor.setDetalhe("+ Detalhes");
		assertEquals("+ Detalhes", registroProfessor.getDetalhe());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario novoUsuario = new ModelUsuario();
		registroProfessor.setUsuario(novoUsuario);
		assertEquals(novoUsuario, registroProfessor.getUsuario());
	}

	@Test
	void testarToString() {
		registroProfessor.getProfessor().setNome("NomeTeste");
		
		assertEquals("NomeTeste", registroProfessor.toString());
	}

}
