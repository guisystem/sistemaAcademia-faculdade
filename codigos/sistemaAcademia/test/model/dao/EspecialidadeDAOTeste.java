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

import model.ModelEspecialidade;
import model.ModelUsuario;

class EspecialidadeDAOTeste {

	private EntityManager em;
    private EspecialidadeDAO especialidadeDAO;
    @Mock
    private Query query;
    
	@SuppressWarnings("deprecation")
	@BeforeEach
	void iniciarTeste() { 
		
		MockitoAnnotations.initMocks(this); 
		em = mock(EntityManager.class);
		especialidadeDAO = new EspecialidadeDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelEspecialidade especialidade = new ModelEspecialidade();
		
		ModelEspecialidade resultado = especialidadeDAO.insert(especialidade);
		verify(em).persist(especialidade);
		
		assertEquals(especialidade, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		
		ModelEspecialidade resultado = especialidadeDAO.update(especialidade);
		verify(em).merge(especialidade);
		
		assertEquals(especialidade, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelEspecialidade especialidade = new ModelEspecialidade();
		
		ModelEspecialidade resultado = especialidadeDAO.insertOrUpdate(especialidade);
		verify(em).persist(especialidade);
		
		assertEquals(especialidade, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(0L);
		
		ModelEspecialidade resultado = especialidadeDAO.insertOrUpdate(especialidade);
		verify(em).persist(especialidade);
		
		assertEquals(especialidade, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelEspecialidade especialidade = new ModelEspecialidade();
		especialidade.setId(1L);
		
		ModelEspecialidade resultado = especialidadeDAO.insertOrUpdate(especialidade);
		verify(em).merge(especialidade);
		
		assertEquals(especialidade, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelEspecialidade especialidade = new ModelEspecialidade();
    	especialidade.setId(1L);

        especialidadeDAO.delete(especialidade);

        verify(em).merge(especialidade);
        verify(em).remove(especialidade);
    }
    
    @Test
    void testarDeleteForUser() {
    	ModelEspecialidade especialidade = new ModelEspecialidade();
    	especialidade.setId(1L);

        when(em.createQuery(anyString())).thenReturn(query);

        List<ModelEspecialidade> especialidades = new ArrayList<>();
        especialidades.add(especialidade);
        when(query.getResultList()).thenReturn(especialidades);

        when(especialidadeDAO.consulta(query)).thenReturn(especialidades);

        especialidadeDAO.deleteForUser();

        verify(em).createQuery("SELECT u FROM especialidade as u WHERE u.usuario IS NULL");
        verify(em).merge(especialidade);
        verify(em).remove(especialidade);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelEspecialidade> especialidades = new ArrayList<>();
        especialidades.add(new ModelEspecialidade());
        when(query.getResultList()).thenReturn(especialidades);

        List<ModelEspecialidade> resultado = especialidadeDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM especialidade as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(especialidades, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelEspecialidade> resultado = especialidadeDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
