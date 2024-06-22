package controller.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelCores;
import model.ModelEspecialidade;
import model.ModelProfessor;
import model.ModelRegistroProfessor;
import model.ModelUsuario;
import view.RegistroProfessorTela;

class RegistroProfessorHelperTeste {

	private RegistroProfessorTela registroProfessorTela;
	private RegistroProfessorHelper helper;
	private ModelUsuario usuario;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario();
		registroProfessorTela = new RegistroProfessorTela(usuario);
		helper = new RegistroProfessorHelper(registroProfessorTela);
	}
	
	@Test
	void testarPreencherTabelaVazia() {
		helper.preencherTabela(usuario.getRegistroProfessores());
		assertEquals(0, registroProfessorTela.getTableRegistroProfessores().getRowCount());
	}
	
	@Test
	void testarPreencherTabelaComDados() {
		ModelProfessor professor = new ModelProfessor("testeNome1" ,"123.456.789-01", "testeEmail1", "00:00", "23:59", 0, new ModelEspecialidade());
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor(professor, "Sim");
		professor.setRegistroProfessor(registroProfessor);
		
		usuario.getProfessores().add(professor);
		usuario.getRegistroProfessores().add(registroProfessor);
		
		helper.preencherTabela(usuario.getRegistroProfessores());
		assertEquals(1, registroProfessorTela.getTableRegistroProfessores().getRowCount());
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
		
		Color corP = registroProfessorTela.getTableRegistroProfessores().getBackground();
		assertEquals(new Color(245, 245, 245), corP);
		
		Color corS = registroProfessorTela.getTableRegistroProfessores().getTableHeader().getBackground();
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
		
		Color corP = registroProfessorTela.getLblRegistroProfessores().getForeground();
		assertEquals(Color.YELLOW, corP);
		
		Color corS = registroProfessorTela.getTableRegistroProfessores().getTableHeader().getBackground();
		assertEquals(coresSecundarias.get(1), corS);
	}

}
