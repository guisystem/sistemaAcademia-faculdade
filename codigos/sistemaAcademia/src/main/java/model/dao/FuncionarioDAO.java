package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelFuncionario;
import model.ModelUsuario;

public class FuncionarioDAO {

	private final EntityManager em;
	
	public FuncionarioDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um funcion�rio dentro do banco de dados
     * @param funcion�rio exige que seja passado um objeto do tipo funcion�rio
	 * @throws SQLException 
     */
    public ModelFuncionario insert(ModelFuncionario funcionario) {
    	em.persist(funcionario);
    	return funcionario;
    	
    }
    
    /**
     * Atualiza um funcion�rio no banco de dados
     * @param funcion�rio
     * @return funcion�rio
     */
    public ModelFuncionario update(ModelFuncionario funcionario){  
    	em.merge(funcionario);
    	return funcionario;    

    }
    
    /**
     * Insere ou atualiza um funcion�rio do banco de dados
     * @param funcion�rio
     * @return funcion�rio para o metodo insert ou update
     */
    public ModelFuncionario insertOrUpdate(ModelFuncionario funcionario) {
    	if(funcionario.getId() == null) {
    		return this.insert(funcionario);
    		
    	}
    	
		if(funcionario.getId() > 0) {
			return this.update(funcionario);
		}
		
		return this.insert(funcionario);
		
	}
    
    /**
     * Deleta um funcion�rio do banco de dados pelo id do funcion�rio passado
     * @param funcion�rio
     * @return 
     */
    public void delete(ModelFuncionario funcionario){
    	em.merge(funcionario);
    	em.remove(funcionario);
    	
    }

    /**
     * Deleta os funcion�rios do banco de dados em que o usuario(FK) for null
     * @param
     * @return 
     */
	public void deleteForUser() {
		String jpql = "SELECT u FROM funcionario as u WHERE u.usuario IS NULL";
		Query query = em.createQuery(jpql);
		
		for(ModelFuncionario funcionarios: consulta(query)) {
    		em.merge(funcionarios);
    		em.remove(funcionarios);
    	}
		
	}
    
	/**
     * Retorna um arraylist com todos os funcion�rios do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos os funcion�rios do banco
     */
    public List<ModelFuncionario> selectAll(ModelUsuario usuario){
		String jpql = "SELECT u FROM funcionario as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condi��o determinada na query
     * @param query
     * @return uma lista de funcion�rios
     */
	@SuppressWarnings("unchecked")
	List<ModelFuncionario> consulta(Query query) {
		List<ModelFuncionario> funcionarios;
		
		try {
			funcionarios = query.getResultList();
		}catch(NoResultException e) {
			funcionarios = new ArrayList<>();
		}
		
		return funcionarios;
	}

}
