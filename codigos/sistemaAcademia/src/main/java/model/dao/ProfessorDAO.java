package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelProfessor;
import model.ModelUsuario;
import servico.Criptografar;

public class ProfessorDAO {

private final EntityManager em;
	
	public ProfessorDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um professor dentro do banco de dados
     * @param professor exige que seja passado um objeto do tipo professor
	 * @throws SQLException 
     */
    public ModelProfessor insert(ModelProfessor professor) {
    	em.persist(professor);
    	return professor;
    	
    }
    
    /**
     * Atualiza um professor no banco de dados
     * @param professor
     * @return professor
     */
    public ModelProfessor update(ModelProfessor professor){  
    	em.merge(professor);
    	return professor;    

    }
    
    /**
     * Insere ou atualiza um professor do banco de dados
     * @param professor
     * @return professor para o metodo insert ou update
     */
    public ModelProfessor insertOrUpdate(ModelProfessor professor) {
    	
		if(professor.getId() > 0) {
			return this.update(professor);
		}
		
		return this.insert(professor);
		
	}
    
    /**
     * Deleta um professor do banco de dados pelo id do professor passado
     * @param professor
     * @return 
     */
    public void delete(ModelProfessor professor){
    	em.merge(professor);
    	em.remove(professor);
    	
    }
    
    /**
     * Retorna um arraylist com todos os professores do banco de dados do usuario passado
     * @param professor
     * @return uma lista com todos os professores do banco
     */
    public List<ModelProfessor> selectAll(ModelUsuario usuario){
    	String jpql = "SELECT u FROM professor as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Busca no banco de dados professores que tenha o mesmo cpf ou email
     * @param usuario, professor
     * @return um inteiro
     */
	public int selectPorEmailCpf(ModelUsuario usuario, ModelProfessor professorTela) {
		String alunosQuery = "SELECT u FROM professor as u WHERE u.usuario = :pUsuario";
    	Query query = em.createQuery(alunosQuery);
    	
    	query.setParameter("pUsuario", usuario);
    	
    	List<ModelProfessor> professores = consulta(query);
    	
    	professorTela.setCpf(Criptografar.criptografar(professorTela.getCpf()));
    	for(ModelProfessor professor: professores) {
    		if(professor.getCpf().equals(professorTela.getCpf())) {
    			return 1;
    		}
    	}
    	
    	professorTela.setEmail(Criptografar.criptografar(professorTela.getEmail()));
    	for(ModelProfessor professor: professores) {
    		if(professor.getEmail().equals(professorTela.getEmail())) {
    			return 2;
    		}
    	}
    	
    	professorTela.setCpf(Criptografar.descriptografar(professorTela.getCpf()));
    	professorTela.setEmail(Criptografar.descriptografar(professorTela.getEmail()));
		return 0;
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de professores
     */
	@SuppressWarnings("unchecked")
	List<ModelProfessor> consulta(Query query) {
		List<ModelProfessor> professores;
		
		try {
			professores = query.getResultList();
		}catch(NoResultException e) {
			professores = new ArrayList<>();
		}
		
		return professores;
		
	}

}
