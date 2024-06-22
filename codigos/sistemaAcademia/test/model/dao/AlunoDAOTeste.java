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

import model.ModelAluno;
import model.ModelPlano;
import model.ModelUsuario;
import servico.Criptografar;

class AlunoDAOTeste {

	private EntityManager em;
    private AlunoDAO alunoDAO;
	
	@BeforeEach
	void iniciarTeste() { 
		em = mock(EntityManager.class);
		alunoDAO = new AlunoDAO(em);
	}
	
	@Test
	void testarInsert() {
		ModelAluno aluno = new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmail", "01/01/1999", "01/01/1999", new ModelPlano());
		
		ModelAluno resultado = alunoDAO.insert(aluno);
		verify(em).persist(aluno);
		
		assertEquals(aluno, resultado);
	}
	
	@Test
	void testarUpdate() {
		ModelAluno aluno = new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmail", "01/01/1999", "01/01/1999", new ModelPlano());
		aluno.setId(1);
		
		ModelAluno resultado = alunoDAO.update(aluno);
		verify(em).merge(aluno);
		
		assertEquals(aluno, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Insert() {
		ModelAluno aluno = new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmail", "01/01/1999", "01/01/1999", new ModelPlano());
		
		ModelAluno resultado = alunoDAO.insertOrUpdate(aluno);
		verify(em).persist(aluno);
		
		assertEquals(aluno, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_InsertIdZero() {
		ModelAluno aluno = new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmail", "01/01/1999", "01/01/1999", new ModelPlano());
		aluno.setId(0);
		
		ModelAluno resultado = alunoDAO.insertOrUpdate(aluno);
		verify(em).persist(aluno);
		
		assertEquals(aluno, resultado);
	}
	
	@Test
	void testarInsertOrUpdate_Update() {
		ModelAluno aluno = new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmail", "01/01/1999", "01/01/1999", new ModelPlano());
		aluno.setId(1);
		
		ModelAluno resultado = alunoDAO.insertOrUpdate(aluno);
		verify(em).merge(aluno);
		
		assertEquals(aluno, resultado);
	}
	

    @Test
    void testarDelete() {
    	ModelAluno aluno = new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmail", "01/01/1999", "01/01/1999", new ModelPlano());
        aluno.setId(1);

        alunoDAO.delete(aluno);

        verify(em).merge(aluno);
        verify(em).remove(aluno);
    }
    
    @Test
    void testarSelectAll() {
        ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), anyLong())).thenReturn(query);

        List<ModelAluno> alunos = new ArrayList<>();
        alunos.add(new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmai", "01/01/1999", "01/01/1999", new ModelPlano()));
        when(query.getResultList()).thenReturn(alunos);

        List<ModelAluno> resultado = alunoDAO.selectAll(usuario);

        verify(em).createQuery("SELECT u FROM aluno as u WHERE u.usuario = :pIdUsuario");
        verify(query).setParameter("pIdUsuario", usuario.getId());

        assertEquals(alunos, resultado);
    }
    
    @Test
    void testarSelectPorEmailCpf() {
    	ModelUsuario usuario = new ModelUsuario("Usuário", "Senha");
        usuario.setId(1);

        ModelAluno alunoTela = new ModelAluno("NomeAluno", "123.456.789-01" ,"testeEmail", "01/01/1999", "01/01/1999", new ModelPlano());

        Query query = mock(Query.class);
        when(em.createQuery(anyString())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);

        List<ModelAluno> alunos = new ArrayList<>(); 
        String cpfCriptografado = Criptografar.criptografar("123.456.789-01");
        String emailCriptografado = Criptografar.criptografar("outroEmail");
        ModelAluno alunoExistente = new ModelAluno("OutroAluno", cpfCriptografado, emailCriptografado, "01/01/1999", "01/01/1999", new ModelPlano());
        alunos.add(alunoExistente); 
        when(query.getResultList()).thenReturn(alunos);

        int resultado = alunoDAO.selectPorEmailCpf(usuario, alunoTela);

        verify(em).createQuery("SELECT u FROM aluno as u WHERE u.usuario = :pUsuario");
        verify(query).setParameter("pUsuario", usuario);
       
        assertEquals(1, resultado);


        alunoExistente.setCpf("98765432109");
        alunoTela.setEmail("outroEmail");
        resultado = alunoDAO.selectPorEmailCpf(usuario, alunoTela);
       
        assertEquals(2, resultado);


        alunoExistente.setEmail("novoEmail");
        resultado = alunoDAO.selectPorEmailCpf(usuario, alunoTela);
        
        assertEquals(0, resultado);
    }
    
    @Test
    void testarConsultaVazia() {
        Query query = mock(Query.class);

        when(query.getResultList()).thenThrow(new NoResultException());

        List<ModelAluno> resultado = alunoDAO.consulta(query);

        assertTrue(resultado.isEmpty());
        
    }

}
