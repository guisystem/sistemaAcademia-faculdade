package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelFotoTeste {

	@SuppressWarnings("unused")
	private ModelFoto fotoConstructor = new ModelFoto();
	private ModelFoto foto;
	
	@BeforeEach
	void iniciarTeste() {
		foto = new ModelFoto("", "");
	}

	@Test
	void testarConstrutorProfessor() {
		ModelProfessor professorNovo = new ModelProfessor();
		ModelFoto foto = new ModelFoto("", "", professorNovo);
		assertEquals(professorNovo, foto.getProfessor());
	}

	@Test
	void testarConstrutorAluno() {
		ModelAluno alunoNovo = new ModelAluno();
		ModelFoto foto = new ModelFoto("", "", alunoNovo);
		assertEquals(alunoNovo, foto.getAluno());
	}
	
	@Test
	void testarSetId() {
		foto.setId(1);
		assertEquals(1, foto.getId());
	}
	
	@Test
	void testarSetNomeImagem() {
		foto.setNomeImagemField("NomeImagemTeste");
		assertEquals("NomeImagemTeste", foto.getNomeImagemField());
	}
	
	@Test
	void testarSetCaminhoImagem() {
		foto.setCaminhoImagem("Caminho/Imagem/Teste.jpeg");
		assertEquals("Caminho/Imagem/Teste.jpeg", foto.getCaminhoImagem());
	}
	
	@Test
	void testarSetAluno() {
		ModelAluno alunoNovo = new ModelAluno();
		foto.setAluno(alunoNovo);
		assertEquals(alunoNovo, foto.getAluno());
	}
	
	@Test
	void testarSetProfessor() {
		ModelProfessor professorNovo = new ModelProfessor();
		foto.setProfessor(professorNovo);
		assertEquals(professorNovo, foto.getProfessor());
	}
}
