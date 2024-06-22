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

import model.ModelCustoBasico;
import model.ModelUsuario;

class CustoBasicoDAOTeste {

	private EntityManager em;
    private CustosBasicosDAO custoBasicoDAO;
    @Mock
    private Query query;
    
	@SuppressWarnings("deprecation")
	@BeforeEach
	void iniciarTeste() { 
		
		MockitoAnnotations.initMocks(this);
		
		em = mock(EntityManager.class);
		custoBasicoDAO = new CustosBasicosDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelCustoBasico custoBasico = new ModelCustoBasico();
		
		ModelCustoBasico resultado = custoBasicoDAO.insert(custoBasico);
		verify(em).persist(custoBasico);
		
		assertEquals(custoBasico, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelCustoBasico custoBasico = new ModelCustoBasico();
		custoBasico.setId(1L);
		
		ModelCustoBasico resultado = custoBasicoDAO.update(custoBasico);
		verify(em).merge(custoBasico);
		
		assertEquals(custoBasico, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelCustoBasico custoBasico = new ModelCustoBasico();
		
		ModelCustoBasico resultado = custoBasicoDAO.insertOrUpdate(custoBasico);
		verify(em).persist(custoBasico);
		
		assertEquals(custoBasico, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelCustoBasico custoBasico = new ModelCustoBasico();
		custoBasico.setId(0L);
		
		ModelCustoBasico resultado = custoBasicoDAO.insertOrUpdate(custoBasico);
		verify(em).persist(custoBasico);
		
		assertEquals(custoBasico, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelCustoBasico custoBasico = new ModelCustoBasico();
		custoBasico.setId(1L);
		
		ModelCustoBasico resultado = custoBasicoDAO.insertOrUpdate(custoBasico);
		verify(em).merge(custoBasico);
		
		assertEquals(custoBasico, resultado);
	}

    @Test
    void testarDelete() {
    	ModelCustoBasico custoBasico = new ModelCustoBasico();
    	custoBasico.setId(1L);

        custoBasicoDAO.delete(custoBasico);

        verify(em).merge(custoBasico);
        verify(em).remove(custoBasico);
    }
    
    @Test
    void testarDeleteForUser() {
    	ModelCustoBasico custoBasico = new ModelCustoBasico();
        custoBasico.setId(1L);

        when(em.createQuery(anyString())).thenReturn(query);

        List<ModelCustoBasico> custosBasicos = new ArrayList<>();
        custosBasicos.add(custoBasico);
        when(query.getResultList()).thenReturn(custosBasicos);

        when(custoBasicoDAO.consulta(query)).thenReturn(custosBasicos);

        custoBasicoDAO.deleteForUser();

        verify(em).createQuery("SELECT u FROM custosbasicos as u WHERE u.usuario IS NULL");
        verify(em).merge(custoBasico);
        verify(em).remove(custoBasico);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelCustoBasico> custosBasicos = new ArrayList<>();
        custosBasicos.add(new ModelCustoBasico());
        when(query.getResultList()).thenReturn(custosBasicos);

        List<ModelCustoBasico> resultado = custoBasicoDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM custosbasicos as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(custosBasicos, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelCustoBasico> resultado = custoBasicoDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
