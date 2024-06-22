package controller.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelAluno;
import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelPlano;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import view.RegistroAlunoTela;

class RegistroAlunoHelperTeste {

	private RegistroAlunoTela registroAlunoTela;
	private RegistroAlunoHelper helper;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		
		registroAlunoTela = new RegistroAlunoTela(usuario);
		helper = new RegistroAlunoHelper(registroAlunoTela);
	}
	
	@Test
	void testarPreencherTabelaVazia() {
		helper.preencherTabela(usuario.getRegistroAlunos());
		assertEquals(0, registroAlunoTela.getTableRegistroAlunos().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComDados() {		
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(1, aluno);
		registroAluno.setUltimoPagamento(LocalDate.now());
		aluno.setRegistroAluno(registroAluno);
		
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		helper.preencherTabela(usuario.getRegistroAlunos());
		assertEquals(1, registroAlunoTela.getTableRegistroAlunos().getRowCount());
	}
	
	@Test
	void testarDataAntiga() {
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(1, aluno);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate dataAntiga = LocalDate.parse("01/01/2010", dtf);
		registroAluno.setProximoPagamento(dataAntiga);
		aluno.setRegistroAluno(registroAluno);
		
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		helper.preencherTabela(usuario.getRegistroAlunos());
		assertTrue(helper.dataAntiga(registroAluno));
	}
	
	@Test
	void testarDataAntigaNaoAntiga() {
		ModelAluno aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", new ModelPlano());
		ModelRegistroAluno registroAluno = new ModelRegistroAluno(1, aluno);
		registroAluno.setProximoPagamento(LocalDate.now());
		aluno.setRegistroAluno(registroAluno);
		
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		helper.preencherTabela(usuario.getRegistroAlunos());
		assertFalse(helper.dataAntiga(registroAluno));
	}
	
	@Test
	void testarSetarCoresCorSecundariaBranca() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = registroAlunoTela.getTableRegistroAlunos().getBackground();
		assertEquals(new Color(245, 245, 245), corP);
		
		Color corS = registroAlunoTela.getTableRegistroAlunos().getTableHeader().getBackground();
		assertEquals(new Color(245, 245, 245), corS);
	}

	@Test
	void testarSetarCoresPrimariaESecundariaMaisDeUma() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = registroAlunoTela.getLblRegistroAlunos().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = registroAlunoTela.getTableRegistroAlunos().getTableHeader().getBackground();
		assertEquals(coresSecundarias.get(1), corS);
	}
}
