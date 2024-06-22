package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelCustoBasicoTeste {

	@SuppressWarnings("unused")
	private ModelCustoBasico custoBasicoContructor = new ModelCustoBasico();
	private ModelCustoBasico custoBasico;
	
	@BeforeEach
	void iniciarTeste() {
		custoBasico = new ModelCustoBasico("", 0);
	}
	
	@Test
	void testarSetId() {
		custoBasico.setId(1L);
		assertEquals(1L, custoBasico.getId());
	}
	
	@Test
	void testarSetNome() {
		custoBasico.setNome("NomeTeste");
		assertEquals("NomeTeste", custoBasico.getNome());
	}
	
	@Test
	void testarSetValor() {
		custoBasico.setValor(10);
		assertEquals(10, custoBasico.getValor());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario novoUsuario = new ModelUsuario();
		custoBasico.setUsuario(novoUsuario);
		assertEquals(novoUsuario, custoBasico.getUsuario());
	}
	
	@Test
	void testarToString() {
		custoBasico.setNome("NomeTeste");
		
		assertEquals("NomeTeste", custoBasico.toString());
	}

}
