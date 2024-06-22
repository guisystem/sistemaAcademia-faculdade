package controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Window;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.ModelUsuario;
import view.CadastroUsuarioTela;

class CadastroUsuarioControllerTeste {

	private CadastroUsuarioTela cadastroUsuarioTela;
	private CadastroUsuarioController controller;
	private EntityManager em;
	private EntityTransaction transaction;
	
	@Mock
    private Query query;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void iniciarTeste() {
		MockitoAnnotations.initMocks(this);
		em = mock(EntityManager.class);
		
		transaction = mock(EntityTransaction.class);

        when(em.getTransaction()).thenReturn(transaction);
		when(em.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<ModelUsuario>());
        cadastroUsuarioTela = new CadastroUsuarioTela();
        controller = new CadastroUsuarioController(cadastroUsuarioTela);
        controller.setEm(em);
	}
	
	@Test
	void testarVoltarLogin() {
		controller.voltarParaLogin();
		/** NOTE: Tem que abrir a tela de login sem problemas */
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
	@Test
	void testarCadastrarUsuarioDadosCriptografados() {
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("s");
		cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().setText("s");
		cadastroUsuarioTela.getTextCpfUsuario().setText("45415958428");
		cadastroUsuarioTela.getTextEmailUsuario().setText("EmailModelo");
		cadastroUsuarioTela.getTextNomeCompletoUsuario().setText("NomeCompleto");
		cadastroUsuarioTela.getTextUsuario().setText("NomeUsuario");
		
		ArrayList<Object> inseridos = controller.cadastrarNoSistema();
		
		if(!inseridos.isEmpty()) {
			verify(em, times(1)).persist(inseridos.get(0));
			verify(em, times(1)).persist(inseridos.get(1));
			
			verify(transaction, times(1)).begin();
			verify(transaction, times(1)).commit();
		}
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
		
		
	}

}
