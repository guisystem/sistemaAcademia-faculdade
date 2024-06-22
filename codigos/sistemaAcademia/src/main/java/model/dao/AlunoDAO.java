package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelAluno;
import model.ModelUsuario;
import servico.Criptografar;

public class AlunoDAO {

	private final EntityManager em;
	
	public AlunoDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um aluno dentro do banco de dados
     * @param aluno exige que seja passado um objeto do tipo aluno
	 * @throws SQLException 
     */
    public ModelAluno insert(ModelAluno aluno) {
    	em.persist(aluno);
    	return aluno;
    	
    }
    
    /**
     * Atualiza um aluno no banco de dados
     * @param aluno
     * @return aluno
     */
    public ModelAluno update(ModelAluno aluno){  
    	em.merge(aluno);
    	return aluno;    

    }
    
    /**
     * Insere ou atualiza um aluno do banco de dados
     * @param aluno
     * @return aluno para o metodo insert ou update
     */
    public ModelAluno insertOrUpdate(ModelAluno aluno) {
    	
    	if(aluno.getId() > 0) {
			return this.update(aluno);
		}
		
		return this.insert(aluno);
		
	}
    
    /**
     * Deleta um aluno do banco de dados pelo id do aluno passado
     * @param aluno
     * @return 
     */
    public void delete(ModelAluno aluno){
    	em.merge(aluno);
    	em.remove(aluno);
    	
    }
    
    /**
     * Retorna um arraylist com todos os alunos do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos os alunos do banco
     */
    public List<ModelAluno> selectAll(ModelUsuario usuario){
    	String jpql = "SELECT u FROM aluno as u WHERE u.usuario = :pIdUsuario";
    	Query query = em.createQuery(jpql);

    	query.setParameter("pIdUsuario", usuario.getId());
    	
		return consulta(query);
		
	}
    
    /**
     * Busca no banco de dados alunos que tenha o mesmo cpf ou email
     * @param usuario, aluno
     * @return um inteiro
     */
    public int selectPorEmailCpf(ModelUsuario usuario, ModelAluno alunoTela) {
    	String alunosQuery = "SELECT u FROM aluno as u WHERE u.usuario = :pUsuario";
    	Query query = em.createQuery(alunosQuery);
    	
    	query.setParameter("pUsuario", usuario);
    	
    	List<ModelAluno> alunos = consulta(query);
    	
    	alunoTela.setCpf(Criptografar.criptografar(alunoTela.getCpf()));
    	for(ModelAluno aluno: alunos) {
    		if(aluno.getCpf().equals(alunoTela.getCpf())) {
    			return 1;
    		}
    	}
    	
    	alunoTela.setEmail(Criptografar.criptografar(alunoTela.getEmail()));
    	for(ModelAluno aluno: alunos) {
    		if(aluno.getEmail().equals(alunoTela.getEmail())) {
    			return 2;
    		}
    	}
    	
    	alunoTela.setCpf(Criptografar.descriptografar(alunoTela.getCpf()));
    	alunoTela.setEmail(Criptografar.descriptografar(alunoTela.getEmail()));
		return 0;
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de alunos
     */
	@SuppressWarnings("unchecked")
	List<ModelAluno> consulta(Query query) {
		List<ModelAluno> alunos;
		
		try {
			alunos = query.getResultList();
		}catch(NoResultException e) {
			alunos = new ArrayList<>();
		}
		
		return alunos;
		
	}
    
}
