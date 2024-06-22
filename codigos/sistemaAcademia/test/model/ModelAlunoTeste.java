package model;

import static org.junit.jupiter.api.Assertions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class ModelAlunoTeste {

	@SuppressWarnings("unused")
	private ModelAluno alunoConstructor = new ModelAluno();
	private ModelAluno aluno;

	@BeforeEach
	void iniciarTeste() {
		aluno = new ModelAluno("testeNome", "testeCpf" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
	}
	
	@Test
	void testarConstrutorComId() {
		ModelAluno alunoId = new ModelAluno(1, "testeNomeID", "testeCpfID" ,"testeEmailID", "01/01/1999", "01/01/1999", new ModelPlano());
		String nome = "testeNomeID";
		assertEquals(nome, alunoId.getNome());
	}
	
	@Test
	void testarConstrutorComIdDataMatriculaNull() {
		ModelAluno alunoComIdMatriculaNull = new ModelAluno(1, "testeNomeID", "testeCpfID" ,"testeEmailID", "01/01/1999", null, new ModelPlano());
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		assertEquals(dataHoje, alunoComIdMatriculaNull.getDataMatriculaFormatada());
	}
	
	@Test
	void testarConstrutorComIdDataNascimentoExcecao() {
		ModelAluno alunoComIdNascimentoExcecao = new ModelAluno(1, "testeNomeID", "testeCpfID" ,"testeEmailID", null, "01/01/1999", new ModelPlano());
		
		assertEquals(null, alunoComIdNascimentoExcecao.getDataNascimentoFormatada());
	}
	
	@SuppressWarnings("unused")
	@Test
	void testarDataConvertida() {
		boolean converte = false;
		Date dataNascimentoConvertida;
		Date dataMatriculaConvertida;
		
		try {
			dataNascimentoConvertida = new SimpleDateFormat("dd/MM/yyyy").parse(aluno.getDataNascimentoFormatada());
			dataMatriculaConvertida = new SimpleDateFormat("dd/MM/yyyy").parse(aluno.getDataMatriculaFormatada());
			
			converte = true;
		} catch (ParseException e) {
			
		}
		
		assertTrue(converte);
	}
	
	@Test
	void testarDataExcecao() {
		boolean converte = true;
		String dataNascimentoExcecao = "1s/01/1979";
	
		@SuppressWarnings("unused")
		Date dataNascimentoConvertida;
		
		try {
			dataNascimentoConvertida = new SimpleDateFormat("dd/MM/yyyy").parse(dataNascimentoExcecao);
		} catch (ParseException e) { 
			converte = false;
		}

		assertFalse(converte);
	}
	
	@Test
	void testarDataMatriculaNullDate() {
		Date dataComparar = null;
		String data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		try {
			dataComparar = new SimpleDateFormat("dd/MM/yyyy").parse(data);
		} catch (ParseException e) {

		}
		
		Date dataMatriculaHoje = null;
		aluno.setDataMatricula(null);
		try {
			dataMatriculaHoje = new SimpleDateFormat("dd/MM/yyyy").parse(aluno.getDataMatriculaFormatada());
		} catch (ParseException e) {
			
		}
		
		assertEquals(dataComparar, dataMatriculaHoje);
	}
	
	@Test
	void testarDataMatriculaNullString() {
		ModelAluno alunoDataMatriculaNull =  new ModelAluno("testeNomeID", "testeCpfID" ,"testeEmailID", "01/01/1999", null, new ModelPlano());
		String dataHoje = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		assertEquals(dataHoje, alunoDataMatriculaNull.getDataMatriculaFormatada());
	}
	
	@Test
	void testarDataNascimentoVaziaExcecao() {
		ModelAluno alunoDataMatriculaExcecao = new ModelAluno("testeNomeID", "testeCpfID" ,"testeEmailID", "", "01/01/1999", new ModelPlano());
		assertEquals(null, alunoDataMatriculaExcecao.getDataNascimentoFormatada());
	}
	
	@Test
	void testarDataNascimentoNullExcecao() {
		ModelAluno alunoDataMatriculaExcecao = new ModelAluno("testeNomeID", "testeCpfID" ,"testeEmailID", null, "01/01/1999", new ModelPlano());
		assertEquals(null, alunoDataMatriculaExcecao.getDataNascimentoFormatada());
	}
	
	@Test
	void testarSetDataNascimento() {
		Date dataInformada = new Date();
		aluno.setDataNascimento(dataInformada);
		
		Date dataComparar = new Date();
		assertEquals(dataComparar, aluno.getDataNascimento());
	}
	
	@Test
	void testarSetDataMatricula() {
		LocalDate dataInformada = LocalDate.now();
		aluno.setDataMatricula(dataInformada);
		
		LocalDate dataComparar = LocalDate.now();
		assertEquals(dataComparar, aluno.getDataMatricula());
	}
	
	@Test
	void testarGetDataNascimentoFormatada() {
		aluno.setDataNascimento(null);
		assertEquals(null, aluno.getDataNascimentoFormatada());
	}
	
	@Test
	void testarSetDataNascimentoFormatada() {
		aluno.setDataNascimentoFormatada("01/01/2000");
		assertEquals("01/01/2000", aluno.getDataNascimentoFormatada());
	}
	
	@Test
	void testarSetDataNascimentoFormatadaExcecao() {
		aluno.setDataNascimentoFormatada("");
		assertEquals("01/01/1979", aluno.getDataNascimentoFormatada());
	}
	
	@Test
	void testarSetDataMatriculaFormatada() {
		String dataMatriculaNull = "01/01/2000";
		aluno.setDataMatriculaFormatada("01/01/2000");
		
		assertEquals(dataMatriculaNull, aluno.getDataMatriculaFormatada());
	}
	
	@Test
	void testarSetDataMatriculaFormatadaNull() {
		String dataMatriculaNull = new SimpleDateFormat("dd/MM/yyyy").format(new Date()); // Quando a matricula é null, recebe a data atual
		aluno.setDataMatricula(null);
		aluno.setDataMatriculaFormatada(null);
		
		assertEquals(dataMatriculaNull, aluno.getDataMatriculaFormatada());
	}
	
	@Test
	void testarSetDataMatriculaFormatadaExcecao() {
		String dataMatriculaNull = "01/01/1979";
		aluno.setDataMatriculaFormatada("");
		
		assertEquals(dataMatriculaNull, aluno.getDataMatriculaFormatada());
	}
	
	@Test
	void testarSetPlano() {
		ModelPlano planoNovo = new ModelPlano("PlanoTeste", 1, 40);
		aluno.setPlano(planoNovo);
		
		assertEquals(planoNovo, aluno.getPlano());
	}
	
	@Test
	void testarSetUsuario() {
		ModelUsuario usuarioNovo = new ModelUsuario("UsuarioTeste", "UsuarioSenha");
		aluno.setUsuario(usuarioNovo);;
		
		assertEquals(usuarioNovo, aluno.getUsuario());
	}
	
	@Test
	void testarSetModalidades() {
		ModelModalidade modalidade1 = new ModelModalidade();
		
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(modalidade1);
		aluno.setModalidades(modalidades);

		assertEquals(modalidades, aluno.getModalidades());
	}
	
	@Test
	void testarSetRegistroAluno() {
		ModelRegistroAluno novoRegistroAluno = new ModelRegistroAluno();
		aluno.setRegistroAluno(novoRegistroAluno);
		
		assertEquals(novoRegistroAluno, aluno.getRegistroAluno());
	}
	
	@Test
	void testarSetAdicionarModalidade() {
		ModelModalidade modalidade2 = new ModelModalidade();
		aluno.adicionarModalidade(modalidade2);
		
		assertEquals(modalidade2, aluno.getModalidades().get(0));
	}
	
	@Test
	void testarSetRemoverModalidade() {
		ModelModalidade modalidade2 = new ModelModalidade();
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		
		aluno.adicionarModalidade(modalidade2);
		aluno.removerModalidade(modalidade2);
		
		assertEquals(modalidades, aluno.getModalidades());
	}
	
	@Test
	void testarSetFoto() {
		ModelFoto fotoNova = new ModelFoto();
		aluno.setFoto(fotoNova);
		
		assertEquals(fotoNova, aluno.getFoto());
	}
	
}
