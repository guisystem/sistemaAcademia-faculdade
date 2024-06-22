package controller.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controller.DadosProfessorController;
import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelEspecialidade;
import model.ModelFoto;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
import view.DadosProfessorTela;

class DadosProfessorHelperTeste {

	private DadosProfessorTela dadosProfessorTela;
	private DadosProfessorController controller;
	private DadosProfessorHelper helper;
	private ModelUsuario usuario;
	private ModelProfessor professor;
	private ModelRegistroProfessor registroProfessor;
	private ModelEspecialidade especialidade;
	private ModelConfiguracao configuracao;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		especialidade = new ModelEspecialidade("Especialidade");
		especialidade.setId(1L);
		professor = new ModelProfessor("testeNome" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, especialidade);
		professor.adicionarDias("Sábado");
		configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		
		registroProfessor = new ModelRegistroProfessor(0, professor, "Sim");
		registroProfessor.setProfessor(professor);
		registroProfessor.getProfessor().setDataAdmissao(new Date());
		registroProfessor.getProfessor().setTipoDeDataContrato("Ano");
		
		registroProfessor.getProfessor().setCpf(Criptografar.criptografar(registroProfessor.getProfessor().getCpf()));
		registroProfessor.getProfessor().setEmail(Criptografar.criptografar(registroProfessor.getProfessor().getEmail()));
		
		professor.setRegistroProfessor(registroProfessor);
		
		usuario.setConfiguracao(configuracao);
		usuario.getEspecialidades().add(especialidade);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		dadosProfessorTela = new DadosProfessorTela(usuario, registroProfessor);
		controller = new DadosProfessorController(dadosProfessorTela);
		helper = new DadosProfessorHelper(dadosProfessorTela);
	}
	
	@Test
	void testarPreencherDadosComFoto(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
	}
	
	@Test
	void testarPreencherDadosComFotoCorSecundariaBranco(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertEquals(1, professor.getEspecialidades().size());
	}
	
	@Test
	void testarPreencherDadosComEspecialidades(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertEquals(2, professor.getEspecialidades().size());
	}
	
	@Test
	void testarPreencherDadosTipoDataMes(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		professor.setTipoDeDataContrato("Mês");
		usuario.getEspecialidades().add(especialidade);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertEquals("Mês", professor.getTipoDeDataContrato());
	}
	
	@Test
	void testarPreencherDadosTipoDataAno(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		professor.setTipoDeDataContrato("Ano");
		usuario.getEspecialidades().add(especialidade);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertEquals("Ano", professor.getTipoDeDataContrato());
	}
	
	@Test
	void testarPreencherDadosAtivoSim(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertEquals("Sim", dadosProfessorTela.getComboBoxAtivo().getSelectedItem().toString());
	}
	
	@Test
	void testarPreencherDadosAtivoNao(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		
		ModelProfessor professor2 = new ModelProfessor("testeNome" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, especialidade);
		professor2.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		professor.getRegistroProfessor().setAtivo("Não");
		usuario.getEspecialidades().add(especialidade);
		dadosProfessorTela.getComboBoxAtivo().setSelectedItem("Não");
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertEquals("Não", dadosProfessorTela.getComboBoxAtivo().getSelectedItem().toString());
	}
	
	@Test
	void testarPreencherDadosAtivoMudaNao(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		dadosProfessorTela.getComboBoxAtivo().setSelectedItem("Não");
		
		assertEquals("Não", dadosProfessorTela.getComboBoxAtivo().getSelectedItem().toString());
	}
	
	@Test
	void testarPreencherDadosAtivoMudaSim(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		registroProfessor.setAtivo("Não");
		dadosProfessorTela.getComboBoxAtivo().setSelectedItem("Não");
		dadosProfessorTela.getComboBoxAtivo().setSelectedItem("Sim");
		
		assertEquals("Sim", dadosProfessorTela.getComboBoxAtivo().getSelectedItem().toString());
	}
	
	@Test
	void testarPreencherDadosDiasDeTrabalho(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		professor.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade);

		professor.adicionarDias("Segunda");
		professor.adicionarDias("Terça");
		professor.adicionarDias("Quarta");
		professor.adicionarDias("Quinta");
		professor.adicionarDias("Sexta");
		professor.adicionarDias("Sabado");
		professor.adicionarDias("Domingo");
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertTrue(dadosProfessorTela.getChckbxDomingo().isSelected());
	}
	
	@Test
	void testarPreencherTabelaDadosComEspecialidade(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		ModelEspecialidade especialidade2 = new ModelEspecialidade();
		especialidade2.setId(2L);
		ModelEspecialidade especialidade3 = new ModelEspecialidade();
		especialidade3.setId(3L);
		
		professor.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade2);
		usuario.getEspecialidades().add(especialidade3);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		assertEquals(2, dadosProfessorTela.getComboBoxAdicionarEspecialidade().getItemCount());
	}
	
	@Test
	void testarCompararDadosDiferentes(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		ModelEspecialidade especialidade2 = new ModelEspecialidade();
		especialidade2.setId(2L);
		ModelEspecialidade especialidade3 = new ModelEspecialidade();
		especialidade3.setId(3L);
		usuario.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade2);
		usuario.getEspecialidades().add(especialidade3);

		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		dadosProfessorTela.getTextFieldNomeProfessor().setText("NovoNome");
		
		assertFalse(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosTipoDeDataAmbosMarcado(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		ModelEspecialidade especialidade2 = new ModelEspecialidade();
		especialidade2.setId(2L);
		ModelEspecialidade especialidade3 = new ModelEspecialidade();
		especialidade3.setId(3L);
		usuario.getEspecialidades().add(especialidade3);
		usuario.getEspecialidades().add(especialidade2);
		usuario.getEspecialidades().add(especialidade);

		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		dadosProfessorTela.getRdbtnMes().setSelected(true);
		dadosProfessorTela.getRdbtnAno().setSelected(true);

		assertFalse(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosTipoDeDataAmbosDesmarcado(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		ModelEspecialidade especialidade2 = new ModelEspecialidade();
		especialidade2.setId(2L);
		ModelEspecialidade especialidade3 = new ModelEspecialidade();
		especialidade3.setId(3L);
		usuario.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade2);
		usuario.getEspecialidades().add(especialidade3);

		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		dadosProfessorTela.getRdbtnMes().setSelected(false);
		dadosProfessorTela.getRdbtnAno().setSelected(false);

		assertFalse(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosTipoDeDataMesMarcado(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		ModelEspecialidade especialidade2 = new ModelEspecialidade();
		especialidade2.setId(2L);
		ModelEspecialidade especialidade3 = new ModelEspecialidade();
		especialidade3.setId(3L);
		usuario.getEspecialidades().add(especialidade);
		usuario.getEspecialidades().add(especialidade2);
		usuario.getEspecialidades().add(especialidade3);

		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		dadosProfessorTela.getRdbtnAno().setSelected(false);
		dadosProfessorTela.getRdbtnMes().setSelected(true);

		assertFalse(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosTipoDeDataAnoMarcado(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		assertTrue(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosFotoNaoNullIgual(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		assertTrue(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosFotoNaoNullDiferente(){
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		dadosProfessorTela.getNomeImagemField().setText("NovoNome");
		
		assertFalse(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosFotoNullCaminhoIgual(){
		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		assertTrue(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}
	
	@Test
	void testarCompararDadosFotoNullCaminhoDiferente(){
		helper.preencherDadosProfessor(usuario, registroProfessor);
		
		dadosProfessorTela.setCaminhoImagem("NovoCaminho");
		
		assertFalse(helper.compararDados(registroProfessor, (ArrayList<ModelEspecialidade>) registroProfessor.getProfessor().getEspecialidades()));
	}

	@Test
	void testarObterEspecialidadeNull(){
		controller.atualizarDados(usuario, registroProfessor);
		dadosProfessorTela.getComboBoxExcluirEspecialidade().setSelectedItem(null);
		assertEquals(null, helper.obterEspecialidade());
	}
	
	@Test
	void testarObterEspecialidade(){
		controller.atualizarDados(usuario, registroProfessor);
		assertEquals(professor.getEspecialidades().get(0), helper.obterEspecialidade());
	}
	
	@Test
	void testarVerificarDias(){
		
		dadosProfessorTela.getChckbxDomingo().setSelected(true);
		dadosProfessorTela.getChckbxQuarta().setSelected(true);
		dadosProfessorTela.getChckbxQuinta().setSelected(true);
		dadosProfessorTela.getChckbxSabado().setSelected(true);
		dadosProfessorTela.getChckbxSegunda().setSelected(true);
		dadosProfessorTela.getChckbxSexta().setSelected(true);
		dadosProfessorTela.getChckbxTerca().setSelected(true);
		
		assertFalse(helper.verificarDias(registroProfessor));
	}
	
	@Test
	void testarObterEspecialidadeAdicionar(){
		controller.atualizarDados(usuario, registroProfessor);
		assertEquals(professor.getEspecialidades().get(0), helper.obterEspecialidade());
	}
	
	@Test
	void testarObterModelo(){
		controller.atualizarDados(usuario, registroProfessor);
		dadosProfessorTela.getTextFieldNomeProfessor().setText("NomeModelo");
		dadosProfessorTela.getTextFieldEmailProfessor().setText("EmailModelo");
		
		dadosProfessorTela.getChckbxDomingo().setSelected(true);
		dadosProfessorTela.getChckbxQuarta().setSelected(true);
		dadosProfessorTela.getChckbxQuinta().setSelected(true);
		dadosProfessorTela.getChckbxSabado().setSelected(true);
		dadosProfessorTela.getChckbxSegunda().setSelected(true);
		dadosProfessorTela.getChckbxSexta().setSelected(true);
		dadosProfessorTela.getChckbxTerca().setSelected(true);
		
		ModelProfessor professorModelo = helper.obterModelo(registroProfessor);
		
		assertEquals("NomeModelo", professorModelo.getNome());
		assertEquals("EmailModelo", professorModelo.getEmail());
	}
	
	@Test
	void testarObterModeloRegistro() {
		controller.atualizarDados(usuario, registroProfessor);
		
		dadosProfessorTela.getComboBoxAtivo().setSelectedItem("Não");
		ModelRegistroProfessor registro = helper.obterModeloRegistro(registroProfessor);
		
		assertEquals("Não", registro.getAtivo());
		assertEquals(professor, registroProfessor.getProfessor());
	}
	

	@Test
	void testarObterModeloFotoCaminhoTelaNull() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		professor.setFoto(foto);
		
		controller.atualizarDados(usuario, registroProfessor);
		dadosProfessorTela.setCaminhoImagem(null);
		
		ModelFoto fotoModelo = helper.obterFoto(registroProfessor);
		assertEquals("caminho/imagem.jpeg", fotoModelo.getCaminhoImagem());
	}
	
	@Test
	void testarObterModeloFotoProfessorNull() {
		
		controller.atualizarDados(usuario, registroProfessor);
		dadosProfessorTela.setCaminhoImagem("caminho/tela.jpeg");
		dadosProfessorTela.getNomeImagemField().setText("NomeFotoTela");
		
		ModelFoto fotoModelo = helper.obterFoto(registroProfessor);
		assertEquals("caminho/tela.jpeg", fotoModelo.getCaminhoImagem());
	}
	
	@Test
	void testarVerificarDadosVazio(){
		dadosProfessorTela.getTextFieldContrato().setText("");
		assertFalse(helper.verificarDados());
	}
	
	@Test
	void testarVerificarDadosNaoVazio(){
		dadosProfessorTela.getTextFieldContrato().setText("1");
		dadosProfessorTela.getTextFieldEmailProfessor().setText("Email");
		dadosProfessorTela.getTextFieldNomeProfessor().setText("NovoNome");
		dadosProfessorTela.getTextFieldSalarioProfessor().setText("1500");
		
		assertTrue(helper.verificarDados());
	}
	
	@Test
	void testarVerificarTipoDeDatasAmbosMarcados(){
		dadosProfessorTela.getRdbtnAno().setSelected(true);
		dadosProfessorTela.getRdbtnMes().setSelected(true);
		
		assertFalse(helper.verificarTipoDataDeContrato());
	}
	
	@Test
	void testarVerificarTipoDeDatasAmbosDesmarcados(){
		dadosProfessorTela.getRdbtnAno().setSelected(false);
		dadosProfessorTela.getRdbtnMes().setSelected(false);
		
		assertFalse(helper.verificarTipoDataDeContrato());
	}
	
	@Test
	void testarVerificarTipoDeDatasMesMarcado(){
		dadosProfessorTela.getRdbtnAno().setSelected(false);
		dadosProfessorTela.getRdbtnMes().setSelected(true);
		
		assertTrue(helper.verificarTipoDataDeContrato());
	}
	
	@Test
	void testarVerificarTipoDeDatasAnoMarcado(){
		dadosProfessorTela.getRdbtnAno().setSelected(true);
		dadosProfessorTela.getRdbtnMes().setSelected(false);
		
		assertTrue(helper.verificarTipoDataDeContrato());
	}
	
	@Test
	void testarVerificarSalarioInvalido(){
		dadosProfessorTela.getTextFieldSalarioProfessor().setText("18tr");
		
		assertFalse(helper.verificarSalario());
	}
	
	@Test
	void testarVerificarSalarioValido(){
		dadosProfessorTela.getTextFieldSalarioProfessor().setText("1800");
		
		assertTrue(helper.verificarSalario());
	}
	
	@Test
	void testarVerificarDataNull(){
		assertFalse(helper.verificarData(null));
	}
	
	@Test
	void testarVerificarDataInvalida(){
		dadosProfessorTela.getTextFieldDataAdmissao().setText("70/10/2000");
		assertFalse(helper.verificarData(dadosProfessorTela.getTextFieldDataAdmissao().getText()));
	}
	
	@Test
	void testarVerificarDataValida(){
		dadosProfessorTela.getTextFieldDataAdmissao().setText("30/10/2000 10:00");
		assertTrue(helper.verificarData(dadosProfessorTela.getTextFieldDataAdmissao().getText()));
	}
	
	@Test
	void testarVerificarHoraNull(){
		assertFalse(helper.verificarHora(null));
	}
	
	@Test
	void testarVerificarHoraInvalida(){
		dadosProfessorTela.getLblHorarioEntradaProfessor().setText("99:00");
		assertFalse(helper.verificarHora(dadosProfessorTela.getLblHorarioEntradaProfessor().getText()));
	}
	
	@Test
	void testarVerificarHoraValida(){
		dadosProfessorTela.getLblHorarioEntradaProfessor().setText("10:00");
		assertTrue(helper.verificarHora(dadosProfessorTela.getLblHorarioEntradaProfessor().getText()));
	}
	
	@Test
	void testarVerificarTempoContratoInvalido(){
		dadosProfessorTela.getTextFieldContrato().setText("r");
		assertFalse(helper.verificarTempoContrato(dadosProfessorTela.getTextFieldContrato().getText()));
	}
	
	@Test
	void testarVerificarTempoContratoValido(){
		dadosProfessorTela.getTextFieldContrato().setText("2");
		assertTrue(helper.verificarTempoContrato(dadosProfessorTela.getTextFieldContrato().getText()));
	}
	
	@Test
	void testarVerificarCpfInvalido(){
		dadosProfessorTela.getTextFieldCpfProfessor().setText("123.456.789-01");
		assertFalse(helper.verificarCPF(dadosProfessorTela.getTextFieldCpfProfessor().getText()));
	}
	
	@Test
	void testarVerificarCpfValido(){
		dadosProfessorTela.getTextFieldCpfProfessor().setText("454.159.584-28");
		assertTrue(helper.verificarCPF(dadosProfessorTela.getTextFieldCpfProfessor().getText()));
	}
	
	@Test
	void testarValidarEmailECpf_CpfExiste() {
		ModelProfessor professor = new ModelProfessor();
		professor.setCpf(Criptografar.criptografar("123.456.789-01"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getProfessores().add(professor);
		
		dadosProfessorTela.getTextFieldNomeProfessor().setText("Nome");
		dadosProfessorTela.getTextFieldCpfProfessor().setText("223.456.789-01");
		dadosProfessorTela.getTextFieldEmailProfessor().setText("email");
		
		ModelProfessor professorTela = helper.obterModelo(registroProfessor);
		professorTela.setCpf(Criptografar.criptografar("223.456.789-01"));

		dadosProfessorTela.getTextFieldCpfProfessor().setText("123.456.789-01");
		
		assertFalse(helper.validarEmailECpf(usuario, professorTela.getRegistroProfessor(), 
				dadosProfessorTela.getTextFieldCpfProfessor().getText(), dadosProfessorTela.getTextFieldEmailProfessor().getText()));
	}

	@Test
	void testarValidarEmailECpf_EmailExiste() {
		ModelProfessor professor = new ModelProfessor();
		professor.setCpf(Criptografar.criptografar("123.456.789-01"));
		professor.setEmail(Criptografar.criptografar("Email"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getProfessores().add(professor);
		
		dadosProfessorTela.getTextFieldNomeProfessor().setText("Nome");
		dadosProfessorTela.getTextFieldCpfProfessor().setText("223.456.789-01");
		dadosProfessorTela.getTextFieldEmailProfessor().setText("email");
		
		ModelProfessor professorTela = helper.obterModelo(registroProfessor);
		professorTela.setCpf(Criptografar.criptografar("223.456.789-01"));
		professorTela.setEmail(Criptografar.criptografar("email"));
		
		dadosProfessorTela.getTextFieldEmailProfessor().setText("Email");
		
		assertFalse(helper.validarEmailECpf(usuario, professorTela.getRegistroProfessor(), 
				dadosProfessorTela.getTextFieldCpfProfessor().getText(), dadosProfessorTela.getTextFieldEmailProfessor().getText()));
	}
	
	@Test
	void testarValidarEmailECpf_NenhumExiste() {
		ModelProfessor professor = new ModelProfessor();
		professor.setCpf(Criptografar.criptografar("123.456.789-01"));
		professor.setEmail(Criptografar.criptografar("Email"));
		
		ModelUsuario usuario = new ModelUsuario();
		usuario.getProfessores().add(professor);
		
		dadosProfessorTela.getTextFieldNomeProfessor().setText("Nome");
		dadosProfessorTela.getTextFieldCpfProfessor().setText("223.456.789-01");
		dadosProfessorTela.getTextFieldEmailProfessor().setText("email");
		
		ModelProfessor professorTela = helper.obterModelo(registroProfessor);
		professorTela.setCpf(Criptografar.criptografar("223.456.789-01"));
		professorTela.setEmail(Criptografar.criptografar("email"));
		
		dadosProfessorTela.getTextFieldEmailProfessor().setText("NovoEmail");
		
		assertTrue(helper.validarEmailECpf(usuario, professorTela.getRegistroProfessor(), 
				dadosProfessorTela.getTextFieldCpfProfessor().getText(), dadosProfessorTela.getTextFieldEmailProfessor().getText()));
	}
	
	@Test
	void testarVerificarNenhumDiaSelecionado(){
		dadosProfessorTela.getChckbxSabado().setSelected(false);
		assertFalse(helper.verificarDiasSelecionados());
	}
	
	@Test
	void testarVerificarDiaSelecionado(){
		dadosProfessorTela.getChckbxDomingo().setSelected(true);
		dadosProfessorTela.getChckbxQuarta().setSelected(true);
		dadosProfessorTela.getChckbxQuinta().setSelected(true);
		dadosProfessorTela.getChckbxSabado().setSelected(true);
		dadosProfessorTela.getChckbxSegunda().setSelected(true);
		dadosProfessorTela.getChckbxSexta().setSelected(true);
		dadosProfessorTela.getChckbxTerca().setSelected(true);
		
		assertTrue(helper.verificarDiasSelecionados());
	}
	
	@Test
	void testarSetarCorPrimariaESecundariaPadrao(){
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Preto");
		configuracao.setCorAtualSecundaria("Branco");
		usuario.setConfiguracao(configuracao);
		
		ArrayList<Color> coresPrimarias = ModelCores.tonsDeCorPrimaria(usuario.getConfiguracao().getCorPrimariaColor(usuario));
		ArrayList<Color> coresSecundarias = ModelCores.tonsDeCorSecundaria(usuario.getConfiguracao().getCorSecundariaColor(usuario));
		
		helper.setarCoresPrimariasNaTela(coresPrimarias);
		helper.setarCoresSecundariasNaTela(coresSecundarias);
		
		Color corP = dadosProfessorTela.getBtnConfirmar().getForeground();
		assertEquals(Color.BLACK, corP);
		
		Color corS = dadosProfessorTela.getBtnConfirmar().getBackground();
		assertEquals(Color.WHITE, corS);
	}

}
