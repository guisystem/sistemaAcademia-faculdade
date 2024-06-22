package controller.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelAluno;
import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelFoto;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import servico.Criptografar;
import view.CadastroAlunoTela;

class CadastroAlunoHelperTeste {

	private CadastroAlunoHelper helper;
	private CadastroAlunoTela cadastroAlunoTela;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		cadastroAlunoTela = new CadastroAlunoTela(usuario);
		helper = new CadastroAlunoHelper(cadastroAlunoTela);
	}
	
	@Test
	void testarAtualizarComboBoxPlano() {
		ModelPlano plano1 = new ModelPlano();
		ModelPlano plano2 = new ModelPlano("Plano2", 4, 200);
		ModelPlano plano3 = new ModelPlano("Plano3", 1, 50);
		
		usuario.getPlanos().add(plano3);
		usuario.getPlanos().add(plano2);
		usuario.getPlanos().add(plano1);
		
		helper.atualizarPlanos(usuario.getPlanos());
		int i = cadastroAlunoTela.getComboBoxPlano().getItemCount();
		
		assertEquals(3, i);
	}
	
	@Test
	void testarAtualizarComboBoxModalidade() {
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		helper.atualizarModalidades(usuario.getModalidades(), usuario);
		int i = cadastroAlunoTela.getComboBoxAdicionarModalidade().getItemCount();
		
		assertEquals(2, i);
	}
	
	@Test
	void testarAtualizarComboBoxModalidadeCpfDiferenteDeNullEVazio() {
		ModelModalidade modalidade1 = new ModelModalidade();
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("12345678901");
		
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		helper.atualizarModalidades(usuario.getModalidades(), usuario);
		
		int i = cadastroAlunoTela.getComboBoxAdicionarModalidade().getItemCount();
		
		assertEquals(2, i);
	}
	
	@Test
	void testarAtualizarComboBoxModalidadeUltimoCaso() {
		ModelModalidade modalidade1 = new ModelModalidade();
		modalidade1.setId(1L);
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 4);
		modalidade2.setId(2L);
		
		ModelAluno aluno = new ModelAluno();
		aluno.adicionarModalidade(modalidade1);
		modalidade1.adicionarAluno(aluno);
		
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade1);
		usuario.getModalidades().add(modalidade2);
		
		helper.atualizarModalidades(usuario.getModalidades(), usuario);
		
		int i = cadastroAlunoTela.getComboBoxAdicionarModalidade().getItemCount();
		assertEquals(1, i);
	}
	
	@Test
	void testarObterPlano() {
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		
		usuario.getPlanos().add(plano);
		
		helper.atualizarPlanos(usuario.getPlanos());
		
		ModelPlano planoCombo = helper.obterPlano();
		
		assertEquals(plano, planoCombo);
	}
	
	@Test
	void testarObterAlunoComCpfDiferenteDeVazio() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("12345678901");
		
		usuario.getAlunos().add(aluno);
		
		ModelAluno alunoObter = helper.obterAluno(usuario);
		
		assertEquals(null, alunoObter);
	}
	
	@Test
	void testarObterAlunoComCpfIgualAVazio() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		
		usuario.getAlunos().add(aluno);
		
		ModelAluno alunoObter = helper.obterAluno(usuario);
		
		assertEquals(aluno, alunoObter);
	}
	
	@Test
	void testarPreencherValorDinamico() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade2", 4);
		modalidade.setId(1L);
		
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		plano.setId(1L);
		
		ModelAluno aluno = new ModelAluno();
		aluno.setPlano(plano);
		plano.getAlunos().add(aluno);
		aluno.adicionarModalidade(modalidade);
		modalidade.adicionarAluno(aluno);
		
		helper.preencherValorDinamico(aluno.getPlano().getValor(), aluno.getPlano().getPeriodo(), aluno.getModalidades());
		
		assertEquals("54.0", cadastroAlunoTela.getTextFieldTotal().getText());
	}
	
	@Test
	void testarPreencherPeriodo() {
		ModelPlano plano = new ModelPlano("Plano", 6, 50);
		plano.setId(1L);
		
		ModelAluno aluno = new ModelAluno();
		aluno.setPlano(plano);
		plano.getAlunos().add(aluno);
		
		helper.preencherPeriodo(aluno.getPlano().getPeriodo());
		
		assertEquals("* A cada 6 meses.", cadastroAlunoTela.getLblTempoPagamento().getText());
	}
	
	@Test
	void testarObterModelo() {
		ModelPlano plano = new ModelPlano("Plano", 6, 50);
		plano.setId(1L);
		usuario.getPlanos().add(plano);
		
		helper.atualizarPlanos(usuario.getPlanos());
		
		cadastroAlunoTela.getTextFieldNomeAluno().setText("Nome");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("Email");
		
		ModelAluno aluno = helper.obterModelo();
		
		assertEquals("Nome", aluno.getNome());
		assertEquals("123.456.789-01", aluno.getCpf());
	}
	
	@Test
	void testarObterFoto() {
		
		ModelFoto fotoVazia = helper.obterFoto();
		assertEquals("", fotoVazia.getNomeImagemField());
		
		cadastroAlunoTela.setCaminhoImagem("Caminho/da/imagem.jpeg");
		cadastroAlunoTela.getNomeImagemField().setText("NomeImagem.jpeg");
		
		ModelFoto fotoNaoVazia = helper.obterFoto();
		
		assertEquals("NomeImagem.jpeg", fotoNaoVazia.getNomeImagemField());
	}
	
	@Test
	void testarObterModeloRegistro() {
		ModelPlano plano = new ModelPlano("Plano", 6, 50);
		plano.setId(1L);
		usuario.getPlanos().add(plano);
		
		helper.atualizarPlanos(usuario.getPlanos());
		
		cadastroAlunoTela.getTextFieldNomeAluno().setText("Nome");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("Email");
		
		ModelRegistroAluno registroAluno = helper.obterModeloRegistro();
		
		assertEquals("Nome", registroAluno.getAluno().getNome());
	}
	
	@Test
	void testarVerificarDadosVazio() {
		cadastroAlunoTela.getTextFieldNomeAluno().setText("Nome");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("Email");
		assertEquals(false, helper.verificarDados());
	}
	
	@Test
	void testarVerificarDadosNaoVazio() {
		cadastroAlunoTela.getTextFieldNomeAluno().setText("Nome");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("Email");
		assertEquals(true, helper.verificarDados());
	}
	
	@Test
	void testarVerificarDataNull() {
		assertEquals(false, helper.verificarData(cadastroAlunoTela.getTextFieldDataNascimento().getText()));
	}
	
	@Test
	void testarVerificarDataInvalida() {
		cadastroAlunoTela.getTextFieldDataNascimento().setText("1/31e/2we0");
		assertEquals(false, helper.verificarData(cadastroAlunoTela.getTextFieldDataNascimento().getText()));
	}
	
	@Test
	void testarVerificarDataFutura() {
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2100");
		assertEquals(false, helper.verificarData(cadastroAlunoTela.getTextFieldDataNascimento().getText()));
	}
	
	@Test
	void testarVerificarDataMuitoAntiga() {
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/1800");
		assertEquals(false, helper.verificarData(cadastroAlunoTela.getTextFieldDataNascimento().getText()));
	}
	
	@Test
	void testarVerificarDataValida() {
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		assertEquals(true, helper.verificarData(cadastroAlunoTela.getTextFieldDataNascimento().getText()));
	}
	
	@Test
	void testarVerificarCpfNaoExiste() {
		cadastroAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		assertEquals(false, helper.verificarCPF(cadastroAlunoTela.getTextFieldCpfAluno().getText()));
	}
	
	@Test
	void testarVerificarCpfExiste() {
		cadastroAlunoTela.getTextFieldCpfAluno().setText("454.159.584-28");
		assertEquals(true, helper.verificarCPF(cadastroAlunoTela.getTextFieldCpfAluno().getText()));
	}
	
	@Test
	void testarVerificarModalidadeNull() {
		assertEquals(false, helper.verificarModalidade((ModelModalidade) cadastroAlunoTela.getComboBoxRemoverModalidade().getSelectedItem()));
	}
	
	@Test
	void testarVerificarModalidadesNaoNull() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade2", 4);
		modalidade.setId(1L);
		
		ModelAluno aluno = new ModelAluno();
		aluno.adicionarModalidade(modalidade);
		
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade);
		
		helper.atualizarModalidades(usuario.getModalidades(), usuario);
		assertEquals(true, helper.verificarModalidade((ModelModalidade) cadastroAlunoTela.getComboBoxRemoverModalidade().getSelectedItem()));
	}
	
	@Test
	void testarVerificarDadosDiferentesCpfExiste(){
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf(Criptografar.criptografar("123.456.789-01"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getAlunos().add(aluno);
		
		cadastroAlunoTela.getTextFieldNomeAluno().setText("Nome");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("Email");
		
		ModelAluno alunoTela = helper.obterModelo();
		
		assertFalse(helper.dadosDiferentes(usuario, alunoTela));
	}
	
	@Test
	void testarVerificarDadosDiferentesEmailExiste(){
		ModelAluno aluno = new ModelAluno();
		aluno.setEmail(Criptografar.criptografar("email"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getAlunos().add(aluno);
		
		cadastroAlunoTela.getTextFieldNomeAluno().setText("Nome");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("email");
		
		ModelAluno alunoTela = helper.obterModelo();
		
		assertFalse(helper.dadosDiferentes(usuario, alunoTela));
	}
	
	@Test
	void testarVerificarDadosDiferentesNenhumExiste(){
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf(Criptografar.criptografar("123.456.789-01"));
		aluno.setEmail(Criptografar.criptografar("email"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getAlunos().add(aluno);
		
		cadastroAlunoTela.getTextFieldNomeAluno().setText("Nome");
		cadastroAlunoTela.getTextFieldCpfAluno().setText("987.654.321-09");
		cadastroAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		cadastroAlunoTela.getTextFieldEmailAluno().setText("Email");
		
		ModelAluno alunoTela = helper.obterModelo();
		
		assertTrue(helper.dadosDiferentes(usuario, alunoTela));
	}
	
	@Test
	void testarBtnCadastrarAlunoVerificarSemModalidadeSelecionada(){
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 4);
		usuario.getModalidades().add(modalidade);
		
		assertFalse(helper.verificarModalidade((ModelModalidade) cadastroAlunoTela.getComboBoxRemoverModalidade().getSelectedItem()));
	}
	
	@Test
	void testarBtnCadastrarAlunoVerificarModalidadeSelecionada(){
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 4);
		usuario.getModalidades().add(modalidade);
		
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf("");
		aluno.getModalidades().add(modalidade);
		usuario.getAlunos().add(aluno);
		helper.atualizarModalidades(usuario.getModalidades(), usuario);
		
		assertTrue(helper.verificarModalidade((ModelModalidade) cadastroAlunoTela.getComboBoxRemoverModalidade().getSelectedItem()));
	}
	
	@Test
	void testarLimparTela() {
		ModelPlano plano = new ModelPlano("Plano", 4, 200);
		usuario.getPlanos().add(plano);
		
		cadastroAlunoTela.getTextFieldCpfAluno().setText("CpfTela");
		cadastroAlunoTela.getTextFieldNomeAluno().setText("NomeTela");
		helper.limparTela(usuario);
	
		assertEquals("   .   .   -  ", cadastroAlunoTela.getTextFieldCpfAluno().getText());
		assertEquals("", cadastroAlunoTela.getTextFieldNomeAluno().getText());
	}
	
	@Test
	void testarLimparTelaCorSecundariaBranco() {
		ModelPlano plano = new ModelPlano("Plano", 4, 200);
		usuario.getPlanos().add(plano);
		
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Azul");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		cadastroAlunoTela.getTextFieldCpfAluno().setText("CpfTela");
		cadastroAlunoTela.getTextFieldNomeAluno().setText("NomeTela");
		helper.limparTela(usuario);
		
		assertEquals("   .   .   -  ", cadastroAlunoTela.getTextFieldCpfAluno().getText());
		assertEquals("", cadastroAlunoTela.getTextFieldNomeAluno().getText());
	}

	@Test
	void testarLimparTelaCorSecundariaPreto() {
		ModelPlano plano = new ModelPlano("Plano", 4, 200);
		usuario.getPlanos().add(plano);
		
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Azul");
		configuracao.setCorAtualSecundaria("Preto");
		usuario.setConfiguracao(configuracao);
		
		cadastroAlunoTela.getTextFieldCpfAluno().setText("CpfTela");
		cadastroAlunoTela.getTextFieldNomeAluno().setText("NomeTela");
		helper.limparTela(usuario);
		
		assertEquals("   .   .   -  ", cadastroAlunoTela.getTextFieldCpfAluno().getText());
		assertEquals("", cadastroAlunoTela.getTextFieldNomeAluno().getText());
	}
	
	@Test
	void testarSetarCoresPrimariasESecundariaUm() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = cadastroAlunoTela.getBtnCadastrarAluno().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = cadastroAlunoTela.getBtnCadastrarAluno().getBackground();
		assertEquals(Color.WHITE, corS);
	}
	
	@Test
	void testarSetarCoresPrimariasESecundariaMaisDeUm() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = cadastroAlunoTela.getBtnCadastrarAluno().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = cadastroAlunoTela.getBtnCadastrarAluno().getBackground();
		assertEquals(Color.BLUE, corS);
	}

}
