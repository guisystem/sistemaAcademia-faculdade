package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelRegistroAluno;
import model.ModelUsuario;

public class RegistroAlunoDAO {

	private final EntityManager em;
	
	public RegistroAlunoDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um registro de aluno dentro do banco de dados
     * @param registro de aluno exige que seja passado um objeto do tipo registro de aluno
	 * @throws SQLException 
     */
    public ModelRegistroAluno insert(ModelRegistroAluno registroAluno) {
    	em.persist(registroAluno);
    	return registroAluno;
    	
    }
    
    /**
     * Atualiza um registro de aluno no banco de dados
     * @param registro de aluno
     * @return registro de aluno
     */
    public ModelRegistroAluno update(ModelRegistroAluno registroAluno){  
    	em.merge(registroAluno);
    	return registroAluno;    

    }
    
    /**
     * Insere ou atualiza um registro de aluno do banco de dados
     * @param registro de aluno
     * @return registro de aluno para o metodo insert ou update
     */
    public ModelRegistroAluno insertOrUpdate(ModelRegistroAluno registroAluno) {
    	
		if(registroAluno.getId() > 0) {
			return this.update(registroAluno);
		}
		
		return this.insert(registroAluno);
		
	}
    
    /**
     * Deleta um registro de aluno do banco de dados pelo id do registro de aluno passado
     * @param registro de aluno
     * @return 
     */
    public void delete(ModelRegistroAluno registroAluno){
    	em.merge(registroAluno);
    	em.remove(registroAluno);
    	
    }
    
    /**
     * Retorna um arraylist com todos os registros de alunos do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos os registros de alunos do banco
     */
    public List<ModelRegistroAluno> selectAll(ModelUsuario usuario){
    	String jpql = "SELECT u FROM registroaluno as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de registros de alunos
     */  
	@SuppressWarnings("unchecked")
	List<ModelRegistroAluno> consulta(Query query) {
		List<ModelRegistroAluno> registroAlunos;
		
		try {
			registroAlunos = query.getResultList();
		}catch(NoResultException e) {
			registroAlunos = new ArrayList<>();
		}
		
		return registroAlunos;
		
	}
}
