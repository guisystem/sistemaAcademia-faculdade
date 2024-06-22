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

import model.ModelRegistroProfessor;
import model.ModelUsuario;

class RegistroProfessorDAOTeste {

	private EntityManager em;
    private RegistroProfessorDAO registroProfessorDAO;
	
	@BeforeEach
	void iniciarTeste() { 
		em = mock(EntityManager.class);
		registroProfessorDAO = new RegistroProfessorDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor();
		
		ModelRegistroProfessor resultado = registroProfessorDAO.insert(registroProfessor);
		verify(em).persist(registroProfessor);
		
		assertEquals(registroProfessor, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor();
		registroProfessor.setId(1);
		
		ModelRegistroProfessor resultado = registroProfessorDAO.update(registroProfessor);
		verify(em).merge(registroProfessor);
		
		assertEquals(registroProfessor, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor();
		
		ModelRegistroProfessor resultado = registroProfessorDAO.insertOrUpdate(registroProfessor);
		verify(em).persist(registroProfessor);
		
		assertEquals(registroProfessor, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor();
		registroProfessor.setId(0);
		
		ModelRegistroProfessor resultado = registroProfessorDAO.insertOrUpdate(registroProfessor);
		verify(em).persist(registroProfessor);
		
		assertEquals(registroProfessor, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor();
		registroProfessor.setId(1);
		
		ModelRegistroProfessor resultado = registroProfessorDAO.insertOrUpdate(registroProfessor);
		verify(em).merge(registroProfessor);
		
		assertEquals(registroProfessor, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelRegistroProfessor registroProfessor = new ModelRegistroProfessor();
    	registroProfessor.setId(1);

        registroProfessorDAO.delete(registroProfessor);

        verify(em).merge(registroProfessor);
        verify(em).remove(registroProfessor);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelRegistroProfessor> registroProfessores = new ArrayList<>();
        registroProfessores.add(new ModelRegistroProfessor());
        when(query.getResultList()).thenReturn(registroProfessores);

        List<ModelRegistroProfessor> resultado = registroProfessorDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM registroprofessor as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(registroProfessores, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelRegistroProfessor> resultado = registroProfessorDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
