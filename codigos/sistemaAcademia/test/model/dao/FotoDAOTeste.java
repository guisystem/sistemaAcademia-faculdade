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

import model.ModelFoto;
import model.ModelUsuario;

class FotoDAOTeste {

	private EntityManager em;
    private FotoDAO fotoDAO;
	
	@BeforeEach
	void iniciarTeste() { 
		em = mock(EntityManager.class);
		fotoDAO = new FotoDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelFoto foto = new ModelFoto();
		
		ModelFoto resultado = fotoDAO.insert(foto);
		verify(em).persist(foto);
		
		assertEquals(foto, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelFoto foto = new ModelFoto();
		foto.setId(1);
		
		ModelFoto resultado = fotoDAO.update(foto);
		verify(em).merge(foto);
		
		assertEquals(foto, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelFoto foto = new ModelFoto();
		
		ModelFoto resultado = fotoDAO.insertOrUpdate(foto);
		verify(em).persist(foto);
		
		assertEquals(foto, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelFoto foto = new ModelFoto();
		foto.setId(0);
		
		ModelFoto resultado = fotoDAO.insertOrUpdate(foto);
		verify(em).persist(foto);
		
		assertEquals(foto, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelFoto foto = new ModelFoto();
		foto.setId(1);
		
		ModelFoto resultado = fotoDAO.insertOrUpdate(foto);
		verify(em).merge(foto);
		
		assertEquals(foto, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelFoto foto = new ModelFoto();
    	foto.setId(1);

        fotoDAO.delete(foto);

        verify(em).merge(foto);
        verify(em).remove(foto);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelFoto> fotos = new ArrayList<>();
        fotos.add(new ModelFoto());
        when(query.getResultList()).thenReturn(fotos);

        List<ModelFoto> resultado = fotoDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM foto as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(fotos, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelFoto> resultado = fotoDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
