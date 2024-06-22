package model.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import model.ModelFuncionario;
import model.ModelUsuario;

class FuncionarioDAOTeste {

	private EntityManager em;
    private FuncionarioDAO funcionarioDAO;
    @Mock
    private Query query;
    
	@SuppressWarnings("deprecation")
	@BeforeEach
	void iniciarTeste() { 
		
		MockitoAnnotations.initMocks(this);
		em = mock(EntityManager.class);
		funcionarioDAO = new FuncionarioDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelFuncionario funcionario = new ModelFuncionario();
		
		ModelFuncionario resultado = funcionarioDAO.insert(funcionario);
		verify(em).persist(funcionario);
		
		assertEquals(funcionario, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelFuncionario funcionario = new ModelFuncionario();
		funcionario.setId(1L);
		
		ModelFuncionario resultado = funcionarioDAO.update(funcionario);
		verify(em).merge(funcionario);
		
		assertEquals(funcionario, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelFuncionario funcionario = new ModelFuncionario();
		
		ModelFuncionario resultado = funcionarioDAO.insertOrUpdate(funcionario);
		verify(em).persist(funcionario);
		
		assertEquals(funcionario, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelFuncionario funcionario = new ModelFuncionario();
		funcionario.setId(0L);
		
		ModelFuncionario resultado = funcionarioDAO.insertOrUpdate(funcionario);
		verify(em).persist(funcionario);
		
		assertEquals(funcionario, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelFuncionario funcionario = new ModelFuncionario();
		funcionario.setId(1L);
		
		ModelFuncionario resultado = funcionarioDAO.insertOrUpdate(funcionario);
		verify(em).merge(funcionario);
		
		assertEquals(funcionario, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelFuncionario funcionario = new ModelFuncionario();
    	funcionario.setId(1L);

        funcionarioDAO.delete(funcionario);

        verify(em).merge(funcionario);
        verify(em).remove(funcionario);
    }
    
    @Test
    void testarDeleteForUser() {
    	ModelFuncionario funcionario = new ModelFuncionario();
    	funcionario.setId(1L);

        when(em.createQuery(anyString())).thenReturn(query);

        List<ModelFuncionario> funcionarios = new ArrayList<>();
        funcionarios.add(funcionario);
        when(query.getResultList()).thenReturn(funcionarios);

        when(funcionarioDAO.consulta(query)).thenReturn(funcionarios);

        funcionarioDAO.deleteForUser();

        verify(em).createQuery("SELECT u FROM funcionario as u WHERE u.usuario IS NULL");
        verify(em).merge(funcionario);
        verify(em).remove(funcionario);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelFuncionario> funcionarios = new ArrayList<>();
        funcionarios.add(new ModelFuncionario());
        when(query.getResultList()).thenReturn(funcionarios);

        List<ModelFuncionario> resultado = funcionarioDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM funcionario as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(funcionarios, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelFuncionario> resultado = funcionarioDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
