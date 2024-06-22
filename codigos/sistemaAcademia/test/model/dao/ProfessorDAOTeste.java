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
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.ModelEspecialidade;
import model.ModelProfessor;
import model.ModelUsuario;
import servico.Criptografar;

class ProfessorDAOTeste {

	private EntityManager em;
    private ProfessorDAO professorDAO;
	
	@BeforeEach
	void iniciarTeste() { 
		em = mock(EntityManager.class);
		professorDAO = new ProfessorDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelProfessor professor = new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, 
				new ModelEspecialidade());
		
		ModelProfessor resultado = professorDAO.insert(professor);
		verify(em).persist(professor);
		
		assertEquals(professor, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelProfessor professor = new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, 
				new ModelEspecialidade());
		professor.setId(1);

		ModelProfessor resultado = professorDAO.update(professor);
		verify(em).merge(professor);
		
		assertEquals(professor, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelProfessor professor = new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, 
				new ModelEspecialidade());
		
		ModelProfessor resultado = professorDAO.insertOrUpdate(professor);
		verify(em).persist(professor);
		
		assertEquals(professor, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelProfessor professor = new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, 
				new ModelEspecialidade());
		professor.setId(0);
		
		ModelProfessor resultado = professorDAO.insertOrUpdate(professor);
		verify(em).persist(professor);
		
		assertEquals(professor, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelProfessor professor = new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, 
				new ModelEspecialidade());
		professor.setId(1);

		ModelProfessor resultado = professorDAO.insertOrUpdate(professor);
		verify(em).merge(professor);
		
		assertEquals(professor, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelProfessor professor = new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, 
    			new ModelEspecialidade());
		professor.setId(1);

        professorDAO.delete(professor);

        verify(em).merge(professor);
        verify(em).remove(professor);
    }
    
    @Test
    void testarSelectPorEmailCpf() {
    	ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        ModelProfessor professorTela = new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, 
        		new ModelEspecialidade());

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);

        List<ModelProfessor> professores = new ArrayList<>();
        String cpfCriptografado = Criptografar.criptografar("123.456.789-01");
        String emailCriptografado = Criptografar.criptografar("outroEmail");
        ModelProfessor professorExistente = new ModelProfessor("OutroProfessor", cpfCriptografado, emailCriptografado, "00:00", "00:00", 0, 
        		new ModelEspecialidade());
        professores.add(professorExistente);
        when(query.getResultList()).thenReturn(professores);

        int resultado = professorDAO.selectPorEmailCpf(usuario, professorTela);

        verify(em).createQuery("SELECT u FROM professor as u WHERE u.usuario = :pUsuario");
        verify(query).setParameter("pUsuario", usuario);
       
        assertEquals(1, resultado);


        professorExistente.setCpf("98765432109");
        professorTela.setEmail("outroEmail");
        resultado = professorDAO.selectPorEmailCpf(usuario, professorTela);
       
        assertEquals(2, resultado);


        professorExistente.setEmail("novoEmail");
        resultado = professorDAO.selectPorEmailCpf(usuario, professorTela);
        
        assertEquals(0, resultado);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelProfessor> professores = new ArrayList<>();
        professores.add(new ModelProfessor("NomeProfessor" ,"123.456.789-01", "testeEmail", "00:00", "00:00", 0, new ModelEspecialidade()));
        when(query.getResultList()).thenReturn(professores);

        List<ModelProfessor> resultado = professorDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM professor as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(professores, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelProfessor> resultado = professorDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
