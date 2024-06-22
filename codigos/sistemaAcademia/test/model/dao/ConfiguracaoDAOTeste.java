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

import model.ModelConfiguracao;
import model.ModelUsuario;

class ConfiguracaoDAOTeste {

	private EntityManager em;
    private ConfiguracaoDAO configuracaoDAO;
	
	@BeforeEach
	void iniciarTeste() { 
		em = mock(EntityManager.class);
		configuracaoDAO = new ConfiguracaoDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		
		ModelConfiguracao resultado = configuracaoDAO.insert(configuracao);
		verify(em).persist(configuracao);
		
		assertEquals(configuracao, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setId(1);
		
		ModelConfiguracao resultado = configuracaoDAO.update(configuracao);
		verify(em).merge(configuracao);
		
		assertEquals(configuracao, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		
		ModelConfiguracao resultado = configuracaoDAO.insertOrUpdate(configuracao);
		verify(em).persist(configuracao);
		
		assertEquals(configuracao, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setId(0);
		
		ModelConfiguracao resultado = configuracaoDAO.insertOrUpdate(configuracao);
		verify(em).persist(configuracao);
		
		assertEquals(configuracao, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setId(1);
		
		ModelConfiguracao resultado = configuracaoDAO.insertOrUpdate(configuracao);
		verify(em).merge(configuracao);
		
		assertEquals(configuracao, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelConfiguracao configuracao = new ModelConfiguracao();
		configuracao.setId(1);

        configuracaoDAO.delete(configuracao);

        verify(em).merge(configuracao);
        verify(em).remove(configuracao);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelConfiguracao> configuracoes = new ArrayList<>();
        configuracoes.add(new ModelConfiguracao());
        when(query.getResultList()).thenReturn(configuracoes);

        List<ModelConfiguracao> resultado = configuracaoDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM configuracao as u WHERE u.usuario.id = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(configuracoes, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelConfiguracao> resultado = configuracaoDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
