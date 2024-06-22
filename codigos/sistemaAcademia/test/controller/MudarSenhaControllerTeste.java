package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Window;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelUsuario;
import servico.Criptografar;
import view.MudarSenhaTela;

class MudarSenhaControllerTeste {

	private MudarSenhaTela mudarSenhaTela;
	private MudarSenhaController controller;
	private ModelUsuario usuario;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@BeforeEach
	void iniciarTeste() {
		usuario = new ModelUsuario("Nome", "Senha");
		
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		
		mudarSenhaTela = new MudarSenhaTela(usuario);
		controller = new MudarSenhaController(mudarSenhaTela);
		controller.setEm(em);
	}
	
	@Test
	void testarAtualizarTela() {
		controller.atualizarTela(usuario);
		assertEquals("Nome", mudarSenhaTela.getTextFieldNomeUsuario().getText());
	}
	
	@Test
	void testarVerificarSenhaNovaIgual() {
		controller.atualizarTela(usuario);
		usuario.setSenha(Criptografar.criptografar(usuario.getSenha()));
		
		mudarSenhaTela.getTextFieldSenhaAtual().setText("Senha");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("SenhaNova");
		
		
		ModelUsuario atualizado = controller.confirmarMudancaSenha(usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja mudar, clicar em "Sim" para mudar e limpar a tela */
		/** NOTE: Irá abrir a tela de login */
		
		if(atualizado != null) {
			verify(em, times(1)).merge(atualizado);
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
			
		}else {
			verify(em, times(0)).merge(any());
			verify(transaction, times(1)).begin();
		    verify(transaction, times(1)).commit();
		}
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
	}
	
	@Test
	void testarCancelarAlteracaoDadoVazio() {
		controller.cancelarMudancaSenha(usuario);
		/** NOTE: Terá que fechar a tela de mudar senha */
	}
	
	@Test
	void testarCancelarAlteracaoDadoPreenchido() {
		mudarSenhaTela.getTextFieldSenhaAtual().setText("SenhaNova");
		mudarSenhaTela.getPasswordFieldDigitarSenhaNovamente().setText("Senha");
		mudarSenhaTela.getPasswordFieldNovaSenha().setText("Senha");
		
		controller.cancelarMudancaSenha(usuario);
		/** NOTE: Terá que exibir uma mensagem perguntando se deseja cancelar, clicar em "Sim" para sair */
		
	}
}
