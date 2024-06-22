package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelFuncionarioTeste {

	@SuppressWarnings("unused")
	private ModelFuncionario funcionarioConstructor = new ModelFuncionario();
	private ModelFuncionario funcionario;
	
	@BeforeEach
	void iniciarTeste() {
		funcionario = new ModelFuncionario("", "", 0);
	}
	
	@Test
	void testarSetId() {
		funcionario.setId(1L);
		assertEquals(1L, funcionario.getId());
	}
	
	@Test
	void testarSetNome() {
		funcionario.setNome("NomeTeste");
		assertEquals("NomeTeste", funcionario.getNome());
	}
	
	@Test
	void testarSetCargo() {
		funcionario.setCargo("CargoTeste");
		assertEquals("CargoTeste", funcionario.getCargo());
	}
	
	@Test
	void testarSetSalario() {
		funcionario.setSalario(10);
		assertEquals(10, funcionario.getSalario());
	}

	@Test
	void testarToString() {
		funcionario.setNome("NomeTeste");
		
		assertEquals("NomeTeste", funcionario.toString());
	}

}
