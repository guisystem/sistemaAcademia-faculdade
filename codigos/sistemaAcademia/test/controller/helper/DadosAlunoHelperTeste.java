package controller.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.time.LocalDate;
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
import view.DadosAlunoTela;

class DadosAlunoHelperTeste {

	private DadosAlunoTela dadosAlunoTela;
	private DadosAlunoHelper helper;
	private ModelUsuario usuario;
	private ModelAluno aluno;
	private ModelRegistroAluno registroAluno;
	private ModelPlano plano;
	private ModelConfiguracao configuracao;
	
	@BeforeEach
	void iniciarTeste() {
		plano = new ModelPlano("Plano", 1, 50);
		plano.setId(1L);
		usuario = new ModelUsuario();
		aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano);
		configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		
		registroAluno = new ModelRegistroAluno(0, aluno);
		registroAluno.setAluno(aluno);
		
		registroAluno.getAluno().setCpf(Criptografar.criptografar(registroAluno.getAluno().getCpf()));
		registroAluno.getAluno().setEmail(Criptografar.criptografar(registroAluno.getAluno().getEmail()));
		
		aluno.setRegistroAluno(registroAluno);
		
		usuario.setConfiguracao(configuracao);
		usuario.getPlanos().add(plano);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		dadosAlunoTela = new DadosAlunoTela(usuario, registroAluno);
		helper = new DadosAlunoHelper(dadosAlunoTela);
	}
	
	@Test
	void testarPreencherDadosAlunoComFoto() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		
		helper.preencherDadosAluno(usuario, registroAluno);
		assertEquals(0, aluno.getModalidades().size());
	}
	
	@Test
	void testarPreencherDadosAlunoComFotoCorSecundariaBranco() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		helper.preencherDadosAluno(usuario, registroAluno);
		assertEquals(0, aluno.getModalidades().size());
	}
	
	@Test
	void testarPreencherDadosAlunoComFotoModalidadeEPlano() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		
		ModelModalidade modalidade = new ModelModalidade();
		ModelPlano plano = new ModelPlano();
		aluno.getModalidades().add(modalidade);
		aluno.setPlano(plano);
		
		helper.preencherDadosAluno(usuario, registroAluno);
		assertEquals(1, aluno.getModalidades().size());
	}
	
	@Test
	void testarPreencherDadosAlunoComModalidades() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		
		ModelModalidade modalidade = new ModelModalidade();
		modalidade.setId(1L);
		ModelModalidade modalidade2 = new ModelModalidade();
		modalidade2.setId(2L);
		aluno.getModalidades().add(modalidade2);
		ModelPlano plano = new ModelPlano();
		aluno.setPlano(plano);
		
		usuario.getModalidades().add(modalidade);
		usuario.getModalidades().add(modalidade2);

		helper.preencherDadosAluno(usuario, registroAluno);
		assertEquals(1, dadosAlunoTela.getComboBoxAdicionarModalidade().getItemCount());
	}
	
	@Test
	void testarPreencherDataValorDinamicamenteComAlunoAtivo(){
		
		registroAluno.setProximoPagamento(LocalDate.now());

		helper.preencherDadosAluno(usuario, registroAluno);
		dadosAlunoTela.getComboBoxPlano().setSelectedItem(null);

		helper.preencherDataValorDinamicamente(registroAluno, usuario);
	}
	
	@Test
	void testarCompararDadosDiferentes(){
		ModelModalidade modalidade = new ModelModalidade();
		modalidade.setId(1L);
		ModelModalidade modalidade2 = new ModelModalidade();
		modalidade2.setId(2L);
		aluno.getModalidades().add(modalidade2);
		aluno.getModalidades().add(modalidade);
		registroAluno.setAluno(aluno);
		
		usuario.getModalidades().add(modalidade);
		usuario.getModalidades().add(modalidade2);
		
		helper.preencherDadosAluno(usuario, registroAluno);
		
		dadosAlunoTela.getTextFieldTotal().setText("3");
		assertFalse(helper.compararDados(registroAluno, (ArrayList<ModelModalidade>) usuario.getModalidades()));
	}
	
	@Test
	void testarCompararDadosFotoNaoNullIgual(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		helper.preencherDadosAluno(usuario, registroAluno);
		
		assertTrue(helper.compararDados(registroAluno, (ArrayList<ModelModalidade>) usuario.getModalidades()));
	}
	
	@Test
	void testarCompararDadosFotoNaoNullDiferente(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		helper.preencherDadosAluno(usuario, registroAluno);
		dadosAlunoTela.getNomeImagemField().setText("NovoNome");
		
		assertFalse(helper.compararDados(registroAluno, (ArrayList<ModelModalidade>) usuario.getModalidades()));
	}
	
	@Test
	void testarCompararDadosFotoNullCaminhoIgual(){
		helper.preencherDadosAluno(usuario, registroAluno);
		
		assertTrue(helper.compararDados(registroAluno, (ArrayList<ModelModalidade>) usuario.getModalidades()));
	}
	
	@Test
	void testarCompararDadosFotoNullCaminhoDiferente(){
		helper.preencherDadosAluno(usuario, registroAluno);
		
		dadosAlunoTela.setCaminhoImagem("NovoCaminho");
		
		assertFalse(helper.compararDados(registroAluno, (ArrayList<ModelModalidade>) usuario.getModalidades()));
	}
	
	@Test
	void testarObterPlanoNull(){
		helper.preencherDadosAluno(usuario, registroAluno);
		dadosAlunoTela.getComboBoxPlano().setSelectedItem(null);
		assertEquals(null, helper.obterPlano());
	}
	
	@Test
	void testarObterPlano(){
		helper.preencherDadosAluno(usuario, registroAluno);
		assertEquals(aluno.getPlano(), helper.obterPlano());
	}
	
	@Test
	void testarObterModelo(){
		helper.preencherDadosAluno(usuario, registroAluno);
		dadosAlunoTela.getTextFieldNomeAluno().setText("NomeModelo");
		dadosAlunoTela.getTextFieldEmailAluno().setText("EmailModelo");
		
		ModelAluno alunoModelo = helper.obterModelo(registroAluno);
		
		assertEquals("NomeModelo", alunoModelo.getNome());
		assertEquals("EmailModelo", alunoModelo.getEmail());
	}
	
	@Test
	void testarObterModeloRegistro() {
		helper.preencherDadosAluno(usuario, registroAluno);
		
		dadosAlunoTela.getTextFieldDataUltimoPagamento().setText("10/10/2024");
		ModelRegistroAluno registro = helper.obterModeloRegistro(registroAluno);
		
		assertEquals("10/11/2024", registro.getProximoPagamentoFormatada());
		assertEquals(aluno, registroAluno.getAluno());
	}
	
	@Test
	void testarObterModeloFotoCaminhoTelaNull() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		
		helper.preencherDadosAluno(usuario, registroAluno);
		dadosAlunoTela.setCaminhoImagem(null);
		
		ModelFoto fotoModelo = helper.obterFoto(registroAluno);
		assertEquals("caminho/imagem.jpeg", fotoModelo.getCaminhoImagem());
	}
	
	@Test
	void testarObterModeloFotoAlunoNull() {
		
		helper.preencherDadosAluno(usuario, registroAluno);
		dadosAlunoTela.setCaminhoImagem("caminho/tela.jpeg");
		dadosAlunoTela.getNomeImagemField().setText("NomeFotoTela");
		
		ModelFoto fotoModelo = helper.obterFoto(registroAluno);
		assertEquals("caminho/tela.jpeg", fotoModelo.getCaminhoImagem());
	}
	
	@Test
	void testarVerificarDadosVazio() {
		dadosAlunoTela.getTextFieldDataMatricula().setText("");
		assertFalse(helper.verificarDados());
	}
	
	@Test
	void testarVerificarDadosNaoVazio() {
		dadosAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		dadosAlunoTela.getTextFieldNomeAluno().setText("Nome");
		dadosAlunoTela.getTextFieldEmailAluno().setText("Email");
		dadosAlunoTela.getTextFieldDataMatricula().setText("10/10/2000");
		dadosAlunoTela.getTextFieldDataNascimento().setText("10/10/2000");
		
		assertTrue(helper.verificarDados());
	}
	
	@Test
	void testarVerificarDataNull() {
		assertFalse(helper.verificarData(null));
	}
	
	@Test
	void testarVerificarDataInvalida() {
		dadosAlunoTela.getTextFieldDataMatricula().setText("70/10/2000");
		assertFalse(helper.verificarData(dadosAlunoTela.getTextFieldDataMatricula().getText()));
	}
	
	@Test
	void testarVerificarDataValida() {
		dadosAlunoTela.getTextFieldDataMatricula().setText("10/10/2000");
		assertTrue(helper.verificarData(dadosAlunoTela.getTextFieldDataMatricula().getText()));
	}
	
	@Test
	void testarCompararDatasNull() {
		assertFalse(helper.compararDatas(null));
	}
	
	@Test
	void testarCampararDatasInvalida() {
		dadosAlunoTela.getTextFieldDataMatricula().setText("70/10/2000");
		assertFalse(helper.compararDatas(dadosAlunoTela.getTextFieldDataMatricula().getText()));
	}
	
	@Test
	void testarCampararDatasValidaMatriculaInvalida() {
		dadosAlunoTela.getTextFieldDataMatricula().setText("10/10/2000");
		dadosAlunoTela.getTextFieldDataNascimento().setText("30/10/2000");
		assertFalse(helper.compararDatas(dadosAlunoTela.getTextFieldDataMatricula().getText()));
	}
	
	@Test
	void testarCampararDatasValidaMatriculaValida() {
		dadosAlunoTela.getTextFieldDataMatricula().setText("30/10/2000");
		dadosAlunoTela.getTextFieldDataNascimento().setText("10/10/2000");
		assertTrue(helper.compararDatas(dadosAlunoTela.getTextFieldDataMatricula().getText()));
	}

	@Test
	void testarCampararDatasValidaUltimoPagamentoInvalido() {
		dadosAlunoTela.getTextFieldDataUltimoPagamento().setText("10/10/2000");
		dadosAlunoTela.getTextFieldDataMatricula().setText("30/10/2000");
		assertFalse(helper.compararDatas(dadosAlunoTela.getTextFieldDataUltimoPagamento().getText()));
	}
	
	@Test
	void testarCampararDatasValidaUltimoPagamentoValido() {
		dadosAlunoTela.getTextFieldDataUltimoPagamento().setText("30/10/2000");
		dadosAlunoTela.getTextFieldDataMatricula().setText("10/10/2000");
		assertTrue(helper.compararDatas(dadosAlunoTela.getTextFieldDataUltimoPagamento().getText()));
	}
	
	@Test
	void testarVerificarCpfInvalido() {
		dadosAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		assertEquals(false, helper.verificarCPF(dadosAlunoTela.getTextFieldCpfAluno().getText()));
	}
	
	@Test
	void testarVerificarCpfValido() {
		dadosAlunoTela.getTextFieldCpfAluno().setText("454.159.584-28");
		assertEquals(true, helper.verificarCPF(dadosAlunoTela.getTextFieldCpfAluno().getText()));
	}
	
	@Test
	void testarValidarEmailECpf_CpfExiste() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf(Criptografar.criptografar("123.456.789-01"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getAlunos().add(aluno);
		
		dadosAlunoTela.getTextFieldNomeAluno().setText("Nome");
		dadosAlunoTela.getTextFieldCpfAluno().setText("223.456.789-01");
		dadosAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		dadosAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		dadosAlunoTela.getTextFieldEmailAluno().setText("Email");
		
		ModelAluno alunoTela = helper.obterModelo(registroAluno);
		alunoTela.setCpf(Criptografar.criptografar("223.456.789-01"));

		dadosAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		
		assertFalse(helper.validarEmailECpf(usuario, alunoTela.getRegistroAluno(), dadosAlunoTela.getTextFieldCpfAluno().getText(), 
				dadosAlunoTela.getTextFieldEmailAluno().getText()));
	}

	@Test
	void testarValidarEmailECpf_EmailExiste() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf(Criptografar.criptografar("987.654.321-01"));
		aluno.setEmail(Criptografar.criptografar("Email"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getAlunos().add(aluno);
		
		dadosAlunoTela.getTextFieldNomeAluno().setText("Nome");
		dadosAlunoTela.getTextFieldCpfAluno().setText("123.456.789-01");
		dadosAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		dadosAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		dadosAlunoTela.getTextFieldEmailAluno().setText("email");
		
		ModelAluno alunoTela = helper.obterModelo(registroAluno);
		alunoTela.setCpf(Criptografar.criptografar("123.456.789-01"));
		alunoTela.setEmail(Criptografar.criptografar("email"));

		dadosAlunoTela.getTextFieldEmailAluno().setText("Email");
		
		assertFalse(helper.validarEmailECpf(usuario, alunoTela.getRegistroAluno(), dadosAlunoTela.getTextFieldCpfAluno().getText(), 
				dadosAlunoTela.getTextFieldEmailAluno().getText()));
	}
	
	@Test
	void testarValidarEmailECpf_NenhumExiste() {
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf(Criptografar.criptografar("123.456.789-01"));
		aluno.setEmail(Criptografar.criptografar("email"));

		ModelUsuario usuario = new ModelUsuario();
		usuario.getAlunos().add(aluno);
		
		dadosAlunoTela.getTextFieldNomeAluno().setText("Nome");
		dadosAlunoTela.getTextFieldCpfAluno().setText("987.654.321-09");
		dadosAlunoTela.getTextFieldDataNascimento().setText("01/01/2000");
		dadosAlunoTela.getTextFieldDataMatricula().setText("01/01/2000");
		dadosAlunoTela.getTextFieldEmailAluno().setText("Email");
		
		ModelAluno alunoTela = helper.obterModelo(registroAluno);
		alunoTela.setCpf(Criptografar.criptografar("987.654.321-09"));
		alunoTela.setEmail(Criptografar.criptografar("Email"));

		dadosAlunoTela.getTextFieldCpfAluno().setText("987.654.321-00");
		dadosAlunoTela.getTextFieldEmailAluno().setText("NovoEmail");
		
		assertTrue(helper.validarEmailECpf(usuario, alunoTela.getRegistroAluno(), dadosAlunoTela.getTextFieldCpfAluno().getText(), 
				dadosAlunoTela.getTextFieldEmailAluno().getText()));
	}
	
	@Test
	void testarVerificarModalidadeNull() {
		assertFalse(helper.verificarModalidade((ModelModalidade) dadosAlunoTela.getComboBoxExcluirModalidade().getSelectedItem()));
	}
	
	@Test
	void testarVerificarModalidadeNaoNull() {
		ModelModalidade modalidade = new ModelModalidade("Modalidade", 4);
		modalidade.setId(1L);
		ModelPlano plano = new ModelPlano("Plano", 1, 50);
		plano.setId(1L);
		
		ModelAluno aluno = new ModelAluno();
		aluno.setCpf(Criptografar.criptografar("987.654.321-09"));
		aluno.setEmail(Criptografar.criptografar("Email"));
		aluno.adicionarModalidade(modalidade);
		aluno.setPlano(plano);
		
		usuario.getPlanos().add(plano);
		usuario.getAlunos().add(aluno);
		usuario.getModalidades().add(modalidade);
		registroAluno.setAluno(aluno);
		
		helper.preencherDadosAluno(usuario, registroAluno);
		assertTrue(helper.verificarModalidade((ModelModalidade) dadosAlunoTela.getComboBoxExcluirModalidade().getSelectedItem()));
	}
	
	@Test
	void testarVerificarSetarCoresPrimariaESecundaria() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = dadosAlunoTela.getBtnConfirmar().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = dadosAlunoTela.getBtnConfirmar().getBackground();
		assertEquals(Color.WHITE, corS);
	}
	
}
