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

import model.ModelPlano;
import model.ModelUsuario;

class PlanoDAOTeste {

	private EntityManager em;
    private PlanoDAO planoDAO;
    @Mock
    private Query query;
    
	@SuppressWarnings("deprecation")
	@BeforeEach
	void iniciarTeste() { 
		
		MockitoAnnotations.initMocks(this);
		em = mock(EntityManager.class);
		planoDAO = new PlanoDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelPlano plano = new ModelPlano();
		
		ModelPlano resultado = planoDAO.insert(plano);
		verify(em).persist(plano);
		
		assertEquals(plano, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelPlano plano = new ModelPlano();
		plano.setId(1L);
		
		ModelPlano resultado = planoDAO.update(plano);
		verify(em).merge(plano);
		
		assertEquals(plano, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelPlano plano = new ModelPlano();
		
		ModelPlano resultado = planoDAO.insertOrUpdate(plano);
		verify(em).persist(plano);
		
		assertEquals(plano, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelPlano plano = new ModelPlano();
		plano.setId(0L);
		
		ModelPlano resultado = planoDAO.insertOrUpdate(plano);
		verify(em).persist(plano);
		
		assertEquals(plano, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelPlano plano = new ModelPlano();
		plano.setId(1L);
		
		ModelPlano resultado = planoDAO.insertOrUpdate(plano);
		verify(em).merge(plano);
		
		assertEquals(plano, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelPlano plano = new ModelPlano();
    	plano.setId(1L);

        planoDAO.delete(plano);

        verify(em).merge(plano);
        verify(em).remove(plano);
    }
    
    @Test
    void testarDeleteForUser() {
    	ModelPlano plano = new ModelPlano();
    	plano.setId(1L);

        when(em.createQuery(anyString())).thenReturn(query);

        List<ModelPlano> planos = new ArrayList<>();
        planos.add(plano);
        when(query.getResultList()).thenReturn(planos); 

        when(planoDAO.consulta(query)).thenReturn(planos);

        planoDAO.deleteForUser();

        verify(em).createQuery("SELECT u FROM plano as u WHERE u.usuario IS NULL");
        verify(em).merge(plano);
        verify(em).remove(plano);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelPlano> planos = new ArrayList<>();
        planos.add(new ModelPlano());
        when(query.getResultList()).thenReturn(planos);

        List<ModelPlano> resultado = planoDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM plano as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(planos, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelPlano> resultado = planoDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
