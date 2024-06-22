package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelUsuario;
import servico.Criptografar;
import view.LoginTela;

class LoginControllerTeste {

	private LoginTela loginTela;
	private LoginController controller;
	private EntityManager em;
	
	@BeforeEach
	void iniciarTeste() {
		loginTela = new LoginTela();
		controller = new LoginController(loginTela);
		em = mock(EntityManager.class);
		controller.setEm(em);
	}
	
	@Test
	void testarEntrarNoSistemaUsuarioIncorreto() {
		Query query = mock(Query.class);
		when(em.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		
		loginTela.getTextUsuario().setText("UsuarioNaoExiste");
		loginTela.getTextSenha().setText("SenhaNaoExiste");
		
		ModelUsuario usuario = new ModelUsuario("UsuarioNaoExiste", "SenhaNaoExiste");
		
		controller.entrarNoSistema();
		
		verify(em, times(2)).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario and u.senha = :pSenha");
		verify(query, times(2)).setParameter("pUsuario", usuario.getNomeUsuario());
		verify(query, times(2)).setParameter("pSenha", Criptografar.criptografar(usuario.getSenha()));

		assertEquals("UsuarioNaoExiste", loginTela.getTextUsuario().getText());
	}
	
	@Test
	void testarEntrarNoSistemaUsuarioCorreto() {
		
		Query query = mock(Query.class);
		when(em.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);

		ModelConfiguracao configuracao = new ModelConfiguracao("NomeAcademiaTeste", "Segunda", "Terça", "00:00", "00:00", "Branco", "Preto", null);
		List<ModelUsuario> usuarios = new ArrayList<>(); 
		String senha = Criptografar.criptografar("SenhaExiste");
		ModelUsuario usuarioExistente = new ModelUsuario("NomeCompleto", "123.456.789-01", "email", "UsuarioExiste", senha, configuracao);
		configuracao.setUsuario(usuarioExistente);
		usuarios.add(usuarioExistente);
		when(query.getResultList()).thenReturn(usuarios);

		loginTela.getTextUsuario().setText("UsuarioExiste");
		loginTela.getTextSenha().setText("SenhaExiste");

		ModelUsuario usuario = new ModelUsuario("UsuarioExiste", "SenhaExiste");
		
		controller.entrarNoSistema();
		
		verify(em, times(2)).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario and u.senha = :pSenha");
		verify(query, times(2)).setParameter("pUsuario", usuario.getNomeUsuario());
		verify(query, times(2)).setParameter("pSenha", Criptografar.criptografar(usuario.getSenha()));
		
		assertEquals("", loginTela.getTextUsuario().getText());

	}
	
	@Test
	void testarIrParaCadastro() {
		
		controller.irParaCadastro();
		/** NOTE: Terá que abrir a tela de cadastro */
		
		for(Window windows: Window.getWindows()) {
			windows.dispose();
		}
	}
	
}
