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

import model.ModelRegistroAluno;
import model.ModelUsuario;

class RegistroAlunoDAOTeste {

	private EntityManager em;
    private RegistroAlunoDAO registroAlunoDAO;
	
	@BeforeEach
	void iniciarTeste() { 
		em = mock(EntityManager.class);
		registroAlunoDAO = new RegistroAlunoDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelRegistroAluno registroAluno = new ModelRegistroAluno();
		
		ModelRegistroAluno resultado = registroAlunoDAO.insert(registroAluno);
		verify(em).persist(registroAluno);
		
		assertEquals(registroAluno, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelRegistroAluno registroAluno = new ModelRegistroAluno();
		registroAluno.setId(1);
		
		ModelRegistroAluno resultado = registroAlunoDAO.update(registroAluno);
		verify(em).merge(registroAluno);
		
		assertEquals(registroAluno, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelRegistroAluno registroAluno = new ModelRegistroAluno();
		
		ModelRegistroAluno resultado = registroAlunoDAO.insertOrUpdate(registroAluno);
		verify(em).persist(registroAluno);
		
		assertEquals(registroAluno, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelRegistroAluno registroAluno = new ModelRegistroAluno();
		registroAluno.setId(0);
		
		ModelRegistroAluno resultado = registroAlunoDAO.insertOrUpdate(registroAluno);
		verify(em).persist(registroAluno);
		
		assertEquals(registroAluno, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelRegistroAluno registroAluno = new ModelRegistroAluno();
		registroAluno.setId(1);
		
		ModelRegistroAluno resultado = registroAlunoDAO.insertOrUpdate(registroAluno);
		verify(em).merge(registroAluno);
		
		assertEquals(registroAluno, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelRegistroAluno registroAluno = new ModelRegistroAluno();
    	registroAluno.setId(1);

        registroAlunoDAO.delete(registroAluno);

        verify(em).merge(registroAluno);
        verify(em).remove(registroAluno);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelRegistroAluno> registroAlunos = new ArrayList<>();
        registroAlunos.add(new ModelRegistroAluno());
        when(query.getResultList()).thenReturn(registroAlunos);

        List<ModelRegistroAluno> resultado = registroAlunoDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM registroaluno as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(registroAlunos, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelRegistroAluno> resultado = registroAlunoDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
