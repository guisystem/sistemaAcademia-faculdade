package controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Color;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelAluno;
import model.ModelConfiguracao;
import model.ModelFoto;
import model.ModelModalidade;
import model.ModelPlano;
import model.ModelRegistroAluno;
import model.ModelUsuario;
import servico.Criptografar;
import view.DadosAlunoTela;
import view.RegistroAlunoTela;

class DadosAlunoControllerTeste {

	private DadosAlunoTela dadosAlunoTela;
	private DadosAlunoController controller;
	private ModelUsuario usuario;
	private ModelAluno aluno;
	private ModelRegistroAluno registroAluno;
	private ModelPlano plano;
	private ModelModalidade modalidade;
	private ModelConfiguracao configuracao;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		plano = new ModelPlano("Plano", 1, 50);
		plano.setId(1L);

		modalidade = new ModelModalidade("Modalidade", 0);
		modalidade.setId(1L);
		
		aluno = new ModelAluno("testeNome", "123.456.789-01" ,"testeEmail", "01/01/1979", "01/01/1979", plano);
		registroAluno = new ModelRegistroAluno(0, aluno);
		registroAluno.setAluno(aluno);
		registroAluno.getAluno().setCpf(Criptografar.criptografar(registroAluno.getAluno().getCpf()));
		registroAluno.getAluno().setEmail(Criptografar.criptografar(registroAluno.getAluno().getEmail()));
		
		aluno.getModalidades().add(modalidade);
		aluno.setRegistroAluno(registroAluno);
		
		configuracao = new ModelConfiguracao();
		configuracao.setCorAtualPrimaria("Amarelo");
		configuracao.setCorAtualSecundaria("Azul");
		
		usuario = new ModelUsuario();
		usuario.setConfiguracao(configuracao);
		usuario.getModalidades().add(modalidade);
		usuario.getPlanos().add(plano);
		usuario.getAlunos().add(aluno);
		usuario.getRegistroAlunos().add(registroAluno);
		
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		
		dadosAlunoTela = new DadosAlunoTela(usuario, registroAluno);
		controller = new DadosAlunoController(dadosAlunoTela);
		controller.setEm(em);
	}
	
	@Test
	void testarAtualizarDadosComFoto() {
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		
		controller.atualizarDados(usuario, registroAluno);
		/** NOTE: Terá que atualizar os dados sem exceção */
	}
	
	@Test
	void testarCancelarAlteracoesDadosIguais() {
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(modalidade);
		
		controller.cancelarAlteracoes(registroAluno, modalidades);
	}
	
	@Test
	void testarCancelarAlteracoesDadosDiferentes() {
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(modalidade);
		
		dadosAlunoTela.getTextFieldNomeAluno().setText("NovoNome");
		
		controller.cancelarAlteracoes(registroAluno, modalidades);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja cancelar */
	}
	
	@Test
	void testarConfirmarAlteracoesCompararDadosIguais() {
		
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(modalidade);
		registroAluno.getAluno().setCpf(Criptografar.criptografar("454.159.584-28"));
		registroAluno.getAluno().setEmail(Criptografar.criptografar("testeEmail"));
		controller.atualizarDados(usuario, registroAluno);

		controller.confirmarAlteracoes(usuario, registroAluno, modalidades);
		
		registroAluno.getAluno().setCpf(Criptografar.descriptografar(registroAluno.getAluno().getCpf()));
		registroAluno.getAluno().setEmail(Criptografar.descriptografar(registroAluno.getAluno().getEmail()));
		
		assertTrue(registroAluno.getAluno().getCpf().equals(dadosAlunoTela.getTextFieldCpfAluno().getText()));
		assertTrue(registroAluno.getAluno().getEmail().equals(dadosAlunoTela.getTextFieldEmailAluno().getText()));
		/** Todos os dados serão iguais, portanto ao clicar no botão de confirmar, ela deve ser apenas fechada */
	}
	
	@Test
	void testarConfirmarAlteracoesCompararDadosDiferentes() {
		RegistroAlunoTela registroAlunoTela = new RegistroAlunoTela(usuario);
		@SuppressWarnings("unused")
		RegistroAlunoController controllerRegistro = new RegistroAlunoController(registroAlunoTela);
		
		ModelFoto foto = new ModelFoto("Nome", "caminho/imagem.jpeg");
		aluno.setFoto(foto);
		
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(modalidade);
		registroAluno.getAluno().setCpf(Criptografar.criptografar("454.159.584-28"));
		registroAluno.getAluno().setEmail(Criptografar.criptografar("testeEmail"));
		
		controller.atualizarDados(usuario, registroAluno);
		
		dadosAlunoTela.getTextFieldNomeAluno().setText("NovoNome");
		
		ArrayList<Object> inseridos = controller.confirmarAlteracoes(usuario, registroAluno, modalidades);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja alterar, clicar em "sim" e a tabela será atualizada */
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			verify(em, times(1)).persist(inseridos.get(2));
			verify(em, times(1)).persist(inseridos.get(3));
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
			
		}else {
			verify(em, times(0)).persist(any());
			verify(transaction, times(1)).begin();
		    verify(transaction, times(1)).commit();
		}
	}
	
	@Test
	void testarSairDaTelaCompararDadosIguais() {
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(modalidade);
		
		controller.sairDaTelaDefaultClose(registroAluno, modalidades);
	}
	
	@Test
	void testarSairDaTelaCompararDadosDiferentes() {
		ArrayList<ModelModalidade> modalidades = new ArrayList<>();
		modalidades.add(modalidade);

		dadosAlunoTela.getTextFieldNomeAluno().setText("NovoNome");
		
		controller.sairDaTelaDefaultClose(registroAluno, modalidades);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja sair */
	}
	
	@Test
	void testarAdicionarModalidadeComboBoxNull() {
		controller.atualizarDados(usuario, registroAluno);
		dadosAlunoTela.getComboBoxAdicionarModalidade().setSelectedItem(null);
		controller.adicionarModalidade(usuario, registroAluno);
		/** NOTE: Terá que exibir uma mensagem de nenhuma modalidade selecionada */
	}
	
	@Test
	void testarAdicionarModalidade() {
		ModelModalidade modalidade2 = new ModelModalidade("Modalidade2", 0);
		modalidade2.setId(2L);
		
		usuario.getModalidades().add(modalidade2);
		modalidade2.setUsuario(usuario);
		
		controller.atualizarDados(usuario, registroAluno);
		controller.adicionarModalidade(usuario, registroAluno);
		
		assertEquals(modalidade2, registroAluno.getAluno().getModalidades().get(1));
	}
	
	@Test
	void testarRemoverModalidadeComboBoxNull() {
		controller.atualizarDados(usuario, registroAluno);
		dadosAlunoTela.getComboBoxExcluirModalidade().setSelectedItem(null);
		controller.removerModalidade(usuario, registroAluno);
		/** NOTE: Terá que exibir uma mensagem de nenhuma modalidade selecionada */
	}
	
	@Test
	void testarRemoverModalidade() {
		controller.removerModalidade(usuario, registroAluno);
		assertEquals(0, registroAluno.getAluno().getModalidades().size());
	}
	
	@Test
	void testarCarregarFoto() {
		controller.carregarFoto();
		/** NOTE: Terá que abrir explore de arquivo do desktop */
	}
	
	@Test
	void testarAtualizarCoresPadrao() {
		usuario.setConfiguracao(null);
		
		controller.atualizarCores(usuario);
		
		Color corP = dadosAlunoTela.getBtnConfirmar().getForeground();
		assertEquals(Color.WHITE, corP);
		
		Color corS = dadosAlunoTela.getBtnConfirmar().getBackground();
		assertEquals(Color.BLACK, corS);
		
	}
	
}
