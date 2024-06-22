package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import model.ModelPlano;
import model.ModelUsuario;

public class PlanoDAO {

	private final EntityManager em;

	public PlanoDAO(EntityManager em) {
		this.em = em;
	}

	/**
     * Insere um plano dentro do banco de dados
     * @param plano exige que seja passado um objeto do tipo plano
	 * @throws SQLException 
     */
    public ModelPlano insert(ModelPlano plano) {
    	em.persist(plano);
    	return plano;
    	
    }
    
    /**
     * Atualiza um plano no banco de dados
     * @param plano
     * @return plano
     */
    public ModelPlano update(ModelPlano plano){  
    	em.merge(plano);
    	return plano;    

    }
    
    /**
     * Insere ou atualiza um plano do banco de dados
     * @param plano
     * @return plano para o metodo insert ou update
     */
    public ModelPlano insertOrUpdate(ModelPlano plano) {
    	if(plano.getId() == null) {
    		return this.insert(plano);
    	}

    	if(plano.getId() > 0) {
			return this.update(plano);
		}
		
		return this.insert(plano);
		
	}
    
    /**
     * Deleta um plano do banco de dados pelo id do plano passado
     * @param plano
     * @return 
     */
    public void delete(ModelPlano plano){
    	em.merge(plano);
    	em.remove(plano);
    	
    }
    
    /**
     * Deleta os planos do banco de dados em que o usuario(FK) for null
     * @param
     * @return 
     */
	public void deleteForUser() {
		String jpql = "SELECT u FROM plano as u WHERE u.usuario IS NULL";
		Query query = em.createQuery(jpql);
		
		for(ModelPlano planos: consulta(query)) {
    		em.merge(planos);
    		em.remove(planos);
    	}
		
	}
    
	/**
     * Retorna um arraylist com todos os planos do banco de dados do usuario passado
     * @param usuario
     * @return uma lista com todos os planos do banco
     */
    public List<ModelPlano> selectAll(ModelUsuario usuario){
    	String jpql = "SELECT u FROM plano as u WHERE u.usuario = :pIdUsuario";
		Query query = em.createQuery(jpql);
		query.setParameter("pIdUsuario", usuario.getId());
		
		return consulta(query);
		
	}
    
    /**
     * Recebe uma query e retorna uma list com valores em favor da condição determinada na query
     * @param query
     * @return uma lista de planos
     */
	@SuppressWarnings("unchecked")
	List<ModelPlano> consulta(Query query) {
		List<ModelPlano> planos;
		
		try {
			planos = query.getResultList();
		}catch(NoResultException e) {
			planos = new ArrayList<>();
		}
		
		return planos;
	}
}
