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

import model.ModelModalidade;
import model.ModelUsuario;

class ModalidadeDAOTeste {

	private EntityManager em;
    private ModalidadeDAO modalidadeDAO;
    @Mock
    private Query query;
    
	@SuppressWarnings("deprecation")
	@BeforeEach
	void iniciarTeste() { 
		
		MockitoAnnotations.initMocks(this);
		em = mock(EntityManager.class);
		modalidadeDAO = new ModalidadeDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelModalidade modalidade = new ModelModalidade();
		
		ModelModalidade resultado = modalidadeDAO.insert(modalidade);
		verify(em).persist(modalidade);
		
		assertEquals(modalidade, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelModalidade modalidade = new ModelModalidade();
		modalidade.setId(1L);
		
		ModelModalidade resultado = modalidadeDAO.update(modalidade);
		verify(em).merge(modalidade);
		
		assertEquals(modalidade, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelModalidade modalidade = new ModelModalidade();
		
		ModelModalidade resultado = modalidadeDAO.insertOrUpdate(modalidade);
		verify(em).persist(modalidade);
		
		assertEquals(modalidade, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelModalidade modalidade = new ModelModalidade();
		modalidade.setId(0L);
		
		ModelModalidade resultado = modalidadeDAO.insertOrUpdate(modalidade);
		verify(em).persist(modalidade);
		
		assertEquals(modalidade, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelModalidade modalidade = new ModelModalidade();
		modalidade.setId(1L);
		
		ModelModalidade resultado = modalidadeDAO.insertOrUpdate(modalidade);
		verify(em).merge(modalidade);
		
		assertEquals(modalidade, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelModalidade modalidade = new ModelModalidade();
    	modalidade.setId(1L);

        modalidadeDAO.delete(modalidade);

        verify(em).merge(modalidade);
        verify(em).remove(modalidade);
    }
    
    @Test
    void testarDeleteForUser() {
    	ModelModalidade modalidade = new ModelModalidade();
    	modalidade.setId(1L);

        when(em.createQuery(anyString())).thenReturn(query);

        List<ModelModalidade> modalidades = new ArrayList<>();
        modalidades.add(modalidade);
        when(query.getResultList()).thenReturn(modalidades);

        when(modalidadeDAO.consulta(query)).thenReturn(modalidades);

        modalidadeDAO.deleteForUser();

        verify(em).createQuery("SELECT u FROM modalidade as u WHERE u.usuario IS NULL");
        verify(em).merge(modalidade);
        verify(em).remove(modalidade);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelModalidade> modalidades = new ArrayList<>();
        modalidades.add(new ModelModalidade());
        when(query.getResultList()).thenReturn(modalidades);

        List<ModelModalidade> resultado = modalidadeDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM modalidade as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(modalidades, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelModalidade> resultado = modalidadeDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
