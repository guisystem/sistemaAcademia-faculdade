package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelModalidade;
import model.ModelUsuario;

public class ModalidadeDAO {
	
	private final EntityManager em;

	public ModalidadeDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere uma modalidade dentro do banco de dados
     * @param modalidade exige que seja passado um objeto do tipo modalidade
	 * @throws SQLException 
     */
    public ModelModalidade insert(ModelModalidade modalidade) {
    	em.persist(modalidade);
    	return modalidade;
    	
    }
    
    /**
     * Atualiza uma modalidade no banco de dados
     * @param modalidade
     * @return modalidade
     */
    public ModelModalidade update(ModelModalidade modalidade){  
    	em.merge(modalidade);
    	return modalidade;    

    }
    
    /**
     * Insere ou atualiza uma modalidade do banco de dados
     * @param modalidade
     * @return modalidade para o metodo insert ou update
     */
    public ModelModalidade insertOrUpdate(ModelModalidade modalidade) {
    	if(modalidade.getId() == null) {
    		return this.insert(modalidade);
    		
    	}
    	
		if(modalidade.getId() > 0) {
			return this.update(modalidade);
		}
		
		return this.insert(modalidade);
		
	}
    
    /**
     * Deleta uma modalidade do banco de dados pelo id da modalidade passado
     * @param modalidade
     * @return 
     */
    public void delete(ModelModalidade modalidade){
    	em.merge(modalidade);
    	em.remove(modalidade);
    	
    }

    /**
     * Deleta as modalidades do banco de dados em que o usuario(FK) for null
     * @param
     * @return 
     */
	public void deleteForUser() {
		String jpql = "SELECT u FROM modalidade as u WHERE u.usuario IS NULL";
		Query query = em.createQuery(jpql);
		
		for(ModelModalidade modalidades: consulta(query)) {
    		em.merge(modalidades);
    		em.remove(modalidades);
    	}
		
	}
    
	/**
     * Retorna um arraylist com todos as modalidades do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos as modalidades do banco
     */
    public List<ModelModalidade> selectAll(ModelUsuario usuario){
		String jpql = "SELECT u FROM modalidade as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de modalidades
     */
	@SuppressWarnings("unchecked")
	List<ModelModalidade> consulta(Query query) {
		List<ModelModalidade> modalidades;
		
		try {
			modalidades = query.getResultList();
		}catch(NoResultException e) {
			modalidades = new ArrayList<>();
		}
		
		return modalidades;
	}

}
