package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelConfiguracao;
import model.ModelUsuario;
import servico.Criptografar;

class UsuarioDAOTeste {

	private EntityManager em;
    private UsuarioDAO usuarioDAO;
	
	@BeforeEach
	void iniciarTeste() { 
		em = mock(EntityManager.class);
		usuarioDAO = new UsuarioDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		
		ModelUsuario resultado = usuarioDAO.insert(usuario);
		verify(em).persist(usuario);
		
		assertEquals(usuario, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		usuario.setId(1);
		
		ModelUsuario resultado = usuarioDAO.update(usuario);
		verify(em).merge(usuario);
		
		assertEquals(usuario, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		
		ModelUsuario resultado = usuarioDAO.insertOrUpdate(usuario);
		verify(em).persist(usuario);
		
		assertEquals(usuario, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		usuario.setId(0);
		
		ModelUsuario resultado = usuarioDAO.insertOrUpdate(usuario);
		verify(em).persist(usuario);
		
		assertEquals(usuario, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
		usuario.setId(1);
		
		ModelUsuario resultado = usuarioDAO.insertOrUpdate(usuario);
		verify(em).merge(usuario);
		
		assertEquals(usuario, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelUsuario usuario = new ModelUsuario("Nome", "Senha");
    	usuario.setId(1);

        usuarioDAO.delete(usuario);

        verify(em).merge(usuario);
        verify(em).remove(usuario);
    }
    
    @Test
    void testarSelectAll() {
    	Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelUsuario> usuarios = new ArrayList<>();
        usuarios.add(new ModelUsuario("Nome", "Senha"));
        when(query.getResultList()).thenReturn(usuarios);

        List<ModelUsuario> resultado = usuarioDAO.selectAll();

        verify(em).createQuery("SELECT u FROM usuario as u");

        assertEquals(usuarios, resultado);
    }
    
    @Test
    void testarSelectPorUsuarioESenha() {
        ModelUsuario usuario = new ModelUsuario("Nome", "Senha");

        Query query = mock(Query.class); 
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);

        List<ModelUsuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        when(query.getResultList()).thenReturn(usuarios);

        boolean resultado = usuarioDAO.selectPorUsuarioESenha(usuario);

        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario and u.senha = :pSenha");

        verify(query).setParameter("pUsuario", usuario.getNomeUsuario());
        verify(query).setParameter("pSenha", Criptografar.criptografar(usuario.getSenha()));


        assertTrue(resultado);
    }
    
    @Test
    void testarSelectUsuarioPorUsuarioESenha() {
    	ModelUsuario usuario = new ModelUsuario("Nome", "Senha");

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);

        List<ModelUsuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        when(query.getResultList()).thenReturn(usuarios);

        ModelUsuario resultado = usuarioDAO.selectUsuarioPorUsuarioESenha(usuario);

        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario and u.senha = :pSenha");

        verify(query).setParameter("pUsuario", usuario.getNomeUsuario());
        verify(query).setParameter("pSenha", Criptografar.criptografar(usuario.getSenha()));


        assertEquals(usuario, resultado);
    }
    
    @Test
    void testarSelectPorNomeEmailCpfNomeExistente() {
    	ModelUsuario usuario = new ModelUsuario("Usuario", "98765432109", "NovoEmail", "Nome", "NovaSenha", new ModelConfiguracao());

        Query query = mock(Query.class);
        Query query2 = mock(Query.class);
        Query query3 = mock(Query.class);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario")).thenReturn(query);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf")).thenReturn(query2);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail")).thenReturn(query3);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query2.setParameter(anyString(), any())).thenReturn(query2);
        when(query3.setParameter(anyString(), any())).thenReturn(query3);
        when(query.getResultList()).thenReturn(Collections.singletonList(new ModelUsuario()));
        when(query2.getResultList()).thenReturn(new ArrayList<>());
        when(query3.getResultList()).thenReturn(new ArrayList<>());

        int resultado = usuarioDAO.selectPorNomeEmailCpf(usuario);

        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail");

        verify(query).setParameter("pUsuario", usuario.getNomeUsuario());
        verify(query2).setParameter("pCpf", Criptografar.criptografar(usuario.getCpf()));
        verify(query3).setParameter("pEmail", Criptografar.criptografar(usuario.getEmail()));

        assertEquals(1, resultado);
    }

    @Test
    void testarSelectPorNomeEmailCpfCpfExistente() {
    	ModelUsuario usuario = new ModelUsuario("Usuario", "98765432109", "NovoEmail", "Nome", "NovaSenha", new ModelConfiguracao());

        Query query = mock(Query.class);
        Query query2 = mock(Query.class);
        Query query3 = mock(Query.class);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario")).thenReturn(query);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf")).thenReturn(query2);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail")).thenReturn(query3);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query2.setParameter(anyString(), any())).thenReturn(query2);
        when(query3.setParameter(anyString(), any())).thenReturn(query3);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        when(query2.getResultList()).thenReturn(Collections.singletonList(new ModelUsuario()));
        when(query3.getResultList()).thenReturn(new ArrayList<>());

        int resultado = usuarioDAO.selectPorNomeEmailCpf(usuario);

        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail");

        verify(query).setParameter("pUsuario", usuario.getNomeUsuario());
        verify(query2).setParameter("pCpf", Criptografar.criptografar(usuario.getCpf()));
        verify(query3).setParameter("pEmail", Criptografar.criptografar(usuario.getEmail()));

        assertEquals(2, resultado);
    }

    @Test
    void testarSelectPorNomeEmailCpfEmailExistente() {
    	ModelUsuario usuario = new ModelUsuario("Usuario", "98765432109", "NovoEmail", "Nome", "NovaSenha", new ModelConfiguracao());

        Query query = mock(Query.class);
        Query query2 = mock(Query.class);
        Query query3 = mock(Query.class);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario")).thenReturn(query);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf")).thenReturn(query2);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail")).thenReturn(query3);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query2.setParameter(anyString(), any())).thenReturn(query2);
        when(query3.setParameter(anyString(), any())).thenReturn(query3);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        when(query2.getResultList()).thenReturn(new ArrayList<>());
        when(query3.getResultList()).thenReturn(Collections.singletonList(new ModelUsuario()));

        int resultado = usuarioDAO.selectPorNomeEmailCpf(usuario);

        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail");

        verify(query).setParameter("pUsuario", usuario.getNomeUsuario());
        verify(query2).setParameter("pCpf", Criptografar.criptografar(usuario.getCpf()));
        verify(query3).setParameter("pEmail", Criptografar.criptografar(usuario.getEmail()));

        assertEquals(3, resultado);
    }
    
    @Test
    void testarSelectPorNomeEmailCpfNenhumExiste() {
    	ModelUsuario usuario = new ModelUsuario("Usuario", "98765432109", "NovoEmail", "Nome", "NovaSenha", new ModelConfiguracao());
    	
        Query query = mock(Query.class);
        Query query2 = mock(Query.class);
        Query query3 = mock(Query.class);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario")).thenReturn(query);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf")).thenReturn(query2);
        when(em.createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail")).thenReturn(query3);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query2.setParameter(anyString(), any())).thenReturn(query2);
        when(query3.setParameter(anyString(), any())).thenReturn(query3);
        when(query.getResultList()).thenReturn(new ArrayList<>());
        when(query2.getResultList()).thenReturn(new ArrayList<>());
        when(query3.getResultList()).thenReturn(new ArrayList<>());

        int resultado = usuarioDAO.selectPorNomeEmailCpf(usuario);

        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.nomeUsuario = :pUsuario");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.cpf = :pCpf");
        verify(em).createQuery("SELECT u FROM usuario as u WHERE u.email = :pEmail");

        verify(query).setParameter("pUsuario", usuario.getNomeUsuario());
        verify(query2).setParameter("pCpf", Criptografar.criptografar(usuario.getCpf()));
        verify(query3).setParameter("pEmail", Criptografar.criptografar(usuario.getEmail()));

        assertEquals(0, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelUsuario> resultado = usuarioDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }
    
}
