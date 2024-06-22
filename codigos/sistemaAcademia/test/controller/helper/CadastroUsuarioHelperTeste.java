package controller.helper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelUsuario;
import servico.Criptografar;
import view.CadastroUsuarioTela;

class CadastroUsuarioHelperTeste {

	private CadastroUsuarioTela cadastroUsuarioTela;
	private CadastroUsuarioHelper helper;
	private EntityManager em;
	
	@BeforeEach
	void iniciarTeste() {
		cadastroUsuarioTela = new CadastroUsuarioTela();
		helper = new CadastroUsuarioHelper(cadastroUsuarioTela);
		em = mock(EntityManager.class);
	}
	
	@Test
	void testarObterModelo(){
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("s");
		cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().setText("s");
		cadastroUsuarioTela.getTextCpfUsuario().setText("45415958428");
		cadastroUsuarioTela.getTextEmailUsuario().setText("EmailModelo");
		cadastroUsuarioTela.getTextNomeCompletoUsuario().setText("NomeCompleto");
		cadastroUsuarioTela.getTextUsuario().setText("NomeUsuario");
		
		ModelUsuario usuario = helper.obterModelo();
		
		assertEquals("NomeCompleto", usuario.getNome());
	}
	
	@Test
	void testarSelectPorNomeEmailCpf_NomeExiste() {
		ModelUsuario usuario = new ModelUsuario("NomeUsuario4", "Senha");
		usuario.setId(1);

		Query query = mock(Query.class);
		when(em.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);

		List<ModelUsuario> usuarios = new ArrayList<>(); 
		ModelUsuario usuarioExistente = new ModelUsuario("NomeCompleto", "123.456.789-01", "email", "NomeUsuario4", "Senha", new ModelConfiguracao());
		usuarios.add(usuarioExistente); 
		when(query.getResultList()).thenReturn(usuarios);

		boolean resultado = helper.verificarDados(em, usuario);

		verify(em).createQuery("SELECT u FROM usuario as u");

		assertEquals(false, resultado);

	}
	
	@Test
	void testarSelectPorNomeEmailCpf_CpfExiste() {
        
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		usuario.setId(1);
		usuario.setCpf("123.456.789-01");
		usuario.setEmail("email");
		
		Query query = mock(Query.class);
		when(em.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		
		List<ModelUsuario> usuarios = new ArrayList<>(); 
		ModelUsuario usuarioExistente = new ModelUsuario("NomeCompleto", "123.456.789-01", "email", "NomeUsuario", "Senha", new ModelConfiguracao());
		usuarioExistente.setCpf(Criptografar.criptografar(usuarioExistente.getCpf()));
		usuarioExistente.setEmail(Criptografar.criptografar(usuarioExistente.getEmail()));
		usuarios.add(usuarioExistente); 
		when(query.getResultList()).thenReturn(usuarios);
		
		boolean resultado = helper.verificarDados(em, usuario);
		
		verify(em).createQuery("SELECT u FROM usuario as u");
		
		assertEquals(false, resultado);
		
	}
	
	@Test
	void testarSelectPorNomeEmailCpf_EmailExiste() {
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		usuario.setId(1);
		usuario.setCpf("987.765.432-01");
		usuario.setEmail("email");
		
		Query query = mock(Query.class);
		when(em.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		
		List<ModelUsuario> usuarios = new ArrayList<>(); 
		ModelUsuario usuarioExistente = new ModelUsuario("NomeCompleto", "123.456.789-01", "email", "NomeUsuario", "Senha", new ModelConfiguracao());
		usuarioExistente.setCpf(Criptografar.criptografar(usuarioExistente.getCpf()));
		usuarioExistente.setEmail(Criptografar.criptografar(usuarioExistente.getEmail()));
		usuarios.add(usuarioExistente); 
		when(query.getResultList()).thenReturn(usuarios);
		
		boolean resultado = helper.verificarDados(em, usuario);
		
		verify(em).createQuery("SELECT u FROM usuario as u");
		
		assertEquals(false, resultado);
		
	}
	
	@Test
	void testarSelectPorNomeEmailCpf_NenhumExiste() {
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		usuario.setId(1);
		usuario.setCpf("987.765.432-01");
		usuario.setEmail("emailNovo");
		
		Query query = mock(Query.class);
		when(em.createQuery(anyString())).thenReturn(query);
		when(query.setParameter(anyString(), any())).thenReturn(query);
		
		List<ModelUsuario> usuarios = new ArrayList<>(); 
		ModelUsuario usuarioExistente = new ModelUsuario("NomeCompleto", "123.456.789-01", "email", "NomeUsuario", "Senha", new ModelConfiguracao());
		usuarioExistente.setCpf(Criptografar.criptografar(usuarioExistente.getCpf()));
		usuarioExistente.setEmail(Criptografar.criptografar(usuarioExistente.getEmail()));
		usuarios.add(usuarioExistente); 
		when(query.getResultList()).thenReturn(usuarios);
		
		boolean resultado = helper.verificarDados(em, usuario);
		
		verify(em).createQuery("SELECT u FROM usuario as u");
		
		assertEquals(true, resultado);
		
	}
	
	@Test
	void testarVerificarCamposVazios(){
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("s");
		cadastroUsuarioTela.getTextCpfUsuario().setText("45415958428");
		cadastroUsuarioTela.getTextEmailUsuario().setText("EmailModelo");
		cadastroUsuarioTela.getTextNomeCompletoUsuario().setText("NomeCompleto");
		cadastroUsuarioTela.getTextUsuario().setText("NomeUsuario");
		assertFalse(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCamposPreenchidos(){
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("s");
		cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().setText("s");
		cadastroUsuarioTela.getTextCpfUsuario().setText("45415958428");
		cadastroUsuarioTela.getTextEmailUsuario().setText("EmailModelo");
		cadastroUsuarioTela.getTextNomeCompletoUsuario().setText("NomeCompleto");
		cadastroUsuarioTela.getTextUsuario().setText("NomeUsuario");
		assertTrue(helper.verificarCampos());
	}
	
	@Test
	void testarVerificarCpfNaoExiste() {
		cadastroUsuarioTela.getTextCpfUsuario().setText("123.456.789-01");
		assertEquals(false, helper.verificarCpf());
	}
	
	@Test
	void testarVerificarCpfExiste() {
		cadastroUsuarioTela.getTextCpfUsuario().setText("454.159.584-28");
		assertEquals(true, helper.verificarCpf());
	}
	
	@Test
	void testarVerificarSenhaDiferentes(){
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("s");
		cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().setText("s2");
		assertFalse(helper.verificarSenha());
	}

	@Test
	void testarLimparTela(){
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("s");
		cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().setText("s");
		cadastroUsuarioTela.getTextCpfUsuario().setText("45415958428");
		cadastroUsuarioTela.getTextEmailUsuario().setText("EmailModelo");
		cadastroUsuarioTela.getTextNomeCompletoUsuario().setText("NomeCompleto");
		cadastroUsuarioTela.getTextUsuario().setText("NomeUsuario");
		
		helper.limparTela();
		assertEquals("", cadastroUsuarioTela.getTextNomeCompletoUsuario().getText());
	}
	
	@Test
	void testarVerificarSenhaIguais(){
		cadastroUsuarioTela.getPasswordSenhaUsuario().setText("s");
		cadastroUsuarioTela.getPasswordConfirmarSenhaUsuario().setText("s");
		assertTrue(helper.verificarSenha());
	}
	
	@Test
	void testarObterConfiguracao(){
		ModelUsuario usuario = new ModelUsuario();
		ModelConfiguracao conf = helper.obterConfiguracao(usuario);
		usuario.setConfiguracao(conf);
		
		assertEquals(conf, usuario.getConfiguracao());
	}


}
