package controller.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelEspecialidade;
import model.ModelFoto;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
import view.CadastroProfessorTela;

class CadastroProfessorHelperTeste {

	private CadastroProfessorTela cadastroProfessorTela;
	private CadastroProfessorHelper helper;
	private ModelUsuario usuario;
	private ModelConfiguracao configuracao;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		cadastroProfessorTela = new CadastroProfessorTela(usuario);
		helper = new CadastroProfessorHelper(cadastroProfessorTela);
	}
	
	@Test
	void testarObterModeloSemSalario() {
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(true);
		cadastroProfessorTela.getRdbtnMes().setSelected(false);
		
		ModelProfessor professor = helper.obterModelo();
		assertEquals(0.0, professor.getSalario());
	}
	
	@Test
	void testarObterModeloSemTempoDeContrato() {
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(true);
		cadastroProfessorTela.getRdbtnMes().setSelected(false);
		
		ModelProfessor professor = helper.obterModelo();
		assertEquals(0, professor.getTempoContrato());
	}
	
	@Test
	void testarObterModeloTipoDeDataMes() {
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		
		ModelProfessor professor = helper.obterModelo();
		assertEquals("Mês", professor.getTipoDeDataContrato());
	}
	
	@Test
	void testarObterModeloDiasDeTrabalho() {
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		
		cadastroProfessorTela.getChckbxDomingo().setSelected(true);
		cadastroProfessorTela.getChckbxQuarta().setSelected(true);
		cadastroProfessorTela.getChckbxQuinta().setSelected(true);
		cadastroProfessorTela.getChckbxSabado().setSelected(true);
		cadastroProfessorTela.getChckbxSegunda().setSelected(true);
		cadastroProfessorTela.getChckbxSexta().setSelected(true);
		cadastroProfessorTela.getChckbxTerca().setSelected(true);
		
		ModelProfessor professor = helper.obterModelo();
		assertEquals(7, professor.getDiasDeTrabalho().size());
	}
	
	@Test
	void testarObterFoto() {
		
		ModelFoto fotoVazia = helper.obterFoto();
		assertEquals("", fotoVazia.getNomeImagemField());
		
		cadastroProfessorTela.setCaminhoImagem("Caminho/da/imagem.jpeg");
		cadastroProfessorTela.getNomeImagemField().setText("NomeImagem.jpeg");
		
		ModelFoto fotoNaoVazia = helper.obterFoto();
		
		assertEquals("NomeImagem.jpeg", fotoNaoVazia.getNomeImagemField());
	}
	
	@Test
	void testarObterModeloRegistro() {
		
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		
		ModelRegistroProfessor registro = helper.obterModeloRegistro();
		
		assertEquals("Sim", registro.getAtivo());
		assertEquals("NomeObterModelo", registro.getProfessor().getNome());
	}
	
	@Test
	void testarVerificarDadosSemDados(){
		assertFalse(helper.verificarDados());
	}
	
	@Test
	void testarVerificarDadosComDados(){
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		
		assertTrue(helper.verificarDados());
	}
	
	@Test
	void testarVerificarTipoDeDataContratoAmbosDesmarcados(){
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(false);
		assertFalse(helper.verificarTipoDataDeContrato());
	}
	
	@Test
	void testarVerificarTipoDeDataContratoAmbosMarcados(){
		cadastroProfessorTela.getRdbtnAno().setSelected(true);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		assertFalse(helper.verificarTipoDataDeContrato());
	}
	
	@Test
	void testarVerificarTipoDeDataContratoValido(){
		cadastroProfessorTela.getRdbtnAno().setSelected(true);
		cadastroProfessorTela.getRdbtnMes().setSelected(false);
		assertTrue(helper.verificarTipoDataDeContrato());
	}
	
	@Test
	void testarVerificarSalarioInvalido(){
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("150r");
		
		assertFalse(helper.verificarSalario());
	}
	
	@Test
	void testarVerificarSalarioValido(){
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		
		assertTrue(helper.verificarSalario());
	}
	
	@Test
	void testarVerificarHoraVazia(){
		assertFalse(helper.verificarHora(cadastroProfessorTela.getTextFieldHoraEntrada().getText()));
	}
	
	@Test
	void testarVerificarHoraInvalida(){
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("94:90");
		assertFalse(helper.verificarHora(cadastroProfessorTela.getTextFieldHoraEntrada().getText()));
	}
	
	@Test
	void testarVerificarHoraValida(){
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("14:00");
		assertTrue(helper.verificarHora(cadastroProfessorTela.getTextFieldHoraEntrada().getText()));
	}
	
	@Test
	void testarVerificarTempoInvalido(){
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1s");
		assertFalse(helper.verificarTempoContrato(cadastroProfessorTela.getTextFieldTempoContrato().getText()));
	}
	
	@Test
	void testarVerificarTempoValido(){
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		assertTrue(helper.verificarTempoContrato(cadastroProfessorTela.getTextFieldTempoContrato().getText()));
	}
	
	@Test
	void testarVerificarCpfInvalido(){
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("123.456.789-01");
		assertFalse(helper.verificarCPF(cadastroProfessorTela.getTextFieldCpfProfessor().getText()));
	}
	
	@Test
	void testarVerificarCpfValido(){
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		assertTrue(helper.verificarCPF(cadastroProfessorTela.getTextFieldCpfProfessor().getText()));
	}
	
	@Test
	void testarVerificarDadosDiferentesCpfExiste(){
		ModelProfessor professor = new ModelProfessor();
		professor.setCpf(Criptografar.criptografar("123.456.789-01"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getProfessores().add(professor);
		
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("123.456.789-01");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailObterModelo");
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		
		ModelProfessor professorTela = helper.obterModelo();
		
		assertFalse(helper.dadosDiferentes(usuario, professorTela));
	}
	
	@Test
	void testarVerificarDadosDiferentesEmailExiste(){
		ModelProfessor professor = new ModelProfessor();
		professor.setEmail(Criptografar.criptografar("email"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getProfessores().add(professor);
		
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("123.456.789-01");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("email");
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		
		ModelProfessor professorTela = helper.obterModelo();
		
		assertFalse(helper.dadosDiferentes(usuario, professorTela));
	}
	
	@Test
	void testarVerificarDadosDiferentesNenhumExiste(){
		ModelProfessor professor = new ModelProfessor();
		professor.setCpf(Criptografar.criptografar("123.456.789-01"));
		professor.setEmail(Criptografar.criptografar("email"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getProfessores().add(professor);
		
		cadastroProfessorTela.getTextFieldNomeProfessor().setText("NomeObterModelo");
		cadastroProfessorTela.getTextFieldCpfProfessor().setText("987.654.321-09");
		cadastroProfessorTela.getTextFieldHoraEntrada().setText("10:00");
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		cadastroProfessorTela.getTextFieldTempoContrato().setText("1");
		cadastroProfessorTela.getTextFieldEmailProfessor().setText("EmailDiferente");
		cadastroProfessorTela.getRdbtnAno().setSelected(false);
		cadastroProfessorTela.getRdbtnMes().setSelected(true);
		
		ModelProfessor professorTela = helper.obterModelo();
		
		assertTrue(helper.dadosDiferentes(usuario, professorTela));
	}
	
	@Test
	void testarVerificarDiasNenhumSelecionado(){
		assertFalse(helper.verificarDias());
	}
	
	@Test
	void testarVerificarDiasSelecionado(){
		cadastroProfessorTela.getChckbxSegunda().setSelected(true);
		assertTrue(helper.verificarDias());
	}
	
	@Test
	void testarLimparTelaSemConfiguracao() {
		ModelEspecialidade especialidade = new ModelEspecialidade("especialidade");
		usuario.getEspecialidades().add(especialidade);
		
		helper.atualizarEspecialidades(usuario.getEspecialidades());
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1");
		helper.limparTela(usuario);
		
		assertEquals("  :  ", cadastroProfessorTela.getTextFieldHoraSaida().getText());
		assertEquals("", cadastroProfessorTela.getTextFieldSalarioProfessor().getText());
		
	}
	
	@Test
	void testarLimparTelaComConfiguracaoComFundoBranco() {
		configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ModelEspecialidade especialidade = new ModelEspecialidade("especialidade");
		usuario.getEspecialidades().add(especialidade);
		
		helper.atualizarEspecialidades(usuario.getEspecialidades());
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1");
		helper.limparTela(usuario);
		
		assertEquals("  :  ", cadastroProfessorTela.getTextFieldHoraSaida().getText());
		assertEquals("", cadastroProfessorTela.getTextFieldSalarioProfessor().getText());
		assertEquals(cadastroProfessorTela.getChckbxDomingo().getForeground(), usuario.getConfiguracao().getCorPrimariaColor(usuario));
		
	}
	
	@Test
	void testarLimparTelaComConfiguracao() {
		configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		ModelEspecialidade especialidade = new ModelEspecialidade("especialidade");
		usuario.getEspecialidades().add(especialidade);
		
		helper.atualizarEspecialidades(usuario.getEspecialidades());
		cadastroProfessorTela.getTextFieldHoraSaida().setText("10:00");
		cadastroProfessorTela.getTextFieldSalarioProfessor().setText("1");
		helper.limparTela(usuario);
		
		assertEquals("  :  ", cadastroProfessorTela.getTextFieldHoraSaida().getText());
		assertEquals("", cadastroProfessorTela.getTextFieldSalarioProfessor().getText());
		assertEquals(cadastroProfessorTela.getChckbxDomingo().getForeground(), usuario.getConfiguracao().getCorPrimariaColor(usuario));
		
	}

	@Test
	void testarAtualizarCoresCorSecundariaBranca() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = cadastroProfessorTela.getBtnCadastrarProfessor().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = cadastroProfessorTela.getBtnCadastrarProfessor().getBackground();
		assertEquals(Color.WHITE, corS);
		
	}
	
	@Test
	void testarAtualizarCoresMaisDeUm() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = cadastroProfessorTela.getBtnCadastrarProfessor().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = cadastroProfessorTela.getBtnCadastrarProfessor().getBackground();
		assertEquals(Color.BLUE, corS);
		
	}
}
