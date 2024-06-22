package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelEspecialidade;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import servico.Criptografar;
import view.RegistroProfessorTela;

class RegistroProfessorControllerTeste {

	private RegistroProfessorTela registroProfessorTela;
	private RegistroProfessorController controller;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		registroProfessorTela = new RegistroProfessorTela(usuario);
		controller = new RegistroProfessorController(registroProfessorTela);
	}
	
	@Test
	void testarPreencherTabela() {
		ModelProfessor professor = new ModelProfessor("testeNome" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, new ModelEspecialidade());
		professor.setCpf(Criptografar.criptografar(professor.getCpf()));
		professor.setEmail(Criptografar.criptografar(professor.getEmail()));
		professor.setDataAdmissao(new Date());
		professor.setTipoDeDataContrato("Ano");
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor(0, professor, "Sim");
		professor.setRegistroProfessor(registroProfessor);
		registroProfessor.setProfessor(professor);
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		RegistroProfessorController.atualizarTabela(usuario);
		assertEquals(1, registroProfessorTela.getTableRegistroProfessores().getRowCount());
	}
	

	@Test
	void testarAtualizarCores() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		usuario.setConfiguracao(configuracao);
		
		controller.atualizarCores(usuario);
		
		Color corP = registroProfessorTela.getTableRegistroProfessores().getTableHeader().getForeground();
		assertEquals(Color.YELLOW, corP);
		
	}

}
